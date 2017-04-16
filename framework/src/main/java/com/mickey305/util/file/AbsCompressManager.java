package com.mickey305.util.file;

import com.mickey305.util.Log;
import com.mickey305.util.file.exception.model.ExceptionData;
import com.mickey305.util.file.exception.model.ExceptionResultSet;
import com.mickey305.util.file.exception.FilePathException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.mickey305.util.file.StringUtils.pickFileNameAndSuffix;
import static com.mickey305.util.file.StringUtils.createUniqueTime;

import static com.mickey305.util.file.FileCheckMethods.FileType;
import static com.mickey305.util.file.FileCheckMethods.chkFileHolder;
import static com.mickey305.util.file.FileCheckMethods.chkDirHolder;
import static com.mickey305.util.file.exception.model.ExceptionValues.*;

/**
 * Created by @author K.Misaki on 2017/04/02.
 */
public abstract class AbsCompressManager<I extends InputStream, O extends OutputStream> implements StreamIO<I, O> {

    private ConflictOutputFileListener listener;

//    public ConflictOutputFileListener getListener() {
//        return listener;
//    }
//
//    public void setListener(ConflictOutputFileListener listener) {
//        this.listener = listener;
//    }

    /**
     *
     * @param inFilePaths
     * @param outFilePath
     * @return
     * @throws FilePathException
     */
    public boolean compress(Collection<String> inFilePaths, String outFilePath) throws FilePathException {
        File outFile = new File(outFilePath);

        chkFileHolder(outFile, FileType.OUT, exception -> {
            // チェックエラー時の処理
            if (exception instanceof FilePathException) {
                if (((FilePathException) exception).getResultCode() == CD_EXISTENCE_FILE_PATH) {
                    // 出力先に同名のファイルが既に存在する場合の処理
                    StringBuilder outFileNameSb = new StringBuilder();
                    StringBuilder outFileSuffixSb = new StringBuilder();

                    // ファイル名と拡張子を抽出する
                    pickFileNameAndSuffix(outFilePath, outFileNameSb, outFileSuffixSb);

                    String outFileName = outFileNameSb.toString();
                    String outFileSuffix = outFileSuffixSb.toString();

                    String originalFileName = outFileName + "." + outFileSuffix;

                    // ファイル名の末尾にユニーク情報（日時）を付与する
                    outFileName += "_" + createUniqueTime("yyyy-MM-dd-HH-mm-ss-SSS");

                    String createdFileName = outFileName + "." + outFileSuffix;

                    compress(inFilePaths, outFilePath.replace(originalFileName, createdFileName));
                }
            }
        });

        try (O os = this.createOutputStream(outFile)) {
            for (String inFilePath : inFilePaths) {
                File inFile = new File(inFilePath);

                chkFileHolder(inFile, FileType.IN, null);

                this.compress(inFile, inFile.getName(), os);
            }
        } catch (IOException e) {
            Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
            Log.e(rs.get(CD_COMPRESS).getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param inFilePath
     * @param outFilePath
     * @return
     * @throws FilePathException
     */
    public boolean compress(String inFilePath, String outFilePath) throws FilePathException {
        Collection<String> inFilePaths = new ArrayList<>();
        inFilePaths.add(inFilePath);
        return this.compress(inFilePaths, outFilePath);
    }

    /**
     *
     * @param inFilePath
     * @return
     * @throws FilePathException
     */
    public boolean compress(String inFilePath) throws FilePathException {
        Collection<String> inFilePaths = new ArrayList<>();
        inFilePaths.add(inFilePath);
        return this.compress(inFilePaths, new File(inFilePath).getParent());
    }

    /**
     *
     * @param inDirPath
     * @return
     * @throws FilePathException
     */
    public boolean compressDir(String inDirPath) throws FilePathException {
        return this.compressDir(inDirPath, new File(inDirPath).getParent());
    }

    /**
     *
     * @param inDirPath
     * @param outFilePath
     * @return
     * @throws FilePathException
     */
    public boolean compressDir(String inDirPath, String outFilePath) throws FilePathException {
        File outFile = new File(outFilePath);
        File inDir = new File(inDirPath);

        chkDirHolder(inDir, FileType.IN, null);
        chkFileHolder(outFile, FileType.OUT, exception -> {
            // チェックエラー時の処理
            if (exception instanceof FilePathException) {
                if (((FilePathException) exception).getResultCode() == CD_EXISTENCE_FILE_PATH) {
                    // 出力先に同名のファイルが既に存在する場合の処理
                    StringBuilder outFileNameSb = new StringBuilder();
                    StringBuilder outFileSuffixSb = new StringBuilder();

                    // ファイル名と拡張子を抽出する
                    pickFileNameAndSuffix(outFilePath, outFileNameSb, outFileSuffixSb);

                    String outFileName = outFileNameSb.toString();
                    String outFileSuffix = outFileSuffixSb.toString();

                    String originalFileName = outFileName + "." + outFileSuffix;

                    // ファイル名の末尾にユニーク情報（日時）を付与する
                    outFileName += "_" + createUniqueTime("yyyy-MM-dd-HH-mm-ss-SSS");

                    String createdFileName = outFileName + "." + outFileSuffix;

                    compressDir(inDirPath, outFilePath.replace(originalFileName, createdFileName));
                }
            }
        });

        try (O os = this.createOutputStream(outFile)) {
            this.compress(inDir, outFile, os, inDir);
        } catch (FileNotFoundException e) {
            Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
            Log.e(rs.get(CD_OUT_FILE_PATH).getMessage());
            return false;
        } catch (IOException e) {
            Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
            Log.e(rs.get(CD_COMPRESS).getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param inDir
     * @param outFile
     * @param os
     * @param inputBaseDir
     * @return
     */
    private O compress(File inDir, File outFile, O os, final File inputBaseDir) {
        File[] inFiles = inDir.listFiles();
        assert inFiles != null;
        for (File inFile : inFiles) {
            if (inFile.isDirectory()) {
                os = this.compress(inFile, outFile, os, inputBaseDir);
            } else if (!inFile.getAbsoluteFile().equals(outFile)) {
                String inFileName = inFile.getAbsolutePath().replace(inputBaseDir.getParent(), "").substring(1);
                os = this.compress(inFile, inFileName, os);
            } else {
                // I/O resource loop(check: outFilePath is different from inputBaseDir)
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                Log.e(rs.get(CD_COMPRESS).getMessage());
            }
        }
        return os;
    }

    /**
     *
     * @param inFile
     * @param inAbsoluteFileName
     * @param os
     * @return
     */
    private O compress(File inFile, String inAbsoluteFileName, O os) {
        try (I is = this.createInputStream(inFile)) {
            os = this.compress(is, inAbsoluteFileName, os);
        } catch (FileNotFoundException e) {
            Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
            Log.e(rs.get(CD_IN_FILE_PATH).getMessage());
        } catch (IOException e) {
            Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
            Log.e(rs.get(CD_COMPRESS).getMessage());
        }
        return os;
    }

    /**
     *
     * @param is
     * @param inAbsoluteFileName
     * @param os
     * @return
     * @throws IOException
     */
    abstract protected O compress(I is, String inAbsoluteFileName, O os) throws IOException;

    /**
     * 入力ストリームを生成する
     * @param inFile 入力ストリームにバインドするファイル
     * @return 入力ストリーム
     * @throws IOException 入出力例外
     */
    @Nonnull @Override abstract public I createInputStream(@Nullable File inFile) throws IOException;

    /**
     * 出力ストリームを生成する
     * @param outFile 出力ストリームにバインドするファイル
     * @return 出力ストリーム
     * @throws IOException 入出力例外
     */
    @Nonnull @Override abstract public O createOutputStream(@Nullable File outFile) throws IOException;
}

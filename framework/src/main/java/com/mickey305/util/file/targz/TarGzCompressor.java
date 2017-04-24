package com.mickey305.util.file.targz;

import com.mickey305.util.Log;
import com.mickey305.util.file.exception.FilePathException;
import java.io.*;
import java.util.Collection;

import static com.mickey305.util.file.StringUtils.createUniqueTime;

/**
 * Created by K.Misaki on 2017/04/02.
 *
 */
public class TarGzCompressor {
    public static final String TAG = TarGzCompressor.class.getSimpleName();

    private TarArchiver archiver;
    private GzCompressor compressor;

    private TarGzCompressor() {
        archiver = TarArchiver.getInstance();
        compressor = GzCompressor.getInstance();
    }

    public static TarGzCompressor getInstance() {
        return TarGzCompressorHolder.INSTANCE;
    }

    private static class TarGzCompressorHolder {
        private static final TarGzCompressor INSTANCE = new TarGzCompressor();
    }

    /**
     * 圧縮する
     * @param inFilePaths 入力ファイルパス名
     * @param outFilePath 出力ファイルパス名
     * @return 処理結果
     * @throws FilePathException ファイルパス例外
     */
    public boolean compress(Collection<String> inFilePaths, String outFilePath) throws FilePathException {
        String tmpFileName = "archiveTemporaryOf" + TAG + createUniqueTime("yyyyMMddHHmmssSSS") + ".tmp";
        String tmpFilePath = System.getProperty("java.io.tmpdir") + tmpFileName;
        File tmpFile = new File(tmpFilePath);

        Log.d(TarGzCompressor.class, "compress", "temporary file = " + tmpFilePath);

        boolean compressStatusOk = false;
        try {
            boolean archiveStatusOk = archiver.compress(inFilePaths, tmpFilePath);

            if (archiveStatusOk && tmpFile.exists()) {
                compressStatusOk = compressor.compress(tmpFilePath, outFilePath);
            }
        } catch (FilePathException e) {
            throw e;
        } finally {
            if (tmpFile.delete())
                Log.d(TarGzCompressor.class, "compress", "temporary file was deleted: " + tmpFilePath);
            else
                Log.d(TarGzCompressor.class, "compress", "temporary file wasn't deleted: " + tmpFilePath);
        }

        return compressStatusOk;
    }

    /**
     * 圧縮する
     * @param inFilePath 入力ファイルパス名
     * @param outFilePath 出力ファイルパス名
     * @return 処理結果
     * @throws FilePathException ファイルパス例外
     */
    public boolean compress(String inFilePath, String outFilePath) throws FilePathException {
        String tmpFileName = "archiveTemporaryOf" + TAG + createUniqueTime("yyyyMMddHHmmssSSS") + ".tmp";
        String tmpFilePath = System.getProperty("java.io.tmpdir") + tmpFileName;
        File tmpFile = new File(tmpFilePath);

        Log.d(TarGzCompressor.class, "compress", "temporary file = " + tmpFilePath);

        boolean compressStatusOk = false;
        try {
            boolean archiveStatusOk = archiver.compress(inFilePath, tmpFilePath);

            if (archiveStatusOk && tmpFile.exists()) {
                compressStatusOk = compressor.compress(tmpFilePath, outFilePath);
            }
        } catch (FilePathException e) {
            throw e;
        } finally {
            if (tmpFile.delete())
                Log.d(TarGzCompressor.class, "compress", "temporary file was deleted: " + tmpFilePath);
            else
                Log.d(TarGzCompressor.class, "compress", "temporary file wasn't deleted: " + tmpFilePath);
        }

        return compressStatusOk;
    }

    /**
     * 圧縮する
     * @param inDirPath 入力フォルダパス名
     * @param outFilePath 出力ファイルパス名
     * @return 処理結果
     * @throws FilePathException ファイルパス例外
     */
    public boolean compressDir(String inDirPath, String outFilePath) throws FilePathException {
        String tmpFileName = "archiveTemporaryOf" + TAG + createUniqueTime("yyyyMMddHHmmssSSS") + ".tmp";
        String tmpFilePath = System.getProperty("java.io.tmpdir") + tmpFileName;
        File tmpFile = new File(tmpFilePath);

        Log.d(TarGzCompressor.class, "compress", "temporary file = " + tmpFilePath);

        boolean compressStatusOk = false;
        try {
            boolean archiveStatusOk = archiver.compressDir(inDirPath, tmpFilePath);

            if (archiveStatusOk && tmpFile.exists()) {
                compressStatusOk = compressor.compress(tmpFilePath, outFilePath);
            }
        } catch (FilePathException e) {
            throw e;
        } finally {
            if (tmpFile.delete())
                Log.d(TarGzCompressor.class, "compress", "temporary file was deleted: " + tmpFilePath);
            else
                Log.d(TarGzCompressor.class, "compress", "temporary file wasn't deleted: " + tmpFilePath);
        }

        return compressStatusOk;
    }

    /**
     * 圧縮する
     * @param inFilePath 入力ファイルパス名
     * @return 処理結果
     * @throws FilePathException ファイルパス例外
     */
    public boolean compress(String inFilePath) throws FilePathException {
        String tmpFileName = "archiveTemporaryOf" + TAG + createUniqueTime("yyyyMMddHHmmssSSS") + ".tmp";
        String tmpFilePath = System.getProperty("java.io.tmpdir") + tmpFileName;
        File tmpFile = new File(tmpFilePath);

        Log.d(TarGzCompressor.class, "compress", "temporary file = " + tmpFilePath);

        boolean compressStatusOk = false;
        try {
            boolean archiveStatusOk = archiver.compress(inFilePath, tmpFilePath);

            if (archiveStatusOk && tmpFile.exists()) {
                compressStatusOk = compressor.compress(tmpFilePath, new File(inFilePath).getParent());
            }
        } catch (FilePathException e) {
            throw e;
        } finally {
            if (tmpFile.delete())
                Log.d(TarGzCompressor.class, "compress", "temporary file was deleted: " + tmpFilePath);
            else
                Log.d(TarGzCompressor.class, "compress", "temporary file wasn't deleted: " + tmpFilePath);
        }

        return compressStatusOk;
    }

    /**
     * 圧縮する
     * @param inDirPath 入力フォルダパス名
     * @return 処理結果
     * @throws FilePathException ファイルパス例外
     */
    public boolean compressDir(String inDirPath) throws FilePathException {
        String tmpFileName = "archiveTemporaryOf" + TAG + createUniqueTime("yyyyMMddHHmmssSSS") + ".tmp";
        String tmpFilePath = System.getProperty("java.io.tmpdir") + tmpFileName;
        File tmpFile = new File(tmpFilePath);

        Log.d(TarGzCompressor.class, "compress", "temporary file = " + tmpFilePath);

        boolean compressStatusOk = false;
        try {
            boolean archiveStatusOk = archiver.compressDir(inDirPath, tmpFilePath);

            if (archiveStatusOk && tmpFile.exists()) {
                compressStatusOk = compressor.compress(tmpFilePath, new File(inDirPath).getParent());
            }
        } catch (FilePathException e) {
            throw e;
        } finally {
            if (tmpFile.delete())
                Log.d(TarGzCompressor.class, "compress", "temporary file was deleted: " + tmpFilePath);
            else
                Log.d(TarGzCompressor.class, "compress", "temporary file wasn't deleted: " + tmpFilePath);
        }

        return compressStatusOk;
    }
}

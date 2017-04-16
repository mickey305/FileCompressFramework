package com.mickey305.util.file.zip;

import com.mickey305.util.Log;
import com.mickey305.util.file.*;
import com.mickey305.util.file.exception.model.ExceptionData;
import com.mickey305.util.file.exception.model.ExceptionResultSet;
import com.mickey305.util.file.exception.FilePathException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.mickey305.util.file.FileCheckMethods.*;
import static com.mickey305.util.file.StringUtils.createUniqueTime;
import static com.mickey305.util.file.StringUtils.pickFileNameAndSuffix;
import static com.mickey305.util.file.exception.model.ExceptionValues.CD_DECOMPRESS;
import static com.mickey305.util.file.exception.model.ExceptionValues.CD_IN_FILE_PATH;
import static com.mickey305.util.file.exception.model.ExceptionValues.CD_NOT_EXISTENCE_DIR_PATH;

/**
 * Created by K.Misaki on 2017/04/02.
 */
public class ZipDeCompressor implements StreamIO<ZipInputStream, BufferedOutputStream> {
    private static final int DEFAULT_BUFFER_POOL_SIZE = 2^10;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final ZipDeCompressor instance = new ZipDeCompressor();

    private String encoding;
    private ConflictOutputFileListener listener;

    private ZipDeCompressor() {}

    public static ZipDeCompressor getInstance() {
        return instance;
    }

//    public ConflictOutputFileListener getListener() {
//        return listener;
//    }
//
//    public void setListener(ConflictOutputFileListener listener) {
//        this.listener = listener;
//    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     *
     * @param inFilePaths
     * @param outDirPath
     * @return
     * @throws FilePathException
     */
    public boolean decompress(Collection<String> inFilePaths, String outDirPath) throws FilePathException {
        final File outDir = new File(outDirPath);

        chkDirHolder(outDir, FileType.OUT, exception -> {
            // チェックエラー時の処理
            if (exception instanceof FilePathException) {
                if (((FilePathException) exception).getResultCode() == CD_NOT_EXISTENCE_DIR_PATH) {
                    if (outDir.mkdirs())
                        Log.d(ZipDeCompressor.class, "decompress", "Created directory: " + outDir.getAbsolutePath());
                    else
                        Log.d(ZipDeCompressor.class, "decompress", "Couldn't create directory: " + outDir.getAbsolutePath());
                }
            }
        });

        ZipEntry zipEntry;

        for (String inFilePath : inFilePaths) {
            File inFile = new File(inFilePath);

            chkFileHolder(inFile, FileType.IN, null);

            File outBaseMedia = null;
            boolean changedOutputName = true;
            String uniqueTime = createUniqueTime("yyyy-MM-dd-HH-mm-ss-SSS");

            try (ZipInputStream is = this.createInputStream(inFile)) {
                while ((zipEntry = is.getNextEntry()) != null) {
                    String outFilePath = outDir + File.separator + zipEntry.getName();
                    outFilePath = validateFileName(new File(outFilePath), outDir, null);

                    if (outBaseMedia == null) {
                        outBaseMedia = new File(
                                outDir + File.separator + zipEntry.getName().split(File.separator, -1)[0]);
                    }

                    if (outBaseMedia.exists() && changedOutputName) {
                        String entryPath = outBaseMedia.getCanonicalPath();
                        if (entryPath.contains(".")) {
                            // 出力先に同名のファイルが既に存在する場合の処理
                            StringBuilder outFileNameSb = new StringBuilder();
                            StringBuilder outFileSuffixSb = new StringBuilder();

                            // ファイル名と拡張子を抽出する
                            pickFileNameAndSuffix(entryPath, outFileNameSb, outFileSuffixSb);

                            String outFileName = outFileNameSb.toString();
                            String outFileSuffix = outFileSuffixSb.toString();

                            String originalFileName = outFileName + "." + outFileSuffix;

                            // ファイル名の末尾にユニーク情報（日時）を付与する
                            outFileName += "_" + uniqueTime;

                            String createdFileName = outFileName + "." + outFileSuffix;

                            outFilePath = outFilePath.replace(originalFileName, createdFileName);
                        } else {
                            // ファイル名、フォルダ名の末尾にユニーク情報（日時）を付与する
                            outFilePath = outFilePath.replace(entryPath, entryPath + "_" + uniqueTime);
                        }
                        changedOutputName = true;
                    } else {
                        changedOutputName = false;
                    }

                    final File outFile = new File(outFilePath);

                    if (zipEntry.isDirectory()) {
                        if (outFile.mkdirs())
                            Log.d(ZipDeCompressor.class, "decompress", "Created directory: " + outFile.getAbsolutePath());
                        else
                            Log.d(ZipDeCompressor.class, "decompress", "Couldn't create directory: " + outFile.getAbsolutePath());
                        continue;
                    }

                    if (!outFile.getParentFile().exists()) {
                        if (outFile.getParentFile().mkdirs())
                            Log.d(ZipDeCompressor.class, "decompress", "Created directory: " + outFile.getParentFile().getAbsolutePath());
                        else
                            Log.d(ZipDeCompressor.class, "decompress", "Couldn't create directory: " + outFile.getParentFile().getAbsolutePath());
                    }

                    try (BufferedOutputStream os = this.createOutputStream(outFile)) {
                        this.decompress(is, os);
                    } catch (IOException e) {
                        Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                        Log.e(rs.get(CD_DECOMPRESS).getMessage());
                        Log.e(e.toString());
                    }
                }
            } catch (FileNotFoundException e) {
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                Log.e(rs.get(CD_IN_FILE_PATH).getMessage());
                Log.e(e.toString());
                return false;
            } catch (IOException e) {
                Map<Integer, ExceptionData> rs = ExceptionResultSet.getInstance().getResultSet();
                Log.e(rs.get(CD_DECOMPRESS).getMessage());
                Log.e(e.toString());
                return false;
            }
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
    public boolean decompress(String inFilePath, String outFilePath) throws FilePathException {
        Collection<String> inFilePaths = new ArrayList<>();
        inFilePaths.add(inFilePath);
        return this.decompress(inFilePaths, outFilePath);
    }

    /**
     *
     * @param inFilePath
     * @return
     * @throws FilePathException
     */
    public boolean decompress(String inFilePath) throws FilePathException {
        Collection<String> inFilePaths = new ArrayList<>();
        inFilePaths.add(inFilePath);
        return this.decompress(inFilePaths, new File(inFilePath).getParent());
    }

    /**
     *
     * @param zis
     * @param bos
     * @return
     * @throws IOException
     * @throws FilePathException
     */
    private BufferedOutputStream decompress(ZipInputStream zis, BufferedOutputStream bos) throws IOException, FilePathException {
        int readSize;
        byte buffer[] = new byte[DEFAULT_BUFFER_POOL_SIZE];
        while ((readSize = zis.read(buffer, 0, DEFAULT_BUFFER_POOL_SIZE)) != -1) {
            bos.write(buffer, 0, readSize);
        }
        zis.closeEntry();
        return bos;
    }

    /**
     *
     * @param inFile
     * @return
     * @throws FileNotFoundException
     */
    @Nonnull
    @Override
    public ZipInputStream createInputStream(@Nullable File inFile) throws FileNotFoundException {
        assert inFile != null;
        FileInputStream fis = new FileInputStream(inFile);
        String charset = (getEncoding() == null)
                ? DEFAULT_ENCODING
                : getEncoding();
        return new ZipInputStream(new BufferedInputStream(fis), Charset.forName(charset));
    }

    /**
     *
     * @param outFile
     * @return
     * @throws FileNotFoundException
     */
    @Nonnull
    @Override
    public BufferedOutputStream createOutputStream(@Nullable File outFile) throws FileNotFoundException {
        assert outFile != null;
        FileOutputStream fos = new FileOutputStream(outFile);
        return new BufferedOutputStream(fos);
    }
}

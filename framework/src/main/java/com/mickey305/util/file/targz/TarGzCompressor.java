package com.mickey305.util.file.targz;

import com.mickey305.util.Log;
import com.mickey305.util.file.exception.FilePathException;
import java.io.*;
import java.util.Collection;

import static com.mickey305.util.file.StringUtils.createUniqueTime;

/**
 * Created by K.Misaki on 2017/04/02.
 */
public class TarGzCompressor {
    public static final String TAG = TarGzCompressor.class.getSimpleName();
    private static final TarGzCompressor instance = new TarGzCompressor();

    private TarArchiver archiver;
    private GzCompressor compressor;

    private TarGzCompressor() {
        archiver = TarArchiver.getInstance();
        compressor = GzCompressor.getInstance();
    }

    public static TarGzCompressor getInstance() {
        return instance;
    }

    /**
     *
     * @param inFilePaths
     * @param outFilePath
     * @return
     * @throws FilePathException
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
     *
     * @param inFilePath
     * @param outFilePath
     * @return
     * @throws FilePathException
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
     *
     * @param inDirPath
     * @param outFilePath
     * @return
     * @throws FilePathException
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
     *
     * @param inFilePath
     * @return
     * @throws FilePathException
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
     *
     * @param inDirPath
     * @return
     * @throws FilePathException
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

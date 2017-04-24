package com.mickey305.util.file.targz;

import com.mickey305.util.file.AbsCompressManager;
import com.mickey305.util.file.exception.FilePathException;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.Collection;

/**
 * Created by K.Misaki on 2017/04/02.
 */
public class GzCompressor extends AbsCompressManager<BufferedInputStream, GzipCompressorOutputStream> {
    private static final int DEFAULT_BUFFER_POOL_SIZE = 2^10;

    private GzCompressor() {}

    public static GzCompressor getInstance() {
        return GzCompressorHolder.INSTANCE;
    }

    private static class GzCompressorHolder {
        private static final GzCompressor INSTANCE = new GzCompressor();
    }

    /**
     *
     * @param inDirPath
     * @param outFilePath
     * @return
     */
    @Deprecated
    @Override
    public boolean compressDir(String inDirPath, String outFilePath) {
        // don't support
        return false;
    }

    /**
     *
     * @param inFilePaths
     * @param outFilePath
     * @return
     * @throws FilePathException
     */
    @Deprecated
    @Override
    public boolean compress(Collection<String> inFilePaths, String outFilePath) throws FilePathException {
        return super.compress(inFilePaths, outFilePath);
    }

    /**
     *
     * @param bis
     * @param inAbsoluteFileName
     * @param gzos
     * @return
     * @throws IOException
     */
    @Override
    protected GzipCompressorOutputStream compress(BufferedInputStream bis, String inAbsoluteFileName, GzipCompressorOutputStream gzos) throws IOException {
        int readSize;
        byte buffer[] = new byte[DEFAULT_BUFFER_POOL_SIZE];
        while ((readSize = bis.read(buffer, 0, buffer.length)) != -1) {
            gzos.write(buffer, 0, readSize);
        }
        return gzos;
    }

    /**
     *
     * @param inFile
     * @return
     * @throws FileNotFoundException
     */
    @Nonnull
    @Override
    public BufferedInputStream createInputStream(@Nullable File inFile) throws FileNotFoundException {
        assert inFile != null;
        FileInputStream fis = new FileInputStream(inFile);
        return new BufferedInputStream(fis);
    }

    /**
     *
     * @param outFile
     * @return
     * @throws IOException
     */
    @Nonnull
    @Override
    public GzipCompressorOutputStream createOutputStream(@Nullable File outFile) throws IOException {
        assert outFile != null;
        FileOutputStream fos = new FileOutputStream(outFile);
        return new GzipCompressorOutputStream(fos);

    }
}

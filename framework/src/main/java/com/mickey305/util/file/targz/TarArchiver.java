package com.mickey305.util.file.targz;

import com.mickey305.util.file.AbsCompressManager;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

/**
 * Created by K.Misaki on 2017/04/02.
 */
public class TarArchiver extends AbsCompressManager<BufferedInputStream, TarArchiveOutputStream> {
    private static final int DEFAULT_BUFFER_POOL_SIZE = 2^10;

    private File inFile;

    private TarArchiver() {}

    public static TarArchiver getInstance() {
        return TarArchiverHolder.INSTANCE;
    }

    private static class TarArchiverHolder {
        private static final TarArchiver INSTANCE = new TarArchiver();
    }

    /**
     *
     * @param bis
     * @param inAbsoluteFileName
     * @param taos
     * @return
     * @throws IOException
     */
    @Override
    protected TarArchiveOutputStream compress(BufferedInputStream bis, String inAbsoluteFileName, TarArchiveOutputStream taos) throws IOException {
        taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
        taos.putArchiveEntry(new TarArchiveEntry(inFile.getAbsoluteFile(), inAbsoluteFileName));
        int readSize;
        byte buffer[] = new byte[DEFAULT_BUFFER_POOL_SIZE];
        while ((readSize = bis.read(buffer, 0, buffer.length)) > 0) {
            taos.write(buffer, 0, readSize);
        }
        taos.closeArchiveEntry();
        return taos;
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
        this.inFile = inFile;
        FileInputStream fis = new FileInputStream(inFile);
        return new BufferedInputStream(fis);
    }

    /**
     *
     * @param outFile
     * @return
     * @throws FileNotFoundException
     */
    @Nonnull
    @Override
    public TarArchiveOutputStream createOutputStream(@Nullable File outFile) throws FileNotFoundException {
        assert outFile != null;
        FileOutputStream fos = new FileOutputStream(outFile);
        return new TarArchiveOutputStream(fos);
    }
}

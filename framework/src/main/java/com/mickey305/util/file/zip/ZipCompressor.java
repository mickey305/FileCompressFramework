package com.mickey305.util.file.zip;

import com.mickey305.util.file.AbsCompressManager;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

/**
 * Created by K.Misaki on 2017/04/02.
 *
 */
public class ZipCompressor extends AbsCompressManager<BufferedInputStream, ZipOutputStream> {
    private static final int DEFAULT_COMPRESS_LEVEL = 5;
    private static final int DEFAULT_BUFFER_POOL_SIZE = 2^10;
    private static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding;

    private ZipCompressor() {}

    public static ZipCompressor getInstance() {
        return ZipCompressorHolder.INSTANCE;
    }

    private static class ZipCompressorHolder {
        private static final ZipCompressor INSTANCE = new ZipCompressor();
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 圧縮する（コアタスク）
     * @param bis オープン済み入力ストリーム（オートクローズ：{@link java.lang.AutoCloseable}）
     * @param inRelativeFileName 圧縮対象のファイルパス名（入力フォルダをルートフォルダとした場合の相対的なファイル名）
     * @param zos オープン済み出力ストリーム（オートクローズ：{@link java.lang.AutoCloseable}）
     * @return オープン済み出力ストリーム（後続処理返却用）
     * @throws IOException 入出力例外
     */
    @Override
    protected ZipOutputStream compress(BufferedInputStream bis, String inRelativeFileName, ZipOutputStream zos) throws IOException {
        zos.setLevel(DEFAULT_COMPRESS_LEVEL);
        zos.setEncoding((getEncoding() == null)
                ? DEFAULT_ENCODING
                : getEncoding());
        zos.putNextEntry(new ZipEntry(inRelativeFileName));
        int readSize;
        byte buffer[] = new byte[DEFAULT_BUFFER_POOL_SIZE];
        while ((readSize = bis.read(buffer, 0, buffer.length)) != -1) {
            zos.write(buffer, 0, readSize);
        }
        zos.closeEntry();
        return zos;
    }

    /**
     * 入力ストリームを生成する
     * @param inFile 入力ストリームにバインドするファイル
     * @return 入力ストリーム
     * @throws FileNotFoundException ファイル存在例外
     */
    @Nonnull
    @Override
    public BufferedInputStream createInputStream(@Nullable File inFile) throws FileNotFoundException {
        assert inFile != null;
        FileInputStream fis = new FileInputStream(inFile);
        return new BufferedInputStream(fis);
    }

    /**
     * 出力ストリームを生成する
     * @param outFile 出力ストリームにバインドするファイル
     * @return 出力ストリーム
     * @throws FileNotFoundException ファイル存在例外
     */
    @Nonnull
    @Override
    public ZipOutputStream createOutputStream(@Nullable File outFile) throws FileNotFoundException {
        assert outFile != null;
        FileOutputStream fos = new FileOutputStream(outFile);
        return new ZipOutputStream(fos);
    }
}

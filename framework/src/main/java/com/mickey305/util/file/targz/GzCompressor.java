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
 *
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
     * 圧縮する
     *
     * @deprecated サポート対象外 <p>上書きすることで親クラスの該当メソッド{@link com.mickey305.util.file.AbsCompressManager#compressDir(String, String)}
     * を無効化しているが、将来的に継承クラスを代替する予定。</p>
     *
     * @param inDirPath 入力フォルダパス名
     * @param outFilePath 出力ファイルパス名
     * @return 処理結果
     */
    @Deprecated
    @Override
    public boolean compressDir(String inDirPath, String outFilePath) {
        // don't support
        return false;
    }

    /**
     * 圧縮する
     *
     * @deprecated サポート対象外 <p>内部的に使用されている箇所があるため、一時的に定義しているが将来的に継承クラスを代替する予定。</p>
     *
     * @param inFilePaths 入力ファイルパス名
     * @param outFilePath 出力ファイルパス名
     * @return 処理結果
     * @throws FilePathException ファイルパス例外
     */
    @Deprecated
    @Override
    public boolean compress(Collection<String> inFilePaths, String outFilePath) throws FilePathException {
        return super.compress(inFilePaths, outFilePath);
    }

    /**
     * 圧縮する（コアタスク）
     * @param bis オープン済み入力ストリーム（オートクローズ：{@link java.lang.AutoCloseable}）
     * @param inRelativeFileName 圧縮対象のファイルパス名（入力フォルダをルートフォルダとした場合の相対的なファイル名）
     * @param gzos オープン済み出力ストリーム（オートクローズ：{@link java.lang.AutoCloseable}）
     * @return オープン済み出力ストリーム（後続処理返却用）
     * @throws IOException 入出力例外
     */
    @Override
    protected GzipCompressorOutputStream compress(BufferedInputStream bis, String inRelativeFileName, GzipCompressorOutputStream gzos) throws IOException {
        int readSize;
        byte buffer[] = new byte[DEFAULT_BUFFER_POOL_SIZE];
        while ((readSize = bis.read(buffer, 0, buffer.length)) != -1) {
            gzos.write(buffer, 0, readSize);
        }
        return gzos;
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
    public GzipCompressorOutputStream createOutputStream(@Nullable File outFile) throws IOException {
        assert outFile != null;
        FileOutputStream fos = new FileOutputStream(outFile);
        return new GzipCompressorOutputStream(fos);

    }
}

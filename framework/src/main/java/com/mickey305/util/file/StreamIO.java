package com.mickey305.util.file;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

/**
 * Created by K.Misaki on 2017/04/09.
 *
 */
public interface StreamIO<I extends InputStream, O extends OutputStream> {
    /**
     * 入力ストリームを生成する
     * @param inFile 入力ストリームにバインドするファイル
     * @return 入力ストリーム
     * @throws IOException 入出力例外
     */
    @Nonnull I createInputStream(@Nullable File inFile) throws IOException;

    /**
     * 出力ストリームを生成する
     * @param outFile 出力ストリームにバインドするファイル
     * @return 出力ストリーム
     * @throws IOException 入出力例外
     */
    @Nonnull O createOutputStream(@Nullable File outFile) throws IOException;
}

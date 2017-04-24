package com.mickey305.util.file.exception.model;

/**
 * Created by K.Misaki on 2017/04/02.
 * <p>例外リソース</p>
 */
public class ExceptionValues {
    public static final int CD_IN_FILE_PATH = 10;
    public static final int CD_OUT_FILE_PATH = 11;
    public static final int CD_EXISTENCE_FILE_PATH = 20;
    public static final int CD_NOT_EXISTENCE_DIR_PATH = 21;
    public static final int CD_VALIDATION_FILE_PATH = 30;
    public static final int CD_COMPRESS = 40;
    public static final int CD_DECOMPRESS = 41;

    protected static final String MSG_IN_FILE_PATH = "入力ファイルのパス名が不正です";
    protected static final String MSG_OUT_FILE_PATH = "出力ファイルのパス名が不正です";
    protected static final String MSG_EXISTENCE_FILE_PATH = "該当パス上にはファイルが既に存在します";
    protected static final String MSG_NOT_EXISTENCE_DIR_PATH = "該当パスのフォルダが存在しません";
    protected static final String MSG_VALIDATION_FILE_PATH = "検証したファイルのパスが不正です";
    protected static final String MSG_COMPRESS = "圧縮に失敗しました";
    protected static final String MSG_DECOMPRESS = "解凍に失敗しました";
}

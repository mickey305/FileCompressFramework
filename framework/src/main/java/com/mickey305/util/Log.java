package com.mickey305.util;

import javax.annotation.Nonnull;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by K.Misaki on 2017/04/16.
 *
 */
public class Log {
    protected static final String ANSI_RESET = "\u001b[0m";

    protected static final String ANSI_FONT_BLACK = "\u001b[30m";
    protected static final String ANSI_FONT_RED = "\u001b[31m";
    protected static final String ANSI_FONT_GREEN = "\u001b[32m";
    protected static final String ANSI_FONT_YELLOW = "\u001b[33m";
    protected static final String ANSI_FONT_BLUE = "\u001b[34m";
    protected static final String ANSI_FONT_PURPLE = "\u001b[35m";
    protected static final String ANSI_FONT_CYAN = "\u001b[36m";
    protected static final String ANSI_FONT_WHITE = "\u001b[37m";

    protected static final String ANSI_BKG_BLACK = "\u001b[40m";
    protected static final String ANSI_BKG_RED = "\u001b[41m";
    protected static final String ANSI_BKG_GREEN = "\u001b[42m";
    protected static final String ANSI_BKG_YELLOW = "\u001b[43m";
    protected static final String ANSI_BKG_BLUE = "\u001b[44m";
    protected static final String ANSI_BKG_PURPLE = "\u001b[45m";
    protected static final String ANSI_BKG_CYAN = "\u001b[46m";
    protected static final String ANSI_BKG_WHITE = "\u001b[47m";

    private Log() {}

    /**
     * 現在日時を取得する
     * @return 現在時刻（フォーマット「yyyy-MM-dd HH:mm:ss.SSS」）
     */
    @Nonnull
    private static String createHeader() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(cal.getTime());
    }

    /**
     * ログ情報を出力する（標準出力・デバッグモード）
     * @param clazz 対象Classクラス
     * @param methodName 対象メソッド名
     * @param msg メッセージ
     */
    public synchronized static void d(Class clazz, String methodName, String msg) {
        if (Config.DEBUG_MODE)
            i(ANSI_BKG_YELLOW + ANSI_FONT_BLACK + "[" + clazz.getSimpleName() + "#" + methodName + "] " + ANSI_RESET + msg);
    }

    /**
     * ログ情報を出力する（標準出力）
     * @param msg メッセージ
     */
    public synchronized static void i(String msg) {
        System.out.println(ANSI_FONT_BLUE + createHeader() + " I/D " + ANSI_RESET + msg);
    }

    /**
     * ログ情報を出力する（エラー出力）
     * @param msg メッセージ
     */
    public synchronized static void e(String msg) {
        System.err.println(createHeader() + "  E  " + msg);
    }
}

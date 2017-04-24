package com.mickey305.util.file;

import javax.annotation.Nonnull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by K.Misaki on 2017/04/09.
 *
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * ユニークな日時情報を取得する（年月日時分秒ミリ秒）
     * @param format 日時フォーマット
     * @return 日時
     */
    public static String createUniqueTime(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    /**
     * ファイルパス名からファイル名と拡張子（接尾辞）を切り出す
     * @param filePath ファイルパス
     * @param fileName 切り出したファイル名
     * @param suffix 切り出した拡張子
     */
    public static void pickFileNameAndSuffix(@Nonnull final String filePath, @Nonnull StringBuilder fileName, @Nonnull StringBuilder suffix) {
        fileName.setLength(0);
        suffix.setLength(0);
        int point = filePath.lastIndexOf(File.separator);
        if (point != -1) {
            fileName.append(filePath.substring(point + 1));
            point = fileName.toString().lastIndexOf(".");
            if (point != -1) {
                suffix.append(fileName.toString().substring(point + 1));
                String tmp = fileName.toString();
                fileName.setLength(0);
                fileName.append(tmp.substring(0, point));
            }
        } else {
            fileName.append(filePath);
        }
    }
}

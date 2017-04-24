package com.mickey305.util.file;

import java.util.List;

/**
 * Created by K.Misaki on 2017/04/16.
 *
 */
public interface ConflictOutputFileListener {
    /**
     * ファイル名前を変更する
     * @param fileNames 対象フォルダに存在するファイル名
     * @param outputFileName 対象フォルダ内で重複するファイル名
     * @return 対象フォルダ内で重複しないファイル名
     */
    String onRename(List<String> fileNames, String outputFileName);
}

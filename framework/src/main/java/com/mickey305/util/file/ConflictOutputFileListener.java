package com.mickey305.util.file;

import java.util.List;

/**
 * Created by K.Misaki on 2017/04/16.
 */
public interface ConflictOutputFileListener {
    /**
     *
     * @param fileNames
     * @param outputFileName
     * @return
     */
    String onRename(List<String> fileNames, String outputFileName);
}

package com.mickey305.util.file.exception.model;

import com.mickey305.util.FieldCounter;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by K.Misaki on 2017/04/15.
 */
public class ExceptionResultSet extends ExceptionValues {
    private Map<Integer, ExceptionData> resultSet;

    public static ExceptionResultSet getInstance() {
        return ExceptionResultSetHolder.INSTANCE;
    }

    private static class ExceptionResultSetHolder {
        private static final ExceptionResultSet INSTANCE = new ExceptionResultSet();
    }

    public Map<Integer, ExceptionData> getResultSet() {
        return resultSet;
    }

    /**
     *
     * @param resultCode
     * @param data
     * @return
     */
    @Deprecated
    public boolean updateResultSet(int resultCode, ExceptionData data) {
        if (resultSet.containsKey(resultCode)) {
            resultSet.put(resultCode, data);
            return true;
        } else {
            return false;
        }
    }

    private ExceptionResultSet() {
        createMapping();
        chkResultSetSize();
    }

    /**
     *
     */
    private void createMapping() {
        resultSet = new HashMap<>();
        resultSet.put(CD_IN_FILE_PATH, new ExceptionData(MSG_IN_FILE_PATH));
        resultSet.put(CD_OUT_FILE_PATH, new ExceptionData(MSG_OUT_FILE_PATH));
        resultSet.put(CD_COMPRESS, new ExceptionData(MSG_COMPRESS));
        resultSet.put(CD_DECOMPRESS, new ExceptionData(MSG_DECOMPRESS));
        resultSet.put(CD_EXISTENCE_FILE_PATH, new ExceptionData(MSG_EXISTENCE_FILE_PATH));
        resultSet.put(CD_NOT_EXISTENCE_DIR_PATH, new ExceptionData(MSG_NOT_EXISTENCE_DIR_PATH));
        resultSet.put(CD_VALIDATION_FILE_PATH, new ExceptionData(MSG_VALIDATION_FILE_PATH));
    }

    /**
     *
     */
    private void chkResultSetSize() {
        FieldCounter fc = new FieldCounter(ExceptionValues.class);
        if (getResultSet().size() != fc.countByPrefix("CD", Modifier::isStatic))
            throw new RuntimeException("definition error of " + ExceptionResultSet.class.getSimpleName() + " occurred");
    }
}

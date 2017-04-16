package com.mickey305.util.file.exception;

import com.mickey305.util.file.exception.model.ExceptionData;

/**
 * Created by K.Misaki on 2017/04/15.
 */
public abstract class AbsException extends Exception {
    private int resultCode;

    public AbsException(int resultCode, ExceptionData data) {
        super(data.getMessage());
        setResultCode(resultCode);
    }

    public int getResultCode() {
        return resultCode;
    }

    protected void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}

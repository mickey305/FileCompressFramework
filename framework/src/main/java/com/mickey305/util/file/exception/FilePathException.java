package com.mickey305.util.file.exception;

import com.mickey305.util.file.exception.model.ExceptionData;

/**
 * Created by K.Misaki on 2017/04/02.
 */
public class FilePathException extends AbsException {
    public FilePathException(int resultCode, ExceptionData data) {
        super(resultCode, data);
    }
}

package com.mickey305.util.file.exception;

import com.mickey305.util.file.exception.model.ExceptionData;

/**
 * Created by K.Misaki on 2017/04/02.
 *
 */
public class CompressingException extends AbsException {
    public CompressingException(int resultCode, ExceptionData data) {
        super(resultCode, data);
    }
}

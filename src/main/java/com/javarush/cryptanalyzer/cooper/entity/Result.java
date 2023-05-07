package com.javarush.cryptanalyzer.cooper.entity;

import java.nio.file.Path;
import com.javarush.cryptanalyzer.cooper.utils.ResultCode;

public class Result {
    private ResultCode resultCode;
    private Path filePath;
    private Exception exception;

    public Result(ResultCode resultCode, Path filePath) {
        this.resultCode = resultCode;
        this.filePath = filePath;
    }

    public Result(ResultCode resultCode, Exception exception) {
        this.resultCode = resultCode;
        this.exception = exception;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Exception getUserException() {
        return exception;
    }
}

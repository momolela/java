package com.momolela.serialize;

public class SerializeException extends RuntimeException {

    public static final int CODE_NOT_FOUND = 404;
    public static final int CODE_IO_FAILED = 501;
    public static final int CODE_CLASS_NOT_FOUND = 502;
    public static final int CODE_PARSE_ERROR = 503;
    private int code;

    public SerializeException() {

    }

    public SerializeException(int code) {
        this.code = code;
    }

    public SerializeException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public SerializeException(int code, Throwable t) {
        super(t);
        this.code = code;
    }

    public SerializeException(int code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }

    public SerializeException(String msg) {
        super(msg);
    }

    public SerializeException(Throwable t) {
        super(t);
    }

    public SerializeException(String msg, Throwable t) {
        super(msg, t);
    }

    public int getCode() {
        return this.code;
    }

    public void throwThis() {
        throw this;
    }
}

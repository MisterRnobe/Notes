package com.medvedev.nikita.notes.utils;

public class ErrorManager {
    public static final int SHORT_LOGIN = 0;
    public static final int LONG_LOGIN = 1;
    public static final int LOGIN_EXISTS = 2;
    public static final int WRONG_LOGIN_SYMBOLS = 3;
    //Errors related to password
    public static final int SHORT_PASSWORD = 10;
    // --/-- to email
    public static final int WRONG_MAIL = 20;
    public static final int MAIL_EXISTS = 21;
    // --/-- to token
    public static final int BAD_TOKEN = 30;
    public static final int EXPIRED_TOKEN = 31;
    public static final int REQUIRES_TOKEN =32;
    //Authorization error
    public static final int BAD_DATA = 40;
    //Common
    public static final int WRONG_REQUEST_PARAMETERS = 1000;
    public static final int INTERNAL_ERROR = 1001;
    public static final int EMPTY_BODY = 1002;
    public static final int WRONG_FUNCTION = 1003;
}

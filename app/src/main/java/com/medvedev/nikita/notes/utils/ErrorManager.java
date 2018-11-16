package com.medvedev.nikita.notes.utils;

import android.content.res.Resources;

import com.medvedev.nikita.notes.R;

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
    public static final int REQUIRES_TOKEN = 32;
    //Authorization error
    public static final int BAD_DATA = 40;
    //Common
    public static final int WRONG_REQUEST_PARAMETERS = 1000;
    public static final int INTERNAL_ERROR = 1001;
    public static final int EMPTY_BODY = 1002;
    public static final int WRONG_FUNCTION = 1003;

    public static int errorToResID(int error) {
        int result = 0;
        switch (error) {
            case SHORT_LOGIN:
                result = R.string.short_login;
                break;
            case LONG_LOGIN:
                result = R.string.long_login;
                break;
            case LOGIN_EXISTS:
                result = R.string.login_exists;
                break;
            case WRONG_LOGIN_SYMBOLS:
                result = R.string.wrong_login_symbols;
                break;
            case SHORT_PASSWORD:
                result = R.string.short_password;
                break;
            case WRONG_MAIL:
                result = R.string.wrong_mail;
                break;
            case MAIL_EXISTS:
                result = R.string.mail_exists;
                break;
            case BAD_TOKEN:
                result = R.string.bad_token;
                break;
            case EXPIRED_TOKEN:
                result = R.string.expired_token;
                break;
            case REQUIRES_TOKEN:
                result = R.string.requires_token;
                break;
            case BAD_DATA:
                result = R.string.bad_data;
                break;
            case WRONG_REQUEST_PARAMETERS:
                result = R.string.wrong_request_parameters;
                break;
            case INTERNAL_ERROR:
                result = R.string.internal_error;
                break;
            case EMPTY_BODY:
                result = R.string.empty_body;
                break;
            case WRONG_FUNCTION:
                result = R.string.wrong_function;
                break;
        }
        return result;
    }
}

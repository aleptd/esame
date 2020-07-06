package com.example.autenticazione;

import com.loopj.android.http.*;

public class RequestHandler {

    // URL statico comune ad ogni chiamata
    private static final String URL = "https://default2-dot-parziale-lcsdep.nw.r.appspot.com/api/v1/users/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    // get e post richiedono url semplici che vengono wrappati con la stringa URL
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return URL + relativeUrl;
    }
}


package com.github.chenjianhua.springbootelasticsearch.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class OkHttpUtil {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient okHttpClient;

    public static void setOkHttpClient(OkHttpClient client) {
        okHttpClient = client;
    }

    public static Response getSync(String url) throws IOException {
        RequestBody body = RequestBody.create("{}", JSON);
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        return call.execute();
    }
}

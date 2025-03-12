package org.fsolsh.deepseek.tools;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author fsolsh
 * @date 2025-02-27
 */
@Slf4j
public class OkHttpUtil {

    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build();

    // 私有构造函数，防止实例化
    private OkHttpUtil() {
    }

    // GET方法
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }

    // 添加带有请求头的GET方法
    public static Response getWithHeaders(String url, Headers headers) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }

    // POST方法
    public static Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }

    // 添加带有请求头的POST方法
    public static Response postWithHeaders(String url, String json, Headers headers) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }

    // PUT 方法
    public static Response put(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }

    // DELETE 方法
    public static Response delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("Unexpected code {}", response);
                throw new IOException("Unexpected code " + response);
            }
            return response;
        }
    }
}

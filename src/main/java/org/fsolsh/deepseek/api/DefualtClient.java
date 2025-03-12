package org.fsolsh.deepseek.api;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSource;
import okio.Okio;
import org.fsolsh.deepseek.exception.DeepSeekException;
import org.fsolsh.deepseek.request.ChatRequest;
import org.fsolsh.deepseek.request.FimRequest;
import org.fsolsh.deepseek.response.*;
import org.fsolsh.deepseek.tools.OkHttpUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author fsolsh
 * @date 2025-02-27
 */

@Slf4j
public class DefualtClient implements Client {

    private static final String DEFAULT_BASE_URL = "https://api.deepseek.com/";
    private final String accessKey;
    private final String baseUrl;

    /**
     * 补全API
     */

    private String chatUrl;
    private String fimUrl;
    private String listModelUrl;
    private String balanceInfoUrl;

    public DefualtClient(String accessKey) {
        this(accessKey, DEFAULT_BASE_URL);
    }


    public DefualtClient(String accessKey, String baseUrl) {
        this.accessKey = accessKey;
        this.baseUrl = baseUrl;
        completeRequestUrl();
    }

    private void completeRequestUrl() {
        this.chatUrl = this.baseUrl + "chat/completions";
        this.fimUrl = this.baseUrl + "beta/completions";
        this.listModelUrl = this.baseUrl + "models";
        this.balanceInfoUrl = this.baseUrl + "user/balance";
    }

    private Headers defaultHeader() {
        return new Headers.Builder()
                .add("Accept", "application/json")
                .add("Content-Type", "application/json")
                .add("Authorization", "Bearer " + accessKey)
                .build();
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        request.setStream(Boolean.FALSE);
        try {
            String body = Objects.requireNonNull(OkHttpUtil.postWithHeaders(chatUrl, JSON.toJSONString(request), defaultHeader()).body()).string();
            return JSON.parseObject(body, ChatResponse.class);
        } catch (IOException e) {
            throw new DeepSeekException("接口异常：" + e.getMessage(), e);
        }
    }

    @Override
    public void streamChat(ChatRequest request, Consumer<StreamChatResponse> consumer) {
        request.setStream(Boolean.TRUE);
        Request httpRequest = new Request.Builder()
                .url(chatUrl)
                .headers(defaultHeader())
                .post(RequestBody.create(JSON.toJSONString(request), MediaType.parse("application/json; charset=utf-8")))
                .build();

        OkHttpUtil.client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                throw new DeepSeekException("StreamChat接口异常：" + e.getMessage(), e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                ResponseBody responseBody = response.body();
                if (response.isSuccessful() && responseBody != null) {

                    BufferedSource bufferedSource = Okio.buffer(responseBody.source());

                    // 格式 data:
                    // 结束格式 data: [DONE]
                    String line;
                    try {
                        while ((line = bufferedSource.readUtf8Line()) != null) {
                            if ("data: [DONE]".equals(line)) {
                                log.debug("all data is DONE");
                                break;
                            } else if (line.startsWith("data: ")) {
                                String json = line.substring(6);
                                StreamChatResponse chatResponse = JSON.parseObject(json, StreamChatResponse.class);
                                consumer.accept(chatResponse);
                            }
                        }
                    } catch (IOException e) {
                        throw new DeepSeekException("StreamChat接口异常：" + e.getMessage(), e);
                    } finally {
                        responseBody.close();
                    }
                }
            }
        });
    }

    @Override
    public FimResponse fim(FimRequest request) {
        request.setStream(Boolean.FALSE);
        try {
            String body = Objects.requireNonNull(OkHttpUtil.postWithHeaders(fimUrl, JSON.toJSONString(request), defaultHeader()).body()).string();
            return JSON.parseObject(body, FimResponse.class);
        } catch (IOException e) {
            throw new DeepSeekException("Fim接口异常：" + e.getMessage(), e);
        }
    }

    @Override
    public void streamFim(FimRequest request, Consumer<StreamFimResponse> consumer) {
        request.setStream(Boolean.TRUE);
        Request httpRequest = new Request.Builder()
                .url(fimUrl)
                .headers(defaultHeader())
                .post(RequestBody.create(JSON.toJSONString(request), MediaType.parse("application/json; charset=utf-8")))
                .build();

        OkHttpUtil.client.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                throw new DeepSeekException("StreamFim接口异常：" + e.getMessage(), e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                ResponseBody responseBody = response.body();
                if (response.isSuccessful() && responseBody != null) {

                    BufferedSource bufferedSource = Okio.buffer(responseBody.source());

                    // 格式 data:
                    // 结束格式 data: [DONE]
                    String line;
                    try {
                        while ((line = bufferedSource.readUtf8Line()) != null) {
                            if ("data: [DONE]".equals(line)) {
                                log.debug("all data is DONE");
                                break;
                            } else if (line.startsWith("data: ")) {
                                String json = line.substring(6);
                                StreamFimResponse streamFimResponse = JSON.parseObject(json, StreamFimResponse.class);
                                consumer.accept(streamFimResponse);
                            }
                        }
                    } catch (IOException e) {
                        throw new DeepSeekException("StreamFim接口异常：" + e.getMessage(), e);
                    } finally {
                        responseBody.close();
                    }
                }
            }
        });
    }

    @Override
    public ListModelResponse listModel() {
        try {
            String body = Objects.requireNonNull(OkHttpUtil.getWithHeaders(listModelUrl, defaultHeader()).body()).string();
            return JSON.parseObject(body, ListModelResponse.class);
        } catch (IOException e) {
            throw new DeepSeekException("ListModel接口异常：" + e.getMessage(), e);
        }
    }

    @Override
    public BalanceInfoResponse queryBalance() {

        try {
            String body = Objects.requireNonNull(OkHttpUtil.getWithHeaders(balanceInfoUrl, defaultHeader()).body()).string();
            return JSON.parseObject(body, BalanceInfoResponse.class);
        } catch (IOException e) {
            throw new DeepSeekException("BalanceInfo接口异常：" + e.getMessage(), e);
        }
    }
}

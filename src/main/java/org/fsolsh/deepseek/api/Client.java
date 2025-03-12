package org.fsolsh.deepseek.api;

import org.fsolsh.deepseek.request.ChatRequest;
import org.fsolsh.deepseek.request.FimRequest;
import org.fsolsh.deepseek.response.*;

import java.util.function.Consumer;


/**
 * @author fsolsh
 * @date 2025-02-27
 */
public interface Client {


    /**
     * 对话
     *
     * @return ChatResponse
     */
    ChatResponse chat(ChatRequest request);

    /**
     * 对话（流式数据）
     */
    void streamChat(ChatRequest request, Consumer<StreamChatResponse> consumer);

    /**
     * 补全
     *
     * @return FimResponse
     */
    FimResponse fim(FimRequest request);

    /**
     * 补全（流式数据）
     *
     * @return FimResponse
     */
    void streamFim(FimRequest request, Consumer<StreamFimResponse> consumer);

    /**
     * 列出模型
     *
     * @return ListModelResponse
     */
    ListModelResponse listModel();

    /**
     * 账户余额信息
     *
     * @return BalanceInfoResponse
     */
    BalanceInfoResponse queryBalance();
}

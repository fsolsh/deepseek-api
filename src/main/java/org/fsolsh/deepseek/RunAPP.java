package org.fsolsh.deepseek;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.fsolsh.deepseek.api.Client;
import org.fsolsh.deepseek.api.DefualtClient;
import org.fsolsh.deepseek.enums.ModelEnums;
import org.fsolsh.deepseek.enums.RoleEnums;
import org.fsolsh.deepseek.request.ChatRequest;
import org.fsolsh.deepseek.request.FimRequest;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class RunAPP {

    public static void main(String[] args) {

        String accessKey = "GSMve9yaLW/f4gb735y46+bBSFmidYEe6mxk600QiEwGEwD3UjP1qLVOF+9XeLRR";

        //chat
        chat(accessKey);
        //streamChat
        streamChat(accessKey);
        //fim
        fim(accessKey);
        //streamFim
        streamFim(accessKey);
        //queryBalance
        queryBalance(accessKey);
        //listModel
        listModel(accessKey);
    }

    private static void chat(String accessKey) {
        Client client = new DefualtClient(accessKey);

        ChatRequest.Message systemMessage = new ChatRequest.Message();
        systemMessage.setRole(RoleEnums.SYSTEM.roleType);
        systemMessage.setContent("You are a helpful assistant");

        ChatRequest.Message userMessage = new ChatRequest.Message();
        userMessage.setRole(RoleEnums.USER.roleType);
        userMessage.setContent("What can DeepSeek be used for");

        List<ChatRequest.Message> messageList = Arrays.asList(systemMessage, userMessage);
        ChatRequest chatRequest = ChatRequest.create(messageList, ModelEnums.DEEPSEEK_CHAT.modelType);
        log.info("chat : {}", JSON.toJSONString(client.chat(chatRequest)));
    }

    private static void streamChat(String accessKey) {
        Client client = new DefualtClient(accessKey);

        ChatRequest.Message systemMessage = new ChatRequest.Message();
        systemMessage.setRole(RoleEnums.SYSTEM.roleType);
        systemMessage.setContent("You are a helpful assistant");

        ChatRequest.Message userMessage = new ChatRequest.Message();
        userMessage.setRole(RoleEnums.USER.roleType);
        userMessage.setContent("What can DeepSeek be used for");

        List<ChatRequest.Message> messageList = Arrays.asList(systemMessage, userMessage);
        ChatRequest chatRequest = ChatRequest.create(messageList, ModelEnums.DEEPSEEK_CHAT.modelType);
        client.streamChat(chatRequest, streamChatResponse -> {
            // handle streamChatResponse
        });
    }

    private static void fim(String accessKey) {
        Client client = new DefualtClient(accessKey);
        FimRequest fimRequest = FimRequest.create("Today is national day", ModelEnums.DEEPSEEK_CHAT.modelType);
        log.info("fim : {}", JSON.toJSONString(client.fim(fimRequest)));
    }

    private static void streamFim(String accessKey) {
        Client client = new DefualtClient(accessKey);
        FimRequest fimRequest = FimRequest.create("Today is national day", ModelEnums.DEEPSEEK_CHAT.modelType);
        client.streamFim(fimRequest, streamFimResponse -> {
            // handle streamFimResponse
        });
    }

    private static void queryBalance(String accessKey) {
        Client client = new DefualtClient(accessKey);
        log.info("queryBalance : {}", JSON.toJSONString(client.queryBalance()));
    }

    private static void listModel(String accessKey) {
        Client client = new DefualtClient(accessKey);
        log.info("listModel : {}", JSON.toJSONString(client.listModel()));
    }

}

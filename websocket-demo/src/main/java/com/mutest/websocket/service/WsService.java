package com.mutest.websocket.service;

import com.mutest.websocket.utlis.WsSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2023/4/8 10:53
 * @description ws操作相关服务
 */
@Service
@Slf4j
public class WsService {
    /**
     * 发送消息
     *
     * @param session 连接信息
     * @param text    消息
     */
    public void sendMsg(WebSocketSession session, String text) throws IOException {
        session.sendMessage(new TextMessage(text));
    }

    /**
     * 广播消息
     *
     * @param text 消息
     */
    public void broadcastMsg(String text) throws IOException {
        for (WebSocketSession session : WsSessionManager.SESSION_POOL.values()) {
            session.sendMessage(new TextMessage(text));
        }
    }
}

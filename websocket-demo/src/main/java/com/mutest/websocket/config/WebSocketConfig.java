package com.mutest.websocket.config;

import com.mutest.websocket.handler.MyWsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2023/4/7 17:34
 * @description websocket config
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private MyWsHandler myWsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(myWsHandler, "myWs")
                //允许跨域
                .setAllowedOrigins("*");
    }
}

package com.mutest.websocket.job;

import com.mutest.websocket.service.WsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2023/4/8 10:52
 * @description 定时生成消息
 */
@Slf4j
@Component
public class MessageJob {
    @Autowired
    WsService wsService;

    /**
     * 每5s发送
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void run() {
        try {
            log.info("推送消息===>" + LocalDateTime.now().toString());
            wsService.broadcastMsg("自动生成消息 " + LocalDateTime.now().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

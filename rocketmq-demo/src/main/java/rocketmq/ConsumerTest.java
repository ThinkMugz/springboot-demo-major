package rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.time.LocalDateTime;

/**
 * @author muguozheng
 * @version 1.0.0
 * @createTime 2021/12/28 20:11
 * @description TODO
 */
public class ConsumerTest {
    public static void main(String[] args) throws MQClientException {

        //创建一个消息消费者，并设置一个消息消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test");

        //指定 NameServer 地址
        consumer.setNamesrvAddr("82.157.165.196:9876");

        // Subscribe one more more topics to consume.
        //订阅指定 Topic 下的所有消息
        consumer.subscribe("TopicTest", "*");

        //负载均衡模式，默认
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // Register callback to execute on arrival of messages fetched from brokers.
        // 注册回调函数，处理消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf("%s %s Receive New Messages: %s %n", LocalDateTime.now(), Thread.currentThread().getName(),
                    new String(msgs.get(0).getBody()));

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 启动消费者
        consumer.start();

        System.out.println("消息消费者已启动");
    }
}

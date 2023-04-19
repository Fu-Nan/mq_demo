package cn.itcast.mq.helloworld;

import cn.itcast.mq.PublisherApplication;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fn
 * @Date: 2023/4/18 10:06
 * @Description:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 基本消息队列
     */
    @Test
    public void testBasicQueue() {
        String queueName = "simple.queue";
        String message = "Hello World";
        rabbitTemplate.convertAndSend(queueName, message);
        System.out.println("发送消息成功");
    }

    /**
     * 工作消息队列
     */
    @Test
    public void testWorkQueue() {
        String queueName = "simple.queue";
        String message = "Hello World__";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
        }
        System.out.println("消息发送完毕");
    }

    /**
     * 发布订阅模型-Fanout
     */
    @Test
    public void testSendFanoutExchange() {
        String exchangeName = "fanout.exchange";
        String message = "Hello World";

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "张三");
        hashMap.put("age", "19");
//        rabbitTemplate.convertAndSend(exchangeName, "", hashMap);

        //方式二，直接将参数转为字符串发送
        rabbitTemplate.convertAndSend(exchangeName, "", JSONObject.toJSONString(hashMap));

        System.out.println("消息发送完毕");
    }

    /**
     * 发布订阅模型-Direct
     */
    @Test
    public void testSendDirectExchange() {
        String exchangeName = "direct.exchange";
        String message = "Hello Direct Exchange";
        //转发到该Exchange的哪一个RoutingKey中
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    /**
     * 发布订阅模型-Topic
     */
    @Test
    public void testSendTopicExchange() {
        String exchangeName = "topic.exchange";
        String message = "Hello Topic Exchange";
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", message);
    }

    @Test
    public void testJacksonToObject() {
    }
}

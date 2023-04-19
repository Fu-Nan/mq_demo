package cn.itcast.mq.listener;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.xml.ws.BindingType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author: fn
 * @Date: 2023/4/18 10:53
 * @Description:
 **/
@Component
public class SpringRabbitListener {
    /**
     * 简单消息队列消费者
     *
     * @param msg
     */
    @RabbitListener(queues = "test.queue")
    public void listenBasicQueue(String msg) {
        System.out.println("接收到消息：" + msg);
    }

    /**
     * 工作消息队列消费者01
     * 每20ms消费一条
     *
     * @param msg
     */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue01(String msg) throws InterruptedException {
        System.out.println("Consumer01接收到消息：" + msg + "=======" + LocalDateTime.now());
        Thread.sleep(20);
    }

    /**
     * 工作消息队列消费者02
     * 每200ms消费一条
     *
     * @param msg
     */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue02(String msg) throws InterruptedException {
        System.err.println("Consumer02接收到消息：" + msg + "=======" + LocalDateTime.now());
        Thread.sleep(200);
    }

    /**
     * 发布订阅模型-Fanout  消费者
     *
     * @param msg
     * @throws InterruptedException
     */
    //接收队列fanout.queue01的消息
    @RabbitListener(queues = "fanout.queue01")
    public void listenFanoutQueue01(String msg) {
        //转为Map
        Map mapTyep = JSONObject.parseObject(msg);
        for (Object o : mapTyep.keySet()) {
            System.out.println(mapTyep.get(o));
        }
        System.out.println("fanout.queue01接收到消息：" + mapTyep);
    }

    //接收队列fanout.queue02的消息
    @RabbitListener(queues = "fanout.queue02")
    public void listenFanoutQueue02(String msg) {
        System.out.println("fanout.queue02接收到消息：" + msg);
    }

    /**
     * 发布订阅模型-Direct 消费者
     */
    //消费者01
    //QueueBinding内说明了Queue和Exchange以及RoutingKey的关系
    @RabbitListener(bindings = @QueueBinding(
            //Queue名称
            value = @Queue("direct.queue01"),
            //Exchange名称，以及该Exchange是哪种类型
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            //RoutingKey名称
            key = {"red"}
    ))
    public void listenDirectQueue01(String msg) {
        System.out.println("direct.queue01接收到消息：" + msg);
    }

    //消费者02
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("direct.queue02"),
            exchange = @Exchange(name = "direct.exchange", type = ExchangeTypes.DIRECT),
            key = {"yellow", "red"}
    ))
    public void listenDirectQueue02(String msg) {
        System.out.println("direct.queue02接收到消息：" + msg);
    }

    /**
     * 发布订阅模型-Topic 消费者
     */
    //消费者01
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("topic.queue01"),
            exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
            //因为使用通配符，所以就不用数组格式的了
            key = "china.#"
    ))
    public void listenTopicQueue01(String msg) {
        System.out.println("topic.queue01接收到消息：" + msg);
    }

    //消费者02
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("topic.queue02"),
            exchange = @Exchange(name = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue02(String msg) {
        System.out.println("topic.queue02接收到消息：" + msg);
    }
}

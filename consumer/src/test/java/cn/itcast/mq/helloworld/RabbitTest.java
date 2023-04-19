package cn.itcast.mq.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: fn
 * @Date: 2023/4/17 16:42
 * @Description:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbit() {

    }
}

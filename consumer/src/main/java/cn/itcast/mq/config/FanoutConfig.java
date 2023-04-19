package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: fn
 * @Date: 2023/4/18 14:59
 * @Description:
 **/
//0. 该类作为一个配置类被扫描
@Configuration
public class FanoutConfig {
    //1. 声明一个Fanout交换机，名称为 fanout.exchange
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.exchange");
    }

    //2. 声明第一个队列，名称为 fanout.queue01
    @Bean
    public Queue fanoutQueue01() {
        return new Queue("fanout.queue01");
    }

    //2. 声明第二个队列，名称为 fanout.queue02
    @Bean
    public Queue fanoutQueue02() {
        return new Queue("fanout.queue02");
    }

    //3. 使用Binding，将队列01和交换机进行绑定
    @Bean
    public Binding fanoutBinding01(FanoutExchange fanoutExchange, Queue fanoutQueue01) {
        return BindingBuilder.bind(fanoutQueue01)
                .to(fanoutExchange);
    }

    //3. 使用Binding，将队列02和交换机进行绑定
    @Bean
    public Binding fanoutBinding02(FanoutExchange fanoutExchange, Queue fanoutQueue02) {
        return BindingBuilder.bind(fanoutQueue02)
                .to(fanoutExchange);
    }
}

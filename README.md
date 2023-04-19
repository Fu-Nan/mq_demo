运行前：
1. 修改consumer和publisher的rabbitMQ相关配置
2. 如果要使用BasicQueue和WorkQueue，在rabbitMQ手动创建消息队列"simple.queue"

相关介绍：
1. ConsumerTest和PublisherTest为最原始的RabbitFactory，没有使用SpringAMQP
2. FanoutConfig配置了发布订阅模型-广播的相关配置，创建交换机和队列并绑定
3. SpringRabbitListener用于定义消息发送者
4. publisher模块下的RabbitTest用于定义消息接收者
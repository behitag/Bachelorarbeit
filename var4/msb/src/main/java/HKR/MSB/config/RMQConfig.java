package HKR.MSB.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RMQConfig {

    public static final String EXCHANGE_NAME = "fanoutExchange";
    public static final String fanoutQueue = "fanoutQueue";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }

    @Bean
    public Queue from_msa() { return new Queue("from_msa", true); }

    @Bean
    public DirectExchange exchange() { return new DirectExchange("exchange"); }

    @Bean
    public Binding binding(Queue from_msa, DirectExchange exchange) { return BindingBuilder.bind(from_msa).to(exchange).with("routingKey"); }

    @Bean
    public Queue leaderQueue() { return new Queue("leaderQueue", true); }

    @Bean
    public DirectExchange leaderExchange() { return new DirectExchange("leaderExchange"); }

    @Bean
    public Binding leaderBinding(Queue leaderQueue, DirectExchange leaderExchange) { return BindingBuilder.bind(leaderQueue).to(leaderExchange).with("leaderRouting"); }

    @Bean
    public FanoutExchange fanoutExchange() { return new FanoutExchange(EXCHANGE_NAME); }

    @Bean
    public Queue fanoutQueue() { return new Queue(fanoutQueue, true); }

    @Bean
    public Binding fanoutBinding(Queue fanoutQueue, FanoutExchange fanoutExchange) { return BindingBuilder.bind(fanoutQueue).to(fanoutExchange); }
}

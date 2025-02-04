package HKR.MSB.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() { return new Jackson2JsonMessageConverter(); }

    @Bean
    public Queue from_msa() { return new Queue("from_msa", true); }

    @Bean
    public DirectExchange exchange() { return new DirectExchange("exchange"); }

    @Bean
    public Binding binding(Queue from_msa, DirectExchange exchange) { return BindingBuilder.bind(from_msa).to(exchange).with("routingKey"); }
}

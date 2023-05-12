package ru.otus.spring.volgin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

@Configuration
public class ChannelConfig {

    @Bean
    public QueueChannel eggsChannel() {
        return MessageChannels.queue( 50 ).get();
    }

    @Bean
    public PublishSubscribeChannel caterpillarChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel cocoonChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel groundChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow eggsFlow() {
        return IntegrationFlows.from( "eggsChannel" )
                .split()
                .handle( "TransformationService", "transformToCaterpillar" )
                .aggregate()
                .channel( "caterpillarChannel" )
                .get();
    }

    @Bean
    public IntegrationFlow caterpillarFlow() {
        return IntegrationFlows.from( "caterpillarChannel" )
                .split()
                .handle( "TransformationService", "transformToCocoon" )
                .aggregate()
                .channel( "cocoonChannel" )
                .get();
    }

    @Bean
    public IntegrationFlow cocoonFlow() {
        return IntegrationFlows.from( "cocoonChannel" )
                .split()
                .handle( "TransformationService", "transformToButterfly" )
                .aggregate()
                .channel( "butterflyChannel" )
                .get();
    }

    @Bean
    public IntegrationFlow groundFlow() {
        return IntegrationFlows.from( "butterflyChannel" )
                .split()
                .handle( "TransformationService", "transformToGround" )
                .aggregate()
                .channel( "groundChannel" )
                .get();
    }
}

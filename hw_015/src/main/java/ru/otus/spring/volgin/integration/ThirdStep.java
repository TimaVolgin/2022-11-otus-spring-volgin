package ru.otus.spring.volgin.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.volgin.domain.Butterfly;
import ru.otus.spring.volgin.domain.Caterpillar;

import java.util.Collection;

@MessagingGateway
public interface ThirdStep {

    @Gateway(requestChannel = "cocoonChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Caterpillar> eaggCollection);
}

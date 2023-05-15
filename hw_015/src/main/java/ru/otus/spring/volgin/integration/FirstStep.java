package ru.otus.spring.volgin.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.volgin.domain.Caterpillar;
import ru.otus.spring.volgin.domain.Egg;

import java.util.Collection;

@MessagingGateway
public interface FirstStep {

    @Gateway(requestChannel = "eggsChannel", replyChannel = "caterpillarChannel")
    Collection<Caterpillar> process(Collection<Egg> eaggCollection);
}

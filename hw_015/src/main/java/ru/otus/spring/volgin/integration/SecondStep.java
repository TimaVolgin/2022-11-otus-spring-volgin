package ru.otus.spring.volgin.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.volgin.domain.Caterpillar;
import ru.otus.spring.volgin.domain.Cocoon;

import java.util.Collection;

@MessagingGateway
public interface SecondStep {

    @Gateway(requestChannel = "caterpillarChannel", replyChannel = "cocoonChannel")
    Collection<Cocoon> process(Collection<Caterpillar> eaggCollection);
}

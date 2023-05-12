package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Butterfly;
import ru.otus.spring.volgin.domain.Caterpillar;
import ru.otus.spring.volgin.domain.Cocoon;
import ru.otus.spring.volgin.domain.Egg;
import ru.otus.spring.volgin.domain.Ground;

public interface TransformationService {

    /**
     * Превращение в гусеницу
     * @param egg яйцо
     * @return гусеница
     */
    Caterpillar transformToCaterpillar(Egg egg);

    /**
     * Превращение в кокон бабочки
     * @param caterpillar гусеница
     * @return кокон бабочки
     */
    Cocoon transformToCocoon(Caterpillar caterpillar);

    /**
     * Превращение в бабочку
     * @param cocoon кокон бабочки
     * @return бабочка
     */
    Butterfly transformToButterfly(Cocoon cocoon);

    /**
     * Превращению в землю
     * @param butterfly бабочка
     * @return земля
     */
    Ground transformToGround(Butterfly butterfly);
}

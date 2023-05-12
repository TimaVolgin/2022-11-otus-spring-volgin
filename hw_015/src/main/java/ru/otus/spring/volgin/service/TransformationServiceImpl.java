package ru.otus.spring.volgin.service;

import ru.otus.spring.volgin.domain.Butterfly;
import ru.otus.spring.volgin.domain.Caterpillar;
import ru.otus.spring.volgin.domain.Cocoon;
import ru.otus.spring.volgin.domain.Egg;
import ru.otus.spring.volgin.domain.Ground;

public class TransformationServiceImpl implements TransformationService {

    @Override
    public Caterpillar transformToCaterpillar(Egg egg) {
        return new Caterpillar();
    }

    @Override
    public Cocoon transformToCocoon(Caterpillar caterpillar) {
        return new Cocoon();
    }

    @Override
    public Butterfly transformToButterfly(Cocoon cocoon) {
        return new Butterfly();
    }

    @Override
    public Ground transformToGround(Butterfly butterfly) {
        return new Ground();
    }
}

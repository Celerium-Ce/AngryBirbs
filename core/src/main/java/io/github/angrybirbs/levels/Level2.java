package io.github.angrybirbs.levels;

import io.github.angrybirbs.Main;
import io.github.angrybirbs.entities.*;

public class Level2 extends Level {

    public Level2(Main game) {
        super(game);
    }

    @Override
    protected void loadLevelData() {
        birds.add(new Red(300, 250));
        birds.add(new Blue(250, 150));
        birds.add(new Yellow(150, 150));
        birds.add(new Red(50, 150));

        pigs.add(new Normal(1850, 150));
        pigs.add(new King(1750, 150));
        pigs.add(new General(1650, 150));
        pigs.add(new Normal(1550, 150));
    }
}

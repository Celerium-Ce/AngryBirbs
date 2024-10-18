package io.github.angrybirbs.levels;

import io.github.angrybirbs.entities.Bird;
import io.github.angrybirbs.entities.Normal;
import io.github.angrybirbs.entities.Pig;
import io.github.angrybirbs.entities.Red;
import io.github.angrybirbs.entities.Blue;
import io.github.angrybirbs.entities.Yellow;
import io.github.angrybirbs.entities.King;
import io.github.angrybirbs.entities.General;

public class Level3 extends Level {

    public Level3(Main game) {
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

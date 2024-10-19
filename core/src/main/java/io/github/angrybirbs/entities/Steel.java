package io.github.angrybirbs.entities;

public class Steel extends Material {

    public Steel(float x, float y, boolean isHorizontal) {
        super(isHorizontal ? "entities/steelH.png" : "entities/steelV.png", x, y);
    }
}

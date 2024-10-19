package io.github.angrybirbs.entities;

public class Ice extends Material {

    public Ice(float x, float y, boolean isHorizontal) {
        super(isHorizontal ? "entities/iceH.png" : "entities/iceV.png", x, y);
    }
}

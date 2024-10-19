package io.github.angrybirbs.entities;

public class Wood extends Material {

    public Wood(float x, float y, boolean isHorizontal) {
        super(isHorizontal ? "entities/woodH.png" : "entities/woodV.png", x, y);
    }
}

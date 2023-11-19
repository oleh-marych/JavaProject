package com.oleg.droids.configs;

import javafx.util.Duration;

import static javafx.util.Duration.seconds;

public class MortarConfig {
    public static final Duration SHOOT_DELAY = seconds(4);
    public static final int HP = 350;
    public static final double DAMAGE = 200;
    public static final int BULLET_RANGE = 1200;
    public static final int SPEED = 150;
    public static final int BULLET_SPEED = 600;
    public static final String TEXTURE_URL = "Mortar.png";
    public static final double SLOWDOWN_COEFFICIENT = 4;
}

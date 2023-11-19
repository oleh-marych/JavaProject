package com.oleg.droids.configs;

import javafx.util.Duration;

import static javafx.util.Duration.seconds;

public class GunnerConfig {
    public static final Duration SHOOT_DELAY = seconds(0.15);
    public static final int HP = 100;
    public static final double DAMAGE = 2;
    public static final int BULLET_RANGE = 300;
    public static final int SPEED = 400;
    public static final int BULLET_SPEED = 500;
    public static final String TEXTURE_URL = "Gunner.png";
    public static final double SLOWDOWN_COEFFICIENT = 1.3;
}

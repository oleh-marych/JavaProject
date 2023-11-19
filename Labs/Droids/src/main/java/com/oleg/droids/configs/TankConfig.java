package com.oleg.droids.configs;

import javafx.util.Duration;

import static javafx.util.Duration.seconds;

public class TankConfig {
    public static final Duration SHOOT_DELAY = seconds(1);
    public static final int HP = 200;
    public static final double DAMAGE = 10;
    public static final int BULLET_RANGE = 800;
    public static final int SPEED = 200;
    public static final int BULLET_SPEED = 1000;
    public static final String TEXTURE_URL = "Regular.png";
    public static final double SLOWDOWN_COEFFICIENT = 2;
}

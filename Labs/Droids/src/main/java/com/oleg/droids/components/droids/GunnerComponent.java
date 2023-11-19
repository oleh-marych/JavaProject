package com.oleg.droids.components.droids;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;

import static com.oleg.droids.configs.GunnerConfig.*;

/**
 * Gunner droid
 *  // TODO description
 */
public class GunnerComponent extends AbstractDroidComponent {

    public GunnerComponent() {
        super();
        speed = SPEED;
        textureURL = TEXTURE_URL;
        maxHP = HP;
        shootDelay = SHOOT_DELAY;
        bulletRange = BULLET_RANGE;
        bulletSpeed = BULLET_SPEED;
        bulletDamage = DAMAGE;
    }

    @Override
    public void onAdded() {
        super.onAdded();
        movableComponent.setSlowdownCoefficient(SLOWDOWN_COEFFICIENT);
    }

    @Override
    public void shoot(Point2D direction) {
        if (weaponTimer.elapsed(shootDelay)) {
            direction = direction.subtract(entity.getCenter());

            var position = normalizeBulletPosition(20, direction);

            var randomAngle = FXGLMath.random(-0.4, 0.4);

            var newDirection = new Point2D(
                    direction.getX() * FXGLMath.cos(randomAngle) - direction.getY() * FXGLMath.sin(randomAngle),
                    direction.getX() * FXGLMath.sin(randomAngle) + direction.getY() * FXGLMath.cos(randomAngle)
            );

            var data = setBulletData(new SpawnData(position.getX(), position.getY()), newDirection, bulletSpeed,
                    (double) bulletRange / bulletSpeed, bulletDamage, FXGLMath.random(4.5, 6.0));

            spawnBullet(data);

            weaponTimer.capture();
        }
    }
}

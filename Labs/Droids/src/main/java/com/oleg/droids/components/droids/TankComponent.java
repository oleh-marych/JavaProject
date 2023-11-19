package com.oleg.droids.components.droids;

import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;

import static com.oleg.droids.configs.TankConfig.*;

/**
 * Gunner droid
 *  // TODO description
 */
public class TankComponent extends AbstractDroidComponent {

    public TankComponent() {
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

            var position = normalizeBulletPosition(1, direction);

            var data = setBulletData(new SpawnData(position.getX(), position.getY()), direction, bulletSpeed,
                    (double) bulletRange / bulletSpeed, bulletDamage,8.0);

            spawnBullet(data);

            weaponTimer.capture();
        }
    }
}

package com.oleg.droids.components.droids;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static com.oleg.droids.configs.MortarConfig.*;

public class MortarComponent extends AbstractDroidComponent {
    public MortarComponent() {
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

            var position = normalizeBulletPosition(60, direction);

            var data = setBulletData(new SpawnData(position.getX(), position.getY()), direction, bulletSpeed,
                    (double) bulletRange / bulletSpeed, bulletDamage,20.0);

            spawnBullet(data);

            weaponTimer.capture();
        }
    }
}

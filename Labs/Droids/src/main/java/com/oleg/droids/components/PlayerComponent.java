package com.oleg.droids.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.oleg.droids.components.droids.AbstractDroidComponent;
import javafx.geometry.Point2D;

public class PlayerComponent extends Component {
    private Point2D direction;
    private AbstractDroidComponent droidComponent;
    public MovableComponent movableComponent;

    @Override
    public void onAdded() {
        direction = entity.getCenter().subtract(FXGL.getInput().getMousePositionUI()).normalize();
        droidComponent = entity.getObject("DroidType");
    }

    @Override
    public void onUpdate(double tpf) {
        direction = entity.getCenter().subtract(FXGL.getInput().getMousePositionWorld()).normalize();
        entity.rotateToVector(direction.multiply(-1));
    }

    public void up() {
        movableComponent.move(MovableComponent.UP);
    }
    public void down() {
        movableComponent.move(MovableComponent.DOWN);
    }
    public void left() {
        movableComponent.move(MovableComponent.LEFT);
    }
    public void right() {
        movableComponent.move(MovableComponent.RIGHT);
    }

    public void stop(Point2D direction) {
        movableComponent.stop(direction);
    }

    public void shoot() {
        droidComponent.shoot(FXGL.getInput().getMousePositionWorld());
    }
}

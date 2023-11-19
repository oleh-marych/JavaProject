package com.oleg.droids.components;

import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.paint.Color;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class HPViewComponent extends ChildViewComponent {
    public HealthDoubleComponent HPComponent;

    public HPViewComponent() {
        super( 0, 0, false);
    }

    @Override
    public void onAdded() {
        super.onAdded();

        super.setX(entity.getWidth() / 3 - entity.getWidth() / 3);
        super.setY(entity.getHeight() + 10);

        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(HPComponent.getMaxValue());
        hpView.setWidth(entity.getWidth() / 1.5);
        hpView.currentValueProperty().bind(HPComponent.valueProperty());

        getViewRoot().getChildren().add(hpView);
    }
}

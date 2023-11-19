package com.oleg.droids.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.oleg.droids.configs.GameConfig.OUTSIDE_DISTANCE;
import static com.oleg.droids.configs.GameConfig.BACKGROUND_COLOR;

public class BackgroundComponent extends Component {
    @Override
    public void onAdded() {
        getGameScene().setBackgroundColor(BACKGROUND_COLOR.darker());
        int     width = getAppWidth() + OUTSIDE_DISTANCE * 2,
                height =  getAppHeight() + OUTSIDE_DISTANCE * 2;
        Canvas bg = new Canvas(width, height);
        var graphic = bg.getGraphicsContext2D();

        graphic.setFill(BACKGROUND_COLOR);
        graphic.fillRect(OUTSIDE_DISTANCE, OUTSIDE_DISTANCE, getAppWidth(), getAppHeight());

        graphic.setStroke(Color.rgb(74, 79, 76, 0.1));
        graphic.setLineWidth(1.5);

        for (int i = 0; i * 90 < width; i++) {
            graphic.strokeLine(i * 90, 0, i * 90, height);
        }
        for (int i = 0; i * 90 < height; i++) {
            graphic.strokeLine(0, i*90, width, i*90);
        }

        graphic.setLineWidth(2);
        graphic.strokeRect(OUTSIDE_DISTANCE, OUTSIDE_DISTANCE, getAppWidth(), getAppHeight());

        entity.getViewComponent().addChild(bg);
    }
}

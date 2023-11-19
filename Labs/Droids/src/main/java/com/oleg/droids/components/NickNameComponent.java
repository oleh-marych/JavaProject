package com.oleg.droids.components;

import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NickNameComponent extends ChildViewComponent {
    private String name;

    public NickNameComponent(String name) {
        super(0, 0, false);
        this.name = name;
    }

    @Override
    public void onAdded() {
        super.onAdded();

        var nickName = new Text(name);
        nickName.setFont(Font.font("Century Gothic", FontWeight.BOLD, FontPosture.REGULAR, 18));

        nickName.setFill(Color.WHITE);
        nickName.setStrokeWidth(1);
        nickName.setStroke(Color.BLACK);

        super.setX(entity.getWidth() / 3 - nickName.getLayoutBounds().getWidth() / 2);
        super.setY(-20);

        getViewRoot().getChildren().add(nickName);
    }
}

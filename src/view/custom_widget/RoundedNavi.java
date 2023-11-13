package view.custom_widget;

import javafx.scene.shape.Circle;

public class RoundedNavi extends NavigationButton {
    public RoundedNavi(String iconName, String message){
        super(iconName, message);

        setPrefHeight(60);
        setShape(new Circle(40));
    }
}

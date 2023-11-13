package view.custom_widget;

import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import util.ResourceHandler;

public class NavigationButton extends Button {
    protected ImageView graphicView;
    protected String iconName;
    protected Consumer<ActionEvent> callback = (ev -> {}); // Vacío

    protected static NavigationButton selectedButton;

    public NavigationButton(String iconName, String message){
        this.iconName = iconName;
        this.graphicView = new ImageView(ResourceHandler.getNormalModeIcon(iconName));

        // Visual
        setGraphic(this.graphicView);
        setTooltip(new Tooltip(message));
        setStyle("-fx-background-color: transparent");
        setCursor(Cursor.HAND);

        setOnAction(ev -> {
            select();
            callback.accept(ev);
        });
    }

    public void onClick(Consumer<ActionEvent> func){
        this.callback = func;
    }

    public void select(){
        setGraphic(new ImageView(ResourceHandler.getSelectedModeIcon(iconName)));
        
        NavigationButton selected = getSelectedButton();
        if(selected != null && selected != this){
            selected.deselect();
        }
        setSelectedButton(this);
    }

    public void deselect() {
        setGraphic(new ImageView(ResourceHandler.getNormalModeIcon(iconName)));
    }

    public static NavigationButton getSelectedButton(){ return selectedButton; }
    public static void setSelectedButton(NavigationButton selectedButton) {
        NavigationButton.selectedButton = selectedButton;
    }
}

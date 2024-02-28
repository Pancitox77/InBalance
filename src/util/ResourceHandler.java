package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class ResourceHandler {
    public static final String DIARY_ICON = "registros";
    public static final String BALANCES_ICON = "balances";
    public static final String ADD_ICON = "agregar";
    public static final String INVENTORY_ICON = "inventario";
    public static final String MENU_ICON = "menu";

    private ResourceHandler() {
    }

    public static AnchorPane getView(String view) {
        try {
            return (AnchorPane) FXMLLoader.load(ResourceHandler.class.getResource("/view/" + view + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image getNormalModeIcon(String name) {
        return new Image(ResourceHandler.class.getResourceAsStream("/res/images/normal_icon/" + name + ".png"));
    }

    public static Image getSelectedModeIcon(String name) {
        return new Image(ResourceHandler.class.getResourceAsStream("/res/images/selected_icon/" + name + ".png"));
    }
}

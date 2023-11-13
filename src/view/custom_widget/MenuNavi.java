package view.custom_widget;

import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import util.ResourceHandler;

public class MenuNavi extends NavigationButton {    
    public MenuNavi(String iconName, MenuItem... items){
        super(iconName, "Menu");
        setGraphic(new ImageView(ResourceHandler.getNormalModeIcon(iconName)));
        
        ContextMenu menu = new ContextMenu(items);
        setContextMenu(menu);

        setOnAction(ev -> {
            select();
            Point2D screen = localToScreen(getLayoutX(), getLayoutY());
            menu.show(this, screen.getX() - 400, screen.getY() - 20);
        });
    }
}

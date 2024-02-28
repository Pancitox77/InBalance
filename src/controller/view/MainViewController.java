package controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.ResourceHandler;
import view.custom_widget.MenuNavi;
import view.custom_widget.NavigationButton;
import view.custom_widget.RoundedNavi;

public class MainViewController {
    private static MainViewController instance;

    @FXML
    private ScrollPane contentPane;
    @FXML
    private HBox navigationBar;

    /* Vistas/Paneles */
    private AnchorPane activePane;

    private AnchorPane balancesPane;
    private AnchorPane createPane1;
    private AnchorPane inventoryPane;
    private AnchorPane preferencesPane;

    @FXML
    public void initialize() {
        balancesPane = ResourceHandler.getView("BalancesView");
        createPane1 = ResourceHandler.getView("CreateView1");
        inventoryPane = ResourceHandler.getView("InventoryView");
        preferencesPane = ResourceHandler.getView("PreferencesView");

        initNavigationBar();
        setView(createPane1);
    }

    private void setView(AnchorPane view) {
        if (activePane == view)
            return;
        activePane = view;
        contentPane.setContent(view);
    }

    private void initNavigationBar() {
        NavigationButton diaryButton = new NavigationButton(ResourceHandler.DIARY_ICON, "Registros");
        NavigationButton balancesButton = new NavigationButton(ResourceHandler.BALANCES_ICON, "Balance");
        RoundedNavi addButton = new RoundedNavi(ResourceHandler.ADD_ICON, "Agregar");
        NavigationButton inventoryButton = new NavigationButton(ResourceHandler.INVENTORY_ICON, "Inventario");
        MenuNavi menuButton = initMenu();

        addButton.select();

        // Funciones
        balancesButton.onClick(ev -> setView(balancesPane));
        addButton.onClick(ev -> setView(createPane1));
        inventoryButton.onClick(ev -> setView(inventoryPane));

        // Agregar a la barra de navegacion
        navigationBar.getChildren().addAll(diaryButton, balancesButton, addButton, inventoryButton, menuButton);
    }

    private MenuNavi initMenu() {
        MenuItem receiptsItem = new MenuItem("Comprobantes");
        MenuItem debtsItem = new MenuItem("Mis deudas");
        SeparatorMenuItem sep1 = new SeparatorMenuItem();
        MenuItem preferencesItem = new MenuItem("Preferencias");
        MenuItem exitItem = new MenuItem("Salir");

        // Funciones
        preferencesItem.setOnAction(ev -> setView(preferencesPane));
        exitItem.setOnAction(ev -> Platform.exit());

        return new MenuNavi(ResourceHandler.MENU_ICON,
                receiptsItem, debtsItem, sep1, preferencesItem, exitItem);
    }

    /* Getters/Setters */
    public static MainViewController getInstance() {
        return instance;
    }

    public static void setInstance(MainViewController instance) {
        MainViewController.instance = instance;
    }
}

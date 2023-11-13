package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TestUIController {
    @FXML
    private ListView<MenuItem> listView;
    @FXML
    private TreeView<String> treeView;

    @FXML
    public void initialize(){
        String abc = "abcdefg";
        String[] chars = abc.split("");

        initListView(chars);
            

        // init tree
        TreeItem<String> root = new TreeItem<>("abc");
        treeView.setRoot(root);
        
        TreeItem<String> mercItem = new TreeItem<>("Mercadería");
        mercItem.setExpanded(true);
        root.getChildren().add(mercItem);
        ObservableList<TreeItem<String>> merc = FXCollections.observableArrayList(
            new TreeItem("Comprar"),
            new TreeItem("Vender")
        );
        mercItem.getChildren().addAll(merc);
    }

    private void initListView(String[] chars){
        for (String i: chars){
            MenuItem label = new MenuItem(i);
            listView.getItems().add(label);
            if(i.equals("d")){
                SeparatorMenuItem sep = new SeparatorMenuItem();
                sep.setText("----- Mercadería -----");
                listView.getItems().add(sep);
            }
        }
        listView.setCellFactory(new CustomCellConverter());
    }


    class CustomCellConverter implements Callback<ListView<MenuItem>,ListCell<MenuItem>> {
        @Override
        public ListCell<MenuItem> call(ListView<MenuItem> param) {
            TextFieldListCell<MenuItem> cell = new TextFieldListCell<>();
            cell.setConverter(new StringConverter<MenuItem>() {
                @Override
                public String toString(MenuItem object) {
                    return object == null ? "" : object.getText();
                }

                @Override
                public MenuItem fromString(String string) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            return cell;
        }
    }
}

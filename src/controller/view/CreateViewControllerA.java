package controller.view;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

public class CreateViewControllerA {
    @FXML
    private DatePicker datePicker;

    @FXML private void initialize(){
        datePicker.setValue(LocalDate.now());
    }
}

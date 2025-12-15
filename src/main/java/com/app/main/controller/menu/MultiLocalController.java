package com.app.main.controller.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MultiLocalController {
    @FXML
    public void duo(ActionEvent event) {
        System.out.println("Lancement du mode 1v1");
    }

    @FXML
    public void trio(ActionEvent event) {
        System.out.println("Lancement du mode 1v1vBOT");
    }

    @FXML
    public void squad(ActionEvent event) {
        System.out.println("Lancement du mode 1v1vBOT1vBOT2");
    }
}

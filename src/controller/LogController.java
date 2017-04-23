package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Main;

public class LogController {
	private Main main;
	
    @FXML
    private TextArea logArea;

    @FXML
    void goToMenu(ActionEvent event) {
    	main.createMenu();
    }

    public void setMain(Main main) {
    	this.main = main;
    }
    
    public void updateLog(String string) {
    	logArea.setText(string);
    }
}

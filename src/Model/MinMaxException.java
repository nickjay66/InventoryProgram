/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.control.Alert;

/**
 *
 * @author nick
 */
public class MinMaxException extends Exception {
    
    public MinMaxException() {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Maximum cannot be less than minimum");

            alert.showAndWait();
    }
    
    public MinMaxException(String errorMessage) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Error");
          alert.setHeaderText("Error");
          alert.setContentText(errorMessage);
          
          alert.showAndWait();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.MinMaxException;
import Model.OutSourced;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class AddPartController implements Initializable {
    
 Stage stage;
 Parent scene;
 static int autoGenPartId;


    @FXML
    private RadioButton inHouseBttn;

    @FXML
    private RadioButton outsourcedBttn;

    @FXML
    private TextField IdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField invTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField coNameTxt;

    @FXML
    private TextField minTxt;
    
    @FXML
    private Label coNameLabel;

    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
    }

    @FXML
    void OnActionSavePart(ActionEvent event) throws IOException, MinMaxException {
        
          
        int id = Integer.parseInt(IdTxt.getText());
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(invTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        
        //throw exception
        if (max < min) {
            throw new MinMaxException();
        }
          
        
        if (inHouseBttn.isSelected()) {
            int machineID = Integer.parseInt(coNameTxt.getText());
            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
        } else {
            String companyName = coNameTxt.getText();
            Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
        }
          
      
        
//Will return to Main menu when saved is click
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
              
         
    }
        
    
    //Made to possibly change text when radio button is clicked
   @FXML
    void onActionInHouse(ActionEvent event) {
        
        coNameLabel.setText("Machine ID");   
      
    }

    @FXML
   void onActionOutsourced(ActionEvent event) {
       
        coNameLabel.setText("Company Name");
    }
   
   
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        IdTxt.setDisable(true);
        autoGenPartId = autoGenPartId + 1;
        IdTxt.setText(Integer.toString(autoGenPartId));
       
    }  
    
    

}
    


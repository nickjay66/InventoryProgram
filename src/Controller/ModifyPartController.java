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
import Model.Part;
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
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton modInHouseBttn;

    @FXML
    private RadioButton modOutsourcedBttn;

    @FXML
    private TextField modIdBttn;

    @FXML
    private TextField modNameBttn;

    @FXML
    private TextField modInvBttn;

    @FXML
    private TextField modPriceBttn;

    @FXML
    private TextField modMaxBttn;

    @FXML
    private TextField modCoNameBttn;

    @FXML
    private TextField modMinBttn;
    
    @FXML
    private Label modCoNameLbl;

    @FXML
    void OnActionCancelMod(ActionEvent event) throws IOException {
        
        int id = Integer.parseInt(modIdBttn.getText());
        String name = modNameBttn.getText();
        double price = Double.parseDouble(modPriceBttn.getText());
        int stock = Integer.parseInt(modInvBttn.getText());
        int min = Integer.parseInt(modMinBttn.getText());
        int max = Integer.parseInt(modMaxBttn.getText());
        
        if (modInHouseBttn.isSelected()) {
            int machineID = Integer.parseInt(modCoNameBttn.getText());
            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
        } else {
            String companyName = modCoNameBttn.getText();
            Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
    }

    @FXML
    void OnActionSaveMod(ActionEvent event) throws IOException, MinMaxException {
        
        int id = Integer.parseInt(modIdBttn.getText());
        String name = modNameBttn.getText();
        double price = Double.parseDouble(modPriceBttn.getText());
        int stock = Integer.parseInt(modInvBttn.getText());
        int min = Integer.parseInt(modMinBttn.getText());
        int max = Integer.parseInt(modMaxBttn.getText());
        
        if (max < min) {
            throw new MinMaxException();
        }
        
        if (modInHouseBttn.isSelected()) {
            int machineID = Integer.parseInt(modCoNameBttn.getText());
            Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
        } else {
            String companyName = modCoNameBttn.getText();
            Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
        }
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene((Parent) scene)); // sets the stage.
        stage.show();
        

    }
    
    public void setPart(Part part) {
       //sets the part automatically from the mainScreen Controller when modifying
       //casting part object to either InHouse or OutSourced
       
     if (part instanceof InHouse) {
         modInHouseBttn.setSelected(true);
         InHouse house = (InHouse) part;
         modCoNameBttn.setText(Integer.toString(house.getMachineId()));
     } else {
         modOutsourcedBttn.setSelected(true);
         OutSourced source = (OutSourced) part;
          modCoNameBttn.setText(source.getCompanyName());
     }
       
       modIdBttn.setText(Integer.toString(part.getId()));
       modNameBttn.setText(part.getName());
       modPriceBttn.setText(Double.toString(part.getPrice()));
       modInvBttn.setText(Integer.toString(part.getStock()));
       modMinBttn.setText(Integer.toString(part.getMin()));
       modMaxBttn.setText(Integer.toString(part.getMax()));
       
       
    }
    
    @FXML
    void onActionInHouse(ActionEvent event) {
         modCoNameLbl.setText("Machine ID");
    }

    @FXML
    void onActionOutSourced(ActionEvent event) {
         modCoNameLbl.setText("Company Name");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modIdBttn.setDisable(true);
    }    
    
}

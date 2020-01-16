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
import Model.Product;
import java.io.IOException;
import java.net.URL;
import static java.nio.file.Files.delete;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nick
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    ObservableList<Part> tempPartsHolder = FXCollections.observableArrayList();

    @FXML
    private TextField SearchProductsTXT;

    @FXML
    private TextField modProdIdTxt;

    @FXML
    private TextField modProdNameTxt;

    @FXML
    private TextField modProdInvTxt;

    @FXML
    private TextField modProdPriceTxt;

    @FXML
    private TextField modProdMaxTxt;

    @FXML
    private TextField modProdMinTxt;
    
    @FXML
    private TableView<Part> ModProdPartsTableFXID;

    @FXML
    private TableColumn<Part,Integer> ModProdPartCol;

    @FXML
    private TableColumn<Part, String> ModProdPartName;

    @FXML
    private TableColumn<Part, Integer> ModProdPartInv;

    @FXML
    private TableColumn<Part, Double> ModProdPartPrice;
    
     @FXML
    private TableView<Part> partTableFXId;

    @FXML
    private TableColumn<Part, Integer> IdCol;

    @FXML
    private TableColumn<Part, String> nameCol;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TableColumn<Part, Double> priceCol;
    
    @FXML
    private Button DeleteBttn;

    @FXML
    void OnActionAddPart(ActionEvent event) {
        
        tempPartsHolder = ModProdPartsTableFXID.getItems();

        Part part = partTableFXId.getSelectionModel().getSelectedItem();
        
        /*int id = Integer.parseInt(modProdIdTxt.getText());
        String name = modProdNameTxt.getText();
        double price = Double.parseDouble(modProdPriceTxt.getText());
        int stock = Integer.parseInt(modProdInvTxt.getText());
        int min = Integer.parseInt(modProdMinTxt.getText());
        int max = Integer.parseInt(modProdMaxTxt.getText());
        */
        //Product product = new Product(id, name, price, stock, min, max);
        tempPartsHolder.add(part);
        ModProdPartsTableFXID.setItems(tempPartsHolder);
        
    }

    @FXML
    void OnActionCancelProduct(ActionEvent event) throws IOException {
        
        tempPartsHolder = ModProdPartsTableFXID.getItems();
        
        int id = Integer.parseInt(modProdIdTxt.getText());
        String name = modProdNameTxt.getText();
        double price = Double.parseDouble(modProdPriceTxt.getText());
        int stock = Integer.parseInt(modProdInvTxt.getText());
        int min = Integer.parseInt(modProdMinTxt.getText());
        int max = Integer.parseInt(modProdMaxTxt.getText());
        
        Product product = new Product(id, name, price, stock, min, max);
        
        product.addAssociatedParts(tempPartsHolder);
        Inventory.addProduct(product);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
    }

    @FXML
    void OnActionDeletePart(ActionEvent event) {

       DeleteBttn.setOnAction(e -> {
           Part selectedItem = ModProdPartsTableFXID.getSelectionModel().getSelectedItem();
                               ModProdPartsTableFXID.getItems().remove(selectedItem);
               });  
    }

    @FXML
    void OnActionSaveProduct(ActionEvent event) throws IOException, MinMaxException {
        
        tempPartsHolder = ModProdPartsTableFXID.getItems();

        try {
            
        int id = Integer.parseInt(modProdIdTxt.getText());
        String name = modProdNameTxt.getText();
        double price = Double.parseDouble(modProdPriceTxt.getText());
        int stock = Integer.parseInt(modProdInvTxt.getText());
        int min = Integer.parseInt(modProdMinTxt.getText());
        int max = Integer.parseInt(modProdMaxTxt.getText());
        
        Product product = new Product(id, name, price, stock, min, max);
        //Exception for no parts added
        if (tempPartsHolder.isEmpty() == true) {
          throw new MinMaxException("All products must have at least one part");
        }
        //exception for max less than min
        if (max < min) {
            throw new MinMaxException();
        }
        //exception for price of parts exceeding price of product
        double total = 0;
        for (Part part: tempPartsHolder) {
            total = total + part.getPrice();
        }
        if (total > product.getPrice()) {
            throw new MinMaxException("Total price of parts cannot exceed price of product");
        }
        
        product.addAssociatedParts(tempPartsHolder);
        Inventory.addProduct(product);
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene((Parent) scene)); // sets the stage.
        stage.show();
        
        } catch(NumberFormatException e) {
            System.out.println("Please enter all information");
        }
         
    }

    @FXML
    void OnActionSearchProducts(ActionEvent event) {

        int userSearch = Integer.parseInt(SearchProductsTXT.getText());
        partTableFXId.getSelectionModel().select(Inventory.lookupPart(userSearch));
    }
    
    public void setProduct(Product product) {
        
       modProdIdTxt.setText(Integer.toString(product.getId()));
       modProdNameTxt.setText(product.getName());
       modProdPriceTxt.setText(Double.toString(product.getPrice()));
       modProdInvTxt.setText(Integer.toString(product.getStock()));
       modProdMinTxt.setText(Integer.toString(product.getMin()));
       modProdMaxTxt.setText(Integer.toString(product.getMax()));
       
       ModProdPartsTableFXID.setItems(product.getAllAssociatedParts());
       
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partTableFXId.setItems(Inventory.getAllParts());
        
        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        ModProdPartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modProdIdTxt.setDisable(true);
    }    
    
}

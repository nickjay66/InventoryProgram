/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import static Controller.AddPartController.autoGenPartId;
import Model.InHouse;
import Model.Inventory;
import Model.MinMaxException;
import Model.OutSourced;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    static private int autoGenProductId;
    ObservableList<Part> tempPartsHolder = FXCollections.observableArrayList();
    
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
    private TableView<Part> ProdPartsTableFXID;

    @FXML
    private TableColumn<Part, Integer> ProdPartsIdCol;

    @FXML
    private TableColumn<Part, String> ProdPartsNameCol;

    @FXML
    private TableColumn<Part, Integer> ProdPartsInvCol;

    @FXML
    private TableColumn<Part, Double> ProdPartsPriceCol;

    @FXML
    private TextField prodIdTxt;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private TextField prodInvTxt;
    
    @FXML
    private TextField prodPriceTxt;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private TextField prodMinTxt;

    @FXML
    private TextField SearchProductsTXT;
    
    @FXML
    private Button DeleteBttn;

    @FXML
    void OnActionAddPartToProd(ActionEvent event) {
        
       
        int id = Integer.parseInt(prodIdTxt.getText());
        String name = prodNameTxt.getText();
        double price = Double.parseDouble(prodPriceTxt.getText());
        int stock = Integer.parseInt(prodInvTxt.getText());
        int min = Integer.parseInt(prodMinTxt.getText());
        int max = Integer.parseInt(prodMaxTxt.getText());
        
        Product product = new Product(id, name, price, stock, min, max);
        Part part = partTableFXId.getSelectionModel().getSelectedItem();
        
        //product.addAssociatedPart(part);
        
        tempPartsHolder.add(part);
      
        ProdPartsTableFXID.setItems(tempPartsHolder);
            
          
    }
    @FXML
    void OnActionCancelProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
    }

   /* @FXML
    void OnActionDeleteProduct(ActionEvent event) {

    }*/

    @FXML
    void OnActionSaveProduct(ActionEvent event) throws IOException, MinMaxException {

        
        
        try {
           //user wrapper class method to convert string to int
        int id = Integer.parseInt(prodIdTxt.getText());
        String name = prodNameTxt.getText();
        double price = Double.parseDouble(prodPriceTxt.getText());
        int stock = Integer.parseInt(prodInvTxt.getText());
        int max = Integer.parseInt(prodMaxTxt.getText());
        int min = Integer.parseInt(prodMinTxt.getText());
        //String coName = coNameTxt.getText();
        //boolean isInHouse;
        //do something for inhouse and outsourced
        Product product = new Product(id, name, price, stock, min, max);
        product.addAssociatedParts(tempPartsHolder);
       
        if (tempPartsHolder.isEmpty() == true) {
          throw new MinMaxException("All products must have at least one part");
        }
        
        if (max < min) {
            throw new MinMaxException();
        }
        //Exception to check that part total is not more than product total
        double total = 0;
        for (Part part: tempPartsHolder) {
            total = total + part.getPrice();
        }
        if (total > product.getPrice()) {
            throw new MinMaxException("Total price of parts cannot exceed price of product");
        }
       
        
          Inventory.addProduct(product);
        
            
               
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/nicholasjerremsc482pa/mainScreen.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
        
        } catch(NumberFormatException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please make sure all fields are filled out!");

            alert.showAndWait();
            System.out.println("Product must contain all fields.");
            
        }
        
    }
    
    @FXML
    void OnActionDeletePart(ActionEvent event) {

        DeleteBttn.setOnAction(e -> {
           Part selectedItem = ProdPartsTableFXID.getSelectionModel().getSelectedItem();
                               ProdPartsTableFXID.getItems().remove(selectedItem);
               }); 
     }  
    
    
     @FXML
    void OnActionSearchProducts(ActionEvent event) {

        int userSearch = Integer.parseInt(SearchProductsTXT.getText());
        partTableFXId.getSelectionModel().select(Inventory.lookupPart(userSearch));
    }


      @Override
    public void initialize(URL url, ResourceBundle rb) {
        prodIdTxt.setDisable(true);
        autoGenProductId = autoGenProductId + 1;
        prodIdTxt.setText(Integer.toString(autoGenProductId));
        
        //Sets up part table in AddProduct.fxml
        partTableFXId.setItems(Inventory.getAllParts());
        
        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //ProdPartsTableFXID.setItems(Inventory.getAllParts());
        
        ProdPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProdPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }  
    
}

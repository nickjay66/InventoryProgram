/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    
    @FXML
    public TableView<Part> partTableFXId;
    
    @FXML
    private TableColumn<Part, Integer> IdCol;

    @FXML
    private TableColumn<Part, String> nameCol;

    @FXML
    private TableColumn<Part, Integer> invCol;

    @FXML
    private TableColumn<Part, Double> priceCol;
    
    @FXML
    private TableView<Product> productTableViewFXId;

    @FXML
    private TableColumn<Product, Integer> prodPartId;

    @FXML
    private TableColumn<Product, String> prodPartName;

    @FXML
    private TableColumn<Product, Integer> prodInv;

    @FXML
    private TableColumn<Product, Double> prodPrice;
    
    //@FXML
    //private Button Modify;

    @FXML
    private TextField SearchPartsTXT;

    @FXML
    private TextField SearchProductsTXT;

    @FXML
    void OnActionAddPart(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();
        
    }

    @FXML
    void OnActionAddProduct(ActionEvent event) throws IOException {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml")); //Creates the scene
        stage.setScene(new Scene(scene)); // sets the stage.
        stage.show();

    }

    @FXML
    void OnActionDeletePart(ActionEvent event) {
       
        Inventory.getAllParts().remove(partTableFXId.getSelectionModel().getSelectedItem());
       
    }

    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        
        Inventory.getAllProducts().remove(productTableViewFXId.getSelectionModel().getSelectedItem());
    }

    @FXML
    void OnActionExitIVM(ActionEvent event) {

        System.exit(0);
    }

    @FXML
    void OnActionModPart(ActionEvent event) throws IOException {
        
        Stage stage2;
        Parent root;

        stage2 = (Stage)((Button)event.getSource()).getScene().getWindow();
                //(Stage) Modify.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyPart.fxml"));
        
        
        root = loader.load();
        ModifyPartController controller = loader.getController();
        Part part = partTableFXId.getSelectionModel().getSelectedItem();
        controller.setPart(part);
     
       
        Inventory.getAllParts().remove(partTableFXId.getSelectionModel().getSelectedItem());
        
        Scene scene2 = new Scene(root);
        stage2.setScene(scene2); // sets the stage.
        stage2.show();
        
        
    }

    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException {
        
        Stage stage2;
        Parent root;

        stage2 = (Stage)((Button)event.getSource()).getScene().getWindow();
                //(Stage) Modify.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ModifyProduct.fxml"));
        
        
        root = loader.load();
        ModifyProductController controller = loader.getController();
        Product product = productTableViewFXId.getSelectionModel().getSelectedItem();
        controller.setProduct(product);
        
     
       
        Inventory.getAllProducts().remove(productTableViewFXId.getSelectionModel().getSelectedItem());
        
        Scene scene2 = new Scene(root);
        stage2.setScene(scene2); // sets the stage.
        stage2.show();
        
    }

    @FXML
    void OnActionSearchParts(ActionEvent event) {
        
        int userSearch = Integer.parseInt(SearchPartsTXT.getText());
        partTableFXId.getSelectionModel().select(Inventory.lookupPart(userSearch));
              
    }

    @FXML
    void OnActionSearchProducts(ActionEvent event) {
        
        int userSearch = Integer.parseInt(SearchProductsTXT.getText());
        productTableViewFXId.getSelectionModel().select(Inventory.lookupProduct(userSearch));

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partTableFXId.setItems(Inventory.getAllParts());
        
        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTableViewFXId.setItems(Inventory.getAllProducts());
        
        prodPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        
       
         }    

   
    
}

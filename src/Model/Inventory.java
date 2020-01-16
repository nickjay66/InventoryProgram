/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;




/**
 *
 * @author nick
 */
public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part part) {
        
        allParts.add(part);
      
     }
    
    public static void addProduct (Product product) {
        allProducts.add(product);
    }
    
    public static Part lookupPart(int partId) {
        //do soemthing
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        
        return null;
    }
    
    public static Product lookupProduct(int productId) {
        
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        
        return null;
    }
    
    public ObservableList<Part> lookupPart(String partName) {
       
       for (Part part : allParts) {
           if (part.getName() == partName)
               return allParts;
       } 
        return null;
       
    }
    
    public ObservableList<Product> lookupProduct(String productName) {
        
        for (Product product : allProducts) {
            if (product.getName() == productName) 
                return allProducts;
        }
        return null;
    }
    
    public void updatePart(int index, Part selectedPart) {
        
        for (Part part : allParts) {
            if (allParts.indexOf(part) == index) {
                allParts.set(index, selectedPart);
            }
        }
    }
    
    public void updateProduct(int index, Product selectedProduct) {
        
        for (Product product : allProducts) {
            if (allProducts.indexOf(product) == index) {
                allProducts.set(index, selectedProduct);
            }
        }
    }
    
    public void deletePart(Part selectedPart) {
        
        allParts.remove(selectedPart);
        
    
    }
    
    public void deleteProduct(Product selectedProduct) {
        
        allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts() {
       
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        
        return allProducts;
    }
}

/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.piattiCollegati;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="boxIngrediente"
    private ComboBox<Condiment> boxIngrediente; // Value injected by FXMLLoader

    @FXML // fx:id="btnDietaEquilibrata"
    private Button btnDietaEquilibrata; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Dieta equilibrata, max calorie\n");
    	Condiment c=boxIngrediente.getValue();
    	if(c!=null) {
    		List<Condiment> listaCal=this.model.calorieMax(c);
    		for (Condiment condiment : listaCal) {
				txtResult.appendText(""+condiment);
			}
		}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	boxIngrediente.getItems().clear();
    	double calorie=0;
    	try {
			calorie=Double.parseDouble(txtCalorie.getText());
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un numero valido!");
			System.out.println("Inserire un numero valido!");
		}
    	this.model.creaGrafo(calorie);
    	List<piattiCollegati> listaCal=this.model.listaPiatti();
    	for(piattiCollegati pt: listaCal) {
    		txtResult.appendText(""+pt);
    		this.boxIngrediente.getItems().add(pt.getC());
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model=model;
	}
}


/*
 * //txtResult.clear();
    	double calorie=0;
    	try {
			calorie=Double.parseDouble(txtCalorie.getText());
		} catch (NumberFormatException e) {
			System.out.println("Inserire un numero valido!");
		}
    	this.model.creaGrafo(calorie);
    	this.boxIngrediente.getItems().addAll(this.model.getGrafo().vertexSet());
    	List<piattiCollegati> listaCal=this.model.listaPiatti();
    	for(piattiCollegati pt: listaCal) {
    		txtResult.appendText(""+pt);
    	}
 */
    		

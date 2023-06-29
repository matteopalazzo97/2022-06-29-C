/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.AlbumBilancio;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<?> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    
    
    //controlli standard comboBox
    
    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(this.cmbA1.getValue() == null) {
    		this.txtResult.setText("Seleziona un valore dalla tendina.");
    	} else {
    		List<AlbumBilancio> result = this.model.getBilanci(this.cmbA1.getValue());

    		for(AlbumBilancio a: result) {
    			this.txtResult.appendText(a.toString() + "\n");
    		}
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    }

    
    
    
    /*
     * controlli super standard per inserimento numero in casella di testo
     * 
     * creazione grafo con stampa vertici e archi
     */
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.cmbA1.getItems().clear();
    	
    	int n = 0;
    	
    	if(this.txtN.getText().equals("")) {
    		this.txtResult.setText("Inserire un valore per n.");
    		return;
    	}
    	
    	try {
    		n = Integer.parseInt(this.txtN.getText());
    		this.model.creaGrafo(n);
    		
    		this.txtResult.setText("Grafo creato.\n");
    		this.txtResult.appendText("# vertici: " + this.model.getNumVertici() + "\n");
    		this.txtResult.appendText("# archi:   " + this.model.getNumArchi() + "\n");
    		
    		this.cmbA1.getItems().addAll(this.model.getVertici());

    		
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore numerico per n.");
    		return;
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}

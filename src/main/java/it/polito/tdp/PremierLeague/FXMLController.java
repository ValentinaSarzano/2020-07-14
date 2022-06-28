/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import it.polito.tdp.PremierLeague.model.Vicino;
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

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	txtResult.clear();
    	Team t = cmbSquadra.getValue();
    	
    	if(t == null) {
    		txtResult.setText("Scegliere una squadra!");
    		return;
    	}
    	
    	txtResult.appendText("SQUADRE MIGLIORI:\n");
    	for(Vicino vv: this.model.getSquadreMigliori(t)) {

        	txtResult.appendText(vv + " " + vv.getPeso() + "\n");
    	}
    	
    	txtResult.appendText("\n SQUADRE PEGGIORI:\n");
    	for(Vicino v: this.model.getSquadreBattute(t)) {

        	txtResult.appendText(v + " " + v.getPeso() + "\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	
    	txtResult.clear();
    	this.model.CreaGrafo();
    	
    	btnClassifica.setDisable(false);
    	
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("#VERTICI: " + this.model.nVertici() + "\n");
    	txtResult.appendText("#ARCHI: " + this.model.nArchi());
    
    }

    @FXML
    void doSimula(ActionEvent event) {

    	txtResult.clear();
    	int N =0;
    	int X =0;
    	try {
    		N = Integer.parseInt(txtN.getText());
    	} catch(NumberFormatException ex) {
    		txtResult.appendText("Errore: inserire un numero valido per N\n");
    		return;
    	}
    	try {
    		X = Integer.parseInt(txtX.getText());
    	} catch(NumberFormatException ex) {
    		txtResult.appendText("Errore: inserire un numero valido per N\n");
    		return;
    	}
    	model.simula(N, X);
    	txtResult.appendText("Numero di partite critiche: "+model.getPartiteCritiche()+"\n");
    	txtResult.appendText("Numero medio di reporter che hanno assistito ad ogni partita: "+model.getMediaReporter());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
   
        btnClassifica.setDisable(true);
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbSquadra.getItems().addAll(this.model.listAllTeams());
    }
}

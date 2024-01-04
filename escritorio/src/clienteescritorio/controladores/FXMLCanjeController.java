/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import modelo.DAO.PromocionDAO;
import modelo.pojo.Mensaje;
import utiles.Utilidades;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLCanjeController implements Initializable {

    @FXML
    private TextField codigoField;
    @FXML
    private Label estado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void canjear(ActionEvent event) {
        Mensaje msj = PromocionDAO.canje(codigoField.getText());
if(!msj.getError()){
    Utilidades.alerta("canjeo exitoso", msj.getContenido(), Alert.AlertType.INFORMATION);
     estado.setText(msj.getContenido());
}
 
else{
    Utilidades.alerta("error al canjear", msj.getContenido(), Alert.AlertType.ERROR);
}
}

    
    
}
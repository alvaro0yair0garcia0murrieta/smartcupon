/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.DAO.PromocionDAO;
import utiles.Utilidades;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLCanjeController implements Initializable {
    private IRespuesta observador;
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
        String msj = PromocionDAO.canje(codigoField.getText());

        Utilidades.alerta("canjeo exitoso", msj, Alert.AlertType.INFORMATION);
        observador.notificarGuardado();
    }

    void init(IRespuesta obs) {
        observador = obs;
    }


}

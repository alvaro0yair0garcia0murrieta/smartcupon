/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.DAO.UsuarioDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;
import utiles.Utilidades;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLLoginController implements Initializable {


    @FXML
    private Button btnInciarSesion;
    @FXML
    private Label lbErrorPersonal;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField PasswordField;

    @FXML
    void handleButtonAction(ActionEvent event) {
        String numeroPesonal = usernameField.getText();
        String password = PasswordField.getText();
        boolean valido = true;
        if (password.isEmpty()) {
            valido = false;
            lbErrorPassword.setText("Error ingrese valore en contraseña");

        }
        if (numeroPesonal.isEmpty()) {
            valido = false;
            lbErrorPersonal.setText("Errot porfavor ingrese numero personal");
        }
        if (valido) {
            verificaSesion(numeroPesonal, password);
        }
    }

    private void verificaSesion(String username, String password) {
        Mensaje respuestaValidacion = UsuarioDAO.validacion(username, password);
        if (respuestaValidacion.getError()) {
            Utilidades.alerta("err or", respuestaValidacion.getContenido(), Alert.AlertType.ERROR);
        } else {
            Utilidades.alerta("credenciales correctas", respuestaValidacion.getContenido(), Alert.AlertType.INFORMATION);

            irPantallaPrincipal(respuestaValidacion.getUsuario());


        }

    }

    private void irPantallaPrincipal(Usuario usuario) {
        try {

            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLHome.fxml"));
            Parent vista = loadVista.load();

            FXMLHomeController controlador = loadVista.getController();
            controlador.inicializarUsuario(usuario);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.setScene(esena);
            stage.setTitle("MAIN");
            stage.show();
        } catch (IOException ex) {

            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritorio.controladores;

import interfaces.Privilegios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.DAO.EmpresaDAO;
import modelo.pojo.Empresa;
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
public class FXMLHomeController implements Initializable, Privilegios {

    private Usuario usuario;
    @FXML
    private ImageView empresaB;
    @FXML
    private ImageView empresaB1;
    @FXML
    private ImageView empresaB111;
    @FXML
    private ImageView empresaB1111;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void inicializarUsuario(Usuario usuario) {
        this.usuario = usuario;


    }

    //  promocion fk empresa fk2 sucu_pro
// suc_emp fk promo fk2 sucur
// sucu fk empresa fk2 sucu_promo
//  para la sucursales muchas tiene que ser un for que repita cada una de inserts recominesa cadena de caracteres y luego cortar los caracteres y metrelos en el for
    @FXML
    private void irASucursalModulo(MouseEvent event) {
        try {

            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLModuloSucursal.fxml"));
            Parent vista = loadVista.load();

            FXMLModuloSucursalController controlador = loadVista.getController();
            controlador.inicializarUsuario(usuario);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("MODULO SUCURSAL");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irModuloEmpresa(MouseEvent event) {
        if (!usuario.privilegios(usuario.getRol())) {
            irAEdicionEmpresa();
        } else {
            try {

                FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLEmpresa.fxml"));
                Parent vista = loadVista.load();


                Stage stage = new Stage();
                Scene esena = new Scene(vista);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(esena);
                stage.setTitle("MODULO Empresa");
                stage.showAndWait();
            } catch (IOException ex) {

                Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void irAEdicionEmpresa() {

        try {
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLModificarEmpresa.fxml"));
            Parent vista = loadVista.load();

            FXMLModificarEmpresaController controlador = loadVista.getController();
            Empresa empresa = EmpresaDAO.obtenerEmpresa(usuario.getIdEmpresa());
            controlador.inicializarCom(empresa);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("MODULO Empresa");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void irAPromocionModulo(MouseEvent event) {
        try {

            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLPromocion.fxml"));
            Parent vista = loadVista.load();

            FXMLPromocionController controlador = loadVista.getController();
            controlador.inicializarUsuario(usuario);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("MODULO Promocion");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irAUsuariosModulo(MouseEvent event) {
        if (usuario.privilegios(usuario.getRol())) {
            try {

                FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLUsuarios2.fxml"));
                Parent vista = loadVista.load();


                Stage stage = new Stage();
                Scene esena = new Scene(vista);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(esena);
                stage.setTitle("MODULO USUARIOS");
                stage.showAndWait();
            } catch (IOException ex) {

                Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Utilidades.alerta("credenciales incorrectas", "solo usuarios Generales pueden aceder al modulo", Alert.AlertType.INFORMATION);
        }


    }

    @FXML
    private void irACuponesModulo(MouseEvent event) {
        try {

            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLCupon.fxml"));
            Parent vista = loadVista.load();

            FXMLCuponController controlador = loadVista.getController();

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("MODULO Cupones");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void vericador() {

    }

}

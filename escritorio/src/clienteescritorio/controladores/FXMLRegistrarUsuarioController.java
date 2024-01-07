/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.DAO.EmpresaDAO;
import modelo.DAO.UsuarioDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Usuario;
import utiles.Utilidades;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLRegistrarUsuarioController implements Initializable {
    String rol;
    private ObservableList<Empresa> empresas;
    private int idEmpresa;

    private IRespuesta observador;
    @FXML
    private ComboBox<Empresa> empresaCombo;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoPField;
    @FXML
    private TextField apellidoMField;
    @FXML
    private TextField CurpField;
    @FXML
    private TextField correoField;
    @FXML
    private TextField usuarioField;
    @FXML
    private TextField contrasenafield;
    @FXML
    private RadioButton GeneralRadio;
    @FXML
    private ToggleGroup roles;
    @FXML
    private RadioButton COMOERCIALRadio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpresa();
        roles.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (GeneralRadio.isSelected()) {
                    empresaCombo.setDisable(true);
                    rol = "GENERAL";

                } else {
                    empresaCombo.setDisable(false);
                    rol = "COMERCIAL";
                }
            }


        });
    }

    private void cargarEmpresa() {
        empresas = FXCollections.observableArrayList();
        List<Empresa> info = EmpresaDAO.obtenerEmpresas();
        empresas.addAll(info);
        empresaCombo.setItems(empresas);
    }

    @FXML
    private void registrari(ActionEvent event) {

        if (Utilidades.validarCorreo(correoField.getText())) {
            try {
                Usuario u = sacaDatos();
                registro(u);
            } catch (NullPointerException e) {
                Utilidades.alerta("error", "correo llene todos los campos", Alert.AlertType.ERROR);
            }


        } else {
            Utilidades.alerta("error", "correo invalido", Alert.AlertType.ERROR);
        }

    }

    private void registro(Usuario usuario) {

        Mensaje msj = UsuarioDAO.registro(usuario);
        if (!msj.getError()) {
            Utilidades.alerta("Usuario registrada", msj.getContenido(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarPantalla();
        } else {
            Utilidades.alerta("error de registro", msj.getContenido(), Alert.AlertType.ERROR);
        }

    }

    private void cerrarPantalla() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();

    }


    private Usuario sacaDatos() {
        Usuario u = new Usuario();
        u.setCurp(CurpField.getText());
        u.setApellidoM(apellidoMField.getText());
        u.setApellidoP(apellidoPField.getText());
        u.setContrasena(contrasenafield.getText());
        u.setCorreo(correoField.getText());


        if (rol.equals("GENERAL")) {
            u.setIdEmpresa(-2);
        } else {
            Empresa empresa = empresas.get(empresaCombo.getSelectionModel().getSelectedIndex());
            u.setIdEmpresa(empresa.getIdEmpresa());
        }

        u.setNombre(nombreField.getText());
        u.setUsername(usuarioField.getText());
        u.setRol(rol);

        return u;
    }

    public void init(IRespuesta observador) {
        this.observador = observador;
    }

}

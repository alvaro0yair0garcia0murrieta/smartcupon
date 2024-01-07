/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class FXMLModificarUsuarioController implements Initializable {
    private ObservableList<Empresa> empresas;
    private IRespuesta observador;
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
    private Usuario usuario;
    @FXML
    private ComboBox<Empresa> empresaCombo;
    @FXML
    private RadioButton GeneralRadio;
    @FXML
    private ToggleGroup roles;
    @FXML
    private RadioButton COMOERCIALRadio;
    private String rol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void inicializar(Usuario usuario, IRespuesta observador) {
        this.usuario = usuario;
        this.observador = observador;
        cargarEmpresa();
        meteDatos();
    }

    @FXML
    private void modificar(ActionEvent event) {
        Usuario u = sacaUsuario();
        Mensaje msj = UsuarioDAO.actualizacion(u);
        if (!msj.getError()) {
            Utilidades.alerta("Usuario guardado", msj.getContenido(), Alert.AlertType.INFORMATION);
            observador.notificarGuardado();
            cerrarPantalla();
        } else {
            Utilidades.alerta("error de guardado", msj.getContenido(), Alert.AlertType.ERROR);
        }
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) nombreField.getScene().getWindow();
        stage.close();

    }

    private void meteDatos() {
        CurpField.setText(usuario.getCurp());
        apellidoMField.setText(usuario.getApellidoM());
        apellidoPField.setText(usuario.getApellidoP());
        contrasenafield.setText(usuario.getContrasena());
        correoField.setText(usuario.getCorreo());
        nombreField.setText(usuario.getNombre());
        usuarioField.setText(usuario.getUsername());


        if (usuario.privilegios(usuario.getRol())) {
            GeneralRadio.setSelected(true);
        } else {
            COMOERCIALRadio.setSelected(true);
            Empresa empresaEncontrada = EmpresaDAO.obtenerEmpresa(usuario.getIdEmpresa());

            if (empresaEncontrada != null) {
                empresaCombo.setValue(empresaEncontrada);
            }
        }

    }


    private Usuario sacaUsuario() {
        Usuario u = new Usuario();
        u.setCurp(CurpField.getText());
        u.setApellidoM(apellidoMField.getText());
        u.setApellidoP(apellidoPField.getText());
        u.setContrasena(contrasenafield.getText());
        u.setCorreo(correoField.getText());
        u.setIdEmpresa(usuario.getIdEmpresa());
        u.setNombre(nombreField.getText());
        u.setUsername(usuarioField.getText());
        u.setIdEmpresa(usuario.getIdEmpresa());
        u.setIdUsuario(usuario.getIdUsuario());
        try {
            Empresa empresa = empresas.get(empresaCombo.getSelectionModel().getSelectedIndex());
            u.setIdEmpresa(empresa.getIdEmpresa());
        } catch (Exception e) {
            u.setIdEmpresa(usuario.getIdEmpresa());
        }
        u.setRol(rol);

        return u;
    }

    private void cargarEmpresa() {
        empresas = FXCollections.observableArrayList();
        List<Empresa> info = EmpresaDAO.obtenerEmpresas();
        empresas.addAll(info);
        empresaCombo.setItems(empresas);
    }
}

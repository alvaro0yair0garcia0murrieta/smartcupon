/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.DAO.EmpresaDAO;
import modelo.DAO.SucursalDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
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
public class FXMLRegistrarSucursalController implements Initializable {
    private ObservableList<Empresa> empresas;
    private Usuario usuario;
    private int idEmpresa;
    private IRespuesta observador;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField calleField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField encargadoField;
    @FXML
    private TextField ciudadField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField codigoPField;
    @FXML
    private TextField latitudField;
    @FXML
    private TextField longitudField;
    @FXML
    private TextField coloniaField;
    @FXML
    private ComboBox<Empresa> empresaCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void registrar(ActionEvent event) {


        if (nombreField.getText().isEmpty() || calleField.getText().isEmpty() || numeroField.getText().isEmpty()) {
            Utilidades.alerta("campos vacios", "porfavor llene todos los campos vacios", Alert.AlertType.WARNING);

        } else if (!Utilidades.numeroValido(numeroField.getText())) {
            Utilidades.alerta("campos incorecctos", "porfavor  introdusca un numero valido de domicilio", Alert.AlertType.WARNING);
        } else {
            try {
                Sucursal s = sacaDatos();
                registro(s);
            } catch (NullPointerException e) {

            }

        }
    }

    private Sucursal sacaDatos() {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombreField.getText());
        sucursal.setCalle(calleField.getText());
        sucursal.setCiudad(ciudadField.getText());
        sucursal.setCodigoP(codigoPField.getText());
        sucursal.setTelefono(telefonoField.getText());
        sucursal.setEncargado(encargadoField.getText());
        sucursal.setLatitud(latitudField.getText());
        sucursal.setLongitud(longitudField.getText());
        sucursal.setNumero(numeroField.getText());
        if (usuario.privilegios(usuario.getRol())) {

            Empresa empresa = empresas.get(empresaCombo.getSelectionModel().getSelectedIndex());
            sucursal.setIdEmpresa(empresa.getIdEmpresa());

        } else {
            sucursal.setIdEmpresa(idEmpresa);

        }
        sucursal.setColonia(coloniaField.getText());
        return sucursal;

    }

    private void registro(Sucursal sucursal) {

        Mensaje msj = SucursalDAO.registro(sucursal);
        if (!msj.getError()) {
            Utilidades.alerta("sucursal registrada", msj.getContenido(), Alert.AlertType.INFORMATION);
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

    private void cargarEmpresa() {
        empresas = FXCollections.observableArrayList();
        List<Empresa> info = EmpresaDAO.obtenerEmpresas();
        empresas.addAll(info);
        empresaCombo.setItems(empresas);
    }


    void inicializador(IRespuesta observador, Usuario u) {
        this.observador = observador;
        this.usuario = u;
        if (usuario.privilegios(usuario.getRol())) {
            cargarEmpresa();
        } else {
            empresaCombo.setDisable(true);
            idEmpresa = usuario.getIdEmpresa();
        }
    }
}

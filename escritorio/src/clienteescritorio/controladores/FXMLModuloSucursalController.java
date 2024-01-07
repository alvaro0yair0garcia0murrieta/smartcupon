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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.DAO.SucursalDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;
import utiles.Utilidades;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLModuloSucursalController implements Initializable, IRespuesta {
    Usuario usuario;
    ObservableList<Sucursal> sucursales;
    @FXML
    private TableView<Sucursal> tableView;
    @FXML
    private TableColumn nombreC;
    @FXML
    private TableColumn encargadoC;
    @FXML
    private TableColumn calleC;
    @FXML
    private TableColumn numeroC;
    @FXML
    private TableColumn ColoniaC;
    @FXML
    private TableColumn cpC;
    @FXML
    private TableColumn ciudadC;
    @FXML
    private TableColumn latitudC;
    @FXML
    private TableColumn LongitudC;
    @FXML
    private TableColumn telC;
    @FXML
    private TextField busqueadaField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();


    }

    void inicializarUsuario(Usuario usuario) {
        this.usuario = usuario;

        if (usuario.privilegios(usuario.getRol())) {
            llenarTablaAdmin();
        } else {
            llenarTabla();
        }
        inicializarBusqueda();
    }


    @FXML
    private void cancelar(ActionEvent event) {
        busqueadaField.clear();
    }

    @FXML
    private void irARegostrar(ActionEvent event) {
        try {
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLRegistrarSucursal.fxml"));
            Parent vista = loadVista.load();
            FXMLRegistrarSucursalController controlador = loadVista.getController();
            controlador.inicializador(this, usuario);
            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("Registro SUCURSAL");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irAActualizar(ActionEvent event) {
        int posicion = tableView.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            Sucursal sucursal = sucursales.get(posicion);
            cambiapantalla(sucursal);
        } else {
            Utilidades.alerta("Selección requerida", "Debes seleccionar una sucursal para su edición", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Sucursal seleccion = tableView.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            Optional<ButtonType> respuesta = Utilidades.mostrarAlertaConfirmacion("Confirmar eliminación", "¿Esta seguro de eliminar  " + seleccion.getNombre() + ", de su registro?");

            if (respuesta.get() == ButtonType.OK) {


                Mensaje msj = SucursalDAO.eliminar(seleccion.getIdSucursal());
                notificarGuardado();
                if (!msj.getError()) {
                    Utilidades.alerta("eliminada", msj.getContenido(), Alert.AlertType.INFORMATION);

                } else {
                    Utilidades.alerta("error de eliminacion", msj.getContenido(), Alert.AlertType.ERROR);
                }
            }

        } else {
            Utilidades.alerta("Selección requerida", "Debes seleccionar  para su eliminación", Alert.AlertType.WARNING);
        }
    }

    private void configurarColumnas() {
        nombreC.setCellValueFactory(new PropertyValueFactory("nombre"));
        calleC.setCellValueFactory(new PropertyValueFactory("calle"));
        ciudadC.setCellValueFactory(new PropertyValueFactory("ciudad"));
        cpC.setCellValueFactory(new PropertyValueFactory("codigoP"));
        ColoniaC.setCellValueFactory(new PropertyValueFactory("colonia"));
        encargadoC.setCellValueFactory(new PropertyValueFactory("encargado"));
        numeroC.setCellValueFactory(new PropertyValueFactory("numero"));
        telC.setCellValueFactory(new PropertyValueFactory("telefono"));
        latitudC.setCellValueFactory(new PropertyValueFactory("latitud"));
        LongitudC.setCellValueFactory(new PropertyValueFactory("longitud"));
    }

    private void llenarTabla() {
        HashMap<String, Object> respMap = SucursalDAO.lista(usuario.getIdEmpresa());
        if (!(boolean) respMap.get("error")) {
            List<Sucursal> lista = (List<Sucursal>) respMap.get("lista");
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(lista);
            tableView.setItems(sucursales);
        } else {
            Utilidades.alerta("erorr", "xd", Alert.AlertType.ERROR);
        }
    }

    private void inicializarBusqueda() {
        if (sucursales != null) {
            FilteredList<Sucursal> filtro = new FilteredList<>(sucursales, p -> true);

            busqueadaField.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                    filtro.setPredicate(itemBusqueda -> {
                        // Regla cuando es vacio
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerFilter = newValue.toLowerCase();
                        // Reglas de filtrado
                        if (itemBusqueda.getNombre().toLowerCase().contains(lowerFilter)) {
                            return true;
                        }
                        if (itemBusqueda.getCalle().toLowerCase().contains(lowerFilter)) {
                            return true;
                        }
                        return itemBusqueda.getNumero().toLowerCase().contains(lowerFilter);
                    });
                }
            });
            SortedList<Sucursal> sucursalesOrdenados = new SortedList<>(filtro);
            sucursalesOrdenados.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sucursalesOrdenados);
        }
    }


    private void cambiapantalla(Sucursal sucursal) {
        try {
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLModificarSucursal.fxml"));
            Parent vista = loadVista.load();

            FXMLModificarSucursalController controlador = loadVista.getController();
            controlador.inicializar(sucursal, this, usuario);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("Modificar Sucucursal");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notificarGuardado() {
        sucursales.clear();
        if (usuario.privilegios(usuario.getRol())) {
            llenarTablaAdmin();
        } else {
            llenarTabla();
        }
        inicializarBusqueda();
    }

    private void llenarTablaAdmin() {
        HashMap<String, Object> respMap = SucursalDAO.lista();
        if (!(boolean) respMap.get("error")) {
            List<Sucursal> lista = (List<Sucursal>) respMap.get("lista");
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(lista);
            tableView.setItems(sucursales);
        } else {
            Utilidades.alerta("erorr", "xd", Alert.AlertType.ERROR);
        }
    }
}

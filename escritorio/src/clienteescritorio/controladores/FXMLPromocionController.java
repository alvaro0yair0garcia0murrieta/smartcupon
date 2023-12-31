/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import clienteescritorio.controladores.fxml.FXMLSucuPromoController;
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
import modelo.DAO.PromocionDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
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
public class FXMLPromocionController implements Initializable, IRespuesta {
    Usuario usuario;
    ObservableList<Promocion> promociones;
    @FXML
    private TableView<Promocion> tableView;
    @FXML
    private TableColumn nombreC;
    @FXML
    private TableColumn codigoC;
    @FXML
    private TableColumn descpC;
    @FXML
    private TableColumn restriccionC;
    @FXML
    private TableColumn categoriaC;
    @FXML
    private TableColumn tipoC;
    @FXML
    private TableColumn estatusC;
    @FXML
    private TableColumn inicioC;
    @FXML
    private TableColumn finC;

    @FXML
    private TextField busquedaPromocionField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configuraColumnas();
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
    private void irASucursales(ActionEvent event) {
        int posicion = tableView.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            Promocion promocion = promociones.get(posicion);
            try {
                FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLSucuPromo.fxml"));
                Parent vista = loadVista.load();

                FXMLSucuPromoController controlador = loadVista.getController();
                controlador.init(promocion.getIdPromocion());

                Stage stage = new Stage();
                Scene esena = new Scene(vista);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(esena);
                stage.setTitle("MODULO");
                stage.showAndWait();
            } catch (IOException ex) {

                Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Utilidades.alerta("Selección requerida", "Debes seleccionar una Promocion para su edición", Alert.AlertType.WARNING);
        }


    }


    @FXML
    private void limpiar(ActionEvent event) {
        busquedaPromocionField.clear();
    }

    @FXML
    private void irARegistrar(ActionEvent event) {
        try {
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLRegistroPromocion.fxml"));
            Parent vista = loadVista.load();
            FXMLRegistroPromocionController controlador = loadVista.getController();
            controlador.inicializar(this, usuario);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("registro de promocion");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambiapantalla(Promocion promocion) {
        try {
            FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLModificaPromocion.fxml"));
            Parent vista = loadVista.load();

            FXMLModificaPromocionController controlador = loadVista.getController();
            controlador.inicializarPromocion(promocion, this, usuario);

            Stage stage = new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("actualizacion de promocion");
            stage.showAndWait();
        } catch (IOException ex) {

            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irAModificar(ActionEvent event) {
        int posicion = tableView.getSelectionModel().getSelectedIndex();
        if (posicion != -1) {
            Promocion promocion = promociones.get(posicion);
            cambiapantalla(promocion);
        } else {
            Utilidades.alerta("Selección requerida", "Debes seleccionar una Promocion para su edición", Alert.AlertType.WARNING);
        }
    }

    private void configuraColumnas() {
        nombreC.setCellValueFactory(new PropertyValueFactory("nombre"));
        categoriaC.setCellValueFactory(new PropertyValueFactory("categoria"));
        inicioC.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        codigoC.setCellValueFactory(new PropertyValueFactory("codigo"));
        estatusC.setCellValueFactory(new PropertyValueFactory("estatus"));
        descpC.setCellValueFactory(new PropertyValueFactory("descripcion"));
        finC.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        restriccionC.setCellValueFactory(new PropertyValueFactory("restriccion"));
        tipoC.setCellValueFactory(new PropertyValueFactory("tipo"));

    }

    private void llenarTabla() {

        HashMap<String, Object> respMap = PromocionDAO.lista(usuario.getIdEmpresa());
        if (!(boolean) respMap.get("error")) {
            List<Promocion> lista = (List<Promocion>) respMap.get("lista");
            promociones = FXCollections.observableArrayList();
            promociones.addAll(lista);

            tableView.setItems(promociones);

        } else {
            Utilidades.alerta("erorr", "xd", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        Promocion seleccion = tableView.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            Alert dialogoAlertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoAlertaConfirmacion.setTitle("confimacion de eliminacion");
            dialogoAlertaConfirmacion.setHeaderText(null);
            dialogoAlertaConfirmacion.setContentText("Desea elmimanar promocion" + seleccion.getNombre() + "? o solo desabilitar");

            ButtonType eliminarButtonType = new ButtonType("Eliminar");
            ButtonType desabilitarButtonType = new ButtonType("desabilitar");

            dialogoAlertaConfirmacion.getButtonTypes().setAll(eliminarButtonType, desabilitarButtonType);
            Optional<ButtonType> respuesta = dialogoAlertaConfirmacion.showAndWait();
            if (respuesta.get() == eliminarButtonType) {

                Mensaje msj = PromocionDAO.eliminar(seleccion.getIdPromocion());
                notificarGuardado();
                if (!msj.getError()) {
                    Utilidades.alerta("Promocion eliminada", msj.getContenido(), Alert.AlertType.INFORMATION);

                } else {
                    Utilidades.alerta("error de eliminacion", msj.getContenido(), Alert.AlertType.ERROR);
                }

            } else if (respuesta.get() == desabilitarButtonType) {
                seleccion.setEstatus("DESACTIVADO");
                PromocionDAO.actualizacion(seleccion);
                notificarGuardado();
            }

        } else {
            Utilidades.alerta("Selección requerida", "Debes seleccionar un paciente para su eliminación", Alert.AlertType.WARNING);
        }
    }

    @Override
    public void notificarGuardado() {
        promociones.clear();
        if (usuario.privilegios(usuario.getRol())) {
            llenarTablaAdmin();
        } else {
            llenarTabla();

        }
        inicializarBusqueda();
    }


    private void inicializarBusqueda() {
        if (promociones != null) {
            FilteredList<Promocion> filtro = new FilteredList<>(promociones, p -> true);

            busquedaPromocionField.textProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                    filtro.setPredicate(busqueda -> {
                        // Regla cuando es vacio
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerFilter = newValue.toLowerCase();
                        // Reglas de filtrado
                        if (busqueda.getNombre().toLowerCase().contains(lowerFilter)) {
                            return true;
                        }
                        if (busqueda.getFechaInicio().toLowerCase().contains(lowerFilter)) {
                            return true;
                        }
                        return busqueda.getFechaFin().toLowerCase().contains(lowerFilter);
                    });
                }
            });
            SortedList<Promocion> promocionesOrdenados = new SortedList<>(filtro);
            promocionesOrdenados.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(promocionesOrdenados);
        }
    }

    private void llenarTablaAdmin() {

        HashMap<String, Object> respMap = PromocionDAO.lista();
        if (!(boolean) respMap.get("error")) {
            List<Promocion> lista = (List<Promocion>) respMap.get("lista");
            promociones = FXCollections.observableArrayList();
            promociones.addAll(lista);

            tableView.setItems(promociones);

        } else {
            Utilidades.alerta("erorr", "xd", Alert.AlertType.ERROR);
        }
    }
}

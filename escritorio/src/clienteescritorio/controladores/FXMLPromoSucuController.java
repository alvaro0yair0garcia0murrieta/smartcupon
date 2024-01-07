/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import modelo.DAO.PromocionDAO;
import modelo.DAO.SucursalDAO;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;
import utiles.Utilidades;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLPromoSucuController implements Initializable {
    List<CheckBox> checkboxes;
    private ObservableList<Sucursal> sucursales;

    private int idPromocion;
    private int idEmpresa;


    @FXML
    private VBox listaVbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    void asignar(int idPromocion, int idSucursal) {

        Mensaje msj = PromocionDAO.promoSucu(idPromocion, idSucursal);
        if (!msj.getError()) {
            Utilidades.alerta("Paciente registrado", msj.getContenido(), Alert.AlertType.INFORMATION);

        } else {
            Utilidades.alerta("Error al registrar", msj.getContenido(), Alert.AlertType.ERROR);
        }
    }


    void inicializarUsuario(Usuario usuario, Integer idPromocion, int idEmpresa) {

        this.idEmpresa = idEmpresa;
        this.idPromocion = idPromocion;
        cargarsucursal();
    }

    private void cargarsucursal() {
        sucursales = FXCollections.observableArrayList();


        List<Sucursal> info = SucursalDAO.obtenerSucursales(idEmpresa);
        sucursales.addAll(info);
        checkboxes = new ArrayList<>();


        for (Sucursal sucursal : sucursales) {
            CheckBox checkbox = new CheckBox();
            checkbox.setText(sucursal.getNombre());
            checkboxes.add(checkbox);
        }
        listaVbox.getChildren().addAll(checkboxes);
    }


    @FXML
    private void aplicarPromocion(ActionEvent event) {


        List<Sucursal> sucursalesSeleccionadas = new ArrayList<>();
        for (CheckBox checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                sucursalesSeleccionadas.add(sucursales.get(checkboxes.indexOf(checkbox)));
            }
        }
        if (sucursalesSeleccionadas.isEmpty()) {


        }
        for (Sucursal sucursalesSeleccionada : sucursalesSeleccionadas) {
            asignar(idPromocion, sucursalesSeleccionada.getIdSucursal());

        }
    }
}




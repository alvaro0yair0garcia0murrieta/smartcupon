/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clienteescritorio.controladores.fxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import modelo.DAO.SucursalDAO;
import modelo.pojo.Sucursal;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLSucuPromoController implements Initializable {
    private ObservableList<Sucursal> sucursales;
    private int idPromocion;
    @FXML
    private ListView<Sucursal> lvSucursales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void init(int idProocion) {
        idPromocion = idProocion;
        cargarsucursal();
    }

    private void cargarsucursal() {
        sucursales = FXCollections.observableArrayList();


        List<Sucursal> info = SucursalDAO.obtenerSucursalesPromo(idPromocion);
        sucursales.addAll(info);
        lvSucursales.setItems(sucursales);
    }
}
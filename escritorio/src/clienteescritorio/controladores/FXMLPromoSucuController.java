/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modelo.DAO.PromocionDAO;
import modelo.DAO.SucursalDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;
import utiles.Utilidades;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLPromoSucuController implements Initializable {

      private ObservableList<Sucursal> sucursales;
    private Usuario usuario;
    private int idPromocion;
    private int idEmpresa;
    @FXML
    private ComboBox<Sucursal> sucursalcb;
    @FXML
    private CheckBox check;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void seleccicon(ActionEvent event) {
   sucursalcb.setDisable(true);
  int idEmpredsa = usuario.getIdEmpresa();
  check.setDisable(true);
   
           
    }

    @FXML
    private void realizar(ActionEvent event) {
        
        if (check.isDisable()) {

            
            sucursales.stream().map((sucursale) -> {
                System.err.println(sucursale.getIdSucursal());
                return sucursale;
            }).map((sucursale) -> {
                System.err.println(idPromocion);    
                return sucursale;
            }).forEachOrdered((sucursale) -> {
                asignar(idPromocion, sucursale.getIdSucursal());
            });
        }else{
            System.out.println(idPromocion);
            Sucursal sucursal = sucursales.get(sucursalcb.getSelectionModel().getSelectedIndex());
            System.out.println(sucursal.getIdSucursal());
            asignar(idPromocion, sucursal.getIdSucursal());
            
        }
        
     }
    
    void asignar(int idPromocion, int idSucursal){
        
               Mensaje msj = PromocionDAO.promoSucu(idPromocion,idSucursal);
        if(!msj.getError()){
            Utilidades.alerta("Paciente registrado", 
                    msj.getContenido(), Alert.AlertType.INFORMATION);
            cerrarVentana();
        }else{
            Utilidades.alerta("Error al registrar", 
                    msj.getContenido(), Alert.AlertType.ERROR);
    }
    }
    
    
    void inicializarUsuario(Usuario usuario, Integer idPromocion,int idEmpresa) {
    this.usuario = usuario;
    this.idEmpresa= idEmpresa;
    this.idPromocion= idPromocion;
    cargarsucursal();
            }
     
    private void cargarsucursal() {
     sucursales =FXCollections.observableArrayList();
        
   
     List<Sucursal> info= SucursalDAO.obtenerSucursales(idEmpresa);
        sucursales.addAll(info);
        sucursalcb.setItems(sucursales);
    }
   
    private void cerrarVentana() {
     Stage stage =(Stage)sucursalcb.getScene().getWindow();
        stage.close();}


}

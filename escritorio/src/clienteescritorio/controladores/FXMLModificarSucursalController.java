/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
public class FXMLModificarSucursalController implements Initializable {
    private Usuario usuario;
    private Sucursal sucursal;
     private IRespuesta observador;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField encargadoField;
    @FXML
    private TextField calleField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField ciudadField;
    @FXML
    private TextField coloniaField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField codigoPField;
    @FXML
    private TextField latitudField;
    @FXML
    private TextField longitudField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   @FXML
    private void registrar(ActionEvent event) {
        Sucursal sucursal = sacaDatos();
       actualizacion(sucursal);
    }
    
    private Sucursal sacaDatos(){
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombreField.getText());
        sucursal.setCalle(calleField.getText());
        sucursal.setCiudad(ciudadField.getText());
        sucursal.setCodigoP(codigoPField.getText());
        sucursal.setColonia(coloniaField.getText());
        sucursal.setTelefono(telefonoField.getText());
        sucursal.setEncargado(encargadoField.getText());
        sucursal.setLatitud(latitudField.getText());
        sucursal.setLongitud(longitudField.getText());
        sucursal.setNumero(numeroField.getText());
        sucursal.setIdEmpresa(this.sucursal.getIdEmpresa());
        sucursal.setIdSucursal(this.sucursal.getIdSucursal());
        
        return sucursal;
    }
    private void meteDatos(){
        
    nombreField.setText(sucursal.getNombre());
    
    encargadoField.setText(sucursal.getEncargado());
    
    calleField.setText(sucursal.getCalle());
    
    numeroField.setText(sucursal.getNumero());
    
    ciudadField.setText(sucursal.getCiudad());
    
    coloniaField.setText(sucursal.getColonia());
    
    telefonoField.setText(sucursal.getTelefono());
    
    codigoPField.setText(sucursal.getCodigoP());
    
    latitudField.setText(sucursal.getLatitud());
    
    longitudField.setText(sucursal.getLongitud());
    }
    
     private void actualizacion(Sucursal sucursal){
         
Mensaje msj = SucursalDAO.actualizacion(sucursal);
if(!msj.getError()){
    Utilidades.alerta("actualzizacion correcta", msj.getContenido(), Alert.AlertType.INFORMATION);
    observador.notificarGuardado();
    cerrarPantalla();
}else{
    Utilidades.alerta("error de actualizacion", msj.getContenido(), Alert.AlertType.ERROR);
}
}
 
     private void cerrarPantalla(){
        Stage stage =(Stage)nombreField.getScene().getWindow();
        stage.close();
    }

    void inicializar(Sucursal sucursal,IRespuesta observador,Usuario u ) {
    this.sucursal= sucursal;
    this.observador = observador;
    meteDatos();
    
    }
    
}

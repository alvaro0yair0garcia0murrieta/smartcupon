/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores.fxml;

import clienteescritorio.controladores.FXMLHomeController;
import clienteescritorio.controladores.FXMLModificarSucursalController;
import clienteescritorio.controladores.FXMLModificarUsuarioController;
import clienteescritorio.controladores.FXMLRegistrarUsuarioController;
import interfaces.IRespuesta;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.DAO.UsuarioDAO;
import modelo.pojo.Sucursal;
import modelo.pojo.Usuario;
import utiles.Utilidades;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLUsuariosController implements Initializable,IRespuesta{

    @FXML
    private TextField busquedaField;
    @FXML
    private TableView<Usuario> tableView;
    @FXML
    private TableColumn<?, ?> nombreC;
    @FXML
    private TableColumn<?, ?> apellidoPC;
    @FXML
    private TableColumn<?, ?> apelidoMC;
    @FXML
    private TableColumn<?, ?> usernameC;
    @FXML
    private TableColumn<?, ?> curpC;
    @FXML
    private TableColumn<?, ?> correoC;
    @FXML
    private TableColumn<?, ?> rolC;
    private ObservableList<Usuario> usuarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configuraColumnas();
        inicializarBusqueda();
        llenarTabla();
    }    

    @FXML
    private void limpiar(ActionEvent event) {
     busquedaField.clear();
    }

    @FXML
    private void irARegistrar(ActionEvent event) {
      try {
        FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLRegistrarUsuario.fxml"));
        FXMLRegistrarUsuarioController controlador = loadVista.getController();
            controlador.inicializar(this);
                
        Parent vista = loadVista.load();
            Stage stage =new Stage();
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
    private void irAModificar(ActionEvent event) {
               int posicion = tableView.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Usuario usuario = usuarios.get(posicion);
            cambiapantalla(usuario);
        }else{
            Utilidades.alerta("Selección requerida", 
                    "Debes seleccionar un usuario para su edición", 
                    Alert.AlertType.WARNING);
    }
    }

    @FXML
    private void eliminar(ActionEvent event) {
     Usuario seleccion = tableView.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            Optional<ButtonType> respuesta =  Utilidades.mostrarAlertaConfirmacion("Confirmar eliminación", 
                    "¿Esta seguro de eliminar "
                    +seleccion.getNombre()+", de su registro?");
                    
            
            if(respuesta.get() == ButtonType.OK){
                UsuarioDAO.eliminar(seleccion.getIdUsuario());
                notificarGuardado();
            }
            
        }else{
            Utilidades.alerta("Selección requerida", 
                    "Debes seleccionar un usuario para su eliminación", 
                    Alert.AlertType.WARNING);
        }
    }

    private void cambiapantalla(Usuario usuario) {
    try {
        FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLModificarUsuario.fxml"));
            Parent vista = loadVista.load();
    
            FXMLModificarUsuarioController controlador = loadVista.getController();
            controlador.inicializar(usuario,this);
            
            Stage stage =new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("MODIFICACION DE USURARIO");
            stage.showAndWait();
        } catch (IOException ex) {
            
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      private void configuraColumnas(){
          apelidoMC.setCellValueFactory(new PropertyValueFactory("apellidoM"));
          apellidoPC.setCellValueFactory(new PropertyValueFactory("apellidoP"));
          correoC.setCellValueFactory(new PropertyValueFactory("correo"));
          curpC.setCellValueFactory(new PropertyValueFactory("curp"));
          nombreC.setCellValueFactory(new PropertyValueFactory("nombre"));
          rolC.setCellValueFactory(new PropertyValueFactory("rol"));
          usernameC.setCellValueFactory(new PropertyValueFactory("username"));
          
      }
       private void inicializarBusqueda(){
        if(usuarios!= null){
            FilteredList<Usuario> filtro = 
                    new FilteredList<>(usuarios, p -> true);
            
            busquedaField.textProperty().addListener(new ChangeListener<String>(){
                
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue) {
                    
                    filtro.setPredicate(itemBusqueda -> {
                        // Regla cuando es vacio
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerFilter = newValue.toLowerCase();
                        // Reglas de filtrado
                        if(itemBusqueda.getUsername().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        if(itemBusqueda.getRol().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        if(itemBusqueda.getNombre().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        
                        return false;    
                    });
                }
            });
            SortedList<Usuario> sucursalesOrdenados = new SortedList<>(filtro);
            sucursalesOrdenados.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sucursalesOrdenados);
        }
    }
        private void llenarTabla(){
        
        HashMap<String,Object> respMap= UsuarioDAO.lista();
        if (!(boolean)respMap.get("error")) {
            List<Usuario> lista= (List<Usuario>) respMap.get("lista");
            usuarios = FXCollections.observableArrayList();
            usuarios.addAll(lista);
        
            tableView.setItems(usuarios);
             
        }else
        {
            Utilidades.alerta("erorr", "error", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarGuardado() {
        usuarios.clear();
    llenarTabla();
    }
    
}

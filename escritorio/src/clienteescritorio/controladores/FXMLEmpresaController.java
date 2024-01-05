/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

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
import modelo.DAO.EmpresaDAO;
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
public class FXMLEmpresaController implements Initializable,IRespuesta {

    @FXML
    private TableView<Empresa> tableView;
    @FXML
    private TableColumn<?, ?> nombreC;
    @FXML
    private TableColumn<?, ?> RFCC;
    @FXML
    private TableColumn<?, ?> representanteC;
    @FXML
    private TableColumn<?, ?> estatusC;
    @FXML
    private TableColumn<?, ?> calleC;
    @FXML
    private TableColumn<?, ?> numeroC;
 
    @FXML
    private TableColumn<?, ?> ciudadC;
    @FXML
    private TableColumn<?, ?> telefonoC;
    @FXML
    private TableColumn<?, ?> CorreoC;
    @FXML
    private TableColumn<?, ?> aliasC;
    @FXML
    private TextField busquedaField;
    private ObservableList<Empresa> empresas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             configuraColumnas();
             llenarTabla();
        inicializarBusqueda();
      
    }    


    @FXML
    private void borrarCampo(ActionEvent event) {
    busquedaField.clear();
    }

    @FXML
    private void irARegistro(ActionEvent event) {   try {
        FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLRegistrarEmpresa.fxml"));
            Parent vista = loadVista.load();
            FXMLRegistrarEmpresaController controlador = loadVista.getController();
            controlador.inicializar(this);
            Stage stage =new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("REGISTRO EMPRESA");
            stage.showAndWait();
        } catch (IOException ex) {
            
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irAModificar(ActionEvent event) {
    
       int posicion = tableView.getSelectionModel().getSelectedIndex();
        if(posicion != -1){
            Empresa empresa =empresas.get(posicion);
            cambiapantalla(empresa);
        }else{
            Utilidades.alerta("Selección requerida", 
                    "Debes seleccionar una empresa para su edición", 
                    Alert.AlertType.WARNING);
                }

    }
    @FXML
    private void eliminaFila(ActionEvent event) {
       Empresa seleccion = tableView.getSelectionModel().getSelectedItem();
        if(seleccion != null){
            Alert dialogoAlertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlertaConfirmacion.setTitle("confimacion de eliminacion");
        dialogoAlertaConfirmacion.setHeaderText(null);
        dialogoAlertaConfirmacion.setContentText("Desea elmimanar promocion"+ seleccion.getNombre()+"? o solo desabilitar");
        
        ButtonType eliminarButtonType = new ButtonType("Eliminar");
        ButtonType desabilitarButtonType = new ButtonType("desabilitar");

        dialogoAlertaConfirmacion.getButtonTypes().setAll(eliminarButtonType, desabilitarButtonType);
       Optional<ButtonType> respuesta= dialogoAlertaConfirmacion.showAndWait();
       if(respuesta.get()== eliminarButtonType){
           Mensaje msj=  EmpresaDAO.eliminar(seleccion.getIdEmpresa());
          if(!msj.getError()){
    Utilidades.alerta("Promocion eliminada", msj.getContenido(), Alert.AlertType.INFORMATION);
    notificarGuardado();
 
}else{
    Utilidades.alerta("error de eliminacion", msj.getContenido(), Alert.AlertType.ERROR);
}
       }else if (respuesta.get()== desabilitarButtonType) {
           seleccion.setEstatus("DESACTIVADO");
              EmpresaDAO.actualizacion(seleccion);
              notificarGuardado();
            }
            
        }else{
            Utilidades.alerta("Selección requerida", 
                    "Debes seleccionar un paciente para su eliminación", 
                    Alert.AlertType.WARNING);
        }
    }


    private void cambiapantalla(Empresa empresa) {
   try {
        FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLModificarEmpresa.fxml"));
            Parent vista = loadVista.load();
    
            FXMLModificarEmpresaController controlador = loadVista.getController();
            controlador.inicializar(empresa,this);
            
            Stage stage =new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("MODIFICACION DE Empresa");
            stage.showAndWait();
        } catch (IOException ex) {
            
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } }
      private void configuraColumnas(){
         CorreoC.setCellValueFactory(new PropertyValueFactory("correo"));
         RFCC.setCellValueFactory(new PropertyValueFactory("rfc"));
         aliasC.setCellValueFactory(new PropertyValueFactory("nombreComercial"));
         calleC.setCellValueFactory(new PropertyValueFactory("calle"));
         ciudadC.setCellValueFactory(new PropertyValueFactory("ciudad"));
         estatusC.setCellValueFactory(new PropertyValueFactory("estatus"));
         nombreC.setCellValueFactory(new PropertyValueFactory("nombre"));
         numeroC.setCellValueFactory(new PropertyValueFactory("numero"));
         representanteC.setCellValueFactory(new PropertyValueFactory("representante"));
         telefonoC.setCellValueFactory(new PropertyValueFactory("telefono"));
      }
     private void inicializarBusqueda(){
        if(empresas != null){
            FilteredList<Empresa> filtro = 
                    new FilteredList<>(empresas, p -> true);
            
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
                        if(itemBusqueda.getNombre().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        if(itemBusqueda.getRfc().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        if(itemBusqueda.getRepresentante().toLowerCase().contains(lowerFilter)){
                            return true;
                        }
                        
                        return false;    
                    });
                }
            });
            SortedList<Empresa> empresasOrdenados = new SortedList<>(filtro);
            empresasOrdenados.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(empresasOrdenados);
        }
    }
     
      private void llenarTabla(){
        
        HashMap<String,Object> respMap= EmpresaDAO.lista();
        if (!(boolean)respMap.get("error")) {
            List<Empresa> lista= (List<Empresa>) respMap.get("lista");
            empresas = FXCollections.observableArrayList();
            empresas.addAll(lista);
        
            tableView.setItems(empresas);
             
        }else
        {
            Utilidades.alerta("erorr", "xd", Alert.AlertType.ERROR);
        }}

    @Override
    public void notificarGuardado() {
    empresas.clear();
    llenarTabla();
    }
 
}

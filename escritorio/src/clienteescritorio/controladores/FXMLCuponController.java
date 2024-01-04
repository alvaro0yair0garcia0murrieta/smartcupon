/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;import javafx.scene.control.Alert;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.DAO.PromocionDAO;
import modelo.pojo.Promocion;
import utiles.Utilidades;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.DAO.PromocionDAO;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLCuponController implements Initializable {
    ObservableList<Promocion> promociones; 

    @FXML
    
    private TableView<Promocion> tableView;
    @FXML
    private TableColumn<?, ?> nombreC;
    @FXML
    private TableColumn<?, ?> codigoC;
    @FXML
    private TableColumn<?, ?> inicioC;
    @FXML
    private TableColumn<?, ?> finalC;
    @FXML
    private TableColumn<?, ?> disponibleC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configuraColumnas();
        llenarTabla();
        
    }    

    @FXML
    private void irACanje(ActionEvent event) {
        try {
        FXMLLoader loadVista = new FXMLLoader(getClass().getResource("fxml/FXMLCanje.fxml"));
            Parent vista = loadVista.load();
            Stage stage =new Stage();
            Scene esena = new Scene(vista);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(esena);
            stage.setTitle("canje");
            stage.showAndWait();
        } catch (IOException ex) {
            
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      private void configuraColumnas(){
          nombreC.setCellValueFactory(new PropertyValueFactory("nombre"));
          codigoC.setCellValueFactory(new PropertyValueFactory("codigo"));
          inicioC.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
          finalC.setCellValueFactory(new PropertyValueFactory("fechaFin"));
          disponibleC.setCellValueFactory(new PropertyValueFactory("cuponesMax"));
      }

       private void llenarTabla(){
        
        HashMap<String,Object> respMap= PromocionDAO.listaCupon();
        if (!(boolean)respMap.get("error")) {
            List<Promocion> lista= (List<Promocion>) respMap.get("lista");
            promociones = FXCollections.observableArrayList();
            promociones.addAll(lista);
        
            tableView.setItems(promociones);
             
        }else
        {
            Utilidades.alerta("erorr", "xd", Alert.AlertType.ERROR);
        }
    }
}

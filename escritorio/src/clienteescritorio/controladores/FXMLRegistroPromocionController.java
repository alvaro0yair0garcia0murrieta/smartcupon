/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modelo.DAO.EmpresaDAO;
import modelo.DAO.PromocionDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import modelo.pojo.Usuario;
import utiles.Utilidades;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLRegistroPromocionController implements Initializable {
  private ObservableList<Empresa> empresas;
  private int idEmpresa;
  private Usuario usuario;
  private String tipoPromo;
 private String simbolo;
  private IRespuesta observador;
   
    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripField;
    @FXML
    private ComboBox<String> categoriaCombo;
    @FXML
    private RadioButton descRadio;
    @FXML
    private ToggleGroup tipo;
    @FXML
    private RadioButton rebRadio;
    @FXML
    private TextField tipoField;
    @FXML
    private TextField RestField;
    @FXML
    private TextField codigoField;
    @FXML
    private TextField cuponField;
    @FXML
    private DatePicker inicioDate;
    @FXML
    private DatePicker liminteDate;
    @FXML
    private ComboBox<Empresa> empresaCombo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String tiposA[] =
                   { "farmacia", "comercial", "cafeteria"};
        ObservableList<String> tiposList = FXCollections.observableArrayList(tiposA);
        categoriaCombo.setItems(tiposList);
         tipo.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, 
                    Toggle oldValue, Toggle newValue) {
                if(descRadio.isSelected()){
                    simbolo = "%";
                }else{
                    simbolo = "$";
                }
            }

           
            
        });
    }    
  private void cargarEmpresa() {
     empresas =FXCollections.observableArrayList();
        List<Empresa> info= EmpresaDAO.obtenerEmpresas();
        empresas.addAll(info);
        empresaCombo.setItems(empresas);
    }

    @FXML
    private void registrar(ActionEvent event) {
   
        
        if (!Utilidades.validarAlfanumerica(codigoField.getText())) {
             Utilidades.alerta("valor incorecto", "codigo no valido", Alert.AlertType.WARNING);
        }
        else if(!Utilidades.numeroValido(cuponField.getText())){
             Utilidades.alerta("valor incorecto", "numero no valido", Alert.AlertType.WARNING);
        
        }else if (!Utilidades.validarAlfanumerica(codigoField.getText())) {
              Utilidades.alerta("valor incorecto", "codigo no alfanumerico no valido", Alert.AlertType.WARNING);
         
        }else if ( simbolo==null) {
             Utilidades.alerta("valor incorecto", "elija promocion no valido", Alert.AlertType.WARNING);
        } else if(tipoPromo==null){
         Utilidades.alerta("valor incorecto", "elija categoria", Alert.AlertType.WARNING);
        }else  {
            try {
            Promocion promocion= sacaPromocion();    
           registro(promocion);
            } catch (NullPointerException e) {
              Utilidades.alerta("valor vacios", "llene los campos", Alert.AlertType.WARNING);
            }
     
            
           
           
        }
        
        
        
    }
   private void registro(Promocion promocion){
         
Mensaje msj = PromocionDAO.registro(promocion);
if(!msj.getError()){
    Utilidades.alerta("Promocion registrada", msj.getContenido(), Alert.AlertType.INFORMATION);
    observador.notificarGuardado();
    cerrarPantalla();
}else{
    Utilidades.alerta("error de registro", msj.getContenido(), Alert.AlertType.ERROR);
}

   }

     private void cerrarPantalla(){
        Stage stage =(Stage)nombreField.getScene().getWindow();
        stage.close();
        
    }
      
       
      private Promocion sacaPromocion(){
          Promocion promocion= new Promocion();
          promocion.setCategoria(tipoPromo);
          if (simbolo.equals("$")) {
          promocion.setTipo(simbolo+tipoField.getText()+" menos");    
          }else{
              promocion.setTipo(tipoField.getText()+simbolo+" de descuento");
          }
           if (usuario.privilegios(usuario.getRol())) {
               
                Empresa empresa = empresas.get(empresaCombo.getSelectionModel().getSelectedIndex());
                promocion.setIdEmpresa(empresa.getIdEmpresa());
                
            }else{
                promocion.setIdEmpresa(idEmpresa);
            }       
         promocion.setRestriccion(RestField.getText());
         promocion.setCodigo(codigoField.getText());
         promocion.setCuponesMax(Integer.valueOf(cuponField.getText()));
         promocion.setDescripcion(descripField.getText());
         promocion.setFechaFin(liminteDate.getValue().toString());
         promocion.setFechaInicio(inicioDate.getValue().toString());
         promocion.setNombre(nombreField.getText());
            
      return  promocion;
      }
      
      void inicializar(IRespuesta observador, Usuario u){
          this.observador = observador;
            this.usuario= u;
      if (usuario.privilegios(usuario.getRol())) {
             cargarEmpresa();
        }else{
            empresaCombo.setDisable(true);
            idEmpresa= usuario.getIdEmpresa();
      }}

    @FXML
    private void tipos(ActionEvent event) {
       
          tipoPromo= categoriaCombo.getValue();
        
    }
      
 }


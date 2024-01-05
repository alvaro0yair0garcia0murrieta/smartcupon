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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modelo.DAO.EmpresaDAO;
import modelo.DAO.PromocionDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import utiles.Utilidades;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLRegistrarEmpresaController implements Initializable {
 private IRespuesta observador;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfnomC;
    @FXML
    private TextField tfRep;
    @FXML
    private TextField tfRFC;
    @FXML
    private TextField tfcalle;
    @FXML
    private TextField tfnumero;
    @FXML
    private TextField tfciudad;
    @FXML
    private TextField tfcolonia;
    @FXML
    private TextField tfCor;
    @FXML
    private TextField tfCP;
    @FXML
    private TextField tftel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void registrar(ActionEvent event) {
        
        if (tfNombre.getText().isEmpty()||tfRFC.getText().isEmpty()||tfRep.getText().isEmpty()) {
              Utilidades.alerta("campo vacio", "llene los campos", Alert.AlertType.WARNING); 
        }else{
            if (Utilidades.numeroValido(tfCP.getText())||Utilidades.numeroValido(tfnumero.getText())||Utilidades.numeroValido(tftel.getText())) {
                if (Utilidades.validarCorreo(tfCor.getText())) {
                    try {
                        Empresa empresa= sacaEmpresa();
                    registro(empresa);    
                    } catch (NullPointerException e) {
                          Utilidades.alerta("campo vacio", "llene los campos", Alert.AlertType.WARNING); 
                    }
 
                    
                }else{
                     Utilidades.alerta("campo invalido", "correo incorecto", Alert.AlertType.WARNING); 
                }
            }else{
                Utilidades.alerta("campo incorecto", "algun campos deberia de ser numeros", Alert.AlertType.WARNING);
            }
        }
    }
    
       private void registro(Empresa empresa){
         
Mensaje msj = EmpresaDAO.registro(empresa);
if(!msj.getError()){
    Utilidades.alerta("Empresa registrada", msj.getContenido(), Alert.AlertType.INFORMATION);
    
    cerrarPantalla();
    observador.notificarGuardado();
}else{
    Utilidades.alerta("error de registro", msj.getContenido(), Alert.AlertType.ERROR);
}

   }

     private void cerrarPantalla(){
        Stage stage =(Stage)tfNombre.getScene().getWindow();
        stage.close();
        
    }
     private Empresa sacaEmpresa(){
         Empresa empresa = new Empresa();
         empresa.setCodigoP(tfCP.getText());
         empresa.setCorreo(tfCor.getText());
         empresa.setNombre(tfNombre.getText());
         empresa.setRfc(tfRFC.getText());
         empresa.setRepresentante(tfRep.getText());
         empresa.setCalle(tfcalle.getText());
         empresa.setCiudad(tfciudad.getText());
         empresa.setNombreComercial(tfnomC.getText());
         empresa.setNumero(tfnumero.getText());
         empresa.setTelefono(tftel.getText());
         return  empresa;
     }
     
    void inicializar(IRespuesta observador) {
    
    this.observador = observador;

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
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
public class FXMLModificaPromocionController implements Initializable {
  private File imagenSeleccionada;
  private String estatusString;
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
    private ToggleGroup estatus;
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
    private Promocion promocion;
    @FXML
    private ImageView imagen;
    @FXML
    private RadioButton actRadio;
    @FXML
    private RadioButton desaRadio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          String tiposA[] =
                   { "Farmacia", "Comercial", "Cafeteria"};
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
         estatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, 
                    Toggle oldValue, Toggle newValue) {
                if(actRadio.isSelected()){
                    estatusString = "ACTIVO";
                }else{
                   estatusString = "DESACTIVADO";
                }
            } 

            
        });
         
         
    }    

 

    @FXML
    private void modificar(ActionEvent event) {
        if(tipoPromo==null){
         Utilidades.alerta("valor incorecto", "elija categoria", Alert.AlertType.WARNING);
        }else  {
            try {
           Promocion promocion = sacaPromocion();
        Mensaje msj = PromocionDAO.actualizacion(promocion);
if(!msj.getError()){
    Utilidades.alerta("Promocion guardada", msj.getContenido(), Alert.AlertType.INFORMATION);
    observador.notificarGuardado();
    cerrarPantalla();
}else{
    Utilidades.alerta("error de guardado", msj.getContenido(), Alert.AlertType.ERROR);
    }
         
            } catch (NullPointerException e) {
              Utilidades.alerta("valor vacios", "llene los campos", Alert.AlertType.WARNING);
            }
        }
    }
    
    void inicializarPromocion(Promocion promocion,IRespuesta observador,Usuario u) {
        this.observador= observador;
    this.promocion = promocion;
    metedatos();
    
    }
    
    private Promocion sacaPromocion(){
          Promocion promocion= new Promocion();
          promocion.setCategoria(tipoPromo);
          if (simbolo.equals("$")) {
          promocion.setTipo(simbolo+tipoField.getText()+" menos");    
          }else{
              promocion.setTipo(tipoField.getText()+simbolo+" de descuento");
          } 
         promocion.setRestriccion(RestField.getText());
         promocion.setCodigo(codigoField.getText());
         promocion.setCuponesMax(Integer.parseInt(cuponField.getText()));
         promocion.setDescripcion(descripField.getText());
         promocion.setFechaFin(liminteDate.getValue().toString());
         promocion.setFechaInicio(inicioDate.getValue().toString());
         promocion.setNombre(nombreField.getText());
         promocion.setEstatus(estatusString);
            promocion.setIdPromocion(this.promocion.getIdPromocion());
            promocion.setIdEmpresa(this.promocion.getIdEmpresa());
      return  promocion;
      }
    
    private void tipos(ActionEvent event) {
       
          tipoPromo= categoriaCombo.getValue();
        
    }

   private void cerrarPantalla(){
        Stage stage =(Stage)nombreField.getScene().getWindow();
        stage.close();
        
    }
   private void metedatos(){
    RestField.setText(promocion.getRestriccion());
    codigoField.setText(promocion.getCodigo());
    cuponField.setText(promocion.getCuponesMax().toString());
       if (promocion.getTipo().contains("$")) {
           rebRadio.setSelected(true);
       }else{
           descRadio.setSelected(true);
       }
       if(promocion.getEstatus().equals("ACTIVO")){
       actRadio.setSelected(true);
   }else{
           desaRadio.setSelected(true);
       }
       descripField.setText(promocion.getDescripcion());
       inicioDate.setValue(LocalDate.parse(promocion.getFechaInicio()));
       liminteDate.setValue(LocalDate.parse(promocion.getFechaFin()));
       nombreField.setText(promocion.getNombre());
       obtenerImagenServicio();
       
    
}

    @FXML
    private void CatgarLogo(ActionEvent event) {
       FileChooser dialogoSeleccionImg =  new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        //Configuracion del tipo de archivo
        FileChooser.ExtensionFilter filtroImg = 
                new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *jpg, *jpeg)", 
                        "*.PNG", "*.JPG", "*.JPEG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroImg);
        Stage stageActual = (Stage) nombreField.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(stageActual);
        if(imagenSeleccionada != null)
            mostrarFotografiaSeleccionada(imagenSeleccionada);
    }
    

    @FXML
    private void subirLogo(ActionEvent event) {
          if(imagenSeleccionada != null){
            cargarFotografiaServidor(imagenSeleccionada);
        }else{
            Utilidades.alerta("Selecciona fotografía", 
            "Para subir una fotografía  debes seleccionarla previamente", 
                    Alert.AlertType.WARNING);
        }
    }
    
    private void cargarFotografiaServidor(File imagen){
        try {
            byte[] imgBytes = Files.readAllBytes(imagen.toPath());
            Mensaje msj = PromocionDAO.subirFotografiaPromocion(
                                             imgBytes,promocion.getIdPromocion());
            if(!msj.getError()){
                Utilidades.alerta("Fotografía enviada", 
                        msj.getContenido(), Alert.AlertType.INFORMATION);
            }else{
                Utilidades.alerta("Error", 
                        msj.getContenido(), Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            Utilidades.alerta("Error", 
                    "Error: "+ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarFotografiaSeleccionada(File img){
        try {
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            imagen.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void obtenerImagenServicio(){
        Promocion promocionFoto = 
                PromocionDAO.obtenerFotografia(promocion.getIdPromocion());
        if(promocionFoto != null && promocionFoto.getImagenBase64() != null 
                && !promocionFoto.getImagenBase64().isEmpty()){
            mostrarFotografiaServidor(promocionFoto.getImagenBase64());
        }
    }
    
    private void mostrarFotografiaServidor(String imgBase64){
        byte[] foto = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", ""));
        Image image = new Image(new ByteArrayInputStream(foto));
        imagen.setImage(image);
    }

    @FXML
    private void asiignaCategoria(ActionEvent event) {
            tipoPromo= categoriaCombo.getValue();
    }
}

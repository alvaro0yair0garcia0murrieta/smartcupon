/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorio.controladores;

import interfaces.IRespuesta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.DAO.EmpresaDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import utiles.Utilidades;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author a-rac
 */
public class FXMLModificarEmpresaController implements Initializable {
    private File imagenSeleccionada;
    private String estatusString;
    private IRespuesta observador;

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfnomC;
    @FXML
    private TextField tfRep;
    @FXML
    private ToggleGroup estatus;
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
    @FXML
    private RadioButton ONRadio;
    @FXML
    private RadioButton OFFRadio;
    @FXML
    private ImageView Imagen;
    private Empresa empresa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        estatus.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (ONRadio.isSelected()) {
                    estatusString = "ACTIVO";
                } else {
                    estatusString = "DESACTIVADO";
                }
            }


        });
    }

    @FXML
    private void editar(ActionEvent event) {
        Empresa empresa = sacaEmpresa();
        Mensaje msj = EmpresaDAO.actualizacion(empresa);
        if (!msj.getError()) {
            Utilidades.alerta("empresa guardada", msj.getContenido(), Alert.AlertType.INFORMATION);
            if (observador != null) {
                observador.notificarGuardado();
            }

            cerrarPantalla();
        } else {
            Utilidades.alerta("error de guardado", msj.getContenido(), Alert.AlertType.ERROR);
        }
    }

    void inicializar(Empresa empresa, IRespuesta observador) {
        this.empresa = empresa;
        this.observador = observador;
        metedatos();
    }

    @FXML
    private void CatgarLogo(ActionEvent event) {
        FileChooser dialogoSeleccionImg = new FileChooser();
        dialogoSeleccionImg.setTitle("Selecciona una imagen");
        //Configuracion del tipo de archivo
        FileChooser.ExtensionFilter filtroImg = new FileChooser.ExtensionFilter("Archivos de imagen (*.png, *jpg, *jpeg)", "*.PNG", "*.JPG", "*.JPEG");
        dialogoSeleccionImg.getExtensionFilters().add(filtroImg);
        Stage stageActual = (Stage) tfNombre.getScene().getWindow();
        imagenSeleccionada = dialogoSeleccionImg.showOpenDialog(stageActual);
        if (imagenSeleccionada != null) mostrarFotografiaSeleccionada(imagenSeleccionada);
    }


    @FXML
    private void subirLogo(ActionEvent event) {
        if (imagenSeleccionada != null) {
            cargarFotografiaServidor(imagenSeleccionada);
        } else {
            Utilidades.alerta("Selecciona fotografía", "Para subir una fotografía debes seleccionarla previamente", Alert.AlertType.WARNING);
        }
    }

    private void cerrarPantalla() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();

    }

    private Empresa sacaEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setCodigoP(tfCP.getText());
        empresa.setCorreo(tfCor.getText());
        empresa.setNombre(tfNombre.getText());
        empresa.setRepresentante(tfRep.getText());
        empresa.setCalle(tfcalle.getText());
        empresa.setCiudad(tfciudad.getText());
        empresa.setNombreComercial(tfnomC.getText());
        empresa.setNumero(tfnumero.getText());
        empresa.setTelefono(tftel.getText());
        empresa.setEstatus(estatusString);
        empresa.setIdEmpresa(this.empresa.getIdEmpresa());
        return empresa;
    }

    private void metedatos() {
        tfCP.setText(empresa.getCodigoP());
        tfCor.setText(empresa.getCorreo());
        tfNombre.setText(empresa.getNombre());
        tfRep.setText(empresa.getRepresentante());
        tfcalle.setText(empresa.getCalle());
        tfciudad.setText(empresa.getCiudad());
        tfnomC.setText(empresa.getNombreComercial());
        tfnumero.setText(empresa.getNumero());
        tftel.setText(empresa.getTelefono());
        if (empresa.getEstatus().equals("ACTIVO")) {
            ONRadio.setSelected(true);
        } else {
            OFFRadio.setSelected(true);
        }
        obtenerImagenServicio();
    }

    private void cargarFotografiaServidor(File imagen) {
        try {
            byte[] imgBytes = Files.readAllBytes(imagen.toPath());
            Mensaje msj = EmpresaDAO.subirFotografiaEmpresa(imgBytes, empresa.getIdEmpresa());
            if (!msj.getError()) {
                Utilidades.alerta("Fotografía enviada", msj.getContenido(), Alert.AlertType.INFORMATION);
            } else {
                Utilidades.alerta("Error", msj.getContenido(), Alert.AlertType.ERROR);
            }
        } catch (IOException ex) {
            Utilidades.alerta("Error", "Error: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarFotografiaSeleccionada(File img) {
        try {
            BufferedImage buffer = ImageIO.read(img);
            Image image = SwingFXUtils.toFXImage(buffer, null);
            Imagen.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obtenerImagenServicio() {
        Empresa empresaFoto = EmpresaDAO.obtenerFotografia(empresa.getIdEmpresa());
        if (empresaFoto != null && empresaFoto.getLogoBase64() != null && !empresaFoto.getLogoBase64().isEmpty()) {
            mostrarFotografiaServidor(empresaFoto.getLogoBase64());
        }
    }

    private void mostrarFotografiaServidor(String imgBase64) {
        byte[] foto = Base64.getDecoder().decode(imgBase64.replaceAll("\\n", ""));
        Image image = new Image(new ByteArrayInputStream(foto));
        Imagen.setImage(image);
    }

    void inicializarCom(Empresa empresa) {
        this.empresa = empresa;
        metedatos();
    }
}

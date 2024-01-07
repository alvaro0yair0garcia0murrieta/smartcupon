/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author a-rac
 */
public class Utilidades {
    public static void alerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }

    public static boolean numeroValido(String cadena) {
        try {
            double numero = Double.parseDouble(cadena);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean FechaValida(String cadena) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha.setLenient(false);

        try {
            Date fechaFormateada = formatoFecha.parse(cadena);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Image base64aImagen(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return new Image(in);
    }

    public static boolean validarAlfanumerica(String cadena) {

        String regex = "^[a-zA-Z0-9]+$";
        return !Pattern.matches(regex, cadena);
    }

    public static boolean validarCorreo(String correo) {

        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        return Pattern.matches(regex, correo);
    }

    public static Optional<ButtonType> mostrarAlertaConfirmacion(String titulo, String confirmacion) {
        Alert dialogoAlertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlertaConfirmacion.setTitle(titulo);
        dialogoAlertaConfirmacion.setHeaderText(null);
        dialogoAlertaConfirmacion.setContentText(confirmacion);
        return dialogoAlertaConfirmacion.showAndWait();
    }

    public static boolean esFechaNoExpirada(String fecha) {
        // Define el formato de fecha de MySQL
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {

            Date fechaParseada = formatoFecha.parse(fecha);


            Date fechaActual = new Date();


            return !fechaParseada.before(fechaActual);
        } catch (ParseException e) {

            return false;
        }
    }

    public static int extractorNummeros(String input) {
        int numero = 0;
        Pattern pattern = Pattern.compile("\\d+"); // Match one or more digits

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String numeroCadena = matcher.group();
            numero = Integer.parseInt(numeroCadena);

        }

        return numero;
    }
}

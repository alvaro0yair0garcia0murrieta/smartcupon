/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.pojo.CodigoHTTP;

/**
 *
 * @author a-rac
 */
public class ConexionHTTP 
{
 
 public static CodigoHTTP peticionPUT(String url, String parametros){
     CodigoHTTP respuesta =new CodigoHTTP();

    try{
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("PUT");
        conexionhttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexionhttp.setDoOutput(true);
        
//datos de peticion
        OutputStream os =conexionhttp.getOutputStream();
        os.write(parametros.getBytes());
        os.flush();
        os.close();
 //termina escritura
 
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
        
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
        
    return respuesta;
 }  
 
 public static CodigoHTTP peticionDELETE(String url){
     CodigoHTTP respuesta =new CodigoHTTP();

    try{
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("DELETE");
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
        
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
        
    return respuesta;
 }
public static CodigoHTTP peticionGET(String url)
{
    CodigoHTTP respuesta = new CodigoHTTP();
    
    try { 
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("GET");
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
    return respuesta;
}   
public static CodigoHTTP peticioPOST(String url, String parametros){
    CodigoHTTP respuesta =new CodigoHTTP();

    try{
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("POST");
        conexionhttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexionhttp.setDoOutput(true);
        
//datos de peticion
        OutputStream os =conexionhttp.getOutputStream();
        os.write(parametros.getBytes());
        os.flush();
        os.close();
 //termina escritura
 
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
        
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
        
    return respuesta;
}

public static CodigoHTTP peticionPUTJson(String url,String parametros ){
  CodigoHTTP respuesta =new CodigoHTTP();

    try{
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("PUT");
        conexionhttp.setRequestProperty("Content-Type", "application/json");
        conexionhttp.setDoOutput(true);
        
//datos de peticion
        OutputStream os =conexionhttp.getOutputStream();
        os.write(parametros.getBytes());
        os.flush();
        os.close();
 //termina escritura
 
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
        
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
        
    return respuesta;   
}
private static String convertirContenido(InputStream contenido) throws IOException
{
    InputStreamReader inputLectura = new InputStreamReader(contenido);
    BufferedReader buffer = new BufferedReader(inputLectura);
    String cadenaEntrada;
    StringBuffer cadenaBuffer =new StringBuffer();
    while ((cadenaEntrada=buffer.readLine())!= null){
        cadenaBuffer.append(cadenaEntrada);
    }
    buffer.close();
    
    return cadenaBuffer.toString();
}


public static CodigoHTTP peticioPOSTJson(String url, String json){
    CodigoHTTP respuesta =new CodigoHTTP();

    try{
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("POST");
        conexionhttp.setRequestProperty("Content-Type", "application/json");
        conexionhttp.setDoOutput(true);
        
//datos de peticion
        OutputStream os =conexionhttp.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        os.close();
 //termina escritura
 
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
        
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
        
    return respuesta;
}

public static CodigoHTTP peticionPUTImagen(String url, byte[] imagen){
     CodigoHTTP respuesta =new CodigoHTTP();

    try{
        URL urlServicio= new URL(url);
        HttpURLConnection conexionhttp = (HttpURLConnection)urlServicio.openConnection();
        conexionhttp.setRequestMethod("PUT");
        
        conexionhttp.setDoOutput(true);
        
//datos de peticion
        OutputStream os =conexionhttp.getOutputStream();
        os.write(imagen);
        os.flush();
        os.close();
 //termina escritura
 
        int codigoRespuesta  = conexionhttp.getResponseCode();
        respuesta.setCodigoRespuesta(codigoRespuesta);
        if( codigoRespuesta == HttpURLConnection.HTTP_OK){
            respuesta.setContenido(convertirContenido(conexionhttp.getInputStream()));
        }else{
          respuesta.setContenido("error de codigo "+codigoRespuesta);
        }
        
    } catch (MalformedURLException ex) {
        Logger.getLogger(ConexionHTTP.class.getName()).log(Level.SEVERE, null, ex);
        respuesta.setCodigoRespuesta(Constantes.ERROR_URL);
          respuesta.setContenido("error de " + ex.getMessage());
    } catch(IOException e){
        respuesta.setCodigoRespuesta(Constantes.ERROR_PETICION);
          respuesta.setContenido("error de " + e.getMessage());
    }
        
    return respuesta;
 }

}



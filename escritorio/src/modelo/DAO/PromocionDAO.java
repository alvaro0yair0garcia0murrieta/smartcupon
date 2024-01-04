/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.HttpURLConnection;
import modelo.pojo.CodigoHTTP;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import utiles.ConexionHTTP;
import utiles.Constantes;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files; 
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.pojo.Sucursal;

/**
 *
 * @author a-rac
 */
public class PromocionDAO {
    public static Mensaje subirFotografiaPromocion(byte[] fotografia, int idPromocion){
          Mensaje msj = new Mensaje();
        String url = Constantes.URI_WS_Promocion_IMAGEN+idPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTImagen(url, fotografia);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setContenido("Hubo un error al enviar la fotografía , "
                    + "por favor inténtalo más tarde.");
        }
        return msj;
    }
    
    public static Promocion obtenerFotografia(int idPromocion){
        Promocion promocion = null;
        String url = Constantes.URI_WS_Promocion_IMAGEN+idPromocion;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK) {
               Gson gson = new Gson();
               promocion = gson.fromJson(respuesta.getContenido(), Promocion.class);
    }
        //columna id domicilio de promocion se borra y se agrega en tabla dominicilio id promocion
        return promocion;
    }
       public static Mensaje registro(Promocion promocion) {
         Gson gson = new Gson();
        Mensaje msj= new Mensaje();
        String url = Constantes.URI_WS_Promocion_R;
        
        String parametros = gson.toJson(promocion, Promocion.class) ;
                         
        CodigoHTTP respuestaConexion = ConexionHTTP.peticioPOSTJson(url, parametros);
        
        if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
           msj = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
         msj.setContenido("error");
         msj.setError(true);
       }
       return msj;
    }

    public static Mensaje actualizacion(Promocion promocion) {
   Gson gson = new Gson();
        Mensaje msj= new Mensaje();
        String url = Constantes.URI_WS_Promocion_M;
        
        String parametros = gson.toJson(promocion, Promocion.class) ;
                         
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionPUTJson(url, parametros);
        
        if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
          
        
           
           msj = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
         msj.setContenido("eroro UWU");
         msj.setError(true);
       }
       return msj;
    }
    
    
    public static Mensaje eliminar(int id){
        Mensaje mensaje = new Mensaje();
        
       String url = Constantes.URI_WS_Promocion_E+ id;
     CodigoHTTP  respuesta= ConexionHTTP.peticionDELETE(url);
        if (respuesta.getCodigoRespuesta()==HttpURLConnection.HTTP_OK ) {
           Gson gson =new  Gson();
           mensaje =gson.fromJson(respuesta.getContenido(),Mensaje.class);
        }
        else{
            mensaje.setError(true);
            mensaje.setContenido("Error por el momento no puede eliminar");
        }
        return mensaje;
    }

    public static Mensaje promoSucu(int idPromocion, int idSucursal) {
         
        Mensaje respuestaws = new Mensaje();
       
        String url = Constantes.URI_WS+"/promocion/promo-sucu";
       String parametros = String.format("idPromocion=%d&idSucursal=%d",idPromocion,idSucursal);
       
       CodigoHTTP respuestaConexion = ConexionHTTP.peticioPOST(url, parametros);
       
       if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){     
//GSON claveSegura456
           Gson gson= new Gson();
           respuestaws = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
           respuestaws.setError(true);
           respuestaws.setContenido("error al realizar peticion");           
       }
        return respuestaws;}

    public static HashMap<String, Object> lista(Integer id) {
                HashMap<String,Object> respuesta= new LinkedHashMap<>();
        String url = Constantes.URI_WS+"promocion/promociones/empresa/"+id;
         CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
          if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
              respuesta.put("error", false);
              Gson gson = new Gson();
          Type tipoLista = new TypeToken<List<Promocion>>(){}.getType();
          List<Promocion> promociones =gson.fromJson(respuestaConexion.getContenido(),tipoLista);
          respuesta.put("lista", promociones);
          }else
          {
              respuesta.put("error", true);
              respuesta.put("mensaje", "error no se pudo consegir la operacion");
          }
        return respuesta;}

    public static HashMap<String, Object> lista() {
                                    HashMap<String,Object> respuesta= new LinkedHashMap<>();
        String url = Constantes.URI_WS+"promocion/promociones";
         CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
          if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
              respuesta.put("error", false);
              Gson gson = new Gson();
          Type tipoLista = new TypeToken<List<Promocion>>(){}.getType();
          List<Promocion> promociones =gson.fromJson(respuestaConexion.getContenido(),tipoLista);
          respuesta.put("lista", promociones);
          }else
          {
              respuesta.put("error", true);
              respuesta.put("mensaje", "error no se pudo consegir la operacion");
          }
        return respuesta;}

    public static HashMap<String, Object> listaCupon() {
               HashMap<String,Object> respuesta= new LinkedHashMap<>();
        String url = Constantes.URI_WS+"promocion/promociones";
         CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
          if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
              respuesta.put("error", false);
              Gson gson = new Gson();
          Type tipoLista = new TypeToken<List<Promocion>>(){}.getType();
          List<Promocion> promociones =gson.fromJson(respuestaConexion.getContenido(),tipoLista);
          respuesta.put("lista", promociones);
          }else
          {
              respuesta.put("error", true);
              respuesta.put("mensaje", "error no se pudo consegir la operacion");
          }
        return respuesta;}

    public static Mensaje canje(String text) {
     Mensaje respuestaws = new Mensaje();
       
        String url = Constantes.URI_WS+"/promocion/canjear/"+text;
          CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
       
       if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){     
//GSON claveSegura456
           Gson gson= new Gson();
           respuestaws = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
           respuestaws.setError(true);
           respuestaws.setContenido("error al realizar peticion");           
       }
     return respuestaws;}


}

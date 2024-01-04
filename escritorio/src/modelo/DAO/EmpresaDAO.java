/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.pojo.CodigoHTTP;
import modelo.pojo.Empresa;
import modelo.pojo.Mensaje;
import utiles.ConexionHTTP;
import utiles.Constantes;

/**
 *
 * @author a-rac
 */
public class EmpresaDAO {

    public static List<Empresa> obtenerEmpresas() {
                List<Empresa> empresas= null;
        String url = Constantes.URI_WS+"empresa/empresas/";
         CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
          if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
          Gson gson = new Gson();
          Type tipoListaEmpresa = new TypeToken<List<Empresa>>(){}.getType();
          empresas = gson.fromJson(respuestaConexion.getContenido(), tipoListaEmpresa);
          }
        return empresas;
    }
    public static Mensaje subirFotografiaEmpresa(byte[] fotografia, int idEmpresa){
         Mensaje msj = new Mensaje();
        String url = Constantes.URI_WS_Promocion_IMAGEN+idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionPUTImagen(url, fotografia);
        if(respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            msj = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        }else{
            msj.setError(true);
            msj.setContenido("Hubo un error al enviar la fotografía, "
                    + "por favor inténtalo más tarde.");
        }
        return msj;
    }
    
    
    public static Empresa obtenerFotografia(int idEmpresa){
        Empresa empresa = null;
        String url = Constantes.URI_WS_Empresa_LOGO+idEmpresa;
        CodigoHTTP respuesta = ConexionHTTP.peticionGET(url);

        if (respuesta.getCodigoRespuesta()== HttpURLConnection.HTTP_OK) {
               Gson gson = new Gson();
               empresa = gson.fromJson(respuesta.getContenido(), Empresa.class);
    }
        //columna id domicilio de empresa se borra y se agrega en tabla dominicilio id empresa
        return empresa;
    }
       public static Mensaje registro(Empresa empresa) {
         Gson gson = new Gson();
        Mensaje msj= new Mensaje();
        String url = Constantes.URI_WS_Empresa_R;
        
        String parametros = gson.toJson(empresa, Empresa.class) ;
                         
        CodigoHTTP respuestaConexion = ConexionHTTP.peticioPOSTJson(url, parametros);
        
        if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
           msj = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
         msj.setContenido("error");
         msj.setError(true);
       }
       return msj;
    }

    public static Mensaje actualizacion(Empresa empresa) {
   Gson gson = new Gson();
        Mensaje msj= new Mensaje();
        String url = Constantes.URI_WS_Empresa_M;
        
        String parametros = gson.toJson(empresa, Empresa.class) ;
                         
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
       String url = Constantes.URI_WS_Empresa_E+id;
     
        return mensaje;
    }

 

    public static Empresa obtenerEmpresa(Integer idEmpresa) {
    Gson gson = new Gson();
    Empresa e= new Empresa();
          String url = Constantes.URI_WS+"empresa/"+idEmpresa;
    CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
        
        if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
          
        
           
           e = gson.fromJson(respuestaConexion.getContenido(),Empresa.class);
           
       }
       return e;}

    public static HashMap<String, Object> lista() {
               HashMap<String,Object> respuesta= new LinkedHashMap<>();
        String url = Constantes.URI_WS+"empresa/empresas";
         CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
          if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
              respuesta.put("error", false);
              Gson gson = new Gson();
          Type tipoLista = new TypeToken<List<Empresa>>(){}.getType();
          List<Empresa> promociones =gson.fromJson(respuestaConexion.getContenido(),tipoLista);
          respuesta.put("lista", promociones);
          }else
          {
              respuesta.put("error", true);
              respuesta.put("mensaje", "error no se pudo consegir la operacion");
          }
        return respuesta;}

}

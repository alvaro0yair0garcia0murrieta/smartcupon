/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import modelo.pojo.CodigoHTTP;
import modelo.pojo.Mensaje;
import modelo.pojo.Promocion;
import modelo.pojo.Usuario;
import utiles.ConexionHTTP;
import utiles.Constantes;

/**
 *
 * @author a-rac
 */
public class UsuarioDAO {

    public static Mensaje validacion(String username, String password){
      
        Mensaje respuestaws = new Mensaje();
       
        String url = Constantes.URI_WS_USUARIO_LOGIN;
       String parametros = String.format("username=%s&contrasena=%s",username,password);
       
       CodigoHTTP respuestaConexion = ConexionHTTP.peticioPOST(url, parametros);
       
       if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){     
//GSON claveSegura456
           Gson gson= new Gson();
           respuestaws = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
           respuestaws.setError(true);
           respuestaws.setContenido("error al realizar peticion");           
       }
        return respuestaws;
    } 
    
        
    public static Mensaje registro(Usuario usuario) {
         Gson gson = new Gson();
        Mensaje msj= new Mensaje();
        String url = Constantes.URI_WS_USUARIO_R;
        
        String parametros = gson.toJson(usuario, Usuario.class) ;
                         
        CodigoHTTP respuestaConexion = ConexionHTTP.peticioPOSTJson(url, parametros);
        
        if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
           msj = gson.fromJson(respuestaConexion.getContenido(),Mensaje.class);
           
       }else{
         msj.setContenido("error");
         msj.setError(true);
       }
       return msj;
    }

    public static Mensaje actualizacion(Usuario usuario) {
   Gson gson = new Gson();
        Mensaje msj= new Mensaje();
        String url = Constantes.URI_WS_USUARIO_M;
        
        String parametros = gson.toJson(usuario, Usuario.class) ;
                         
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
       String url = Constantes.URI_WS_USUARIO_E+id;
     
        return mensaje;
    }

    public static HashMap<String, Object> lista() {
              HashMap<String,Object> respuesta= new LinkedHashMap<>();
        String url = Constantes.URI_WS+"usuario/usuarios";
         CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
          if(respuestaConexion.getCodigoRespuesta()== HttpURLConnection.HTTP_OK){
              respuesta.put("error", false);
              Gson gson = new Gson();
          Type tipoLista = new TypeToken<List<Usuario>>(){}.getType();
          List<Usuario> promociones =gson.fromJson(respuestaConexion.getContenido(),tipoLista);
          respuesta.put("lista", promociones);
          }else
          {
              respuesta.put("error", true);
              respuesta.put("mensaje", "error no se pudo consegir la operacion");
          }
        return respuesta; }
}

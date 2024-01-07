/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.pojo.CodigoHTTP;
import modelo.pojo.Mensaje;
import modelo.pojo.Sucursal;
import utiles.ConexionHTTP;
import utiles.Constantes;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author a-rac
 */
public class SucursalDAO {

    public static HashMap<String, Object> lista(int id) {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URI_WS + "sucursal/sucursales/empresa/" + id;
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.put("error", false);
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();
            List<Sucursal> sucursales = gson.fromJson(respuestaConexion.getContenido(), tipoLista);
            respuesta.put("lista", sucursales);
        } else {
            respuesta.put("error", true);
            respuesta.put("mensaje", "error no se pudo consegir la operacion");
        }
        return respuesta;
    }

    public static HashMap<String, Object> lista() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String url = Constantes.URI_WS + "sucursal/sucursales";
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            respuesta.put("error", false);
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();
            List<Sucursal> sucursales = gson.fromJson(respuestaConexion.getContenido(), tipoLista);
            respuesta.put("lista", sucursales);
        } else {
            respuesta.put("error", true);
            respuesta.put("mensaje", "error no se pudo consegir la operacion");
        }
        return respuesta;
    }

    public static Mensaje registro(Sucursal sucursal) {
        Gson gson = new Gson();
        Mensaje msj = new Mensaje();
        String url = Constantes.URI_WS_SUCURSAL_R;

        String parametros = gson.toJson(sucursal, Sucursal.class);

        CodigoHTTP respuestaConexion = ConexionHTTP.peticioPOSTJson(url, parametros);

        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            msj = gson.fromJson(respuestaConexion.getContenido(), Mensaje.class);

        } else {
            msj.setContenido("error");
            msj.setError(true);
        }
        return msj;
    }

    public static Mensaje actualizacion(Sucursal sucursal) {
        Gson gson = new Gson();
        Mensaje msj = new Mensaje();
        String url = Constantes.URI_WS_SUCURSAL_M;

        String parametros = gson.toJson(sucursal, Sucursal.class);

        CodigoHTTP respuestaConexion = ConexionHTTP.peticionPUTJson(url, parametros);

        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {


            msj = gson.fromJson(respuestaConexion.getContenido(), Mensaje.class);

        } else {
            msj.setContenido("eroro UWU");
            msj.setError(true);
        }
        return msj;
    }

    public static Mensaje eliminar(int id) {
        Mensaje mensaje = new Mensaje();
        String url = Constantes.URI_WS_SUCURSAL_E + id;
        CodigoHTTP respuesta = ConexionHTTP.peticionDELETE(url);
        if (respuesta.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            mensaje = gson.fromJson(respuesta.getContenido(), Mensaje.class);
        } else {
            mensaje.setError(true);
            mensaje.setContenido("Error por el momento no puede eliminar");
        }
        return mensaje;
    }

    public static List<Sucursal> obtenerSucursales(int id) {
        List<Sucursal> sucursales = null;
        String url = Constantes.URI_WS + "sucursal/sucursales/empresa/" + id;
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaSucursal = new TypeToken<List<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuestaConexion.getContenido(), tipoListaSucursal);
        }
        return sucursales;
    }


    public static List<Sucursal> obtenerSucursalesPromo(int id) {
        List<Sucursal> sucursales = null;
        String url = Constantes.URI_WS + "promocion/" + id;
        CodigoHTTP respuestaConexion = ConexionHTTP.peticionGET(url);
        if (respuestaConexion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoListaSucursal = new TypeToken<List<Sucursal>>() {
            }.getType();
            sucursales = gson.fromJson(respuestaConexion.getContenido(), tipoListaSucursal);
        }
        return sucursales;
    }


}




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.pojo.Cliente;
import modelo.pojo.Respuesta;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * @author a-rac
 */
public class ClienteDAO {
    public static Respuesta registrar(Cliente cliente) {
        Respuesta msj = new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");
        } else if (conexionDB.selectOne("cliente.busquedaCorreo", cliente.getCorreo()) != null) {
            msj.setContenido("erorr: alguen ya registrado con el mismo correo");
        } else if (ValidacionFecha(cliente.getNacimiento())) {
            msj.setContenido("Error fecha invalida para base de datos");
        } else {
            try {
                int numeroFilasAfectadas = conexionDB.insert("cliente.registrar", cliente);
                conexionDB.commit();
                if (numeroFilasAfectadas > 0) {
                    msj.setError(false);
                    msj.setContenido("fue registrado con exito");
                } else {
                    msj.setContenido("no se pudo registrar");
                }
            } catch (Exception e) {
                msj.setContenido("ERROR:" + e.getMessage());
            } finally {
                conexionDB.close();
            }
        }
        return msj;
    }

    public static boolean ValidacionFecha(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha.setLenient(false);
        try {
            Date fechaFormateada = formatoFecha.parse(fecha);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public static Respuesta actualizar(Cliente cliente) {
        Respuesta msj = new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");
        } else if (ValidacionFecha(cliente.getNacimiento())) {
            msj.setContenido("Error fecha invalida para base de datos");
        } else {
            try {
                if (cliente.getIdCliente() != null) {
                    int numeroFilasAfectadas = conexionDB.update("cliente.actualizar", cliente);
                    conexionDB.commit();
                    if (numeroFilasAfectadas > 0) {
                        msj.setError(false);
                        msj.setContenido("fue actualizado con exito");

                    } else {
                        msj.setContenido("no se pudo actualizar xd");
                    }
                } else {
                    msj.setContenido("error id es nulo");
                }

            } catch (Exception ignored) {
            } finally {
                conexionDB.close();
            }
        }
        return msj;
    }

    public static Respuesta login(String correo, String contrasena) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession sqlSession = MyBatisUtil.getSession();
        if (sqlSession == null) {
            respuesta.setContenido("error no conexion a bd");
        } else {
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("correo", correo);
            parametros.put("contrasena", contrasena);
            Cliente cliente = sqlSession.selectOne("cliente.login", parametros);
            if (cliente == null) {
                respuesta.setContenido("contraseña y/o correo incorrecto");
            } else {
                respuesta.setCliente(cliente);
                respuesta.setError(false);
                respuesta.setContenido("bienvenido " + cliente.getNombre());
            }
        }
        return respuesta;
    }
}
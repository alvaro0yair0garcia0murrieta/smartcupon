/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.pojo.Respuesta;
import modelo.pojo.Usuario;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.HashMap;
import java.util.List;
/**
 * @author a-rac
 */
public class UsuarioDAO {
    public static List<Usuario> usuarios() {
        List<Usuario> usuarios = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        try {
            usuarios = conexionDB.selectList("usuarios");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionDB.close();
        }
        return usuarios;
    }

    public static Respuesta registrar(Usuario usuario) {
        Respuesta msj = new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession();

        if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");

        } else if (conexionDB.selectOne("usuario.busquedaCurp", usuario.getCurp()) != null) {
            msj.setContenido("error: alguen ya registrado con el mismo curp");

        } else if (conexionDB.selectOne("usuario.busquedaUsername", usuario.getUsername()) != null) {
            msj.setContenido("error: alguen ya registrado con el mismo username");

        } else {
            try {
                int numeroFilasAfectadas = conexionDB.insert("usuario.registrar", usuario);
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

    public static Respuesta modificar(Usuario usuario) {
        Respuesta msj = new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");
        } else {
            try {
                if (usuario.getIdUsuario() != null) {
                    int numeroFilasAfectadas = conexionDB.update("usuario.actualizar", usuario);
                    conexionDB.commit();
                    if (numeroFilasAfectadas > 0) {
                        msj.setError(false);
                        msj.setContenido("fue actualizado con exito");

                    } else {
                        msj.setContenido("no se pudo actualizar xd");
                    }
                } else {
                    msj.setContenido(" error id es nulo");
                }

            } catch (Exception e) {
            } finally {
                conexionDB.close();
            }
        }
        return msj;
    }

    public static Respuesta eliminar(int id) {
        SqlSession conexionDB = MyBatisUtil.getSession();
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);


        if (conexionDB != null) {
            int numeroFilasAfectadas = conexionDB.delete("usuario.eliminar", id);
            conexionDB.commit();

            if (numeroFilasAfectadas > 0) {
                respuesta.setContenido("eliminado con exito");
                respuesta.setError(false);
            } else {
                respuesta.setContenido("no se pudo eliminar");
            }
        } else {
            respuesta.setContenido("no conexion a al base de datos");
        }
        conexionDB.close();

        return respuesta;

    }


    public static Respuesta login(String username, String contrasena) {
        Respuesta respuesta = new Respuesta();
        respuesta.setError(true);
        SqlSession sqlSession = MyBatisUtil.getSession();
        if (sqlSession == null) {
            respuesta.setContenido("error no conexion a bd");
        } else {
            HashMap<String, String> parametros = new HashMap<>();
            parametros.put("username", username);
            parametros.put("contrasena", contrasena);
            Usuario usuario = sqlSession.selectOne("usuario.login", parametros);
            if (usuario == null) {
                respuesta.setContenido("contraseña y/o correo incorrecto");
            } else {
                respuesta.setUsuario(usuario);
                respuesta.setError(false);
                respuesta.setContenido("bienvenido " + usuario.getNombre());

            }
        }
        return respuesta;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;


import modelo.pojo.Respuesta;
import modelo.pojo.Sucursal;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author a-rac
 */
public class SucursalDAO {
    
    public static Respuesta registrar(Sucursal sucursal) {
     Respuesta msj= new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession(); 
        
   if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");        
   }else{
            try {
                int numeroFilasAfectadas = conexionDB.insert("sucursal.registrar", sucursal);
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

    public static Respuesta modificar(Sucursal sucursal) {
    Respuesta msj= new Respuesta();
       msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession(); 
        if (conexionDB== null) {
            msj.setContenido("NO CONEXION A DB");
        }else {
            try {
                if (sucursal.getIdSucursal() != null) {
                    int numeroFilasAfectadas = conexionDB.update("sucursal.actualizar", sucursal);
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
                
            } catch (Exception e) {
            }finally{
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
           if (conexionDB.selectOne("busquedaPromocion", id)== null) {
                      int numeroFilasAfectadas = conexionDB.delete("sucursal.eliminar", id);
                      conexionDB.commit();
                      conexionDB.close();
                      if (numeroFilasAfectadas > 0) {
                          respuesta.setContenido("eliminado con exito");
                          respuesta.setError(false);
                      } else {
                          respuesta.setContenido("no se pudo eliminar");
                      }
                  }else {
                      respuesta.setContenido("se encontratados promociones asociadas porfavor elimine promociones ");
                  }
        }else{
                  respuesta.setContenido("no conexion a al base de datos");
              }
             conexionDB.close();
              
                     return respuesta;
                
    }
    
}

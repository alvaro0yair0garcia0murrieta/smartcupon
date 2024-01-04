/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.pojo.Empresa;
import modelo.pojo.Respuesta;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author a-rac
 */
public class EmpresaDAO {

    public static Respuesta subirLogo(int id, byte[] logo) {
    Respuesta msj= new Respuesta();
    msj.setError(true);
     SqlSession ss= MyBatisUtil.getSession();
     
     if (ss!=null) {
         try{
            Empresa empresa = new Empresa();
             empresa.setIdEmpresa(id);
             empresa.setLogo(logo);
             int numeroFilasAfectadas = ss.update("empresa.SubirLogo", empresa);
             ss.commit();
             
              if (numeroFilasAfectadas > 0) {
                        msj.setError(false);
                        msj.setContenido("fue subida con exito el archivo ");

                    } else {
                        
                        msj.setContenido("no se pudo subir xd");
              }
             
         }catch(Exception e){
          
         }finally{
             ss.close();
         }
     }else{
         msj.setContenido("no se pudo conectar db xd");
     }

     return msj;
    }

    public static Empresa obtenerLogo(int id) {
    Empresa empresa= null;
     SqlSession ss= MyBatisUtil.getSession();
     if (ss!=null) {
         try{
             empresa= ss.selectOne("empresa.obtenerLogo", id);
         }catch(Exception e) {
             e.printStackTrace();
         }finally{
             ss.close();
         }
     }
     return empresa;
    }

    public static Respuesta registrar(Empresa empresa) {
     Respuesta msj= new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession(); 
        
   if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");        
   }else{
            try {
                int numeroFilasAfectadas = conexionDB.insert("empresa.registrar", empresa);
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

    public static Respuesta modificar(Empresa empresa) {
    Respuesta msj= new Respuesta();
       msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession(); 
        if (conexionDB== null) {
            msj.setContenido("NO CONEXION A DB");
        }else {
            try {
                if (empresa.getIdEmpresa()!= null) {
                    int numeroFilasAfectadas = conexionDB.update("empresa.actualizar", empresa);
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
            
                  if (conexionDB.selectOne("busquedaSucursal", id)== null) {
                      int numeroFilasAfectadas = conexionDB.delete("empresa.eliminar", id);
                      conexionDB.commit();
                      conexionDB.close();
                      if (numeroFilasAfectadas > 0) {
                          respuesta.setContenido("eliminado con exito");
                          respuesta.setError(false);
                      } else {
                          respuesta.setContenido("no se pudo eliminar");
                      }
                  }else {
                      respuesta.setContenido("se encontratados sucursales asociadas porfavor elimine sucursales ");
                  }
        }else{
                  respuesta.setContenido("no conexion a al base de datos");
              }
              
                     return respuesta;
                
    }
    
}

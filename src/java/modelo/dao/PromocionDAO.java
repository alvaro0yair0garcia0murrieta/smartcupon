/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import modelo.pojo.Promocion;
import modelo.pojo.Respuesta;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author a-rac
 */
public class PromocionDAO {
      public static Respuesta registrar(Promocion promocion) {
     Respuesta msj= new Respuesta();
        msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession(); 
        
   if (conexionDB == null) {
            msj.setContenido("NO CONEXION A DB");        
   }else if(conexionDB.selectOne("promocion.busquedaCodigo",promocion.getCodigo())!= null){
   msj.setContenido("codigo registrado con otra promocion");
   }
   else{
            try {
                int numeroFilasAfectadas = conexionDB.insert("promocion.registrar", promocion);
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

    public static Respuesta modificar(Promocion promocion) {
    Respuesta msj= new Respuesta();
       msj.setError(true);
        SqlSession conexionDB = MyBatisUtil.getSession(); 
        if (conexionDB== null) {
            msj.setContenido("NO CONEXION A DB");
        }else {
            try {
                if (promocion.getIdPromocion() != null) {
                    int numeroFilasAfectadas = conexionDB.update("promocion.actualizar", promocion);
                    conexionDB.commit();
                    if (numeroFilasAfectadas > 0) {
                        msj.setError(false);
                        msj.setContenido("fue actualizado con exito");
                        
                    } else {
                        msj.setContenido("no se pudo actualizar xd");
                    }
                }else{
                    msj.setContenido(" error id es nulo ");
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
                  if (id>0) {
                      int numeroFilasAfectadas = conexionDB.delete("promocion.eliminar", id);
                      conexionDB.commit();
                      
                      if (numeroFilasAfectadas > 0) {
                          respuesta.setContenido("eliminado con exito");
                          respuesta.setError(false);
                      } else {
                          respuesta.setContenido("no se pudo eliminar");
                      }
                  } else {
                     respuesta.setContenido("id es invalido");
                  }
        }else{
                  respuesta.setContenido("no conexion a al base de datos");
              }
             conexionDB.close();
              
                     return respuesta;
                
    }
    
    public static Respuesta subirImagen(int id, byte[] imagen) {
    Respuesta msj= new Respuesta();
    msj.setError(true);
     SqlSession ss= MyBatisUtil.getSession();
      
     if (ss!=null) {
         try{
            Promocion promocion = new Promocion();
             promocion.setIdPromocion(id);
             promocion.setImagen(imagen);
             int numeroFilasAfectadas = ss.update("promocion.SubirImagen", promocion);
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

    public static Promocion obtenerImagen(int id) {
    Promocion promocion= null;
     SqlSession ss= MyBatisUtil.getSession();
     if (ss!=null) {
         try{
             promocion= ss.selectOne("promocion.obtenerImagen", id);
         }catch(Exception e) {
             e.printStackTrace();
         }finally{
             ss.close();
         }
     }
     return promocion;
    }
    
    public static String canje(String codigo){
        SqlSession sqlSession = MyBatisUtil.getSession();
        Promocion promocion= sqlSession.selectOne("busquedaCodigo", codigo);
        String respuesta = new String();
        
        if(promocion.estatus()){
        int cuponesDisponibles= promocion.getCuponesMax();
            if (cuponesDisponibles>0) {
                promocion.setCuponesMax(cuponesDisponibles-1);
               modificar(promocion);
            respuesta = "cupones disponibles" + cuponesDisponibles;
            }
            else{
                promocion.setEstatus("DESACTIVADO");
               modificar(promocion); 
            }
        }else{
        respuesta ="lo sentimos la promocion  ya no es valida";    
        }
        return respuesta;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import modelo.pojo.Promocion;
import modelo.pojo.Respuesta;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

/**
 *
 * @author a-rac
 */
public class PromocionDAO 

{
    
    public static List<Promocion> promociones() {
     List<Promocion> promociones = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        try {
            promociones = conexionDB.selectList("promociones");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionDB.close();
        }
        return promociones;
    }
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
        Respuesta msj = new Respuesta();
   SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB == null) {
            msj.setError(true);
            msj.setContenido("NO CONEXION A DB");
        } else {
            try {
               
                  int numeroFilasAfectadas = conexionDB.update("promocion.actualizar", promocion);
                    conexionDB.commit();
                    if (numeroFilasAfectadas > 0) {
                        msj.setError(false);
                        msj.setContenido("fue actualizado con exito");

                    } else {
                        msj.setError(true);
                        msj.setContenido("no se pudo actualizar xd");
                    }    
             
            } catch (Exception e) {
                msj.setError(true);
                msj.setContenido("ERROR:" + e.getMessage());

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
                  if (id>0) {
                      int numeroFilasAfectadas = conexionDB.delete("promocion.eliminar", id);
                      conexionDB.commit();
                      
                      if (numeroFilasAfectadas > 0) {
                          respuesta.setContenido("eliminado con exito");
                          respuesta.setError(false);
                          conexionDB.delete("promocion.promosucuDelete",id);
                          conexionDB.commit();
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
        if (promocion!=null) {
             
        if(promocion.estatus()){
        int cuponesDisponibles= promocion.getCuponesMax();
            if (cuponesDisponibles>0) {
                promocion.setCuponesMax(cuponesDisponibles-1);
               int cuponesRestantes = cuponesDisponibles-1;
               modificar(promocion);
            respuesta = "cupones disponibles "+ cuponesRestantes;
            }
            else{
                promocion.setEstatus("DESACTIVADO");
               modificar(promocion); 
            }
        }else{
        respuesta ="lo sentimos la promocion  ya no es valida";    
        }
        } else {
            respuesta =  "codigo erroneo verifique codigo";
        }
        return respuesta;
    }

    public static Respuesta insertarPromoSucu(Integer promocion, Integer sucursal) {
    Respuesta respuesta = new Respuesta();
    respuesta.setError(true);
    SqlSession session = MyBatisUtil.getSession();
        if (session!= null) {
            HashMap<String,Integer> parametros= new HashMap<>();
            parametros.put("promocion", promocion);
            parametros.put("sucursal", sucursal);
            
            try {
                int filas= session.insert("promocion.asignar", parametros);
                session.commit();
                if(filas>0){
                    respuesta.setContenido("asignado con exito ");
                    respuesta.setError(false);
                }
            } catch (Exception e) {
                respuesta.setContenido("la promocion ya esta asignada a las sucurales");
            }finally {
                session.close();
            }
 
        } else {
            respuesta.setContenido("lo sentimos no existe conexion a la base de datos");
        }
        return respuesta;
    }

    public static List<Promocion> promociones(String categoria) {
         List<Promocion> promociones = null;
         
        SqlSession conexionDB = MyBatisUtil.getSession();
        try {
            promociones = conexionDB.selectList("promocionesCategoria",categoria);
            /* for (Promocion promocione : promociones) {
               if (esFechaNoExpirada(promocione.getFechaFin())) {
                    if (promocione.estatus()) {
                     filter.add(promocione);
                    }
                }
            }*/
            for (Promocion promocione : promociones) {
                if (!promocione.estatus()) {
                    promociones.remove(promocione);
                }
               
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionDB.close();
        }
        return promociones;
    
    }

    public static List<Promocion> sucursales(int idEmpresa) {
     List<Promocion> promociones = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        try {
            promociones= conexionDB.selectList("promocionesE", idEmpresa);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexionDB.close();
        }
        return promociones;}
    
private static boolean esFechaNoExpirada(String fecha) {
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        try {
        
            Date fechaParseada = formatoFecha.parse(fecha);

          
            Date fechaActual = new Date();

         
            return !fechaParseada.before(fechaActual);
        } catch (ParseException e) {
           
            return false;
        }
    }
   
}

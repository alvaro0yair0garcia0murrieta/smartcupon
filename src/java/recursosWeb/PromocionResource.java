/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursosWeb;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.dao.PromocionDAO;
import modelo.pojo.Promocion;
import modelo.pojo.Respuesta;

/**
 * REST Web Service
 *
 * @author a-rac
 */
@Path("promocion")
public class PromocionResource {
    Gson gson = new Gson();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PromocionResource
     */
    public PromocionResource() {
    }

    /**
     * Retrieves representation of an instance of recursosWeb.PromocionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of PromocionResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
@POST
    @Path("registrar")
    @Produces
    public Respuesta registrar(String json){
         Promocion promocion = gson.fromJson(json,  Promocion.class);
         if (promocion == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
         return  PromocionDAO.registrar(promocion);
    }
    
    
    @PUT
    @Path("actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta actualizar(String json){
         Promocion promocion= gson.fromJson(json,  Promocion.class);
        
        if (promocion != null) {
            return  PromocionDAO.modificar(promocion);
        }
        return null;   
    }
    
    @DELETE
    @Path("eliminar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizar(@PathParam("id")int id){
        if (id<0) {
            throw new WebApplicationException (Response.Status.BAD_REQUEST);
        }
        else{
        return  PromocionDAO.eliminar(id);        
        }
    }
     @PUT
    @Path("imagen/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarImagen(@PathParam("id") int id,byte[] imagen){
        if (id > 0 && imagen != null) {
            return PromocionDAO.subirImagen(id, imagen);
        }else{
               throw new WebApplicationException (Response.Status.BAD_REQUEST);
        }
        
    }
    
    @GET
    @Path("imagen/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion obtenerfoto(@PathParam("id")int id){
        if (id>0) {
             return PromocionDAO.obtenerImagen(id);
        }else{
            throw new WebApplicationException (Response.Status.BAD_REQUEST);
        }
       
    }
    
    @GET
    @Path("canjear/{codigo}")
    @Produces(MediaType.TEXT_PLAIN)
    public String canjear(@PathParam("codigo") String codigo){
    
        return PromocionDAO.canje(codigo);
    }
    }


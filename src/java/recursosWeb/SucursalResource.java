/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursosWeb;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.dao.SucursalDAO;
import modelo.pojo.Respuesta;
import modelo.pojo.Sucursal;

/**
 * REST Web Service
 *
 * @author a-rac
 */
@Path("sucursal")
public class SucursalResource {
Gson gson = new Gson();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SucursalResource
     */
    public SucursalResource() {
    }

    /**
     * Retrieves representation of an instance of recursosWeb.SucursalResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SucursalResource
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
         Sucursal sucursal = gson.fromJson(json,  Sucursal.class);
         if (sucursal == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
         return  SucursalDAO.registrar(sucursal);
    }
    
    
    @PUT
    @Path("actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta actualizar(String json){
         Sucursal sucursal= gson.fromJson(json,  Sucursal.class);
        
        if (sucursal != null) {
            return  SucursalDAO.modificar(sucursal);
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
        return  SucursalDAO.eliminar(id);        
        }
     
    }
}

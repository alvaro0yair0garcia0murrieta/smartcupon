/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursosWeb;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.dao.EmpresaDAO;
import modelo.pojo.Empresa;
import modelo.pojo.Respuesta;
import com.google.gson.Gson;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author a-rac
 */
@Path("empresa")
public class EmpresaResource {
    Gson gson= new Gson();

    public EmpresaResource() {
    }
    
    
    @POST
    @Path("registrar")
    @Produces
    public Respuesta registrar(String json){
        Empresa empresa = gson.fromJson(json, Empresa.class);
         if (empresa == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
         return EmpresaDAO.registrar(empresa);
    }
    
    
    @PUT
    @Path("actualizar")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Respuesta actualizar(String json){
        Empresa empresa= gson.fromJson(json, Empresa.class);
        
        if (empresa != null) {
            return EmpresaDAO.modificar(empresa);
        }
        return null;   
    }
    
    @DELETE
    @Path("eliminar/{idEmpresa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta actualizar(@PathParam("idEmpresa")int id){
        if (id<0) {
            throw new WebApplicationException (Response.Status.BAD_REQUEST);
        }
        else{
        return EmpresaDAO.eliminar(id);        
        }
     
    }
    
    @PUT
    @Path("logo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrarLogo(@PathParam("id") int id,byte[] logo){
        
       
        
        if (id > 0 && logo != null) {
            return EmpresaDAO.subirLogo(id,logo);
        }else{
               throw new WebApplicationException (Response.Status.BAD_REQUEST);
        }
        
    }
    
    @GET
    @Path("logo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa obtenerfoto(@PathParam("id")int id){
        if (id>0) {
             return EmpresaDAO.obtenerLogo(id);
        }else{
            throw new WebApplicationException (Response.Status.BAD_REQUEST);
        }
       
    }
    
    
}

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
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.dao.UsuarioDAO;
import modelo.pojo.Respuesta;
import modelo.pojo.Usuario;

/**
 * REST Web Service
 *
 * @author a-rac
 */
@Path("usuario")
public class UsuarioResource {
    Gson gson = new Gson();
    
  
    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
    }

    /**
     * Retrieves representation of an instance of recursosWeb.UsuarioResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of UsuarioResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
   
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("registrar")
    public Respuesta registro(String json){
      Usuario usuario= gson.fromJson(json, Usuario.class);
        if (usuario == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return UsuarioDAO.registrar(usuario);
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("actualizar")
    public Respuesta actualizar(String json){
     Usuario usuario = gson.fromJson(json, Usuario.class);
        if (usuario.getIdUsuario()<0 || usuario.getIdUsuario()==null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
            
        }
        return UsuarioDAO.modificar(usuario);
    } 
    
    @POST
    @Produces
    @Path("login")
    public Respuesta login(@FormParam("username") String user,@FormParam("contrasena") String contrasena){
       return UsuarioDAO.login(user,contrasena); 
    }
}

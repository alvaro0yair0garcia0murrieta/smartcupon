/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursosWeb;

import com.google.gson.Gson;
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
import modelo.dao.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Respuesta;

/**
 * REST Web Service
 *
 * @author a-rac
 */
@Path("cliente")
public class ClienteResource {
    Gson gson = new Gson();
    public ClienteResource() {}

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String bienvenido(){
        return "raiz de cliente";
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("registrar")
    public Respuesta registro(String json){
      Cliente cliente= gson.fromJson(json, Cliente.class);
        if (cliente == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ClienteDAO.registrar(cliente);
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("actualizar")
    public Respuesta actualizar(String json){
     Cliente cliente = gson.fromJson(json, Cliente.class);
        if (cliente.getIdCliente()<0 || cliente.getIdCliente()==null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ClienteDAO.actualizar(cliente);
    } 
    
    @POST
    @Produces
    @Path("login")
    public Respuesta login(@FormParam("correo") String correo,@FormParam("contrasena") String contrasena){
       return ClienteDAO.login(correo,contrasena); 
    }
            
}
    
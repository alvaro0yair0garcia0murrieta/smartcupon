/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursosWeb;

import com.google.gson.Gson;
import modelo.dao.ClienteDAO;
import modelo.pojo.Cliente;
import modelo.pojo.Respuesta;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author a-rac
 */
@Path("cliente")
public class ClienteResource {
    Gson gson = new Gson();

    public ClienteResource() {
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String bienvenido() {
        return "raiz de cliente";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("registrar")
    public Respuesta registro(String json) {
        Cliente cliente = gson.fromJson(json, Cliente.class);
        if (cliente == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ClienteDAO.registrar(cliente);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("actualizar")
    public Respuesta actualizar(String json) {
        Cliente cliente = gson.fromJson(json, Cliente.class);
        if (cliente.getIdCliente() < 0 || cliente.getIdCliente() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return ClienteDAO.actualizar(cliente);
    }

    @POST
    @Produces
    @Path("login")
    public Respuesta login(@FormParam("correo") String correo, @FormParam("contrasena") String contrasena) {
        return ClienteDAO.login(correo, contrasena);
    }

}
    
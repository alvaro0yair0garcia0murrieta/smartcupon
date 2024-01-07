/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;


/**
 * @author a-rac
 */
public class Mensaje {
    private String contenido;
    private boolean error;
    private Cliente cliente;
    private Usuario usuario;

    public Mensaje(String contenido, boolean error) {
        this.contenido = contenido;
        this.error = error;

    }

    public Mensaje() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "contenido=" + contenido + ", error=" + error + ", cliente=" + cliente + ", usuario=" + usuario.toString() + '}';
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}

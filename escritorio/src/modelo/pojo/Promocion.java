/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.pojo;

/**
 * @author a-rac
 */
public class Promocion {

    private Integer idPromocion;
    private Integer idEmpresa;
    private byte[] imagen;
    private String imagenBase64;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String restriccion;
    private String categoria;
    private String tipo;
    private String estatus;
    private String fechaInicio;
    private String fechaFin;
    private Integer cuponesMax;
    private String getImagenBase64;

    public Promocion() {
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }


    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCuponesMax() {
        return cuponesMax;
    }

    public void setCuponesMax(Integer cuponesMax) {
        this.cuponesMax = cuponesMax;
    }

    public boolean estatus() {
        String status = this.estatus;
        return "ACTIVO".equals(status);
    }

    @Override
    public String toString() {
        return nombre;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getGetImagenBase64() {
        return getImagenBase64;
    }

    public void setGetImagenBase64(String getImagenBase64) {
        this.getImagenBase64 = getImagenBase64;
    }
}

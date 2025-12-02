package es.peluqueria.gestion.modelo;

import java.math.BigDecimal;

public class Servicio {
    private int idServicio;
    private String nombre;
    private BigDecimal precio;
    private int duracionMinutos;
    private String descripcion;

    // ----- Getters -----
    public int getIdServicio() {
        return idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // ----- Setters -----
    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

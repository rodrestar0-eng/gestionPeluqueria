package es.peluqueria.gestion.modelo;

import java.time.LocalDate;

public class Cita {

    private int idCita;
    private int idCliente;
    private int idPeluquero;
    private int idServicio;
    private LocalDate fechaCita;
    private String horaInicio;
    private String horaFin;
    private int estado; // 1 = pendiente, 2 = completada, 3 = cancelada
    private String nombrePeluquero;
    private String nombreServicio;
    private String nombreCliente;
    
    

    public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getNombrePeluquero() {
        return nombrePeluquero;
    }

    public void setNombrePeluquero(String nombrePeluquero) {
        this.nombrePeluquero = nombrePeluquero;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    // ====== GETTERS ======
    public int getIdCita() {
        return idCita;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdPeluquero() {
        return idPeluquero;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public int getEstado() {
        return estado;
    }

    // ====== SETTERS ======
    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdPeluquero(int idPeluquero) {
        this.idPeluquero = idPeluquero;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}

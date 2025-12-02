package es.peluqueria.gestion.modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private int tipoUsuario; // 1 = ADMIN, 2 = RECEPCIONISTA, 3 = PELUQUERO
    private String telefono;

    // ----- Getters -----
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }
    
    

    public String getTelefono() {
		return telefono;
	}

	// ----- Setters -----
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}

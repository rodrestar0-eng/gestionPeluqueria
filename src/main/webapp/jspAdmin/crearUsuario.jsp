<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>

<%
    // Seguridad: solo admin
    Usuario admin = (Usuario) session.getAttribute("usuario");
    if (admin == null || admin.getTipoUsuario() != 1) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
    
    String error = (String) request.getAttribute("error");

    // Especialidades pasadas desde AdminController
    List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Usuario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .form-check { margin-bottom: 0.5rem; }
        .card-body { padding: 1.5rem; }
    </style>
</head>

<body class="bg-light">
    <div class="container-fluid mt-3">
        <div class="row justify-content-center">
            <div class="col-md-10 col-lg-8 col-xl-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h2 class="card-title mb-0">Crear Nuevo Usuario</h2>
                    </div>
                    <div class="card-body">
                        <a href="admin?accion=panel" class="btn btn-secondary mb-4">⬅ Volver al Panel</a>
                        <% if (error != null) { %>
    <div class="alert alert-danger text-center fw-bold">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <%= error %>
    </div>
			<% } %>
                        
                        <form action="admin" method="post">
                            <input type="hidden" name="accion" value="crearUsuario">
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="nombre" class="form-label">Nombre:</label>
                                    <input type="text" id="nombre" name="nombre" class="form-control" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="apellido" class="form-label">Apellido:</label>
                                    <input type="text" id="apellido" name="apellido" class="form-control" required>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="email" class="form-label">Email:</label>
                                    <input type="email" id="email" name="email" class="form-control" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="telefono" class="form-label">Teléfono:</label>
                                    <input type="text" id="telefono" name="telefono" class="form-control">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="contrasena" class="form-label">Contraseña:</label>
                                <input type="password" id="contrasena" name="contrasena" class="form-control" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="tipoUsuario" class="form-label">Tipo de Usuario:</label>
                                <select name="tipoUsuario" id="tipoUsuario" class="form-select" onchange="mostrarEspecialidades()" required>
                                    <option value="">Seleccione...</option>
                                    <option value="1">Administrador</option>
                                    <option value="3">Recepcionista</option>
                                    <option value="2">Peluquero / Trabajador</option>
                                </select>
                            </div>
                                 
                            <div class="d-grid mt-4">
                                <button type="submit" class="btn btn-primary btn-lg">Crear Usuario</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function mostrarEspecialidades() {
            let tipo = document.getElementById("tipoUsuario").value;
            let div = document.getElementById("box-especialidades");

            if (tipo === "2") div.style.display = "block";
            else div.style.display = "none";
        }
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: none;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .badge {
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lista cargada desde el controlador
        List<Especialidad> especialidades = (List<Especialidad>) request.getAttribute("especialidades");
    %>
    
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="bi bi-person-circle"></i> Mi Perfil</a>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h2 class="mb-0"><i class="bi bi-pencil-square"></i> Editar Mi Perfil</h2>
                    </div>
                    <div class="card-body">
                        <!-- AHORA USA actualizarMiPerfil -->
                        <form method="post" action="<%= request.getContextPath() %>/usuario">
                            
                            <!-- Acción correcta -->
                            <input type="hidden" name="accion" value="actualizarMiPerfil">

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label"><i class="bi bi-person"></i> Nombre</label>
                                    <input type="text" class="form-control" name="nombre"
                                           value="<%= usuario.getNombre() %>" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label class="form-label"><i class="bi bi-person"></i> Apellido</label>
                                    <input type="text" class="form-control" name="apellido"
                                           value="<%= usuario.getApellido() %>">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label"><i class="bi bi-envelope"></i> Email</label>
                                    <input type="email" class="form-control" name="email"
                                           value="<%= usuario.getEmail() %>" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label class="form-label"><i class="bi bi-telephone"></i> Teléfono</label>
                                    <input type="text" class="form-control" name="telefono"
                                           value="<%= usuario.getTelefono() %>">
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label"><i class="bi bi-star"></i> Especialidades</label>
                                <div>
                                    <%
                                        if (especialidades != null && !especialidades.isEmpty()) {
                                            for (Especialidad esp : especialidades) {
                                    %>
                                        <span class="badge bg-secondary me-2"><%= esp.getNombre() %></span>
                                    <%
                                            }
                                        } else {
                                    %>
                                        <span class="text-muted">No hay especialidades asignadas.</span>
                                    <%
                                        }
                                    %>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between">
                                <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle"></i> Guardar Cambios</button>
                                <a href="<%= request.getContextPath() %>/usuario?accion=inicio"
                                   class="btn btn-secondary"><i class="bi bi-arrow-left"></i> Volver</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
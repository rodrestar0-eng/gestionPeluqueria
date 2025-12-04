<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.BloqueoPeluquero" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Bloqueos</title>
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
        .table-hover tbody tr:hover {
            background-color: rgba(0, 123, 255, 0.1);
        }
    </style>
</head>
<body>
    <%
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null || usuario.getTipoUsuario() != 2) {
            response.sendRedirect("indexUsuario.jsp");
            return;
        }
        List<BloqueoPeluquero> bloqueos = (List<BloqueoPeluquero>) request.getAttribute("bloqueos");
    %>
    
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="bi bi-calendar-x"></i> Gestionar Bloqueos</a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <!-- Formulario para agregar bloqueo -->
                <div class="card mb-4">
                    <div class="card-header bg-primary text-white">
                        <h2 class="mb-0"><i class="bi bi-plus-circle"></i> Agregar Nuevo Bloqueo (Vacaciones)</h2>
                    </div>
                    <div class="card-body">
                        <form method="post" action="<%= request.getContextPath() %>/bloqueos">
                            <input type="hidden" name="accion" value="crear">
                            <input type="hidden" name="idTrabajador" value="<%= usuario.getIdUsuario() %>">
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="fechaInicio" class="form-label"><i class="bi bi-calendar-date"></i> Fecha Inicio</label>
                                    <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="fechaFin" class="form-label"><i class="bi bi-calendar-date"></i> Fecha Fin</label>
                                    <input type="date" class="form-control" id="fechaFin" name="fechaFin" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="motivo" class="form-label"><i class="bi bi-chat-text"></i> Motivo</label>
                                <textarea class="form-control" id="motivo" name="motivo" rows="3"></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle"></i> Agregar Bloqueo</button>
                        </form>
                    </div>
                </div>
                
                <!-- Tabla de bloqueos existentes -->
                <div class="card">
                    <div class="card-header bg-secondary text-white d-flex justify-content-between align-items-center">
                        <h3 class="mb-0"><i class="bi bi-list-ul"></i> Bloqueos Existentes</h3>
                        <a href="<%= request.getContextPath() %>/usuario?accion=inicio" class="btn btn-light btn-sm">
                            <i class="bi bi-arrow-left-circle"></i> Volver
                        </a>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover text-center">
                                <thead class="table-dark">
                                    <tr>
                                        <th><i class="bi bi-hash"></i> ID</th>
                                        <th><i class="bi bi-calendar-date"></i> Fecha Inicio</th>
                                        <th><i class="bi bi-calendar-date"></i> Fecha Fin</th>
                                        <th><i class="bi bi-chat-text"></i> Motivo</th>
                                        <th><i class="bi bi-gear"></i> Acción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (bloqueos != null && !bloqueos.isEmpty()) {
                                            for (BloqueoPeluquero bloqueo : bloqueos) {
                                    %>
                                        <tr>
                                            <td><%= bloqueo.getIdBloqueo() %></td>
                                            <td><%= bloqueo.getFechaInicio() %></td>
                                            <td><%= bloqueo.getFechaFin() %></td>
                                            <td><%= bloqueo.getMotivo() != null ? bloqueo.getMotivo() : "Sin motivo" %></td>
                                            <td>
                                                <form method="post" action="<%= request.getContextPath() %>/bloqueos" class="d-inline">
                                                    <input type="hidden" name="accion" value="eliminar">
                                                    <input type="hidden" name="idBloqueo" value="<%= bloqueo.getIdBloqueo() %>">
                                                    <input type="hidden" name="idTrabajador" value="<%= usuario.getIdUsuario() %>">
                                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro de eliminar este bloqueo?');">
                                                        <i class="bi bi-trash"></i> Eliminar
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                        <tr><td colspan="5" class="text-muted">No hay bloqueos registrados</td></tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Cita" %>
<%@ page import="es.peluqueria.gestion.modelo.Cliente" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect(request.getContextPath() + "/cliente?accion=perfil");
        return;
    }

    List<Cita> citas = (List<Cita>) request.getAttribute("citas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Citas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
        .table th {
            background-color: #e9ecef;
            border-top: none;
            font-weight: 600;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f3f4;
        }
        .badge-pendiente {
            background-color: #ffc107;
        }
        .badge-completada {
            background-color: #28a745;
        }
        .badge-cancelada {
            background-color: #dc3545;
        }
    </style>
</head>

<body class="bg-light">
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold text-center">Mis Citas</h1>
            <p class="lead text-center">Gestiona tus citas pasadas, presentes y futuras.</p>
        </div>
        <%
    String error = (String) session.getAttribute("error");
    if (error != null) {
        session.removeAttribute("error");
%>
    <div class="alert alert-danger text-center">
        <%= error %>
    </div>
<% } %>
    </div>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a href="cliente?accion=panel" class="btn btn-secondary">
                <i class="bi bi-arrow-left me-2"></i>Volver al Panel
            </a>
            <a href="cita?accion=crearFormulario" class="btn btn-primary">
                <i class="bi bi-plus-circle me-2"></i>Nueva Cita
            </a>
        </div>

        <%
            if (citas == null || citas.isEmpty()) {
        %>
            <div class="alert alert-info text-center" role="alert">
                <i class="bi bi-info-circle-fill me-2"></i>No tienes citas registradas.
            </div>
        <%
            } else {
        %>
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover table-striped mb-0">
                            <thead>
                                <tr>
                                   
                                    <th>Fecha</th>
                                    <th>Hora Inicio</th>
                                    <th>Hora Fin</th>
                                    <th>Estado</th>
                                    <th class="text-end">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%
                                for (Cita c : citas) {
                                    String estadoTxt = "Desconocido";
                                    String badgeClass = "badge-secondary";

                                    if (c.getEstado() == 1) {
                                        estadoTxt = "Pendiente";
                                        badgeClass = "badge-pendiente";
                                    } else if (c.getEstado() == 2) {
                                        estadoTxt = "Completada";
                                        badgeClass = "badge-completada";
                                    } else if (c.getEstado() == 3) {
                                        estadoTxt = "Cancelada";
                                        badgeClass = "badge-cancelada";
                                    }
                            %>
                                <tr>
                                
                                    <td>
    						<%
    				    java.time.format.DateTimeFormatter formatter =
    				        java.time.format.DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new java.util.Locale("es", "ES"));
   									 %>
   					 <%= c.getFechaCita().format(formatter) %>
								</td>

                                    <td><%= c.getHoraInicio() %></td>
                                    <td><%= c.getHoraFin() %></td>
                                    <td><span class="badge <%= badgeClass %>"><%= estadoTxt %></span></td>
                                    <td class="text-end">
                                        <a href="<%= request.getContextPath() %>/cita?accion=ver&id=<%= c.getIdCita() %>" class="btn btn-sm btn-info me-1">
                                            <i class="bi bi-eye"></i> Ver
                                        </a>
                                        <% if (c.getEstado() == 1) { %>
                                            <a href="<%= request.getContextPath() %>/cita?accion=cancelar&id=<%= c.getIdCita() %>" class="btn btn-sm btn-danger" onclick="return confirm('¿Seguro que deseas cancelar esta cita?');">
                                                <i class="bi bi-x-circle"></i> Cancelar
                                            </a>
                                        <% } %>
                                    </td>
                                </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        <% } %>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
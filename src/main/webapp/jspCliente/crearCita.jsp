<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>
<%@ page import="es.peluqueria.gestion.modelo.Servicio" %>
<%@ page import="es.peluqueria.gestion.modelo.Cliente" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    List<Usuario> peluqueros = (List<Usuario>) request.getAttribute("peluqueros");
    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Cita</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

<div class="container mt-4">

    <h2 class="fw-bold mb-3">Crear Nueva Cita</h2>

<a href="cita?accion=misCitasCliente" class="btn btn-secondary mb-3">⬅ Volver</a>

    <%
        if (request.getAttribute("error") != null) {
    %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/cita" method="post" class="card p-4 shadow-sm">

        <input type="hidden" name="accion" value="crear">
        <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

        <!-- Seleccionar peluquero -->
        <div class="mb-3">
            <label class="form-label">Peluquero:</label>
            <select class="form-select" name="idPeluquero" required>
                <option value="">Seleccione...</option>

                <%
                    if (peluqueros != null)
                        for (Usuario p : peluqueros) {
                %>
                    <option value="<%= p.getIdUsuario() %>">
                        <%= p.getNombre() %> <%=p.getApellido() %>
                    </option>
                <%
                        }
                %>
            </select>
        </div>

        <!-- Seleccionar servicio -->
        <div class="mb-3">
            <label class="form-label">Servicio:</label>
            <select class="form-select" name="idServicio" required>
                <option value="">Seleccione...</option>

                <%
                    if (servicios != null) {
                        for (Servicio s : servicios) {
                %>
                    <option value="<%= s.getIdServicio() %>">
                        <%= s.getNombre() %> - <%= s.getDuracionMinutos() %> min
                    </option>
                    <% }
                        } 
            
                %>
            </select>
        </div>

        <!-- Fecha -->
        <div class="mb-3">
            <label class="form-label">Fecha:</label>
            <input type="date" name="fecha" class="form-control" required>
        </div>

        <!-- Hora -->
        <div class="mb-3">
            <label class="form-label">Hora Inicio:</label>
            <input type="time" name="horaInicio" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary w-100 mt-3">
            Guardar Cita
        </button>

    </form>

</div>

</body>
</html>

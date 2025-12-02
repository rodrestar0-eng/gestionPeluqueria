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

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

<div class="container mt-4">

    <h2 class="fw-bold mb-4">Mis Citas</h2>

    <div class="mb-3">
	<a href="cliente?accion=panel" class="btn btn-secondary">⬅ Volver al Panel</a>
	<a href="cita?accion=crearFormulario" class="btn btn-primary">+ Nueva Cita</a>
    </div>

    <%
        if (citas == null || citas.isEmpty()) {
    %>

    <div class="alert alert-info text-center">
        No tienes citas registradas.
    </div>

    <%
        } else {
    %>

    <table class="table table-hover table-striped mt-3">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>Hora Inicio</th>
                <th>Hora Fin</th>
                <th>Estado</th>
                <th></th>
            </tr>
        </thead>

        <tbody>
        <%
            for (Cita c : citas) {

                String estadoTxt = "Desconocido";

                if (c.getEstado() == 1) {
                    estadoTxt = "Pendiente";
                } else if (c.getEstado() == 2) {
                    estadoTxt = "Completada";
                } else if (c.getEstado() == 3) {
                    estadoTxt = "Cancelada";
                }
        %>

            <tr>
                <td><%= c.getIdCita() %></td>
                <td><%= c.getFechaCita() %></td>
                <td><%= c.getHoraInicio() %></td>
                <td><%= c.getHoraFin() %></td>
                <td><%= estadoTxt %></td>

                <td class="text-end">
                    <a href="<%= request.getContextPath() %>/cita?accion=ver&id=<%= c.getIdCita() %>"
                       class="btn btn-sm btn-info">
                        Ver
                    </a>

                    <% if (c.getEstado() == 1) { %>
                        <a href="<%= request.getContextPath() %>/cita?accion=cancelar&id=<%= c.getIdCita() %>"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('¿Seguro que deseas cancelar esta cita?');">
                           Cancelar
                        </a>
                    <% } %>
                </td>
            </tr>

        <% } %>
        </tbody>
    </table>

    <% } %>

</div>

</body>
</html>

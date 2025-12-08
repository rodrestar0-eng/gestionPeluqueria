<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.peluqueria.gestion.modelo.Cita" %>
<%@ page import="es.peluqueria.gestion.modelo.Servicio" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>

<%
    Cita cita = (Cita) request.getAttribute("cita");
    Usuario peluquero = (Usuario) request.getAttribute("peluquero");
    Servicio servicio = (Servicio) request.getAttribute("servicio");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Cita</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container py-4">

    <h3 class="mb-4">📄 Detalle de la Cita</h3>

    <table class="table table-bordered">
        <tr>
            <th>Fecha</th>
            <td><%= cita.getFechaCita() %></td>
        </tr>
        <tr>
            <th>Hora Inicio</th>
            <td><%= cita.getHoraInicio() %></td>
        </tr>
        <tr>
            <th>Hora Fin</th>
            <td><%= cita.getHoraFin() %></td>
        </tr>
        <tr>
            <th>Servicio</th>
            <td><%= servicio != null ? servicio.getNombre() : "N/D" %></td>
        </tr>
        <tr>
            <th>Peluquero</th>
            <td><%= peluquero != null ? peluquero.getNombre() : "N/D" %></td>
        </tr>
        <tr>
            <th>Estado</th>
            <td>
                <%
                    switch (cita.getEstado()) {
                        case 1: out.print("<span class='badge bg-warning text-dark'>Pendiente</span>"); break;
                        case 2: out.print("<span class='badge bg-success'>Realizada</span>"); break;
                        default: out.print("<span class='badge bg-danger'>Cancelada</span>");
                    }
                %>
            </td>
        </tr>
    </table>

    <div class="mt-3">
        <a href="<%= request.getContextPath() %>/cita?accion=misCitasCliente" class="btn btn-secondary">⬅ Volver</a>

        <% if (cita.getEstado() == 1) { %>
<a href="<%= request.getContextPath() %>/cita?accion=cancelar&id=<%= cita.getIdCita() %>"
               onclick="return confirm('¿Seguro que deseas cancelar esta cita?');"
               class="btn btn-danger">
                Cancelar Cita
            </a>
        <% } %>
    </div>

</div>

</body>
</html>

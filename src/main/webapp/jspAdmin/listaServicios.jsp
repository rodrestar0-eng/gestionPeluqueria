<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="es.peluqueria.gestion.modelo.Servicio" %>

<%
    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Servicios</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="p-3">

<h3 class="mb-3">Servicios Disponibles</h3>

<table class="table table-bordered table-striped">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio (€)</th>
            <th>Duración</th>
            <th>Descripción</th>
            <th>Acciones</th>
        </tr>
    </thead>

    <tbody>
    <% if (servicios != null) {
        for (Servicio s : servicios) {

            int dur = s.getDuracionMinutos();
            int h = dur / 60;
            int m = dur % 60;

            String duracionStr = (h > 0)
                    ? (h + " h" + (m > 0 ? " " + m + " min" : ""))
                    : (m + " min");
    %>

        <tr>
            <td><%= s.getIdServicio() %></td>
            <td><%= s.getNombre() %></td>
            <td><%= s.getPrecio() %></td>
            <td><%= duracionStr %></td>
            <td><%= s.getDescripcion() %></td>

            <td>
                <a href="admin?accion=editarServicio&id=<%= s.getIdServicio() %>"
                   class="btn btn-primary btn-sm">Editar</a>

                <a href="admin?accion=eliminarServicio&id=<%= s.getIdServicio() %>"
                   onclick="return confirm('¿Seguro que deseas eliminar este servicio?');"
                   class="btn btn-danger btn-sm">Eliminar</a>
            </td>
        </tr>

    <% } } %>

    </tbody>
</table>

</body>
</html>

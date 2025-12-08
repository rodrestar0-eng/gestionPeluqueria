<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>

<%
    List<Especialidad> lista = (List<Especialidad>) request.getAttribute("especialidades");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Especialidades</title>

<style>
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
        font-family: Arial;
    }
    th, td {
        padding: 8px;
        border: 1px solid #ccc;
        text-align: center;
    }
    th {
        background: #eee;
        font-weight: bold;
    }
    a.btn {
        padding: 6px 10px;
        text-decoration: none;
        border-radius: 4px;
        color: white;
    }
    .edit { background: #5cb85c; }
    .delete { background: #d9534f; }
    .delete:hover { background: #b52b27; }
</style>

</head>
<body>

<h2>Listado de Especialidades</h2>

<table>
    <tr>
        <th>Nombre</th>
        <th>Acciones</th>
    </tr>

    <% if (lista != null) {
        for (Especialidad e : lista) { %>
            <tr>
                <td><%= e.getNombre() %></td>
                <td>
                    <a class="btn edit" href="admin?accion=editarEspecialidad&id=<%= e.getIdEspecialidad() %>">Editar</a>
                    <a class="btn delete" 
                        onclick="return confirm('¿Seguro que deseas eliminar esta especialidad? Se eliminarán también sus asignaciones.');"
                        href="admin?accion=eliminarEspecialidad&id=<%= e.getIdEspecialidad() %>">Eliminar</a>
                </td>
            </tr>
    <%  }
    } %>

</table>

</body>
</html>

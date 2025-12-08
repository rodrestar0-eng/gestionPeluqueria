<%@ page contentType="text/html; charset=UTF-8" language="java" %> 
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>

<%
    Usuario admin = (Usuario) session.getAttribute("usuario");

    if (admin == null || admin.getTipoUsuario() != 1) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }

    List<Usuario> trabajadores = (List<Usuario>) request.getAttribute("todosUsu");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Trabajadores</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <style>
        body {
            background: white;
            margin: 0;
            padding: 10px;
        }

        .tabla-contenedor {
            max-height: 380px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 6px;
            background: #fafafa;
            padding: 10px;
        }

        /* FILAS MÁS PEQUEÑAS */
        table.table-sm td,
        table.table-sm th {
            padding-top: 4px !important;
            padding-bottom: 4px !important;
        }

        /* Botones compactos */
        .btn-sm {
            padding: 2px 6px !important;
            font-size: 0.75rem !important;
        }
    </style>
</head>

<body>

<h4 class="mb-2">👷 Lista de Usuarios</h4>

<div class="tabla-contenedor">

    <% if (trabajadores == null || trabajadores.isEmpty()) { %>

        <p class="text-muted">No hay usuarios registrados.</p>

    <% } else { %>

        <table class="table table-bordered table-hover table-sm mb-0">
            <thead class="table-dark">
            <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Email</th>
                <th style="width: 200px;">Acciones</th>
            </tr>
            </thead>

            <tbody>
            <% for (Usuario t : trabajadores) { %>
            <tr>
                <td><%= t.getNombre() %></td>
                <td><%= t.getApellido() %></td>
                <td><%= t.getEmail() %></td>

                <td>
                    <a href="admin?accion=editarUsuario&id=<%= t.getIdUsuario() %>"
                       class="btn btn-sm btn-warning">Editar</a>

                    <a href="usuario?accion=eliminar&id=<%= t.getIdUsuario() %>"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('¿Eliminar este trabajador?');">
                        Eliminar
                    </a>

                    <% if (t.getTipoUsuario() == 2) { %>
   				    <a href="trabajador-especialidades?idTrabajador=<%= t.getIdUsuario() %>"
   		    			class="btn btn-sm btn-info">
   		    			 Especialidades
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

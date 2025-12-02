<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>

<%
    Usuario admin = (Usuario) session.getAttribute("usuario");

    if (admin == null || admin.getTipoUsuario() != 1) {
        response.sendRedirect(request.getContextPath() + "/loginUsuario.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Administrador</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* Botón de logout arriba a la derecha, siempre fijo */
        .logout-btn {
            position: fixed;
            top: 15px;
            right: 20px;
            z-index: 1000;
        }

        iframe {
            width: 100%;
            height: 260px;
            border: 1px solid #ddd;
            border-radius: 6px;
        }
    </style>
</head>

<body class="bg-light">

<!-- Botón de logout fijo -->
<a href="<%= request.getContextPath() %>/app?accion=logout"
   class="btn btn-danger logout-btn">
    Cerrar sesión
</a>

<div class="container py-5">

    <h1 class="text-center mb-4">Panel de Administración</h1>

    <!-- USUARIOS -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Usuarios Registrados</h5>
        </div>
        <div class="card-body">
            <iframe src="<%= request.getContextPath() %>/admin?accion=listarUsuarios"></iframe>

            <a href="<%= request.getContextPath() %>/admin?accion=nuevoUsuario"
               class="btn btn-primary mt-3">
                + Crear Trabajador
            </a>
        </div>
    </div>

    <!-- ESPECIALIDADES -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-success text-white">
            <h5 class="mb-0">Especialidades</h5>
        </div>
        <div class="card-body">
            <iframe src="<%= request.getContextPath() %>/admin?accion=listarEspecialidades"></iframe>

            <a href="<%= request.getContextPath() %>/admin?accion=nuevaEspecialidad"
               class="btn btn-success mt-3">
                + Crear Especialidad
            </a>
        </div>
    </div>

    <!-- SERVICIOS -->
    <div class="card shadow-sm mb-5">
        <div class="card-header bg-warning">
            <h5 class="mb-0">Servicios</h5>
        </div>
        <div class="card-body">
            <iframe src="<%= request.getContextPath() %>/admin?accion=listarServicios"></iframe>

            <a href="<%= request.getContextPath() %>/admin?accion=nuevoServicio"
               class="btn btn-warning mt-3 text-white">
                + Crear Servicio
            </a>
        </div>
    </div>

</div>

</body>
</html>

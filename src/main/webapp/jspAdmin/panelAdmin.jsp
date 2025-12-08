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

    <!-- ICONS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            padding-top: 70px; /* Compensar la altura del navbar fijo */
        }
        .navbar {
            background: rgba(0, 0, 0, 0.8) !important;
            backdrop-filter: blur(10px);
        }
        .card {
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.3);
            border: none;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.4);
        }
        .card-header {
            border-radius: 20px 20px 0 0 !important;
            font-weight: bold;
        }
        .btn {
            border-radius: 10px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
        }
        iframe {
            width: 100%;
            height: 260px;
            border: none;
            border-radius: 10px;
        }
        .logout-btn {
            position: fixed;
            top: 15px;
            right: 20px;
            z-index: 1000;
            border-radius: 10px;
        }
    </style>
</head>

<body class="bg-light">

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow fixed-top">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3" href="<%= request.getContextPath() %>/jsp/index.jsp"><i class="bi bi-scissors me-2"></i>Peluquería</a>
        <a href="<%= request.getContextPath() %>/app?accion=logout" class="btn btn-danger logout-btn"><i class="bi bi-box-arrow-right me-2"></i>Cerrar sesión</a>
    </div>
</nav>

<div class="container py-5">

    <h1 class="text-center mb-5 fw-bold display-4 text-white">Panel de Administración</h1>

    <!-- USUARIOS -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white">
            <i class="bi bi-people-fill me-2"></i>
            <h5 class="mb-0">Usuarios Registrados</h5>
        </div>
        <div class="card-body">
            <iframe src="<%= request.getContextPath() %>/admin?accion=listarUsuarios"></iframe>

            <a href="<%= request.getContextPath() %>/admin?accion=nuevoUsuario"
               class="btn btn-primary mt-3">
                <i class="bi bi-person-plus me-2"></i>+ Crear Usuario
            </a>
        </div>
    </div>

    <!-- ESPECIALIDADES -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-success text-white">
            <i class="bi bi-star-fill me-2"></i>
            <h5 class="mb-0">Especialidades</h5>
        </div>
        <div class="card-body">
            <iframe src="<%= request.getContextPath() %>/admin?accion=listarEspecialidades"></iframe>

            <a href="<%= request.getContextPath() %>/admin?accion=nuevaEspecialidad"
               class="btn btn-success mt-3">
                <i class="bi bi-plus-circle me-2"></i>+ Crear Especialidad
            </a>
        </div>
    </div>

    <!-- SERVICIOS -->
    <div class="card shadow-sm mb-5">
        <div class="card-header bg-warning text-dark">
            <i class="bi bi-tools me-2"></i>
            <h5 class="mb-0">Servicios</h5>
        </div>
        <div class="card-body">
            <iframe src="<%= request.getContextPath() %>/admin?accion=listarServicios"></iframe>

            <a href="<%= request.getContextPath() %>/admin?accion=nuevoServicio"
               class="btn btn-warning mt-3 text-dark">
                <i class="bi bi-plus-circle me-2"></i>+ Crear Servicio
            </a>
        </div>
    </div>

</div>

<!-- BOOTSTRAP JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 3rem 0;
            margin-bottom: 2rem;
        }
        .navbar-brand {
            font-weight: bold;
        }
        .card {
            border: none;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card:hover {
            box-shadow: 0 8px 16px rgba(0,0,0,0.2);
            transition: box-shadow 0.3s ease;
        }
    </style>
</head>

<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/usuario">
                <i class="bi bi-scissors me-2"></i>Peluquería Gestión
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <!-- PERFIL -->
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/usuario?accion=perfil">
                            <i class="bi bi-person-circle me-1"></i>Mi Perfil
                        </a>
                    </li>

                    <% if (usuario.getTipoUsuario() == 2) { %>
                        <!-- HORARIOS -->
                        <li class="nav-item">
                            <a class="nav-link" href="<%= request.getContextPath() %>/horarios?idTrabajador=<%=usuario.getIdUsuario()%>">
                                <i class="bi bi-clock me-1"></i>Gestionar Horarios
                            </a>
                        </li>

                        <!-- BLOQUEOS -->
                        <li class="nav-item">
                            <a class="nav-link" href="<%= request.getContextPath() %>/bloqueos?idTrabajador=<%=usuario.getIdUsuario()%>">
                                <i class="bi bi-calendar-x me-1"></i>Gestionar Bloqueos
                            </a>
                        </li>

                        <!-- MIS CITAS -->
                        <li class="nav-item">
                            <a class="nav-link" href="<%= request.getContextPath() %>/cita?accion=misCitasPeluquero&idTrabajador=<%=usuario.getIdUsuario()%>">
                                <i class="bi bi-calendar-check me-1"></i>Mis Citas
                            </a>
                        </li>
                    <% } %>
                </ul>

                <!-- LOGOUT -->
                <form action="<%= request.getContextPath() %>/usuario" method="get" class="d-flex">
                    <input type="hidden" name="accion" value="cerrarSesion">
                    <button type="submit" class="btn btn-outline-light">
                        <i class="bi bi-box-arrow-right me-1"></i>Logout
                    </button>
                </form>
            </div>
        </div>
    </nav>

    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold text-center">Bienvenido, <%= usuario.getNombre() %>!</h1>
            <p class="lead text-center">Selecciona una opción del menú superior para gestionar tu cuenta.</p>
        </div>
    </div>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card">
                    <div class="card-body text-center">
                        <i class="bi bi-house-door-fill text-primary mb-3" style="font-size: 3rem;"></i>
                        <h5 class="card-title">Panel de Usuario</h5>
                        <p class="card-text">Aquí puedes acceder a tus opciones personales y de gestión.</p>
                        <a href="<%= request.getContextPath() %>/usuario?accion=perfil" class="btn btn-primary">
                            <i class="bi bi-person-circle me-2"></i>Ir a Mi Perfil
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
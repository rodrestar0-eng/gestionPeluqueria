<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="es.peluqueria.gestion.modelo.Usuario" %>

<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || u.getTipoUsuario() != 2) { // 2 = peluquero
        response.sendRedirect("../jsp/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel del Peluquero</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background: #f8f9fa;
        }
        .menu-card {
            transition: transform .15s ease;
            cursor: pointer;
        }
        .menu-card:hover {
            transform: scale(1.03);
        }
        .icon {
            font-size: 2.6rem;
        }
    </style>
</head>

<body>

<div class="container py-4">

    <h2 class="mb-4">👋 Bienvenido, <%= u.getNombre() %></h2>

    <div class="row g-4">

        <!-- Mis Citas -->
        <div class="col-md-4">
            <a href="cita?accion=misCitasPeluquero" class="text-decoration-none text-dark">
                <div class="card menu-card shadow-sm p-3">
                    <div class="text-center">
                        <div class="icon">📅</div>
                        <h5 class="mt-2">Mis citas</h5>
                        <p class="small text-muted">Listado de tus citas, completadas o pendientes.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Gestión Horarios -->
        <div class="col-md-4">
            <a href="horarios" class="text-decoration-none text-dark">
                <div class="card menu-card shadow-sm p-3">
                    <div class="text-center">
                        <div class="icon">🕒</div>
                        <h5 class="mt-2">Gestión de horarios</h5>
                        <p class="small text-muted">Define tus franjas de trabajo cada semana.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Gestión Bloqueos -->
        <div class="col-md-4">
            <a href="gestionBloqueos.jsp" class="text-decoration-none text-dark">
                <div class="card menu-card shadow-sm p-3">
                    <div class="text-center">
                        <div class="icon">⛔</div>
                        <h5 class="mt-2">Bloqueos / Vacaciones</h5>
                        <p class="small text-muted">Crea días no disponibles.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Mi perfil -->
        <div class="col-md-4">
            <a href="miPerfil.jsp" class="text-decoration-none text-dark">
                <div class="card menu-card shadow-sm p-3">
                    <div class="text-center">
                        <div class="icon">👤</div>
                        <h5 class="mt-2">Mi perfil</h5>
                        <p class="small text-muted">Edita tu información personal.</p>
                    </div>
                </div>
            </a>
        </div>

        <!-- Cerrar sesión -->
        <div class="col-md-4">
            <a href="../logout" class="text-decoration-none text-dark">
                <div class="card menu-card shadow-sm p-3 bg-danger text-white">
                    <div class="text-center">
                        <div class="icon">🚪</div>
                        <h5 class="mt-2">Cerrar sesión</h5>
                    </div>
                </div>
            </a>
        </div>

    </div>
</div>

</body>
</html>

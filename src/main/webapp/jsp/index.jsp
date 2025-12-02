<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Peluquería - Inicio</title>

    <!-- BOOTSTRAP -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ICONS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background-color: #f8f9fa;
        }
        .hero {
            height: 75vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: url('img/peluqueria1.jpg') center/cover no-repeat;
        }
        .hero-overlay {
            background: rgba(0,0,0,0.6);
            padding: 50px;
            border-radius: 15px;
        }
        .hero h1, .hero p {
            color: white;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand fw-bold">Peluquería</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">

            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link active" href="index.jsp">Inicio</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Servicios</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Sobre nosotros</a></li>
            </ul>

            <div class="d-flex">
			<a href="<%= request.getContextPath() %>/jsp/login.jsp" class="btn btn-outline-light me-2">Iniciar Sesión</a>
			<a href="<%= request.getContextPath() %>/jsp/registroCliente.jsp" class="btn btn-success">Registrarse</a>
            </div>
        </div>
    </div>
</nav>

<!-- HERO SECTION -->
<section class="hero">
    <div class="hero-overlay text-center">
        <h1 class="fw-bold">Tu peluquería de confianza</h1>
        <p class="lead">Cortes, color, peinados y tratamientos profesionales.</p>
        <a href="<%= request.getContextPath() %>/jsp/login.jsp" class="btn btn-primary btn-lg mt-3">Reservar Cita</a>
    </div>
</section>

<!-- SERVICIOS -->
<div class="container my-5">
    <h2 class="text-center fw-bold mb-4">Nuestros Servicios</h2>

    <div class="row g-4">

        <!-- Tarjeta ejemplo 1 -->
        <div class="col-md-4">
            <div class="card shadow-sm">
                <img src="img/corte.jpg" class="card-img-top" alt="Corte">
                <div class="card-body text-center">
                    <h5 class="card-title fw-bold">Corte de pelo</h5>
                    <p class="card-text">Profesional y adaptado a tu estilo.</p>
                </div>
            </div>
        </div>

        <!-- Tarjeta ejemplo 2 -->
        <div class="col-md-4">
            <div class="card shadow-sm">
                <img src="img/color.jpg" class="card-img-top" alt="Color">
                <div class="card-body text-center">
                    <h5 class="card-title fw-bold">Color y tintes</h5>
                    <p class="card-text">Los mejores tratamientos de coloración.</p>
                </div>
            </div>
        </div>

        <!-- Tarjeta ejemplo 3 -->
        <div class="col-md-4">
            <div class="card shadow-sm">
                <img src="img/peinado.jpg" class="card-img-top" alt="Peinados">
                <div class="card-body text-center">
                    <h5 class="card-title fw-bold">Peinados</h5>
                    <p class="card-text">Para ocasiones especiales o tu día a día.</p>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- SOBRE NOSOTROS -->
<div class="bg-light py-5">
    <div class="container">

        <h2 class="text-center fw-bold mb-4">Sobre Nosotros</h2>

        <div class="row">
            <div class="col-md-6 d-flex justify-content-center">
                <img src="img/equipo.jpg" class="img-fluid rounded shadow" style="max-height: 300px;">
            </div>

            <div class="col-md-6">
                <p class="lead">
                    Somos un equipo de estilistas profesionales con más de 10 años de experiencia.
                    Te ofrecemos un servicio personalizado, moderno y de calidad.
                </p>
                <p>
                    Nuestro objetivo es que salgas con la mejor versión de ti mismo,
                    disfrutando de un ambiente agradable y un trato excepcional.
                </p>
            </div>
        </div>

    </div>
</div>

<!-- FOOTER -->
<footer class="bg-dark text-white py-4 mt-5">
    <div class="container text-center">
        <p class="mb-1">© 2025 Peluquería - Todos los derechos reservados</p>
        <p class="small text-muted">Diseñado para tu proyecto Java Web</p>
    </div>
</footer>

<!-- BOOTSTRAP JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

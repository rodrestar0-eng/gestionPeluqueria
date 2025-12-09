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
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding-top: 70px; /* Compensar la altura del navbar fijo */
        }
        .hero {
            height: 75vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('img/peluqueria1.jpg') center/cover no-repeat;
            position: relative;
        }
        .hero-overlay {
            background: rgba(0,0,0,0.7);
            padding: 60px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }
        .hero h1 {
            color: white;
            font-size: 3rem;
            margin-bottom: 20px;
        }
        .hero p {
            color: #e9ecef;
            font-size: 1.25rem;
        }
        .card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: none;
            border-radius: 15px;
        }
        .card:hover {
            transform: translateY(-10px);
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
        }
        .card-img-top {
            border-radius: 15px 15px 0 0;
            height: 200px;
            object-fit: cover;
        }
        .bg-light {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        }
        .footer-icon {
            font-size: 1.5rem;
            margin: 0 10px;
            color: #adb5bd;
            transition: color 0.3s ease;
        }
        .footer-icon:hover {
            color: #ffffff;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow fixed-top">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3" href="#"><i class="bi bi-scissors me-2"></i>Peluquería</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">

            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link active" href="#inicio"><i class="bi bi-house-door me-1"></i>Inicio</a></li>
                <li class="nav-item"><a class="nav-link" href="#servicios"><i class="bi bi-tools me-1"></i>Servicios</a></li>
                <li class="nav-item"><a class="nav-link" href="#sobre-nosotros"><i class="bi bi-info-circle me-1"></i>Sobre nosotros</a></li>
            </ul>

            <div class="d-flex">
                <a href="<%= request.getContextPath() %>/jsp/login.jsp" class="btn btn-outline-light me-2"><i class="bi bi-box-arrow-in-right me-1"></i>Iniciar Sesión</a>
                <a href="<%= request.getContextPath() %>/jsp/registroCliente.jsp" class="btn btn-success"><i class="bi bi-person-plus me-1"></i>Registrarse</a>
            </div>
        </div>
    </div>
</nav>

<!-- HERO SECTION -->
<section class="hero" id="inicio">
    <div class="hero-overlay text-center">
        <h1 class="fw-bold display-4">Tu peluquería de confianza</h1>
        <p class="lead">Cortes, color, peinados y tratamientos profesionales para realzar tu estilo.</p>
        <a href="<%= request.getContextPath() %>/jsp/login.jsp" class="btn btn-primary btn-lg mt-3 px-4 py-2"><i class="bi bi-calendar-check me-2"></i>Reservar Cita</a>
    </div>
</section>

<!-- SERVICIOS -->
<div class="container my-5" id="servicios">
    <h2 class="text-center fw-bold mb-5 display-5">Nuestros Servicios</h2>

    <div class="row g-4">

        <!-- Tarjeta ejemplo 1 -->
        <div class="col-md-4">
            <div class="card shadow-sm h-100">
                <img src="<%= request.getContextPath() %>/images/pelu2.png" class="card-img-top" alt="Corte">
                <div class="card-body text-center">
                    <i class="bi bi-scissors text-primary fs-1 mb-3"></i>
                    <h5 class="card-title fw-bold">Corte de pelo</h5>
                    <p class="card-text">Profesional y adaptado a tu estilo personal.</p>
                </div>
            </div>
        </div>

        <!-- Tarjeta ejemplo 2 -->
        <div class="col-md-4">
            <div class="card shadow-sm h-100">
                <img src="<%= request.getContextPath() %>/images/pelu.png" class="card-img-top" alt="Color">
                <div class="card-body text-center">
                    <i class="bi bi-palette text-primary fs-1 mb-3"></i>
                    <h5 class="card-title fw-bold">Color y tintes</h5>
                    <p class="card-text">Los mejores tratamientos de coloración para un look vibrante.</p>
                </div>
            </div>
        </div>

        <!-- Tarjeta ejemplo 3 -->
        <div class="col-md-4">
            <div class="card shadow-sm h-100">
                <img src="<%= request.getContextPath() %>/images/pelu3.png" class="card-img-top" alt="Peinados">
                <div class="card-body text-center">
                    <i class="bi bi-brush text-primary fs-1 mb-3"></i>
                    <h5 class="card-title fw-bold">Peinados</h5>
                    <p class="card-text">Para ocasiones especiales o tu día a día con elegancia.</p>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- SOBRE NOSOTROS -->
<div class="bg-light py-5" id="sobre-nosotros">
    <div class="container">

        <h2 class="text-center fw-bold mb-5 display-5">Sobre Nosotros</h2>

        <div class="row align-items-center">
            <div class="col-md-6 d-flex justify-content-center mb-4 mb-md-0">
                <img src="<%= request.getContextPath() %>/images/equipo.png" class="img-fluid rounded shadow" style="max-height: 350px; border-radius: 20px;">
            </div>

            <div class="col-md-6">
                <p class="lead fs-5">
                    Somos un equipo de estilistas profesionales con más de 10 años de experiencia en el arte de la belleza capilar.
                </p>
                <p class="fs-6">
                    Te ofrecemos un servicio personalizado, moderno y de calidad. Nuestro objetivo es que salgas con la mejor versión de ti mismo, disfrutando de un ambiente agradable y un trato excepcional.
                </p>
                <div class="mt-4">
                    <i class="bi bi-check-circle-fill text-success me-2"></i>Atención personalizada<br>
                    <i class="bi bi-check-circle-fill text-success me-2"></i>Productos de alta calidad<br>
                    <i class="bi bi-check-circle-fill text-success me-2"></i>Ambiente relajante
                </div>
            </div>
        </div>

    </div>
</div>

<!-- FOOTER -->
<footer class="bg-dark text-white py-5 mt-5">
    <div class="container text-center">
        <div class="mb-3">
            <a href="#" class="footer-icon"><i class="bi bi-facebook"></i></a>
            <a href="#" class="footer-icon"><i class="bi bi-instagram"></i></a>
            <a href="#" class="footer-icon"><i class="bi bi-twitter"></i></a>
            <a href="#" class="footer-icon"><i class="bi bi-youtube"></i></a>
        </div>
        <p class="mb-2 fs-5">© 2025 Peluquería - Todos los derechos reservados</p>
        <p class="small text-muted"></p>
    </div>
</footer>

<!-- BOOTSTRAP JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
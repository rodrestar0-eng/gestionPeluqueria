<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Especialidad</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ICONS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
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
        }
        .form-control {
            border-radius: 10px;
            border: 1px solid #ddd;
            padding-left: 40px;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-control:focus {
            border-color: #0984e3;
            box-shadow: 0 0 0 0.2rem rgba(9, 132, 227, 0.25);
        }
        .input-group-text {
            background: transparent;
            border: none;
            color: #0984e3;
        }
        .btn {
            border-radius: 10px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
        }
        .btn-primary {
            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
            border: none;
        }
        .btn-secondary {
            background: #6c757d;
            border: none;
        }
    </style>

    <script>
        function validar() {
            const nombre = document.getElementById("nombre").value.trim();
            if (nombre === "") {
                alert("El nombre no puede estar vacío");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow fixed-top">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3" href="<%= request.getContextPath() %>/jsp/index.jsp"><i class="bi bi-scissors me-2"></i>Peluquería</a>
        <a href="<%= request.getContextPath() %>/app?accion=logout" class="btn btn-danger"><i class="bi bi-box-arrow-right me-2"></i>Cerrar sesión</a>
    </div>
</nav>

<div class="container d-flex justify-content-center align-items-center flex-grow-1">
    <div class="col-md-6">
        <div class="card shadow p-5">

            <div class="text-center mb-4">
                <i class="bi bi-star text-primary fs-1 mb-3"></i>
                <h2 class="fw-bold">Crear Especialidad</h2>
                <p class="text-muted">Añade una nueva especialidad al sistema</p>
            </div>

            <form action="admin" method="POST" onsubmit="return validar();">
                <input type="hidden" name="accion" value="crearEspecialidad">

                <div class="mb-4">
                    <label class="form-label fw-bold">Nombre</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-tag"></i></span>
                        <input type="text" id="nombre" name="nombre" class="form-control" placeholder="Nombre de la especialidad" required>
                    </div>
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                    <button type="submit" class="btn btn-primary btn-lg"><i class="bi bi-check-circle me-2"></i>Crear</button>
                    <a class="btn btn-secondary btn-lg" href="admin?accion=panel"><i class="bi bi-arrow-left me-2"></i>Volver</a>
                </div>
            </form>

        </div>
    </div>
</div>

<!-- BOOTSTRAP JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
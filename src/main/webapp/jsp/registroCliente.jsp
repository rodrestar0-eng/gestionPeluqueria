<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro Cliente - Peluquería</title>
    
 <script>
document.addEventListener("DOMContentLoaded", () => {
    const pass1 = document.getElementById("pass1");
    const pass2 = document.getElementById("pass2");
    const mensajeError = document.getElementById("mensajeErrorPass");
    const boton = document.getElementById("btnCrear");

    function validar() {
        if (pass1.value !== pass2.value) {
            mensajeError.style.display = "block";
            boton.disabled = true;
        } else {
            mensajeError.style.display = "none";
            boton.disabled = false;
        }
    }

    pass1.addEventListener("input", validar);
    pass2.addEventListener("input", validar);
});
</script>

    

    <!-- BOOTSTRAP 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- ICONS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body {
            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            padding-top: 70px; 
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
        .btn-success {
            background: linear-gradient(135deg, #74b9ff 0%, #0984e3 100%);
            border: none;
            border-radius: 10px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .btn-success:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(9, 132, 227, 0.4);
        }
        .alert {
            border-radius: 10px;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow fixed-top">
    <div class="container">
        <a class="navbar-brand fw-bold fs-3" href="<%= request.getContextPath() %>/jsp/index.jsp"><i class="bi bi-scissors me-2"></i>Peluquería</a>
        <a class="navbar-brand fw-bold" href="<%= request.getContextPath() %>/jsp/index.jsp"><i class="bi bi-arrow-left me-2"></i>Volver</a>
    </div>
</nav>

<!-- REGISTER FORM -->
<div class="container d-flex justify-content-center align-items-center flex-grow-1">
    <div class="col-md-6">
        <div class="card shadow p-5">

            <div class="text-center mb-4">
                <i class="bi bi-person-plus-circle text-primary fs-1 mb-3"></i>
                <h3 class="fw-bold">Registro de Cliente</h3>
                <p class="text-muted">Crea tu cuenta para reservar citas</p>
            </div>

            <!-- Mensaje de error -->
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-danger text-center"><i class="bi bi-exclamation-triangle me-2"></i><%= error %></div>
            <% } %>

            <form action="<%= request.getContextPath() %>/cliente" method="post">
                <input type="hidden" name="accion" value="generarCodigoRegistro">

                <div class="row">
                    <div class="col-md-6 mb-3">
    <label class="form-label fw-bold">Nombre</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-person"></i></span>
        <input type="text" name="nombre" class="form-control" placeholder="Tu nombre" required
               pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras">
    </div>
</div>

<div class="col-md-6 mb-3">
    <label class="form-label fw-bold">Apellidos</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-person"></i></span>
        <input type="text" name="apellido" class="form-control" placeholder="Tus apellidos" required
               pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+" title="Solo se permiten letras">
    </div>
</div>

<div class="mb-3">
    <label class="form-label fw-bold">Teléfono</label>
    <div class="input-group">
        <span class="input-group-text"><i class="bi bi-telephone"></i></span>
        <input type="text" name="telefono" class="form-control" placeholder="Tu teléfono" required
               pattern="\d+" title="Solo se permiten números">
    </div>
</div>


                <div class="mb-3">
                    <label class="form-label fw-bold">Email</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                        <input type="email" name="email" class="form-control" placeholder="tuemail@ejemplo.com" required>
                    </div>
                </div>
</div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Contraseña</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-lock"></i></span>
                            <input type="password" id="pass1" name="contrasena" class="form-control" placeholder="Tu contraseña" required>
                        </div>
                    </div>

                    <div class="col-md-6 mb-4">
                        <label class="form-label fw-bold">Repite la Contraseña</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-lock"></i></span>
                            <input type="password" id="pass2" name="repetirContrasena" class="form-control" placeholder="Repite tu contraseña" required>
                        </div>	
                    </div>
                    <div id="mensajeErrorPass" class="alert alert-danger mt-2" style="display:none;">
  						  <i class="bi bi-exclamation-triangle me-2"></i>Las contraseñas no coinciden.
							</div>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn btn-success btn-lg" id="btnCrear"><i class="bi bi-check-circle me-2"></i>Crear Cuenta</button>
                </div>
            </form>

            <div class="text-center mt-4">
                ¿Ya tienes cuenta? <a href="login.jsp" class="fw-bold text-primary"><i class="bi bi-box-arrow-in-right me-1"></i>Inicia sesión</a>
            </div>

        </div>
    </div>
</div>

<!-- BOOTSTRAP JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
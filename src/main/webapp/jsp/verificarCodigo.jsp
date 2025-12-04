<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Verificar Código - Peluquería</title>

    <!-- BOOTSTRAP -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #00b894 0%, #0984e3 100%);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
        }
        .card {
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
        }
        .btn-primary {
            border-radius: 10px;
            background: linear-gradient(135deg, #0984e3 0%, #6c5ce7 100%);
            border: none;
        }
        .btn-secondary {
            border-radius: 10px;
        }
    </style>
</head>

<body>

<div class="container d-flex justify-content-center">
    <div class="col-md-5">
        <div class="card p-4">

            <div class="text-center mb-4">
                <i class="bi bi-shield-lock fs-1 text-primary mb-2"></i>
                <h3 class="fw-bold">Verificación de Código</h3>
                <p class="text-muted">Hemos enviado un código de 6 dígitos a tu correo.</p>
            </div>

            <% String error = (String) request.getAttribute("error"); 
               if (error != null) { %>
                <div class="alert alert-danger text-center">
                    <i class="bi bi-x-circle me-2"></i><%= error %>
                </div>
            <% } %>

            <!-- FORM VALIDAR CÓDIGO -->
            <form action="<%= request.getContextPath() %>/cliente" method="post">
                <input type="hidden" name="accion" value="verificarCodigo">

                <div class="mb-3">
                    <label class="form-label fw-bold">Introduce el código recibido</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-key"></i></span>
                        <input type="number" name="codigoIngresado" class="form-control"
                               placeholder="Ej: 123456" required min="100000" max="999999">
                    </div>
                </div>

                <div class="d-grid mt-4">
                    <button class="btn btn-primary btn-lg">
                        <i class="bi bi-check-circle me-2"></i>Verificar
                    </button>
                </div>
            </form>

            <!-- BOTONES EXTRAS -->
            <div class="text-center mt-4">

                <!-- BOTÓN REENVIAR CÓDIGO -->
                <form id="reenviarForm" action="<%= request.getContextPath() %>/cliente" method="post" class="d-inline">
                    <input type="hidden" name="accion" value="reenviarCodigo">
                    <button id="btnReenviar" class="btn btn-warning" disabled>
                        Reenviar código (<span id="contador">30</span>s)
                    </button>
                </form>

                <!-- BOTÓN VOLVER -->
                <a href="<%= request.getContextPath() %>/jsp/registroCliente.jsp"
                   class="btn btn-secondary ms-2">
                    Volver al registro
                </a>
            </div>

            <div class="text-center mt-3">
                <small class="text-muted">Si no te llegó el correo, revisa tu carpeta de SPAM.</small>
            </div>

        </div>
    </div>
</div>


<!-- SCRIPT CONTADOR -->
<script>
    let tiempo = 30;
    const btn = document.getElementById("btnReenviar");
    const contador = document.getElementById("contador");

    const intervalo = setInterval(() => {
        tiempo--;
        contador.textContent = tiempo;

        if (tiempo <= 0) {
            clearInterval(intervalo);
            btn.disabled = false;
            btn.textContent = "Reenviar código";
        }
    }, 1000);
</script>

</body>
</html>

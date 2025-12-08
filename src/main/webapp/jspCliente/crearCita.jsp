<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="es.peluqueria.gestion.modelo.Servicio" %>
<%@ page import="es.peluqueria.gestion.modelo.Cliente" %>

<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect(request.getContextPath() +"/jsp/login.jsp");
        return;
    }

    List<Servicio> servicios = (List<Servicio>) request.getAttribute("servicios");
%>

<script>
document.addEventListener("DOMContentLoaded", function () {

    const servicioSelect = document.querySelector("select[name='idServicio']");
    const peluqueroSelect = document.querySelector("select[name='idPeluquero']");

    //  Bloqueamos peluquero hasta que el usuario elija un servicio
    peluqueroSelect.disabled = true;

    servicioSelect.addEventListener("change", function () {

        const idServicio = this.value;

        if (idServicio === "") {
            peluqueroSelect.disabled = true;
            peluqueroSelect.innerHTML = `<option value="">Seleccione un servicio primero</option>`;
            return;
        }

        // Activamos el select pero lo marcamos como "cargando"
        peluqueroSelect.disabled = true;
        peluqueroSelect.innerHTML = `<option>Cargando...</option>`;

        fetch("cita?accion=filtrarPeluqueros&idServicio=" + idServicio)
            .then(res => res.text())
            .then(html => {

                peluqueroSelect.innerHTML = html;
                peluqueroSelect.disabled = false;  //  Ya puede seleccionarse
            })
            .catch(() => {
                peluqueroSelect.innerHTML = `<option>Error al cargar</option>`;
            });
    });

});
</script>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Cita</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .form-card {
            border: none;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .form-card:hover {
            box-shadow: 0 8px 16px rgba(0,0,0,0.2);
            transition: box-shadow 0.3s ease;
        }
        .hero-section {
            background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
            color: white;
            padding: 2rem 0;
            margin-bottom: 2rem;
        }
    </style>
</head>

<body class="bg-light">

    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 fw-bold text-center">Crear Nueva Cita</h1>
            <p class="lead text-center">Reserva tu cita con facilidad. Selecciona tus preferencias y confirma.</p>
        </div>
    </div>

    <div class="container">
        <a href="cita?accion=misCitasCliente" class="btn btn-secondary mb-4">
            <i class="bi bi-arrow-left me-2"></i>Volver
        </a>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= request.getAttribute("error") %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>

        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card form-card p-4">
                    <div class="card-body">

                        <form action="<%= request.getContextPath() %>/cita" method="post">

                            <input type="hidden" name="accion" value="crear">
                            <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">

                            <!-- Seleccionar servicio -->
                            <div class="mb-3">
                                <label class="form-label">
                                    <i class="bi bi-scissors text-success me-2"></i>Servicio:
                                </label>
                                <select class="form-select" name="idServicio" required>
                                    <option value="">Seleccione...</option>
                                    <% if (servicios != null) {
                                           for (Servicio s : servicios) { %>
                                        <option value="<%= s.getIdServicio() %>">
                                            <%= s.getNombre() %> - <%= s.getDuracionMinutos() %> min
                                        </option>
                                    <% } } %>
                                </select>
                            </div>

                            <!-- Seleccionar peluquero (vacío hasta seleccionar servicio) -->
                            <div class="mb-3">
                                <label class="form-label">
                                    <i class="bi bi-person-fill text-primary me-2"></i>Peluquero:
                                </label>
                                <select class="form-select" name="idPeluquero" required disabled>
                                    <option value="">Seleccione un servicio primero</option>
                                </select>
                            </div>

                            <!-- Fecha -->
                            <div class="mb-3">
                                <label class="form-label"><i class="bi bi-calendar-date text-warning me-2"></i>Fecha:</label>
                                <input type="date" name="fecha" class="form-control" required>
                            </div>

                            <!-- Hora -->
                            <div class="mb-3">
                                <label class="form-label"><i class="bi bi-clock text-info me-2"></i>Hora Inicio:</label>
                                <input type="time" name="horaInicio" class="form-control" required>
                            </div>

                            <div class="d-grid mt-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-check-circle-fill me-2"></i>Guardar Cita
                                </button>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

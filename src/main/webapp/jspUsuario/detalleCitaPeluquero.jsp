<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="es.peluqueria.gestion.modelo.Cita" %>

<%
    Cita cita = (Cita) request.getAttribute("cita");
    if (cita == null) {
%>
        <h2>No se encontró la cita.</h2>
<%
        return;
    }

    String nombreCliente = (String) request.getAttribute("nombreCliente");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Detalle Cita Peluquero</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

</head>
<body class="container mt-4">

    <h2><i class="bi bi-calendar-check"></i> Detalle de la Cita</h2>

    <a href="cita?accion=misCitasPeluquero" class="btn btn-secondary mb-3">
        <i class="bi bi-arrow-left-circle"></i> Volver
    </a>

    <div class="card p-3">

        <p><strong>Cliente:</strong> <%= nombreCliente %></p>
        <p><strong>Fecha:</strong> <%= cita.getFechaCita() %></p>
        <p><strong>Hora:</strong> <%= cita.getHoraInicio() %></p>
        <p><strong>Servicio:</strong> <%= cita.getNombreServicio() %></p>
        <p><strong>Estado actual:</strong>
            <%
switch (cita.getEstado()) {
    case 1: out.print("Pendiente"); break;
    case 2: out.print("Completada"); break;
    case 3: out.print("Cancelada"); break;
    default: out.print("Desconocido");
}
%>

        </p>

        <hr>

        <h4>Cambiar Estado</h4>

        <form method="get" action="cita">
            <input type="hidden" name="accion" value="cambiarEstado">
            <input type="hidden" name="idCita" value="<%= cita.getIdCita() %>">

            <select name="estado" class="form-select mb-3">
    <option value="1" <%= cita.getEstado()==1?"selected":"" %>>Pendiente</option>
    <option value="2" <%= cita.getEstado()==2?"selected":"" %>>Completada</option>
    <option value="3" <%= cita.getEstado()==3?"selected":"" %>>Cancelada</option>
</select>


            <button class="btn btn-primary w-100">
                <i class="bi bi-check-circle"></i> Guardar Cambios
            </button>
        </form>

    </div>

</body>
</html>

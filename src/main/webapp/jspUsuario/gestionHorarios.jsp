<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.*" %>
<%@ page import="es.peluqueria.gestion.modelo.HorarioPeluquero" %>
<%@ page import="es.peluqueria.gestion.modelo.Cita" %>

<%
    // atributos puestos por el controller
    Integer idTrabajador = (Integer) request.getAttribute("idTrabajador");
    List<LocalDate> fechasSemana = (List<LocalDate>) request.getAttribute("fechasSemana");
    Map<java.time.LocalDate, List<Cita>> citasPorDia = (Map<java.time.LocalDate, List<Cita>>) request.getAttribute("citasPorDia");
    List<HorarioPeluquero> horarios = (List<HorarioPeluquero>) request.getAttribute("horarios");
    String weekStart = (String) request.getAttribute("weekStart");

    DateTimeFormatter df = DateTimeFormatter.ofPattern("EEE dd/MM");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <title>Gestión de Horarios - Semana</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .tabla-semana th, .tabla-semana td { vertical-align: top; }
        .horario-block { font-size: .9rem; padding: .25rem .5rem; border-radius: .25rem; background:#f5f5f5; margin-bottom:.35rem; }
        .cita-badge { display:block; margin-bottom:.35rem; padding:.45rem .6rem; border-radius:.35rem; }
        .cita-pendiente { background:#fff3cd; border:1px solid #ffeeba; }
        .cita-realizada { background:#d1e7dd; border:1px solid #badbcc; }
        .cita-cancelada { background:#f8d7da; border:1px solid #f5c2c7; }
        .card-header .small { font-size: .85rem; opacity:.9; }
    </style>
</head>
<body class="bg-light">

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">📅 Gestión de Horarios (Semana desde <strong><%= weekStart %></strong>)</h3>
        <div>
            <a href="javascript:history.back()" class="btn btn-outline-secondary me-2">Volver</a>
            <a href="horarios?idTrabajador=<%= idTrabajador %>&weekStart=<%= LocalDate.parse(weekStart).minusWeeks(1) %>" class="btn btn-sm btn-light">← Semana anterior</a>
            <a href="horarios?idTrabajador=<%= idTrabajador %>&weekStart=<%= LocalDate.parse(weekStart).plusWeeks(1) %>" class="btn btn-sm btn-light">Semana siguiente →</a>
        </div>
    </div>

    <div class="row g-3">
        <div class="col-lg-8">
            <div class="card shadow-sm mb-3">
                <div class="card-header">
                    Horario semanal
                    <span class="small text-muted ms-2">Bloques programados y citas</span>
                </div>
                <div class="card-body p-2">
                    <table class="table table-bordered tabla-semana mb-0">
                        <thead class="table-light">
                        <tr>
                            <% for (LocalDate d : fechasSemana) { %>
                                <th class="text-center"><%= d.format(df) %></th>
                            <% } %>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <% for (LocalDate d : fechasSemana) { %>
                                <td style="width:14%; min-width:120px;">

                                    <!-- Mostrar bloques de horario para ese día -->
                                    <div class="mb-2">
                                        <strong class="small">Bloques:</strong>
                                        <%
                                            int diaSemana = d.getDayOfWeek().getValue(); // 1=lunes
                                            for (HorarioPeluquero h : horarios) {
                                                if (h.getDiaSemana() == diaSemana) {
                                        %>
                                                    <div class="horario-block">
                                                        <small><%= h.getHoraInicio() %> - <%= h.getHoraFin() %></small>
                                                        <form method="post" action="horarios" style="display:inline;">
                                                            <input type="hidden" name="accion" value="eliminar">
                                                            <input type="hidden" name="idHorario" value="<%= h.getIdHorario() %>">
                                                            <input type="hidden" name="idTrabajador" value="<%= idTrabajador %>">
                                                            <input type="hidden" name="weekStart" value="<%= weekStart %>">
                                                            <button class="btn btn-link btn-sm text-danger p-0 ms-2" onclick="return confirm('Eliminar bloque?')">Eliminar</button>
                                                        </form>
                                                    </div>
                                        <%
                                                }
                                            }
                                        %>
                                    </div>

                                    <!-- Mostrar citas de ese día -->
                                    <div>
                                        <strong class="small">Citas:</strong>
                                        <%
                                            List<Cita> citas = citasPorDia.get(d);
                                            if (citas == null || citas.isEmpty()) {
                                        %>
                                            <div class="text-muted small">Sin citas</div>
                                        <% } else {
                                                for (Cita c : citas) {
                                                    String clase = "cita-pendiente";
                                                    if (c.getEstado() == 2) clase = "cita-realizada";
                                                    if (c.getEstado() == 3) clase = "cita-cancelada";
                                        %>
                                                    <div class="cita-badge <%= clase %>">
                                                        <div class="d-flex justify-content-between">
                                                            <div>
                                                                <strong><%= c.getHoraInicio() %></strong>
                                                                &nbsp; - &nbsp;
                                                                <small><%= c.getNombreServicio() != null ? c.getNombreServicio() : "Servicio" %></small>
                                                            </div>
                                                            <div class="text-end small">
                                                                <form action="cita" method="post" style="display:inline;">
                                                                    <input type="hidden" name="accion" value="cambiarEstado">
                                                                    <input type="hidden" name="idCita" value="<%= c.getIdCita() %>">
                                                                    <input type="hidden" name="idTrabajador" value="<%= idTrabajador %>">
                                                                    <select name="estado" class="form-select form-select-sm" onchange="this.form.submit()">
                                                                        <option value="1" <%= c.getEstado()==1 ? "selected": "" %>>Pendiente</option>
                                                                        <option value="2" <%= c.getEstado()==2 ? "selected": "" %>>Completada</option>
                                                                        <option value="3" <%= c.getEstado()==3 ? "selected": "" %>>Cancelada</option>
                                                                    </select>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                        <%
                                                }
                                            }
                                        %>
                                    </div>

                                </td>
                            <% } %>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- panel lateral: crear horario -->
        <div class="col-lg-4">
            <div class="card shadow-sm mb-3">
                <div class="card-header bg-success text-white">
                    Crear bloque horario
                </div>
                <div class="card-body">
                    <form action="horarios" method="post">
                        <input type="hidden" name="accion" value="crear">
                        <input type="hidden" name="idTrabajador" value="<%= idTrabajador %>">
                        <input type="hidden" name="weekStart" value="<%= weekStart %>">

                        <label class="form-label">Día de la semana</label>
                        <select name="diaSemana" class="form-select" required>
                            <option value="1">Lunes</option>
                            <option value="2">Martes</option>
                            <option value="3">Miércoles</option>
                            <option value="4">Jueves</option>
                            <option value="5">Viernes</option>
                            <option value="6">Sábado</option>
                            <option value="7">Domingo</option>
                        </select>

                        <label class="form-label mt-2">Hora inicio (HH:mm)</label>
                        <input type="time" name="horaInicio" class="form-control" required>

                        <label class="form-label mt-2">Hora fin (HH:mm)</label>
                        <input type="time" name="horaFin" class="form-control" required>

                        <button class="btn btn-success mt-3 w-100">Crear bloque</button>
                    </form>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-header">
                    Leyenda
                </div>
                <div class="card-body small">
                    <div><span class="badge cita-pendiente">Pendiente</span></div>
                    <div class="mt-1"><span class="badge cita-realizada">Completada</span></div>
                    <div class="mt-1"><span class="badge cita-cancelada">Cancelada</span></div>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>

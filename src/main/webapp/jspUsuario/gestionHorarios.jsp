<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.*" %>
<%@ page import="es.peluqueria.gestion.modelo.Cita" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">

<%
    Map<LocalDate, List<Cita>> citasPorDia = (Map<LocalDate, List<Cita>>) request.getAttribute("citasPorDia");
    String weekStart = (String) request.getAttribute("weekStart");
    String weekPrev = (String) request.getAttribute("weekPrev");
    String weekNext = (String) request.getAttribute("weekNext");
    Integer idTrabajador = (Integer) request.getAttribute("idTrabajador");

    if (citasPorDia == null) citasPorDia = new LinkedHashMap<>();
    List<LocalDate> fechas = new ArrayList<>(citasPorDia.keySet());
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestionar Horarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .hour-column {
            width: 90px;
            background: #f8f9fa;
            font-weight: bold;
            text-align: center;
        }

        /* Citas más pequeñas */
        .cita {
            background: #f1f1f1;
            border-left: 4px solid #4a90e2;
            padding: 4px 6px;
            margin: 3px 0;
            font-size: 11px;
            border-radius: 3px;
        }

        .estado-1 { background: #f0ad4e; } /* Pendiente */
        .estado-2 { background: #5cb85c; } /* Completada */
        .estado-3 { background: #d9534f; } /* Cancelada */

        .top-bar {
            background: #343a40;
            padding: 12px;
            border-radius: 8px;
        }

        .table-wrapper {
            background: white;
            padding: 15px;
            border-radius: 12px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.15);

            /* SCROLL vertical */
            max-height: 500px;
            overflow-y: auto;
        }
    </style>
</head>

<body class="p-4" style="background:#eef1f5;">

    <!-- BARRA SUPERIOR -->
    <div class="top-bar d-flex align-items-center mb-4 text-white">
        <!-- Icono Bootstrap en lugar de emoji roto -->
        <a href="<%= request.getContextPath() %>/usuario?accion=inicio"
           class="btn btn-light btn-sm">
            <span class="me-1">🏠</span> Volver al inicio
        </a>

        <a class="btn btn-outline-light btn-sm"
           href="horarios?idTrabajador=<%=idTrabajador%>&weekStart=<%=weekPrev%>">
            ← Semana anterior
        </a>

        <span class="mx-3 fs-5">
            <strong>Semana de <%= weekStart %></strong>
        </span>

        <a class="btn btn-outline-light btn-sm"
           href="horarios?idTrabajador=<%=idTrabajador%>&weekStart=<%=weekNext%>">
            Semana siguiente →
        </a>

        <div class="flex-grow-1"></div>

        <span class="text-light me-2">
            👤 Trabajador #<%= idTrabajador %>
        </span>
    </div>

    <div class="table-wrapper">

        <table class="table table-bordered table-hover text-center">
            <thead class="table-secondary">
                <tr>
                    <th class="hour-column">Hora</th>
                    <% for (LocalDate d : fechas) { %>
                        <th>
                            <%= d.getDayOfWeek().toString().substring(0,1).toUpperCase()
                                + d.getDayOfWeek().toString().substring(1).toLowerCase() %>
                            <br>
                            <small class="text-muted"><%= d %></small>
                        </th>
                    <% } %>
                </tr>
            </thead>

            <tbody>
                <% for (int h = 9; h <= 20; h++) {
                       String horaLabel = String.format("%02d:00", h);
                %>

                <tr>
                    <td class="hour-column"><%= horaLabel %></td>

                    <% for (LocalDate dia : fechas) {

                           List<Cita> lista = citasPorDia.get(dia);
                           Cita encontrada = null;

                           if (lista != null) {
                               for (Cita c : lista) {
                                   if (c.getHoraInicio() != null &&
                                       c.getHoraInicio().startsWith(String.format("%02d", h))) {
                                       encontrada = c;
                                       break;
                                   }
                               }
                           }
                    %>

                    <td style="height: 70px;">

                        <% if (encontrada != null) { %>
                            <div class="cita estado-<%= encontrada.getEstado() %>">
                                <strong><%= encontrada.getNombreCliente() %></strong>
                                <div><%= encontrada.getNombreServicio() %></div>
                                <div class="small"><%= encontrada.getHoraInicio() %> - <%= encontrada.getHoraFin() %></div>

                                <form method="post" action="horarios" class="mt-1">
                                    <input type="hidden" name="accion" value="cambiarEstadoCita">
                                    <input type="hidden" name="idCita" value="<%= encontrada.getIdCita() %>">
                                    <input type="hidden" name="idTrabajador" value="<%= idTrabajador %>">
                                    <input type="hidden" name="weekStart" value="<%= weekStart %>">

                                    <select name="estado" class="form-select form-select-sm" onchange="this.form.submit()">
                                        <option value="1" <%= encontrada.getEstado()==1 ? "selected":"" %>>Pendiente</option>
                                        <option value="2" <%= encontrada.getEstado()==2 ? "selected":"" %>>Completada</option>
                                        <option value="3" <%= encontrada.getEstado()==3 ? "selected":"" %>>Cancelada</option>
                                    </select>
                                </form>
                            </div>
                        <% } %>

                    </td>

                    <% } %>
                </tr>

                <% } %>
            </tbody>
        </table>

    </div>

</body>
</html>

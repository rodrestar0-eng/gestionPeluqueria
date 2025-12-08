<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, es.peluqueria.gestion.modelo.Cita" %>
<%
    List<Cita> citas = (List<Cita>) request.getAttribute("misCitas");
    String filtroEstado = request.getParameter("estado");
    String filtroServicio = request.getParameter("servicio");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Mis Citas</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fa;
        }
.table tbody tr.estado-1 td {
    background-color: #fff3cd !important; /* Pendiente */
}

.table tbody tr.estado-2 td {
    background-color: #d1e7dd !important; /* Completada */
}

.table tbody tr.estado-3 td {
    background-color: #f8d7da !important; /* Cancelada */
}




        .tabla-scroll {
            max-height: 500px;
            overflow-y: auto;
        }

        th { position: sticky; top: 0; background: white; z-index: 1; }

        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: none;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }

        .table-hover tbody tr:hover {
            background-color: rgba(0, 123, 255, 0.1);
        }
    </style>
</head>

<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="bi bi-calendar-check"></i> Mis Citas</a>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-lg-10">
                <div class="card">
                    <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                        <h2 class="mb-0"><i class="bi bi-calendar-check"></i> Mis Citas</h2>
                        <a href="<%= request.getContextPath() %>/usuario?accion=inicio" class="btn btn-light btn-sm">
                            <i class="bi bi-arrow-left-circle"></i> Volver
                        </a>
                    </div>
                    <div class="card-body">
                        <!-- Filtros -->
                        <form method="get" class="row g-3 mb-4">
                            <input type="hidden" name="accion" value="misCitasPeluquero">

                            <div class="col-md-4">
                                <label class="form-label"><i class="bi bi-flag"></i> Estado</label>
                                <select name="estado" class="form-select">
   									 <option value="">Todos</option>
   									 <option value="1" <%= "1".equals(filtroEstado) ? "selected" : "" %>>Pendiente</option>
    								<option value="2" <%= "2".equals(filtroEstado) ? "selected" : "" %>>Completada</option>
   									 <option value="3" <%= "3".equals(filtroEstado) ? "selected" : "" %>>Cancelada</option>
								</select>

                            </div>

                            <div class="col-md-4">
                                <label class="form-label"><i class="bi bi-scissors"></i> Servicio</label>
                                <select name="servicio" class="form-select">
                                    <option value="">Todos</option>
                                    <% if (citas != null) {
                                        Set<String> servicios = new LinkedHashSet<>();
                                        for (Cita c : citas) servicios.add(c.getNombreServicio());
                                        for (String s : servicios) { %>
                                            <option value="<%= s %>" <%= s.equals(filtroServicio) ? "selected" : "" %>>
                                                <%= s %>
                                            </option>
                                    <% }} %>
                                </select>
                            </div>

                            <div class="col-md-4 d-flex align-items-end">
                                <button class="btn btn-primary w-100">
                                    <i class="bi bi-funnel"></i> Filtrar
                                </button>
                            </div>
                        </form>

                        <!-- Tabla de citas -->
                        <div class="table-responsive tabla-scroll">
                            <table class="table table-bordered table-hover text-center">
                                <thead class="table-dark">
                                    <tr>
                                        <th><i class="bi bi-calendar-date"></i> Fecha</th>
                                        <th><i class="bi bi-clock"></i> Hora</th>
                                        <th><i class="bi bi-scissors"></i> Servicio</th>
                                        <th><i class="bi bi-flag"></i> Estado</th>
                                        <th><i class="bi bi-gear"></i> Acciones</th>
                                    </tr>
                                </thead>

                                <tbody>
                                <% if (citas != null && !citas.isEmpty()) {
                                    for (Cita c : citas) {

                                        if (filtroEstado != null && !filtroEstado.isEmpty() &&
                                            c.getEstado() != Integer.parseInt(filtroEstado)) continue;

                                        if (filtroServicio != null && !filtroServicio.isEmpty() &&
                                            !c.getNombreServicio().equals(filtroServicio)) continue;
                                %>

                                    <tr class="<%= "estado-" + c.getEstado() %>">
                                        <td><%= c.getFechaCita() %></td>
                                        <td><%= c.getHoraInicio() %> - <%= c.getHoraFin() %></td>
                                        <td><%= c.getNombreServicio() %></td>

                                       <td>
   											 <%
											switch (c.getEstado()) {
    										case 1: out.print("Pendiente"); break;
   											 case 2: out.print("Completada"); break;
   											 case 3: out.print("Cancelada"); break;
    											default: out.print("Desconocido");
												}
												%>

									</td>


                                        <td>
                                            <a href="cita?accion=detallePeluquero&id=<%= c.getIdCita() %>"
                                               class="btn btn-info btn-sm">
                                                <i class="bi bi-eye"></i> Ver
                                            </a>
                                        </td>
                                    </tr>

                                <% }} else { %>
                                    <tr><td colspan="5" class="text-muted">No hay citas disponibles</td></tr>
                                <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
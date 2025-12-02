<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="es.peluqueria.gestion.modelo.Especialidad" %>

<html>
<head>
    <title>Especialidades del Trabajador</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">

    <style>
        body {
            background: #f5f7fa;
        }
        .card {
            border-radius: 12px;
        }
        /* Borde completo para toda la tabla */
        .table-container {
            border: 2px solid #d0d0d0;
            border-radius: 10px;
            overflow: hidden;
        }
        .btn-back-custom {
            padding: 10px 25px;
            background: #6c757d;
            border: none;
            border-radius: 30px;
            font-size: 16px;
            font-weight: 600;
            color: white;
            transition: 0.3s ease;
            box-shadow: 0 3px 6px rgba(0,0,0,0.2);
            text-decoration: none;
        }
        .btn-back-custom:hover {
            background: #5a6268;
            color: white;
            transform: scale(1.05);
        }
    </style>
</head>

<body class="container mt-4">

<h2 class="mb-4 fw-bold text-primary">
    Especialidades del trabajador
</h2>

<%
    List<Especialidad> lista = (List<Especialidad>) request.getAttribute("especialidades");
    int idTrabajador = Integer.parseInt(request.getParameter("idTrabajador"));
%>

<!-- LISTADO DE ESPECIALIDADES -->
<div class="card shadow-sm mb-4 border-0">
    <div class="card-header bg-primary text-white fw-semibold">
        Especialidades asignadas
    </div>

    <!-- CONTENEDOR CON BORDE -->
    <div class="table-container">

        <table class="table table-striped mb-0">
            <thead class="table-light">
                <tr>
                    <th>Especialidad</th>
                    <th class="text-center">Acción</th>
                </tr>
            </thead>

            <tbody>
            <% if (lista.isEmpty()) { %>
                <tr>
                    <td colspan="2" class="text-center text-muted py-4">
                        No hay especialidades asignadas
                    </td>
                </tr>
            <% } else {
                for (Especialidad esp : lista) { %>
                <tr>
                    <td class="align-middle"><%= esp.getNombre() %></td>
                    <td class="text-center">
                        <form action="trabajador-especialidades" method="post" class="d-inline">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="idTrabajador" value="<%= idTrabajador %>">
                            <input type="hidden" name="idEspecialidad" value="<%= esp.getIdEspecialidad() %>">

                            <button class="btn btn-danger btn-sm px-3">
                                Eliminar
                            </button>
                        </form>
                    </td>
                </tr>
            <% }} %>
            </tbody>
        </table>

    </div>
</div>

<!-- FORMULARIO DE ASIGNACIÓN -->
<div class="card shadow-sm border-0">
    <div class="card-header bg-success text-white fw-semibold">
        Asignar nueva especialidad
    </div>

    <div class="card-body">
        <form action="trabajador-especialidades" method="post">

            <input type="hidden" name="accion" value="asignar">
            <input type="hidden" name="idTrabajador" value="<%= idTrabajador %>">

            <label class="form-label fw-semibold">Seleccionar especialidad:</label>
            <select name="idEspecialidad" class="form-select" required>
                <%
                    List<Especialidad> todas = (List<Especialidad>)
                        request.getSession().getAttribute("listaTodasEspecialidades");
                    for (Especialidad e : todas) {
                %>
                    <option value="<%= e.getIdEspecialidad() %>">
                        <%= e.getNombre() %>
                    </option>
                <% } %>
            </select>

            <button class="btn btn-success mt-3 px-4">
                Añadir
            </button>
        </form>
    </div>
</div>

<!-- BOTÓN VOLVER SUPER BONITO -->
<div class="mt-4">
    <a href="admin?accion=listarUsuarios" class="btn-back-custom">
        ⬅ Volver
    </a>
</div>

</body>
</html>

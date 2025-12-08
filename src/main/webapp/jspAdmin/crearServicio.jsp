<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Servicio</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
          <% java.util.List<es.peluqueria.gestion.modelo.Especialidad> especialidades = (java.util.List<es.peluqueria.gestion.modelo.Especialidad>) request.getAttribute("especialidades"); %>
</head>

<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h3 class="card-title mb-0">Crear Nuevo Servicio</h3>
                    </div>
                    <div class="card-body">
                    <% if (error != null) { %>
    <div class="alert alert-danger text-center fw-bold">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        <%= error %>
    </div>
<% } %>
                    
                        <form action="admin" method="post">
                            <input type="hidden" name="accion" value="crearServicio">
                            
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" id="nombre" name="nombre" class="form-control" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="precio" class="form-label">Precio (€)</label>
                                <input type="number" id="precio" name="precio" class="form-control" step="0.01" min="0.1" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="duracion" class="form-label">Duración (minutos)</label>
                                <input type="number" id="duracion" name="duracion" class="form-control" min="1" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="descripcion" class="form-label">Descripción</label>
                                <textarea id="descripcion" name="descripcion" class="form-control" rows="3"></textarea>
                            </div>
                            
                            <div class="mb-3">
                                <label for="idEspecialidad" class="form-label">Especialidad</label>
                                <select id="idEspecialidad" name="idEspecialidad" class="form-select" required>
                                    <option value="">-- Seleccionar especialidad --</option>
                                    <% 
                                       
                                        
                                        for (es.peluqueria.gestion.modelo.Especialidad esp : especialidades) {
                                    %>
                                        <option value="<%= esp.getIdEspecialidad() %>">
                                            <%= esp.getNombre() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button type="submit" class="btn btn-success me-md-2">Crear Servicio</button>
                                <a href="admin?accion=panel" class="btn btn-secondary">Volver</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Crear Especialidad</title>

<style>
    body { font-family: Arial; }
    label { display:block; margin-top:10px; }
    input[type="text"] { width: 300px; padding: 6px; }
    .btn {
        margin-top: 15px;
        padding: 8px 14px;
        background: #0275d8;
        color: white;
        border-radius: 5px;
        text-decoration:none;
    }
    .btn:hover {
        background: #01549b;
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

<h2>Crear Especialidad</h2>

<form action="admin" method="POST" onsubmit="return validar();">
    <input type="hidden" name="accion" value="crearEspecialidad">

    <label>Nombre:</label>
    <input type="text" id="nombre" name="nombre">

    <br>

    <button type="submit" class="btn">Crear</button>
    <a class="btn" href="admin?accion=panel">Volver</a>
</form>

</body>
</html>

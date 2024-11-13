<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Editar Empleado</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
    <h1>Editar Empleado</h1>

    <form action="modificarEmpleado" method="post">
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="${empleado.nombre}" required><br>

        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" value="${empleado.dni}" readonly><br>

        <label for="sexo">Sexo:</label>
        <select id="sexo" name="sexo">
            <option value="M" ${empleado.sexo == 'M' ? 'selected' : ''}>Masculino</option>
            <option value="F" ${empleado.sexo == 'F' ? 'selected' : ''}>Femenino</option>
        </select><br>

        <label for="categoria">Categoría:</label>
        <input type="text" id="categoria" name="categoria" value="${empleado.categoria}" required>c

        <label for="anosTrabajados">Años Trabajados:</label>
        <input type="number" id="anosTrabajados" name="anosTrabajados" value="${empleado.anosTrabajados}" required><br>

        <button type="submit">Guardar Cambios</button>
    </form>
	</br>
	<!-- Botón para volver atrás -->
	<button onclick="goBack()">Volver a la página anterior</button>


	<!-- Código JavaScript -->
	<script>
		function goBack() {
			window.history.back(); // Regresa a la página anterior
		}
	</script>
    <br>
    <a href="views/empleados.jsp">Volver a la lista</a>
    </br>
    </br>
    <button onclick="window.location.href='views/empleados.jsp'">Volver al
		menu principal</button>
</body>
</html>

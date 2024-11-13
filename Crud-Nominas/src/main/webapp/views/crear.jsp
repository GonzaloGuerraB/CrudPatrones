<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Crear Empleado</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
    <h1>Crear Empleado</h1>

    <form action="front" method="post">
    <input type="hidden" name="option" value="crearEmpleado" />
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" required>
        
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" required>
        
        <label for="sexo">Sexo:</label>
        <select id="sexo" name="sexo" required>
            <option value="M">Masculino</option>
            <option value="F">Femenino</option>
        </select>

        <label for="categoria">Categoría:</label>
        <input type="text" id="categoria" name="categoria" required>
        
        <label for="anosTrabajados">Años Trabajados:</label>
        <input type="number" id="anosTrabajados" name="anosTrabajados" required>
        <button type="submit">Crear Empleado</button>
    </form>
    <!-- Botón para volver atrás -->
	<button onclick="goBack()">Volver a la página anterior</button>
	</br>

	<!-- Código JavaScript -->
	<script>
		function goBack() {
			window.history.back(); // Regresa a la página anterior
		}
	</script>
	</br>
	<button onclick="window.location.href='index.jsp'">Volver al
		menu principal</button>
</body>
</html>

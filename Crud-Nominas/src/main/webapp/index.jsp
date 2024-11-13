<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menú Principal</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
	<h1>Gestión de Empleados</h1>
	<div class="button-container">
		<nav>
			<button onclick="window.location.href='front?option=crear'">Crear
				empleados</button>
			<button onclick="window.location.href='front?option=listar'">Listar
				empleados</button>
			<button onclick="window.location.href='front?option=salario'">Buscar
				salario</button>
			<button onclick="window.location.href='front?option=buscar'">Buscar
				empleado</button>

		</nav>
		<!-- Botón para volver atrás -->
		<button onclick="goBack()">Volver a la página anterior</button>
		</br>

		<!-- Código JavaScript -->
		<script>
			function goBack() {
				window.history.back(); // Regresa a la página anterior
			}
		</script>
	</div>
</body>
</html>

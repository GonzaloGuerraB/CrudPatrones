<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.empresa.model.Empleado"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Editar Empleado</title>
<link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
	<h1>Editar Empleado</h1>

	<%
	String error = (String) request.getAttribute("error");
	String success = (String) request.getAttribute("success");
	Empleado empleado = (Empleado) request.getAttribute("empleado");

	if (error != null && !error.isEmpty()) {
	%>
	<div style="color: red;"><%=error%></div>
	<%
	}
	if (success != null && !success.isEmpty()) {
	%>
	<div style="color: green;"><%=success%></div>
	<%
	}
	%>

	<form action="front" method="post">
		<input type="hidden" name="option" value="editarEmpleado" /> <label
			for="nombre">Nombre:</label> <input type="text" name="nombre"
			value="<%=empleado != null ? empleado.getNombre() : ""%>" required />

		<label for="dni">DNI:</label> <input type="text" name="dni"
			value="<%=empleado != null ? empleado.getDni() : ""%>" readonly
			required /> <label for="sexo">Sexo:</label> <select name="sexo"
			required>
			<option value="M"
				<%="M".equals(empleado != null ? empleado.getSexo() : "") ? "selected" : ""%>>Masculino</option>
			<option value="F"
				<%="F".equals(empleado != null ? empleado.getSexo() : "") ? "selected" : ""%>>Femenino</option>
		</select> <label for="categoria">Categoría:</label> <input type="number"
			name="categoria"
			value="<%=empleado != null ? empleado.getCategoria() : ""%>"
			required /> <label for="anosTrabajados">Años Trabajados:</label> <input
			type="number" name="anosTrabajados"
			value="<%=empleado != null ? empleado.getAnosTrabajados() : ""%>"
			required />

		<button type="submit">Guardar Cambios</button>
	</form>
	</br>
	</br>

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
		menú principal</button>
</body>
</html>

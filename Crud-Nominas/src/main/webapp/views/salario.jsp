<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Salario de Empleado</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
    <% 
        // Recuperar los atributos de error y éxito del request
        String error = (String) request.getAttribute("error");
        String success = (String) request.getAttribute("success");
        
        // Recuperar el atributo sueldo como Double
        Double sueldo = (Double) request.getAttribute("sueldo");
        
        // Capturar el DNI de la consulta
        String dni = request.getParameter("dni");
        
        // Mostrar mensaje de error si existe
        if (error != null && !error.isEmpty()) {
    %>
        <div style="color: red;"><%= error %></div>
    <% 
        }
        
        // Mostrar mensaje de éxito si existe
        if (success != null && !success.isEmpty()) {
    %>
        <div style="color: green;"><%= success %></div>
    <% 
        }
    %>

    <h1>Consultar Salario de Empleado</h1>

    <!-- Formulario de consulta -->
    <form action="front" method="post">
        <input type="hidden" name="option" value="buscarSalario" />
        <label for="dni">DNI del Empleado:</label>
        <input type="text" id="dni" name="dni" required />
        <button type="submit">Consultar</button>
    </form>

    <% 
        // Si el sueldo está disponible, mostrarlo
        if (sueldo != null) { 
    %>
        <h2>Salario del empleado con DNI <%= dni %>: <%= sueldo.toString() %> Euros.</h2>
    <% 
        }
    %>

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

    <button onclick="window.location.href='index.jsp'">Volver al menu principal</button>
</body>
</html>

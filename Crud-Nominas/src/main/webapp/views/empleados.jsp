<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.empresa.model.Empleado" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Empleados</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <!-- Incluye tu archivo CSS si es necesario -->
</head>
<body>
    <% 
        // Obtener los atributos del request
        String error = (String)  request.getAttribute("error");
        String success = (String) request.getAttribute("success");
        String mensaje = (String) request.getAttribute("mensaje");
        List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
        Map<String, Double> sueldos = (Map<String, Double>) request.getAttribute("sueldos");

        if (error != null) {
    %>
        <div style="color: red;"><%= error %></div>
    <% 
        } 
        if (success != null) { 
    %>
        <div style="color: green;"><%= success %></div>
    <% 
        }
        if (mensaje != null) {
    %>
        <div><%= mensaje %></div>
    <% 
        }
    %>
    <h1>Lista de Empleados</h1>

    <table border="1">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>DNI</th>
                <th>Sexo</th>
                <th>Categoría</th>
                <th>Años Trabajados</th>
                <th>Sueldo</th>
                <th>Modificar</th>
                <th>Eliminar</th>
            </tr>
        </thead>
        <tbody>
            <%-- Iterar sobre la lista de empleados --%>
            <% if (empleados != null) { 
                for (Empleado empleado : empleados) { 
            %>
            <tr>
                <td><%= empleado.getNombre() %></td>
                <td><%= empleado.getDni() %></td>
                <td><%= empleado.getSexo() %></td>
                <td><%= empleado.getCategoria() %></td>
                <td><%= empleado.getAnosTrabajados() %></td>
                <td>
                    <%= (sueldos != null && sueldos.get(empleado.getDni()) != null) 
                        ? sueldos.get(empleado.getDni()) 
                        : "No disponible" %>
                </td>
                <td>
                    <!-- Botón de "Editar" que redirige al servlet con el DNI del empleado -->
                    <form action="front" method="get">
                        <input type="hidden" name="option" value="editar" />
                        <input type="hidden" name="dni" value="<%= empleado.getDni() %>">
                        <input type="submit" value="Editar">
                    </form>
                </td>
                <td>
                    <!-- Botón de "Eliminar" que redirige al servlet con el DNI del empleado -->
                    <form action="front" method="get">
                        <input type="hidden" name="option" value="eliminar" />
                        <input type="hidden" name="dni" value="<%= empleado.getDni() %>">
                        <input type="submit" value="Eliminar">
                    </form>
                </td>
            </tr>
            <% 
                } 
            } 
            %>
        </tbody>
    </table>

    <!-- Formulario para agregar un nuevo empleado -->
    <h2>Agregar Empleado</h2>
    <form action="front" method="post">
        <input type="hidden" name="option" value="crearEmpleado" />
        <label for="nombre">Nombre:</label> <input type="text" id="nombre" name="nombre" required />
        <label for="dni">DNI:</label> <input type="text" id="dni" name="dni" required />
        <label for="sexo">Sexo:</label>
        <select id="sexo" name="sexo" required>
            <option value="M">Masculino</option>
            <option value="F">Femenino</option>
        </select>
        <label for="categoria">Categoría:</label> <input type="text" id="categoria" name="categoria" required />
        <label for="anosTrabajados">Años Trabajados:</label> <input type="number" id="anosTrabajados" name="anosTrabajados" required />

        <button type="submit">Crear Empleado</button>
    </form>
    <br/>
    <!-- Botón para volver atrás -->
    <button onclick="goBack()">Volver a la página anterior</button>
    <br/>

    <!-- Código JavaScript -->
    <script>
        function goBack() {
            window.history.back(); // Regresa a la página anterior
        }
    </script>
    <br/>
    <button onclick="window.location.href='index.jsp'">Volver al menu principal</button>
</body>
</html>

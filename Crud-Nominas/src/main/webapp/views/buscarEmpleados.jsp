<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.empresa.model.Empleado" %>

<html>
<head>
    <title>Búsqueda de Empleados</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
    <h1>Búsqueda de Empleados</h1>

    <form action="front" method="post">
        <input type="hidden" name="option" value="buscarEmpleado" />
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="<%= request.getParameter("nombre") != null ? request.getParameter("nombre") : "" %>" />
        
        <label for="dni">DNI:</label>
        <input type="text" id="dni" name="dni" value="<%= request.getParameter("dni") != null ? request.getParameter("dni") : "" %>" />
        
        <label for="sexo">Sexo:</label>
        <select id="sexo" name="sexo">
            <option value="">Seleccionar</option>
            <option value="M" <%= "M".equals(request.getParameter("sexo")) ? "selected" : "" %>>Masculino</option>
            <option value="F" <%= "F".equals(request.getParameter("sexo")) ? "selected" : "" %>>Femenino</option>
        </select>
        
        <label for="categoria">Categoría:</label>
        <input type="number" id="categoria" name="categoria" value="<%= request.getParameter("categoria") != null ? request.getParameter("categoria") : "" %>" />
        
        <label for="anosTrabajados">Años trabajados:</label>
        <input type="number" id="anosTrabajados" name="anosTrabajados" value="<%= request.getParameter("anosTrabajados") != null ? request.getParameter("anosTrabajados") : "" %>" />
        
        <button type="submit">Buscar</button>
    </form>
    </br>
    <button onclick="window.location.href='front?option=buscar'">Resetear filtros</button>
    </br>
    </br>

    <div class="button-container">
        <button onclick="window.location.href='index.jsp'">Volver al menu principal</button>
        <!-- Botón para volver atrás -->
        <button onclick="goBack()">Volver a la página anterior</button>
    </div>

    <!-- Mostrar resultados -->
    <h2>Resultados de la Búsqueda</h2>
    <% 
        List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
        Map<String, Double> sueldos = (Map<String, Double>) request.getAttribute("sueldos");
        
        if (empleados != null && !empleados.isEmpty()) { 
    %>
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
                <% for (Empleado empleado : empleados) { %>
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
                            <input type="hidden" name="dni" value="<%= empleado.getDni() %>" />
                            <input type="submit" value="Editar">
                        </form>
                    </td>
                    <td>
                        <!-- Botón de "Eliminar" que redirige al servlet con el DNI del empleado -->
                        <form action="front" method="get">
                            <input type="hidden" name="option" value="eliminar" />
                            <input type="hidden" name="dni" value="<%= empleado.getDni() %>" />
                            <input type="submit" value="Eliminar">
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    <% 
        } else { 
    %>
        <p>No se encontraron empleados.</p>
    <% 
        } 
    %>
    </br>

    <!-- Código JavaScript -->
    <script>
        function goBack() {
            window.history.back(); // Regresa a la página anterior
        }
    </script>
</body>
</html>

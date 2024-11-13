package com.empresa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.empresa.dao.EmpleadoDAO;
import com.empresa.model.Empleado;

@WebServlet("/front")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final EmpleadoDAO empleadoDAO = EmpleadoDAO.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String option = req.getParameter("option");
		try {
			switch (option) {
			case "crear":
				redirectToPage(req, resp, "/views/crear.jsp");
				break;
			case "empleados":
				listarEmpleados(req);
				redirectToPage(req, resp, "/views/empleados.jsp");
				break;
			case "salario":
				redirectToPage(req, resp, "/views/salario.jsp");
				break;
			case "listar":
				listarEmpleados(req);
				redirectToPage(req, resp, "/views/empleados.jsp");
				break;
			case "editar":
				editarEmpleados(req, resp);
				break;
			case "eliminar":
				eliminarEmpleado(req, resp);
				break;
			case "buscar":
				buscarEmpleados(req);
				redirectToPage(req, resp, "views/buscarEmpleados.jsp");
				break;
			default:
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opción no válida");
				break;
			}
		} catch (Exception ex) {
			log("Error en FrontController: " + ex.getMessage(), ex);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String option = req.getParameter("option");
		try {
			switch (option) {
			case "crearEmpleado":
				crearEmpleado(req);
				listarEmpleados(req);
				redirectToPage(req, resp, "views/empleados.jsp");
				break;
			case "buscarSalario":
				buscarPorSalario(req);
				redirectToPage(req, resp, "/views/salario.jsp");
				break;
			case "buscarEmpleado":
				buscarEmpleados(req);
				redirectToPage(req, resp, "views/buscarEmpleados.jsp");
				break;
			case "editarEmpleado":
				editarEmpleado(req);
				listarEmpleados(req);
				redirectToPage(req, resp, "views/empleados.jsp");
				break;
			default:
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Opción no válida");
				break;
			}
		} catch (Exception ex) {
			log("Error en FrontController: " + ex.getMessage(), ex);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
		}
	}

	// Métodos Privados para Encapsular Funcionalidad Repetitiva y Simplificar el
	// Código

	private void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.forward(req, resp);
	}

	private void listarEmpleados(HttpServletRequest req) throws Exception {
		List<Empleado> empleados = empleadoDAO.obtenerTodosLosEmpleados();
		Map<String, Double> sueldos = new HashMap<>();
		for (Empleado empleado : empleados) {
			sueldos.put(empleado.getDni(), empleadoDAO.obtenerSueldoPorDni(empleado.getDni()));
		}
		req.setAttribute("empleados", empleados);
		req.setAttribute("sueldos", sueldos);
	}

	private void editarEmpleados(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String dni = req.getParameter("dni");
		Empleado empleado = empleadoDAO.obtenerEmpleadoPorDni(dni);
		if (empleado != null) {
			req.setAttribute("empleado", empleado);
			redirectToPage(req, resp, "views/editarEmpleado.jsp");
		} else {
			req.setAttribute("error", "Empleado no encontrado.");
			redirectToPage(req, resp, "views/empleados.jsp");
		}
	}

	private void eliminarEmpleado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String dni = req.getParameter("dni");
		empleadoDAO.eliminarEmpleado(dni);
		resp.sendRedirect(req.getContextPath() + "/front?option=empleados");
	}

	private void buscarEmpleados(HttpServletRequest req) throws Exception {
		String nombre = req.getParameter("nombre");
		String dni = req.getParameter("dni");
		String sexo = req.getParameter("sexo");
		Integer categoria = parseInteger(req.getParameter("categoria"));
		Integer anosTrabajados = parseInteger(req.getParameter("anosTrabajados"));

		List<Empleado> empleados = empleadoDAO.buscarEmpleados(nombre, dni, sexo, categoria, anosTrabajados);
		Map<String, Double> sueldos = new HashMap<>();
		for (Empleado empleado : empleados) {
			sueldos.put(empleado.getDni(), empleadoDAO.obtenerSueldoPorDni(empleado.getDni()));
		}
		req.setAttribute("empleados", empleados);
		req.setAttribute("sueldos", sueldos);
	}

	private void crearEmpleado(HttpServletRequest req) throws Exception {
		// Recuperamos los parámetros del formulario
		String nombre = req.getParameter("nombre");
		String dni = req.getParameter("dni");
		String sexo = req.getParameter("sexo");
		int categoria = Integer.parseInt(req.getParameter("categoria"));
		int anosTrabajados = Integer.parseInt(req.getParameter("anosTrabajados"));

		// Validamos los datos antes de proceder a la creación del empleado
		if (!esNombreValido(nombre)) {
			req.setAttribute("error", "El nombre es inválido.");
			return; // Salimos del método si hay error en la validación
		}

		if (!esDniValido(dni)) {
			req.setAttribute("error", "El DNI es inválido. Tiene que ser un número de 8 dígitos y una letra como el siguiente: 12345678Z");
			return;
		}

		if (!esSexoValido(sexo)) {
			req.setAttribute("error", "El sexo es inválido.");
			return;
		}

		if (!esCategoriaValida(categoria)) {
			req.setAttribute("error", "La categoría es inválida. Tiene que ser un número entre 1 y 5");
			return;
		}

		if (!esAnosTrabajadosValido(anosTrabajados)) {
			req.setAttribute("error", "Los años trabajados son inválidos. Tiene que ser un número entre 0 y 50");
			return;
		}

		// Si todos los datos son válidos, creamos el empleado
		Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anosTrabajados);
		boolean creado = empleadoDAO.crearEmpleado(empleado);

		// Establecemos el mensaje correspondiente dependiendo del resultado
		if (creado) {
			req.setAttribute("success", "Empleado creado exitosamente.");
		} else {
			req.setAttribute("error", "Error al crear el empleado. Inténtalo de nuevo.");
		}
	}

	private void buscarPorSalario(HttpServletRequest req) throws Exception {
		String dni = req.getParameter("dni");
		Double sueldo = empleadoDAO.obtenerSueldoPorDni(dni);
		if (sueldo == null) {
			req.setAttribute("error", "El empleado con DNI " + dni + " no existe.");
		} else {
			req.setAttribute("dni", dni);
			req.setAttribute("sueldo", sueldo);
		}
	}

	private void editarEmpleado(HttpServletRequest req) throws Exception {
		// Recuperamos los parámetros del formulario
		String nombre = req.getParameter("nombre");
		String dni = req.getParameter("dni");
		String sexo = req.getParameter("sexo");
		Integer categoria = parseInteger(req.getParameter("categoria"));
		Integer anosTrabajados = parseInteger(req.getParameter("anosTrabajados"));

		// Validamos los datos antes de proceder con la edición del empleado
		if (!esNombreValido(nombre)) {
			req.setAttribute("error", "El nombre es inválido.");
			return; // Salimos del método si hay error en la validación
		}

		if (!esDniValido(dni)) {
			req.setAttribute("error", "El DNI es inválido. Tiene que ser un número de 8 dígitos y una letra como el siguiente: 12345678Z");
			return;
		}

		if (!esSexoValido(sexo)) {
			req.setAttribute("error", "El sexo es inválido.");
			return;
		}

		if (categoria == null || !esCategoriaValida(categoria)) {
			req.setAttribute("error", "La categoría es inválida. Tiene que ser un número entre 1 y 5");
			return;
		}

		if (anosTrabajados == null || !esAnosTrabajadosValido(anosTrabajados)) {
			req.setAttribute("error", "Los años trabajados son inválidos. Tiene que ser un número entre 0 y 50");
			return;
		}

		// Si todos los datos son válidos, procedemos a modificar el empleado
		Empleado empleado = new Empleado(nombre, dni, sexo, categoria, anosTrabajados);
		boolean actualizado = empleadoDAO.modificarEmpleadoConSueldo(empleado);

		// Establecemos el mensaje correspondiente dependiendo del resultado
		if (actualizado) {
			req.setAttribute("success", "Empleado actualizado exitosamente.");
		} else {
			req.setAttribute("error", "Error al actualizar el empleado. Inténtalo de nuevo.");
		}
	}

	private Integer parseInteger(String param) {
		return (param != null && !param.isEmpty()) ? Integer.parseInt(param) : null;
	}

	// Validar nombre (no debe estar vacío)
	private boolean esNombreValido(String nombre) {
		return nombre != null && !nombre.trim().isEmpty();
	}

	// Validar DNI (debe seguir el patrón de 8 dígitos seguidos de una letra)
	private boolean esDniValido(String dni) {
		return dni != null && dni.matches("\\d{8}[A-Za-z]");
	}

	// Validar sexo (solo debe ser 'M' o 'F')
	private boolean esSexoValido(String sexo) {
		return "M".equals(sexo) || "F".equals(sexo);
	}

	// Validar categoría (debe estar dentro de un rango válido)
	private boolean esCategoriaValida(int categoria) {
		return categoria >= 1 && categoria <= 5;
	}

	// Validar años trabajados (debe estar dentro de un rango válido)
	private boolean esAnosTrabajadosValido(int anosTrabajados) {
		return anosTrabajados >= 0 && anosTrabajados <= 50;
	}

}

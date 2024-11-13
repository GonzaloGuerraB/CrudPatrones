package com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.empresa.conexion.Conexion;
import com.empresa.model.Empleado;
import com.empresa.model.Nomina;

public class EmpleadoDAO {

	private static EmpleadoDAO instancia;
	
	public static EmpleadoDAO getInstance () {
		if (EmpleadoDAO.instancia == null) {
			return new EmpleadoDAO();
		} else {
			return instancia;
		}
	}
	
	private final Nomina nomina = Nomina.getInstance();
	
    /**
     * Obtiene todos los empleados de la base de datos, incluyendo su nómina si existe.
     *
     * @return una lista de objetos {@link Empleado}.
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT e.nombre, e.dni, e.sexo, e.categoria, e.anyos, n.sueldo " +
                       "FROM empleados e " +
                       "LEFT JOIN nominas n ON e.id = n.empleado_id";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setDni(rs.getString("dni"));
                empleado.setSexo(rs.getString("sexo"));
                empleado.setCategoria(rs.getInt("categoria"));
                empleado.setAnosTrabajados(rs.getInt("anyos"));

                empleados.add(empleado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    /**
     * Busca empleados en la base de datos según los filtros proporcionados.
     *
     * @param nombre el nombre del empleado (puede ser null o vacío).
     * @param dni el DNI del empleado (puede ser null).
     * @param sexo el sexo del empleado (puede ser null).
     * @param categoria la categoría del empleado (puede ser null).
     * @param anosTrabajados los años trabajados del empleado (puede ser null).
     * @return una lista de objetos {@link Empleado} que cumplen con los criterios de búsqueda.
     */
    public List<Empleado> buscarEmpleados(String nombre, String dni, String sexo, Integer categoria, Integer anosTrabajados) {
        List<Empleado> empleados = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT nombre, dni, sexo, categoria, anyos FROM empleados WHERE 1=1");

        List<Object> parametros = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            query.append(" AND nombre LIKE ?");
            parametros.add("%" + nombre + "%");
        }
        if (dni != null && !dni.isEmpty()) {
            query.append(" AND dni = ?");
            parametros.add(dni);
        }
        if (sexo != null && !sexo.isEmpty()) {
            query.append(" AND sexo = ?");
            parametros.add(sexo);
        }
        if (categoria != null) {
            query.append(" AND categoria = ?");
            parametros.add(categoria);
        }
        if (anosTrabajados != null) {
            query.append(" AND anyos = ?");
            parametros.add(anosTrabajados);
        }

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Empleado empleado = new Empleado();
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setDni(rs.getString("dni"));
                    empleado.setSexo(rs.getString("sexo"));
                    empleado.setCategoria(rs.getInt("categoria"));
                    empleado.setAnosTrabajados(rs.getInt("anyos"));
                    empleados.add(empleado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }

    /**
     * Obtiene un empleado por su DNI.
     *
     * @param dni el DNI del empleado.
     * @return un objeto {@link Empleado} si se encuentra, o null si no existe.
     */
    public Empleado obtenerEmpleadoPorDni(String dni) {
        Empleado empleado = null;
        String query = "SELECT nombre, dni, sexo, categoria, anyos FROM empleados WHERE dni = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = new Empleado();
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setDni(rs.getString("dni"));
                    empleado.setSexo(rs.getString("sexo"));
                    empleado.setCategoria(rs.getInt("categoria"));
                    empleado.setAnosTrabajados(rs.getInt("anyos"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
    }

    /**
     * Crea un nuevo empleado en la base de datos junto con su nómina.
     *
     * @param empleado el objeto {@link Empleado} a crear.
     * @return true si el empleado se creó exitosamente, false en caso de error.
     */
    public boolean crearEmpleado(Empleado empleado) {
        String sqlEmpleado = "INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES (?, ?, ?, ?, ?)";
        String sqlNomina = "INSERT INTO nominas (empleado_id, sueldo) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtEmpleado = conn.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS)) {
                stmtEmpleado.setString(1, empleado.getNombre());
                stmtEmpleado.setString(2, empleado.getDni());
                stmtEmpleado.setString(3, empleado.getSexo());
                stmtEmpleado.setInt(4, empleado.getCategoria());
                stmtEmpleado.setInt(5, empleado.getAnosTrabajados());

                int rowsAffected = stmtEmpleado.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = stmtEmpleado.getGeneratedKeys();
                    if (rs.next()) {
                        long empleadoId = rs.getLong(1);

                        try (PreparedStatement stmtNomina = conn.prepareStatement(sqlNomina)) {
                            stmtNomina.setLong(1, empleadoId);
                            stmtNomina.setDouble(2, nomina.calculaSueldo(empleado.getCategoria(), empleado.getAnosTrabajados()));
                            stmtNomina.executeUpdate();
                        }
                    }

                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modifica la información de un empleado y actualiza su sueldo.
     *
     * @param empleado el objeto {@link Empleado} con la información actualizada.
     * @return true si la modificación fue exitosa, false en caso de error.
     */
    public boolean modificarEmpleadoConSueldo(Empleado empleado) {
        String sqlEmpleado = "UPDATE empleados SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";
        String sqlNomina = "UPDATE nominas SET sueldo = ? WHERE empleado_id = (SELECT id FROM empleados WHERE dni = ?)";

        try (Connection conn = Conexion.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtEmpleado = conn.prepareStatement(sqlEmpleado);
                 PreparedStatement stmtNomina = conn.prepareStatement(sqlNomina)) {

                stmtEmpleado.setString(1, empleado.getNombre());
                stmtEmpleado.setString(2, empleado.getSexo());
                stmtEmpleado.setInt(3, empleado.getCategoria());
                stmtEmpleado.setInt(4, empleado.getAnosTrabajados());
                stmtEmpleado.setString(5, empleado.getDni());

                int filasActualizadasEmpleado = stmtEmpleado.executeUpdate();

                stmtNomina.setDouble(1, nomina.calculaSueldo(empleado.getCategoria(), empleado.getAnosTrabajados()));
                stmtNomina.setString(2, empleado.getDni());

                int filasActualizadasNomina = stmtNomina.executeUpdate();

                if (filasActualizadasEmpleado > 0 && filasActualizadasNomina > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene la nómina de un empleado por su DNI.
     *
     * @param dni el DNI del empleado.
     * @return un objeto {@link Nomina} si se encuentra, o null si no existe.
     */
    public Nomina obtenerNominaPorDni(String dni) {
        Nomina nomina = null;
        String sql = "SELECT n.sueldo FROM nominas n " +
                     "JOIN empleados e ON n.empleado_id = e.id " +
                     "WHERE e.dni = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double sueldo = rs.getDouble("sueldo");
                    nomina = new Nomina(dni, sueldo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nomina;
    }

    /**
     * Obtiene el sueldo de un empleado por su DNI.
     *
     * @param dni el DNI del empleado.
     * @return el sueldo del empleado como {@link Double}, o null si no se encuentra.
     */
    public Double obtenerSueldoPorDni(String dni) {
        Double sueldo = null;
        String query = "SELECT n.sueldo FROM nominas n " +
                       "JOIN empleados e ON n.empleado_id = e.id " +
                       "WHERE e.dni = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                sueldo = rs.getDouble("sueldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sueldo;
    }

    /**
     * Elimina un empleado por su DNI.
     *
     * @param dni el DNI del empleado a eliminar.
     * @return true si el empleado fue eliminado exitosamente, false en caso de error.
     */
    public boolean eliminarEmpleado(String dni) {
        String query = "DELETE FROM empleados WHERE dni = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dni);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

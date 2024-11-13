package com.empresa.model;

import com.empresa.dao.EmpleadoDAO;

/**
 * La clase {@code Nomina} representa la nómina de un empleado, que incluye su
 * DNI y salario. Permite calcular el sueldo basado en la categoría y los años
 * trabajados. También proporciona métodos para acceder y modificar los datos
 * del empleado.
 */
public class Nomina {

	private static Nomina instancia;

	public static Nomina getInstance() {
		if (Nomina.instancia == null) {
			return new Nomina();
		} else {
			return instancia;
		}
	}

	/**
	 * El DNI del empleado.
	 */
	private String dni;

	/**
	 * El salario actual del empleado.
	 */
	private double salario;

	/**
	 * El sueldo base para cada categoría de empleado. La categoría está definida
	 * por un índice entre 1 y 10.
	 */
	private static final double SUELDO_BASE[] = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000,
			230000 };

	/**
	 * Constructor por defecto de la clase {@code Nomina}. Crea una nueva instancia
	 * de Nomina con valores no inicializados.
	 */
	public Nomina() {

	}

	/**
	 * Constructor con parámetros para la clase {@code Nomina}. Crea una nueva
	 * instancia de Nomina con un DNI y un salario especificados.
	 *
	 * @param dni     El DNI del empleado.
	 * @param salario El salario inicial del empleado.
	 */
	public Nomina(String dni, double salario) {

	}

	/**
	 * Obtiene el DNI del empleado.
	 *
	 * @return El DNI del empleado.
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Establece el DNI del empleado.
	 *
	 * @param dni El nuevo DNI del empleado.
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Obtiene el salario actual del empleado.
	 *
	 * @return El salario del empleado.
	 */
	public double getSalario() {
		return salario;
	}

	/**
	 * Establece el salario del empleado.
	 *
	 * @param salario El nuevo salario del empleado.
	 */
	public void setSalario(double salario) {
		this.salario = salario;
	}

	/**
	 * Calcula el sueldo total del empleado basado en su categoría y años
	 * trabajados. El sueldo base depende de la categoría, y a este se le suma un
	 * incremento de 5000 por cada año trabajado.
	 *
	 * @param categoria      La categoría del empleado (valor entre 1 y 10).
	 * @param anosTrabajados Los años que el empleado ha trabajado.
	 * @return El sueldo total calculado del empleado.
	 */
	public double calculaSueldo(int categoria, int anosTrabajados) {
		return SUELDO_BASE[categoria - 1] + anosTrabajados * 5000;
	}

}

package com.empresa.model;

/**
 * La clase {@code Empleado} representa a un empleado con información personal y profesional.
 * Contiene los atributos del nombre, DNI, sexo, categoría laboral y años trabajados.
 * Proporciona métodos para obtener y modificar dichos atributos.
 */
public class Empleado {

    /**
     * El nombre del empleado.
     */
    private String nombre;

    /**
     * El DNI del empleado.
     */
    private String dni;

    /**
     * El sexo del empleado.
     */
    private String sexo;

    /**
     * La categoría laboral del empleado. Generalmente un valor entre 1 y 10.
     */
    private int categoria;

    /**
     * Los años trabajados por el empleado.
     */
    private int anosTrabajados;

    /**
     * Constructor con parámetros para inicializar los atributos del empleado.
     * 
     * @param nombre        El nombre del empleado.
     * @param dni           El DNI del empleado.
     * @param sexo          El sexo del empleado.
     * @param categoria     La categoría laboral del empleado.
     * @param anosTrabajados Los años que el empleado ha trabajado.
     */
    public Empleado(String nombre, String dni, String sexo, int categoria, int anosTrabajados) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
        this.categoria = categoria;
        this.anosTrabajados = anosTrabajados;
    }

    /**
     * Constructor por defecto que crea una instancia de {@code Empleado} sin inicializar los atributos.
     */
    public Empleado() {
    }

    /**
     * Obtiene el nombre del empleado.
     * 
     * @return El nombre del empleado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del empleado.
     * 
     * @param nombre El nombre del empleado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @param dni El DNI del empleado.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el sexo del empleado.
     * 
     * @return El sexo del empleado.
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Establece el sexo del empleado.
     * 
     * @param sexo El sexo del empleado.
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene la categoría laboral del empleado.
     * 
     * @return La categoría del empleado.
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría laboral del empleado.
     * 
     * @param i La categoría laboral del empleado.
     */
    public void setCategoria(int i) {
        this.categoria = i;
    }

    /**
     * Obtiene los años trabajados del empleado.
     * 
     * @return Los años trabajados por el empleado.
     */
    public int getAnosTrabajados() {
        return anosTrabajados;
    }

    /**
     * Establece los años trabajados del empleado.
     * 
     * @param anosTrabajados Los años trabajados por el empleado.
     */
    public void setAnosTrabajados(int anosTrabajados) {
        this.anosTrabajados = anosTrabajados;
    }
}

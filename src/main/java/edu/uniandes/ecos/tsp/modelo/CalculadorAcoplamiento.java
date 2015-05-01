package edu.uniandes.ecos.tsp.modelo;

/**
 * Clase encargada de calcular el acoplamiento de un programa java.
 * @author Aleja Chica am.chica10@uniandes.edu.co
 * @version 1.0
 */
public class CalculadorAcoplamiento {

	/**
	 * Metodo constructor
	 */
	public CalculadorAcoplamiento(){

	}


	/**
	 * Realiza el calculo de la relaci�n de acoplamiento / fuerza total del programa.
	 * La relacion de acoplamiento / fuerza es la relacion de los numeros calculados 
	 * en el data binding externo y el data binding interno.
	 * Desde una perspectiva de calidad, valores bajos (<= 0,5) de esta relaci�n 
	 * se consideran mejores que los altos (> 0.5).
	 * @param totalFuerzaUnionInterna total de data binding interno del programa.
	 * @param totalDataBindingExterno total de data binding externo del programa.
	 * @return int el valor del calculo de acoplamiento total del programa
	 */
	public int calcularAcoplamientoTotal(int totalFuerzaUnionInterna, int totalDataBindingExterno){
		
		return 0;
	}

}
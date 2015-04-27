package edu.uniandes.ecos.tsp.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de calcular la fuerza de union interna o DataBindingInterno.
 * @author Aleja Chica am.chica10@uniandes.edu.co
 * @version 1.0
 */
public class CalculadorDataBindingInterno {
	
	/**
	 * Lista que contienen el total de los data binding internos por metodo.
	 */
	private List<Integer> totalDataBindingInterno;
	
	/**
	 * Metodo constructor
	 */
	public CalculadorDataBindingInterno(){

		totalDataBindingInterno = new ArrayList<Integer>();
	}
	
	
	/**
	 * Realiza el calculo de la  fuerza de union interna de un metodo.
	 * @param lineasPorMetodo Lista con las lineas del metodo a analizar.
	 * @return Int total data binding interno por metodo.
	 */
		public int calcularFuerzaUnionInternaPorMetodo(List<String> lineasPorMetodo){
		return 0;
	}

	
	/**
	 * Realiza el calculo de la fuera de union interna total de un programa.
	 * @return int total data binding interno total del programa analizado.
	 */
	public int calcularFuerzaUnionInternaTotal(List<String> totalVariablesGlobales) {
		return 0;
	}
	
	

}
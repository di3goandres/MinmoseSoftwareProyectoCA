package edu.uniandes.ecos.psp21.modelo;

import java.util.List;

/**
 * Clase que calcula una sumatoria.
 * @author Aleja Chica
 *
 */
public class Sumatoria {
	
	/**
	 * Metodo que permite calcular una sumatoria
	 * @param numeros Lista de numeros a sumar.
	 * @return double resultado de la sumatoria.
	 */
	public double calcularSumatoria(List<Double> numeros){
		
		double sumatoria = 0;
		
		for (Double numero : numeros) {
			
			sumatoria += numero;
		}
		
		return sumatoria;
	}

}

package edu.uniandes.ecos.tsp.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de calcular el databinding externo de un programa java.
 * @author Aleja Chica am.chica10@uniandes.edu.co
 * @version 1.0
 */
public class CalculadorDataBindingExterno {

	/**
	 * Lista que contienen el total de los data binding externos por metodo.
	 */
	private List<Integer> totalDataBindingExterno;
	
	/**
	 * Metodo constructor
	 */
	public CalculadorDataBindingExterno(){
		
		totalDataBindingExterno = new ArrayList<Integer>();
	}

	/**
	 * Calcula el data binding externo de un metodo.
	 * @param lineasPorMetodo Lista con las lineas del metodo a analizar.
	 * @return Int total data binding externo por metodo.
	 */
	public int calculadorDataBindingExternoPorMetodo(List<String> lineasPorMetodo){
		
		int dataBindingExternoMetodo = 0;
		
		if(lineasPorMetodo !=  null && !lineasPorMetodo.isEmpty()){
			
			/*
			 * Se supone que la primera linea que viene en el arreglo es la firma del metodo.
			 */
			dataBindingExternoMetodo += Utilidades.determinarNumParametrosMetodo(lineasPorMetodo.get(0));
			lineasPorMetodo.remove(0);
			
			/*
			 * Se analizan las demas lineas del cuerpo del metodo
			 */
			for (String lineaDeCodigo : lineasPorMetodo) {
				
				if(Utilidades.validarLineaModificadoraVariables(lineaDeCodigo)){
					
					dataBindingExternoMetodo++;
				}
			}
		}
		
		/*
		 * Se acumula el total del data binding externo de este metodo, al total del programa.
		 */
		totalDataBindingExterno.add(dataBindingExternoMetodo);
		
		return dataBindingExternoMetodo;
	}

	/**
	 * Calcula en data binding externo total de un programa.
     * @return int el valor del calculo del data binding externo total de un programa.
	 */
	public int calcularDataBindingExternoTotal(){
		
		int totalDBExternoPrograma = 0;
		
		for (Integer totalMetodo : totalDataBindingExterno) {
			
			totalDBExternoPrograma += totalMetodo;
		}
		
		return totalDBExternoPrograma;
	}
	

}
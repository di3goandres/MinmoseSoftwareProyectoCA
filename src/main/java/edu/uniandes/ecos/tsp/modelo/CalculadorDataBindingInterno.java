package edu.uniandes.ecos.tsp.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase encargada de calcular la fuerza de union interna o DataBindingInterno.
 * @author Minmose
 * @version 1.0
 */
public class CalculadorDataBindingInterno {
	
	/**
	 * Expresion regular para determinar si la linea de codigo analizada modifica una variable.
	 */
	private static final String EXP_MODIFICADOR_VARIABLE = "[\\+\\*\\-\\/]+";
	
	/**
	 * Lista que contienen el total de los BD internos por metodo
	 */
	private List<Integer> totalDBInterno;

	/**
	 * Metodo constructor
	 */
	public CalculadorDataBindingInterno(){

		totalDBInterno = new ArrayList<Integer>();
	}

	/**
	 * Realiza el calculo de la  fuerza de union interna de un metodo.
	 * @param lineasPorMetodo Lista con las lineas del metodo a analizar
	 * @return Int total DBInterno por metodo.
	 */
	public int calcularFuerzaUnionInternaPorMetodo(List<String> lineasPorMetodo){
		
		int fuerzaUnionInterna = 0;
		
		if(lineasPorMetodo !=  null && !lineasPorMetodo.isEmpty()){
			
			/*
			 * Se supone que la primera linea que viene en el arreglo es la firma del metodo.
			 */
			fuerzaUnionInterna += determinarNumParametrosMetodo(lineasPorMetodo.get(0));
			lineasPorMetodo.remove(0);
			
			/*
			 * Se analizan las demas lineas del cuerpo del metodo
			 */
			for (String lineaDeCodigo : lineasPorMetodo) {
				
				if(validarLineaModificadoraVariables(lineaDeCodigo)){
					
					fuerzaUnionInterna++;
				}
			}
		}
		
		/*
		 * Se acumula el total del DBInterno de este metodo, al total del programa.
		 */
		totalDBInterno.add(fuerzaUnionInterna);
		
		return fuerzaUnionInterna;
	}

	/**
	 * Realiza el calculo de la  fuera de union interna total de un programa.
	 * @return DBInterno total del programa analizado.
	 */
	public int calcularFuerzaUnionInternaTotal(){
		
		int totalDBInternoPrograma = 0;
		
		for (Integer totalMetodo : totalDBInterno) {
			
			totalDBInternoPrograma += totalMetodo;
		}
		
		return totalDBInternoPrograma;
	}
	
	/**
	 * Metodo que permite determinar si en la linea de codigo analizada, se esta modificando una variable.
	 * @param lineaDeCodigo Linea de codigo analizada
	 * @return true si la linea de codigo analizada modifica una variable, false en caso contrario.
	 */
	private boolean validarLineaModificadoraVariables(String lineaDeCodigo){
		
		Pattern patron = Pattern.compile(EXP_MODIFICADOR_VARIABLE);
		Matcher matcher = patron.matcher(lineaDeCodigo);
		
		if (matcher.find() && (!lineaDeCodigo.startsWith("if") && !lineaDeCodigo.startsWith("else") 
				&& !lineaDeCodigo.startsWith("while") && !lineaDeCodigo.startsWith("switch"))) {
			
			return true;
			
		}else if (lineaDeCodigo.contains("return") || lineaDeCodigo.contains("set") || lineaDeCodigo.contains("new") || 
				(lineaDeCodigo.contains("=") && !lineaDeCodigo.contains("!") && !lineaDeCodigo.contains("<")
						&& !lineaDeCodigo.contains(">") && !lineaDeCodigo.contains("\"")
						&& !lineaDeCodigo.startsWith("if") && !lineaDeCodigo.startsWith("else") 
						&& !lineaDeCodigo.startsWith("while") && !lineaDeCodigo.startsWith("switch"))) {
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Metodo que determina el numero de parametros de un metodo analizado.
	 * @param lineaDeCodigo Linea de codigo analizada
	 * @return int Numero de parametros de un metodo.
	 */
	private int determinarNumParametrosMetodo(String lineaDeCodigo){
		
		int numParametros = 0;
		
		String param = lineaDeCodigo.substring(lineaDeCodigo.indexOf('('), lineaDeCodigo.indexOf(')'));
		
		if(param != null && param.length() > 1){
			
			String parametros[] = param.split(",");
			
			numParametros = parametros.length;
		}
		
		return numParametros;
	}

}
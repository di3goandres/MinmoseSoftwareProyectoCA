package edu.uniandes.ecos.tsp.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase con funcionalidades de utilidad para el analisis de metodos y clases.
 * @author Aleja Chica am.chica10@uniandes.edu.co
 * @version 1.0
 */
public class Utilidades {
	
	/**
	 * Expresion regular para determinar si la linea de codigo analizada modifica una variable.
	 */
	private static final String EXP_MODIFICADOR_VARIABLE = "[\\+\\*\\-\\/]+";
	
	/**
	 * Metodo que determina el numero de parametros de un metodo analizado.
	 * @param lineaDeCodigo Linea de codigo analizada
	 * @return int Numero de parametros de un metodo.
	 */
	public static int determinarNumParametrosMetodo(String lineaDeCodigo){
		
		int numParametros = 0;
		
		String param = lineaDeCodigo.substring(lineaDeCodigo.indexOf('('), lineaDeCodigo.indexOf(')'));
		
		if(param != null && param.length() > 1){
			
			String parametros[] = param.split(",");
			
			numParametros = parametros.length;
		}
		
		return numParametros;
	}
	
	/**
	 * Metodo que permite determinar si en la linea de codigo analizada, se esta modificando una variable.
	 * @param lineaDeCodigo Linea de codigo analizada
	 * @return true si la linea de codigo analizada modifica una variable, false en caso contrario.
	 */
	public static boolean validarLineaModificadoraVariables(String lineaDeCodigo){
		
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

}

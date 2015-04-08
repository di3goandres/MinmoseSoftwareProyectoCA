package edu.uniandes.ecos.tsp.modelo;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 09:00:08 p.m.
 */
public class CalculadorDataBindingInterno {


	public CalculadorDataBindingInterno(){

	}


	/**
	 * Realiza el calculo de la  fuera de union interna de un metodo.
	 * 
	 * @param numeroParametros
	 */
	public int calcularFuerzaUnionInternaPorMetodo(List<String> lineasPorMetodo){
		
		int fuerzaUnionInterna = 0;
		
		if(lineasPorMetodo !=  null && !lineasPorMetodo.isEmpty()){
			
			for (String lineaDeCodigo : lineasPorMetodo) {
				
				lineaDeCodigo = lineaDeCodigo.trim().toLowerCase();
				
				if(validarLineaModificadoraVariables(lineaDeCodigo)){
					
					fuerzaUnionInterna++;
				}
			}
		}
		
		/*
		 * Se supone que la primera linea que viene en el arreglo es la firma del metodo.
		 */
		fuerzaUnionInterna += determinarNumParametrosMetodo(lineasPorMetodo.get(0));
		
		return fuerzaUnionInterna;
	}

	/**
	 * Realiza el calculo de la  fuera de union interna total de un programa.
	 * 
	 * @param numeroParametros
	 */
	public int calcularFuerzaUnionInternaTotal(int numeroParametros){
		return 0;
	}
	
	private boolean validarLineaModificadoraVariables(String lineaDeCodigo){
		
		//TODO: crear expresion regular
		String regex = "^(public|private|protected)\\s[\\w\\<\\>\\s]+\\({1}";
		Pattern patron = Pattern.compile(regex);
		
		Matcher matcher = patron.matcher(lineaDeCodigo);
		
		if (matcher.find()) {
			
			return true;
		}
		
		return false;
	}
	
	private int determinarNumParametrosMetodo(String lineaDeCodigo){
		
		int numParametros = 0;
		
		//TODO: completar la implementacion
		
		return numParametros;
	}

}
package edu.uniandes.ecos.tsp.modelo;

/**
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 09:00:08 p.m.
 */
public class AnalizadorFuncional implements IAnalizadorFuncional {

	/**
	 * Representa la complejidad ciclomatica del metodo analizado.
	 */
	private int totalComplejidadCiclomaticaMetodo;
	/**
	 * Representa la complejidad ciclomatica total del programa analizado.
	 */
	private int totalComplejidadCiclomatica;
	/**
	 * Representa la fuerza de union interna del metodo analizado.
	 */
	private int totalFuerzaUnionInternaMetodo;
	/**
	 * Representa la fuerza de union interna total del programa analizado.
	 */
	private int totalFuerzaUnionInterna;

	public AnalizadorFuncional(){

	}


	/**
	 * Realiza el analisis de las caracteristicas de un programa.
	 */
	public String realizarAnalisis(){
		return "";
	}

	public String mostrarReporte(){
		return "";
	}

}
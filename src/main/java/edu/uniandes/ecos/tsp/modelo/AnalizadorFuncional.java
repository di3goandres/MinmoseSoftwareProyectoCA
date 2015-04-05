package edu.uniandes.ecos.tsp.modelo;

/**
 * @author Minmose
 * @version 1.0
 * @created 05-abr-2015 09:11:50 a.m.
 */
public class AnalizadorFuncional implements IAnalizadorFuncional {

	/**
	 * Representa el total del acoplamiento del metodo analizado.
	 */
	private int totalAcoplamientoMetodo;
	/**
	 * Representa el total del acoplamiento del programa analizado.
	 */
	private int totalAcoplamiento;
	/**
	 * Representa la fuerza de union interna del metodo analizado.
	 */
	private int totalFuerzaUnionInternaMetodo;
	/**
	 * Representa la fuerza de union interna total del programa analizado.
	 */
	private int totalFuerzaUnionInterna;
	
	/**
	 * Representa el total del databiding externo del programa analizado.
	 */
	private int totalDataBindingExterno;
	
	/**
	 * Calculador del databinding externo
	 */
	private CalculadorDataBindingExterno calculadorDataBindingExterno;
	
	/**
	 * Calculador de acoplamiento
	 */
	private CalculadorAcoplamiento calculadorAcoplamiento;
	
	/**
	 *Calculador de fuerza de union interna. 
	 */
	private CalculadorDataBindingInterno calculadorDataBindingInterno;

	/**
	 * Metodo constructor.
	 */
	public AnalizadorFuncional(){
		
		calculadorDataBindingInterno = new CalculadorDataBindingInterno();
		calculadorDataBindingExterno = new CalculadorDataBindingExterno();
		calculadorAcoplamiento = new CalculadorAcoplamiento();
	}

	
	/**
	 * Realiza el analisis de las caracteristicas de un programa.
	 */
	public String realizarAnalisis(){
		
		String resultado = "";
		
		/*
		 * Se calcula la fuerza de union interna
		 */
		//TODO: completar la implementacion
		totalFuerzaUnionInternaMetodo = calculadorDataBindingInterno.calcularFuerzaUnionInternaPorMetodo(0);
		totalFuerzaUnionInterna = calculadorDataBindingInterno.calcularFuerzaUnionInternaTotal(0);
		
		resultado = "El resultado de la fuerza de union interna por metodo es: " + totalFuerzaUnionInternaMetodo 
				+ "El resultado de la fuerza de union interna total del programa es: " + totalFuerzaUnionInterna;
		
		return resultado;
	}

	public String mostrarReporte(){
		return "";
	}

}
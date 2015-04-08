package edu.uniandes.ecos.tsp.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Minmose
 * @version 1.0
 * @created 05-abr-2015 09:11:50 a.m.
 */
public class AnalizadorFuncional implements IAnalizadorFuncional {

	public static final String RUTA_ARCHIVO = "src/site/resources";
	
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
	 * Objeto que realiza el conteo de loc de una clase.
	 */
	private ContadorLOC contadorLOC;

	/**
	 * Metodo constructor.
	 */
	public AnalizadorFuncional(){
		
		calculadorDataBindingInterno = new CalculadorDataBindingInterno();
		calculadorDataBindingExterno = new CalculadorDataBindingExterno();
		calculadorAcoplamiento = new CalculadorAcoplamiento();
		contadorLOC = new ContadorLOC();
	}

	
	/**
	 * Realiza el analisis de las caracteristicas de un programa.
	 * @return String con el resultado del analisis.
	 * @throws IOException Excepcion al realizar la lectura de los archivos java que seran analizados.
	 */
	public String realizarAnalisis() throws IOException{
		
		StringBuilder resultado = new StringBuilder();
		
		contadorLOC.contarLineasDeCodigo(RUTA_ARCHIVO);
		
		Map<String, ArrayList<String>> mapaMetodo= contadorLOC.getLineasDeMetodos();
		
		/*
		 * Se calcula la fuerza de union interna por metodo
		 */
		//TODO: completar la implementacion
		for (String nombreMetodo : mapaMetodo.keySet()) {
			
			totalFuerzaUnionInternaMetodo = calculadorDataBindingInterno.calcularFuerzaUnionInternaPorMetodo(mapaMetodo.get(nombreMetodo));
			
			resultado.append("El resultado de la fuerza de union interna del metodo ");
			resultado.append(nombreMetodo);
			resultado.append(" es: ");
			resultado.append(totalFuerzaUnionInternaMetodo); 
		}
		
		totalFuerzaUnionInterna = calculadorDataBindingInterno.calcularFuerzaUnionInternaTotal(0);
		
		resultado.append("El resultado de la fuerza de union interna total del programa es: ");
		resultado.append(totalFuerzaUnionInterna);
		
		return resultado.toString();
	}

	public String mostrarReporte(){
		return "";
	}

}
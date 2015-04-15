package edu.uniandes.ecos.tsp.modelo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de realizar en analisis funcional de un programa.
 * @author Minmose
 * @version 1.0
 */
public class AnalizadorFuncional implements IAnalizadorFuncional {

	/**
	 * Constante con la ruta del archivo
	 */
	public static final String RUTA_ARCHIVO = "src/site/resources/";
	
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
	private ExploradorDirectorios exploradorDirectorios;

	/**
	 * Metodo constructor.
	 */
	public AnalizadorFuncional(){
		
		calculadorDataBindingInterno = new CalculadorDataBindingInterno();
		calculadorDataBindingExterno = new CalculadorDataBindingExterno();
		calculadorAcoplamiento = new CalculadorAcoplamiento();
		exploradorDirectorios = new  ExploradorDirectorios();
	}

	/**
	 * Realiza el analisis de las caracteristicas de un programa.
	 * @return String con el resultado del analisis.
	 * @throws IOException Excepcion al realizar la lectura de los archivos java que seran analizados.
	 */
	public String realizarAnalisis() throws IOException{
		
		exploradorDirectorios.enviarAContadorLOC(RUTA_ARCHIVO);
		
		Map<String, List<String>> mapaMetodo= exploradorDirectorios.getLineasDeMetodos();
		
		StringBuilder resultado = new StringBuilder();
		
		/*
		 * Se calcula la fuerza de union interna por metodo
		 */
		for (String nombreMetodo : mapaMetodo.keySet()) {
			
			totalFuerzaUnionInternaMetodo = calculadorDataBindingInterno.calcularFuerzaUnionInternaPorMetodo(mapaMetodo.get(nombreMetodo));
			
			resultado.append("El resultado de la fuerza de union interna del metodo ");
			resultado.append(nombreMetodo);
			resultado.append(" es: ");
			resultado.append(totalFuerzaUnionInternaMetodo); 
			resultado.append("\n"); 
		}
		
		totalFuerzaUnionInterna = calculadorDataBindingInterno.calcularFuerzaUnionInternaTotal();
		
		resultado.append("El resultado de la fuerza de union interna total del programa es: ");
		resultado.append(totalFuerzaUnionInterna);
		
		return resultado.toString();
	}

	/* (non-Javadoc)
	 * @see edu.uniandes.ecos.tsp.modelo.IAnalizadorFuncional#mostrarReporte()
	 */
	public String mostrarReporte(){
		//TODO: implementar en el ciclo 2
		return "";
	}

}
package edu.uniandes.ecos.tsp.modelo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de realizar en analisis funcional de un programa.
 * @author Aleja Chica am.chica10@uniandes.edu.co
 * @version 1.0
 */
public class AnalizadorFuncional implements IAnalizadorFuncional {

	/**
	 * Constante con la ruta del archivo
	 */
	public static final String RUTA_ARCHIVO = "src/site/resources/";
	
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
	 * Representa el total del databiding externo del metodo analizado.
	 */
	private int totalDataBindingExternoMetodo;
	
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
         * Objeto que realiza la gestion de reporte.
         */
        private Reporte reporte;
	/**
	 * Metodo constructor.
	 */
	public AnalizadorFuncional(){
		
		calculadorDataBindingInterno = new CalculadorDataBindingInterno();
		calculadorDataBindingExterno = new CalculadorDataBindingExterno();
		calculadorAcoplamiento = new CalculadorAcoplamiento();
		exploradorDirectorios = new  ExploradorDirectorios();
                reporte = new Reporte();
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
                String databindingExterno = analizarDataBindingExterno(mapaMetodo);
		this.reporte.agregarReporte("Data Binding Externo", databindingExterno);
		resultado.append(databindingExterno);
		resultado.append("\n");
		
		return resultado.toString();
	}
        

	/**
	 * Metodo que realiza en analisis de databinding externo.
	 * @param mapaMetodo Mapa con el metodo y sus lineas.
	 * @return String con el resultado de analisis.
	 */
	private String analizarDataBindingExterno(Map<String, List<String>> mapaMetodo) {
		
		StringBuilder resultado = new StringBuilder();
		
		/*
		 * Se calcula la fuerza de union interna por metodo
		 */
		for (String nombreMetodo : mapaMetodo.keySet()) {
			
			totalDataBindingExternoMetodo = calculadorDataBindingExterno.calculadorDataBindingExternoPorMetodo(mapaMetodo.get(nombreMetodo));
			
			resultado.append("El resultado del data binding externo del metodo ");
			resultado.append(nombreMetodo);
			resultado.append(" es: ");
			resultado.append(totalDataBindingExternoMetodo); 
			resultado.append("\n"); 
		}
		
		totalDataBindingExterno = calculadorDataBindingExterno.calcularDataBindingExternoTotal();
		
		resultado.append("El resultado del data binding externo total del programa es: ");
		resultado.append(totalDataBindingExterno);
		
		return resultado.toString();
	}
	
	/**
	 * Metodo que realiza en analisis de databinding interno.
	 * @param mapaMetodo Mapa con el metodo y sus lineas.
	 * @return String con el resultado de analisis.
	 */
	private String analizarDataBindingInterno(Map<String, List<String>> mapaMetodo) {
		
		StringBuilder resultado = new StringBuilder();
		
		//TODO: implementar en el ciclo 2
		
		return resultado.toString();
	}
	
	/**
	 * Este metodo permite calcular el acoplamiento de un programa.
	 * La relacin de acoplamiento / fuerza es la relacin de los numeros calculados 
	 * en el data binding externo y el data binding interno.
	 * Desde una perspectiva de calidad, valores bajos (<= 0,5) de esta relaci�n 
	 * se consideran mejores que los altos (> 0.5).
	 * @return String con el resultado de analisis.
	 */
	private String calcularAcoplamiento(){
		
		StringBuilder resultado = new StringBuilder();

		// TODO: implementar en el ciclo 2

		return resultado.toString();
	}

	/**
	 * Metodo encargado de mostrar el reporte del analisis funcional
	 * @return String Resultado del analisis funcional
	 */
	public String mostrarReporte(){
		return this.reporte.mostrarReporte();
	}

}
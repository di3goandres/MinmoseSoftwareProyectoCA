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
	private double totalAcoplamiento;
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
		
		StringBuilder resultado = new StringBuilder();
		
		exploradorDirectorios.enviarAContadorLOC(RUTA_ARCHIVO);
		
		/*
		 * Data Binding externo
		 */
		Map<String, List<String>> mapaMetodo= exploradorDirectorios.getLineasDeMetodos();
        String databindingExterno = analizarDataBindingExterno(mapaMetodo);
        
		this.reporte.agregarReporte("Data Binding Externo", databindingExterno);
		resultado.append(databindingExterno);
		resultado.append("\n");
		
		/*
		 * Data Binding interno
		 */
		List<String> variablesGlobales = exploradorDirectorios.getVariablesGlobales();
		String databindingInterno = analizarDataBindingInterno(mapaMetodo, variablesGlobales);
		
		this.reporte.agregarReporte("Data Binding Interno", databindingInterno);
		resultado.append("--------------------------------------------------------------------");
		resultado.append("\n");
		resultado.append(databindingInterno);
		resultado.append("\n");
		
		/*
		 * Acoplamiento
		 */
		String acoplamiento = calcularAcoplamiento();
		this.reporte.agregarReporte("Relacion fuerza acoplamiento", acoplamiento);
		resultado.append("--------------------------------------------------------------------");
		resultado.append("\n");
		resultado.append(acoplamiento);
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
		 * Se calcula el data binding externo por metodo
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
	 * @param variablesGlobales Arreglo con las variables globales de un programa.
	 * @return String con el resultado de analisis.
	 */
	private String analizarDataBindingInterno(Map<String, List<String>> mapaMetodo, List<String> variablesGlobales) {
		
		StringBuilder resultado = new StringBuilder();
		
		/*
		 * Data Binding interno por metodo
		 */
		for (String nombreMetodo : mapaMetodo.keySet()) {
			
			totalFuerzaUnionInternaMetodo = calculadorDataBindingInterno.calcularFuerzaUnionInternaPorMetodo(nombreMetodo);
			
			resultado.append("El resultado del data binding interno del metodo ");
			resultado.append(nombreMetodo);
			resultado.append(" es: ");
			resultado.append(totalFuerzaUnionInternaMetodo); 
			resultado.append("\n"); 
		}
		
		/*
		 * Data Binding interno total de programa
		 */
		totalFuerzaUnionInterna = calculadorDataBindingInterno.calcularFuerzaUnionInternaTotal(variablesGlobales);
		
		resultado.append("El resultado del data binding interno total del programa es: ");
		resultado.append(totalFuerzaUnionInterna);
		
		return resultado.toString();
	}
	
	/**
	 * Este metodo permite calcular el acoplamiento de un programa.
	 * La relacin de acoplamiento / fuerza es la relacin de los numeros calculados 
	 * en el data binding externo y el data binding interno.
	 * Desde una perspectiva de calidad, valores bajos (<= 0,5) de esta relacion 
	 * se consideran mejores que los altos (> 0.5).
	 * @return String con el resultado de analisis.
	 */
	private String calcularAcoplamiento(){
		
		StringBuilder resultado = new StringBuilder();

		totalAcoplamiento = calculadorAcoplamiento.calcularAcoplamientoTotal(totalFuerzaUnionInterna, totalDataBindingExterno);
		
		resultado.append("La relacion de fuerza de acoplamiento del programa es: ");
		resultado.append(totalAcoplamiento);
		
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
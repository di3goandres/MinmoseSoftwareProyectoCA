package edu.uniandes.ecos.tsp.modelo;

import java.io.IOException;

/**
 * Interface para el analizador funcional
 * @author Aleja Chica am.chica10@uniandes.edu.co
 * @version 1.0
 */
public interface IAnalizadorFuncional {

	/**
	 * Realiza el analisis de las caracteristicas de un programa.
	 * @return String con el resultado del analisis.
	 * @throws IOException Excepcion al realizar la lectura de los archivos java que seran analizados.
	 */
	String realizarAnalisis() throws IOException;

	/**
	 * Metodo encargado de mostrar el reporte del analisis funcional
	 * @return String Resultado del analisis funcional
	 */
	String mostrarReporte();

}
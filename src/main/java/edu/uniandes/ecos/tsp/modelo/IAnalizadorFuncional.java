package edu.uniandes.ecos.tsp.modelo;

import java.io.IOException;

/**
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 09:00:08 p.m.
 */
public interface IAnalizadorFuncional {


	/**
	 * Realiza el analisis de las caracteristicas de un programa.
	 * @return String con el resultado del analisis.
	 * @throws IOException Excepcion al realizar la lectura de los archivos java que seran analizados.
	 */
	String realizarAnalisis() throws IOException;

	String mostrarReporte();

}
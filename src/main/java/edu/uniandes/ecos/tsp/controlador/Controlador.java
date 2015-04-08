package edu.uniandes.ecos.tsp.controlador;
import java.io.IOException;

import edu.uniandes.ecos.tsp.modelo.AnalizadorFuncional;
import edu.uniandes.ecos.tsp.modelo.IAnalizadorFuncional;

/**
 * Clase que implementa el controlador del patron MVC.
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 09:00:08 p.m.
 */
public class Controlador {

	/**
	 * Analizador funcional de programas.
	 */
	private IAnalizadorFuncional analizadorFuncional;

	/**
	 * Metodo constructor
	 */
	public Controlador(){
		
		analizadorFuncional = new AnalizadorFuncional();
	}

	/**
	 * Metodo encargado de invocar el analisis funcional de un programa.
	 * @return String Resultado del analisis.
	 * @throws IOException Excepcion al realizar la lectura de los archivos java que seran analizados.
	 */
	public String realizarAnalisisFuncional() throws IOException{
		
		return analizadorFuncional.realizarAnalisis();
	}
	

}
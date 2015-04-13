package edu.uniandes.ecos.tsp.vista;
import java.io.IOException;

import edu.uniandes.ecos.tsp.controlador.Controlador;

/**
 * Clase que implementa la vista en el patron MVC.
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 08:57:23 p.m.
 */
public class VistaTexto {

	/**
	 * Metodo para ejecutar el programa
	 * @param args Argumentos para la ejecucion del programa,
	 */
	public static void main(String[] args) {
		
		try {
			
			Controlador controlador = new Controlador();
			String resultado = controlador.realizarAnalisisFuncional();
			
			System.out.println(resultado);
			
		} catch (IOException e) {

			System.out.println("Ocurrio un error al leer los archivos java para su analisis.");
		}
	}

}
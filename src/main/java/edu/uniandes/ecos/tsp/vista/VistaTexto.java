package edu.uniandes.ecos.tsp.vista;
import edu.uniandes.ecos.tsp.controlador.Controlador;

/**
 * Clase que implementa la vista en el patron MVC.
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 08:57:23 p.m.
 */
public class VistaTexto {

	public static void main(String[] args) {
		
		Controlador controlador = new Controlador();
		String resultado = controlador.realizarAnalisisFuncional();
		
		System.out.println(resultado);
	}

}
package edu.uniandes.ecos.psp21.vista;

import edu.uniandes.ecos.psp21.controlador.Controlador;

/**
 *Clase que permite probar el programa psp2.1 por consola. 
 *@author Aleja Chica
 */
public class VistaConsola {


	/**
	 * Metodo que permite ejecutar una aplicacion java
	 * @param args argumentos de java
	 */
	public static void main(String[] args) {

		test1();
		test2();
		test3();
	}

	/**
	 * Metodo que permite ejecutar el test1
	 */
	public static void test1() {


		double dof = 6;
		int numSeg = 10;
		double limiteSuperior = 1.0;
		double valorEsperado = 0.20;
		
		Controlador c = new Controlador();
		String resultado = c.realizarCalculos(dof, numSeg, limiteSuperior, valorEsperado);
		
		System.out.println("Test 1, x = " + resultado);
	}

	/**
	 * Metodo que permite ejecutar el test2
	 */
	public static void test2() {

		double dof = 15;
		int numSeg = 10;
		double limiteSuperior = 1.0;
		double valorEsperado = 0.45;
		
		Controlador c = new Controlador();
		String resultado = c.realizarCalculos(dof, numSeg, limiteSuperior, valorEsperado);
		
		System.out.println("Test 2, x = " + resultado);
	}
	
	/**
	 * Metodo que permite ejecutar el test3
	 */
	public static void test3() {

		double dof = 4;
		int numSeg = 10;
		double limiteSuperior = 1.0;
		double valorEsperado = 0.495;
		
		Controlador c = new Controlador();
		String resultado = c.realizarCalculos(dof, numSeg, limiteSuperior, valorEsperado);
		
		System.out.println("Test 3, x = " + resultado);
	}

}

/**
 * 
 */
package edu.uniandes.ecos.psp21.modelo;

/**
 * Clase que permite calcular el valor de la distribucion T.
 * @author Aleja Chica
 *
 */
public class DistribucionT {

	/**
	 * Metodo que permite calcular el valor de la distribucion T.
	 * @param dof Grados de libertad
	 * @param x Valor de X
	 * @return double con el resultado del calculo de la distribucion T.
	 */
	public double calcularDistribucionT(double dof, double x) {

		double dividendo;
		double factor1;
		double factor2;
		double divisor;
		double multiplicador;
		double resultado;

		double dividendoGamma = (dof + 1) / 2f;
		double divisorGamma = dof / 2f;

		if (dividendoGamma % 1 == 0) {

			dividendo = funcionGammaEnteros((int) dividendoGamma);

		} else {

			dividendo = funcionGammaNoEnteros(dividendoGamma);
		}

		factor1 = Math.pow((dof * Math.PI), 0.5);

		if (divisorGamma % 1 == 0) {

			factor2 = funcionGammaEnteros((int) divisorGamma);

		} else {

			factor2 = funcionGammaNoEnteros(divisorGamma);
		}

		divisor = factor1 * factor2;

		multiplicador = Math
				.pow(1 + ((Math.pow(x, 2)) / dof), -((dof + 1) / 2));

		resultado = (dividendo / divisor) * multiplicador;

		return resultado;
	}

	/**
	 * Metodo que permite calcular el valor de la funcion gamma cuando el parametro es entero.
	 * @param x Parametro de la funcion gamma.
	 * @return long con el resultado del calculo de la funcion gamma para numero entero.
	 */
	public long funcionGammaEnteros(int x) {
		
		long factorial = 1;
		x -= 1;

		for (int i = 1; i <= x; i++) {

			factorial *= i;
		}
		
		return factorial;
	}

	/**
	 * Metodo que permite calcular el valor de la funcion gamma cuando el parametro no es entero.
	 * @param x Parametro de la funcion gamma.
	 * @return double con el resultado del calculo de la funcion gamma para numero no entero.
	 */
	public double funcionGammaNoEnteros(double x) {

		double factorial = 1;

		x -= 1;
		
		while (x >= 0.5) {

			factorial *= x;
			x -= 1;
		}
		
		factorial *= Math.sqrt(Math.PI);

		return factorial;
	}

}

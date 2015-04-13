package edu.uniandes.ecos.psp21.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite calcular una integral numerica.
 * @author Aleja Chica
 *
 */
public class IntegracionNumerica {

	/**
	 * El ancho del segmento
	 */
	private double w;

	/**
	 * Numero inicial de segmentos, un numero par.
	 */
	private int numSeg;

	/**
	 * Grados de libertad
	 */
	private double dof;

	/**
	 * Lista de valores de X
	 */
	private List<Double> valoresX;

	/**
	 * Calculadora de la distribucion T.
	 */
	private DistribucionT calculadoraDistribucionT;

	/**
	 * Calculadora de la sumatoria
	 */
	private Sumatoria sumatoria;

	/**
	 * Sumatoria de los valores de X
	 */
	private List<Double> numerosSum;

	/**
	 * Metodo constructor.
	 * @param dof Grados de libertad
     * @param numSeg Numero de segmentos
     * @param valorX Valor de X
	 */
	public IntegracionNumerica(double dof, int numSeg, double valorX) {

		this.numSeg = numSeg;
		this.dof = dof;
		calculadoraDistribucionT = new DistribucionT();
		sumatoria = new Sumatoria();
		valoresX = new ArrayList<Double>();
		numerosSum = new ArrayList<Double>();
		
		w = valorX / numSeg;
		valoresX = calcularValoresX(valorX);
	}

	/**
	 * Metodo que permite calcular una integral numerica
	 * @return double con el resultado del calculo de la integral.
	 */
	public double calcularIntegral() {

		double integral = 0;
		int i = 0;

		for (Double x : valoresX) {

			if (i == 0) {

				integral = calcularFx(x);
				numerosSum.add(integral);
				
			} else if (i < valoresX.size() - 1) {

				integral = calcularMultiplicador(i) * calcularFx(x);
				numerosSum.add(integral);

			} else if (i == valoresX.size() - 1) {

				integral = calcularFx(x);
				numerosSum.add(integral);
			}
			
			i++;
		}

		return sumatoria.calcularSumatoria(numerosSum) * (w / 3) ;
	}

	/**
	 * Metodo que permite calcular el multiplicador de F(x).
	 * @param i numero par o impar que permite determinar el valor del multiplicador.
	 * @return double con el valor del multiplicador.
	 */
	public double calcularMultiplicador(int i) {

		double multiplicador;

		if (i == 0) {

			multiplicador = 1;

		} else if (i % 2 == 0) {

			multiplicador = 2;

		} else {

			multiplicador = 4;
		}

		return multiplicador;
	}

	/**
	 * Metodo que permite calcular F(x).
	 * @param x Parametro de la funcion F(x).
	 * @return double con el valor de la funcion F(x).
	 */
	public double calcularFx(double x) {

		return calculadoraDistribucionT.calcularDistribucionT(dof, x);
	}

	/**
	 * Metodo que permite calcular los valores de X para la integral.
	 * @param x Valor inicial de X.
	 * @return List<Double> con los valores de X para la integral.
	 */
	private List<Double> calcularValoresX(double x) {

		double tempX = 0;
		valoresX.add(tempX);

		for (int i = 1; i <= numSeg; i++) {

			tempX += w;

			valoresX.add(tempX);
		}

		return valoresX;
	}

}

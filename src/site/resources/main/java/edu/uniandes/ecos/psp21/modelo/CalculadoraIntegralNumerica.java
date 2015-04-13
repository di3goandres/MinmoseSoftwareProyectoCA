package edu.uniandes.ecos.psp21.modelo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import edu.uniandes.ecos.psp21.modelo.IntegracionNumerica;

/**
 * Clase que permite calcular las integrales numericas, hasta que:
 * integralInicial - integralDefinitiva < E
 * 
 * @author Aleja Chica
 *
 */
public class CalculadoraIntegralNumerica {

	/**
	 * valor del error aceptable
	 */
	public static final Double E = 0.00001;

	/**
	 * Metodo que permite realizar el calculo de la integral numerica.
	 * @param dof Grados de libertad
     * @param numSeg Numero de segmentos
     * @param valorX Valor de X
	 * @return double con el resultado del calculo de la integral.
	 */
	public double calcularIntegral(double dof, int numSeg, double valorX) {
		
		IntegracionNumerica in = new IntegracionNumerica(dof, numSeg, valorX);
		double integralInicial = in.calcularIntegral();
		
		numSeg *= 2;
		IntegracionNumerica in2 = new IntegracionNumerica(dof, numSeg, valorX);
		double integralDefinitiva = in2.calcularIntegral();
		
		while((integralInicial - integralDefinitiva) > E){
			
			integralInicial = integralDefinitiva;
			numSeg *= 2;
			
			in2 = new IntegracionNumerica(dof, numSeg, valorX);
			integralDefinitiva = in2.calcularIntegral();
		}
		
		return integralDefinitiva;
	}

	/**
	 * Metodo que permite encontrar el valor de x superior, con el cual se calcula la integral.
	 * @param dof Grados de libertas.
	 * @param numSeg Numero de segmentos.
	 * @param limiteSuperior Valor inicial de X.
	 * @param valorEsperado Valor resultado esperado de la integral.
	 * @return double con el valor de X
	 */
	public double calcularIntegralEncontrarX(double dof, int numSeg, double limiteSuperior, double valorEsperado) {
		
		boolean ajustarX = true;
		double d = 0.5;

		DecimalFormat formatoDecimal = new DecimalFormat("#.#####");
		DecimalFormatSymbols formatoDecimalSym = formatoDecimal.getDecimalFormatSymbols();
		formatoDecimalSym.setDecimalSeparator('.');
		formatoDecimal.setDecimalFormatSymbols(formatoDecimalSym);

		double integralInicial = Double.valueOf(formatoDecimal.format(calcularIntegral(dof, numSeg, limiteSuperior)));

		double integralDefinitiva = 0;

		double diferenciaIntegrales = Math.abs(integralInicial - valorEsperado);

		if (diferenciaIntegrales < E) {

			return integralInicial;

		} else {

			while (diferenciaIntegrales > E) {

				diferenciaIntegrales = integralDefinitiva - valorEsperado;

				if (ajustarX && (diferenciaIntegrales < E)) {

					d = ajustarD(d, limiteSuperior);
					
				} else if (!ajustarX && (diferenciaIntegrales > E)) {

					d = ajustarD(d, limiteSuperior);
				}

				if (integralDefinitiva > valorEsperado) {

					ajustarX = true;

				} else {

					ajustarX = false;
				}
				
				limiteSuperior = ajustarValorX(ajustarX, limiteSuperior, d);

				integralInicial = integralDefinitiva;
				integralDefinitiva = Double.valueOf(formatoDecimal
						.format(calcularIntegral(dof, numSeg, limiteSuperior)));

				diferenciaIntegrales = Math.abs(integralDefinitiva - valorEsperado);
			}
		}

		limiteSuperior = Double.valueOf(formatoDecimal.format(limiteSuperior));
		return limiteSuperior;
	}
	
	/**
	 * Metodo que permite ajustar el valor de X si es necesario.
	 * @param ajustarX indica si el valor de X debe ser ajustado.
	 * @param limiteSuperior valor a ajustar.
	 * @param d valor d.
	 * @return double con el valor de X.
	 */
	private double ajustarValorX(boolean ajustarX, double limiteSuperior, double d){
		
		if (ajustarX) {

			limiteSuperior -= d;

		} else {

			limiteSuperior += d;
		}

		return limiteSuperior;
	}
	
	/**
	 * Metodo que permite ajustar el valor de d, si es necesario.
	 * @param d Valor a ajustar.
	 * @param limiteSuperior Valor de X.
	 * @return double con el valor de d.
	 */
	private double ajustarD(double d, double limiteSuperior){
		
		if (limiteSuperior != 1.0) {

			d /= 2;
		}

		return d;
	}


}

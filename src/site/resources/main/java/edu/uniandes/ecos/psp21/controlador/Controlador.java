package edu.uniandes.ecos.psp21.controlador;

import edu.uniandes.ecos.psp21.modelo.CalculadoraIntegralNumerica;

/**
 *Clase que sirve de controlador en el patron MVC 
 * @author Aleja Chica
 */
public class Controlador {
    
    /**
     * Metodo que permite encontrar el valor de x superior, con el cual se calcula la integral.
     * @param dof Grados de libertad
     * @param numSeg Numero de segmentos
     * @param limiteSuperior valor inicial de X como limite superior de la integral
     * @param valorEsperado Valor esperado de la integral
     * @return String con el valor encontrado para X.
     */
    public String realizarCalculos(double dof, int numSeg, double limiteSuperior, double valorEsperado){
        
		Double resultado = 0.0;

		CalculadoraIntegralNumerica calc = new CalculadoraIntegralNumerica();

		resultado = calc.calcularIntegralEncontrarX(dof, numSeg, limiteSuperior, valorEsperado);

		return resultado.toString();
    }
    
     
}

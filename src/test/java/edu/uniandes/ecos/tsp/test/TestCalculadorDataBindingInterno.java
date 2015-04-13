/**
 * 
 */
package edu.uniandes.ecos.tsp.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.uniandes.ecos.tsp.modelo.CalculadorDataBindingInterno;

/**
 * @author ingeneo
 *
 */
public class TestCalculadorDataBindingInterno {
	
	/**
	 * Calculador de fuerza de union interna. 
	 */
	CalculadorDataBindingInterno  calculador;
	
	/**
	 * Lineas del metodo a probar
	 */
	private List<String> lineasPorMetodo1;
	
	/**
	 * Lineas del metodo a probar
	 */
	private List<String> lineasPorMetodo2;
	
	/**
	 * Metodo que permite configurar los datos de prueba
	 */
	@Before
	public void configurarDatos(){
		
		calculador = new CalculadorDataBindingInterno();
		
		lineasPorMetodo1 = new ArrayList<String>();
		lineasPorMetodo1.add("public double calcularIntegral(double dof, int numSeg, double valorX) {");
		lineasPorMetodo1.add("IntegracionNumerica in = new IntegracionNumerica(dof, numSeg, valorX);");
		lineasPorMetodo1.add("double integralInicial = in.calcularIntegral();");
		lineasPorMetodo1.add("numSeg *= 2;");
		lineasPorMetodo1.add("IntegracionNumerica in2 = new IntegracionNumerica(dof, numSeg, valorX);");
		lineasPorMetodo1.add("double integralDefinitiva = in2.calcularIntegral();");
		lineasPorMetodo1.add("while((integralInicial - integralDefinitiva) > E){");
		lineasPorMetodo1.add("integralInicial = integralDefinitiva;");
		lineasPorMetodo1.add("numSeg *= 2;");
		lineasPorMetodo1.add("in2 = new IntegracionNumerica(dof, numSeg, valorX);");
		lineasPorMetodo1.add("integralDefinitiva = in2.calcularIntegral();");
		lineasPorMetodo1.add("}");
		lineasPorMetodo1.add("return integralDefinitiva;");
		lineasPorMetodo1.add("}");
		
		lineasPorMetodo2 = new ArrayList<String>();
		lineasPorMetodo2.add("private double ajustarValorX(boolean ajustarX, double limiteSuperior, double d){");
		lineasPorMetodo2.add("if (ajustarX) {");
		lineasPorMetodo2.add("limiteSuperior -= d;");
		lineasPorMetodo2.add("} else {");
		lineasPorMetodo2.add("limiteSuperior += d;");
		lineasPorMetodo2.add("}");
		lineasPorMetodo2.add("return limiteSuperior;");
		lineasPorMetodo2.add("}");
	}

	/**
	 * Test method for {@link edu.uniandes.ecos.tsp.modelo.CalculadorDataBindingInterno#calcularFuerzaUnionInternaPorMetodo(int)}.
	 */
	@Test
	public void testCalcularFuerzaUnionInternaPorMetodo() {
		
		int total = calculador.calcularFuerzaUnionInternaPorMetodo(lineasPorMetodo1);
		
		assertEquals(13, total);
	}

	/**
	 * Test method for {@link edu.uniandes.ecos.tsp.modelo.CalculadorDataBindingInterno#calcularFuerzaUnionInternaTotal(int)}.
	 */
	@Test
	public void testCalcularFuerzaUnionInternaTotal() {
		
		calculador.calcularFuerzaUnionInternaPorMetodo(lineasPorMetodo1);
		calculador.calcularFuerzaUnionInternaPorMetodo(lineasPorMetodo2);
		
		int total = calculador.calcularFuerzaUnionInternaTotal();
		
		assertEquals(19, total);
	}

}

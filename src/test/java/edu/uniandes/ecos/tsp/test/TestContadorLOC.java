package edu.uniandes.ecos.tsp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import edu.uniandes.ecos.tsp.modelo.ContadorLOC;

public class TestContadorLOC {

	private ContadorLOC contador;
	
	private String getRutaArchivoTexto(){
		ClassLoader classLoader = this.getClass().getClassLoader();
    	return classLoader.getResource("LOCCountTest.txt").getFile();
	}
	
	private String getRutaCodigoFuente(){
		ClassLoader classLoader = this.getClass().getClassLoader();
    	return classLoader.getResource("Test.java").getFile();
	}
	
	@Test
	public void testContarLineasDeCodigo() {
		try {
			contador = new ContadorLOC();
			//Archivo de Texto
			contador.contarLineasDeCodigo(getRutaArchivoTexto());
			assertEquals(12, contador.getLineasTotales());
			assertEquals(6, contador.getLineasEfectivas());
			
			contador = new ContadorLOC();
			//Archivo Java
			contador.contarLineasDeCodigo(getRutaCodigoFuente());
			assertEquals(21, contador.getLineasTotales());
			assertEquals(11, contador.getLineasEfectivas());
			assertEquals(3, contador.getNombresDeMetodos().size());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetResumenNombresMetodos() {
	}

	@Test
	public void testGetNombreArchivoFuente() {
		try {
			contador = new ContadorLOC();
			contador.contarLineasDeCodigo(getRutaCodigoFuente());
			assertEquals("Test.java", contador.getNombreArchivoFuente());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

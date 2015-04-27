package edu.uniandes.ecos.tsp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAnalizadorFuncional.class, TestCalculadorDataBindingExterno.class, TestContadorLOC.class,
		TestExploradorDirectorios.class })
public class AllTests {

}

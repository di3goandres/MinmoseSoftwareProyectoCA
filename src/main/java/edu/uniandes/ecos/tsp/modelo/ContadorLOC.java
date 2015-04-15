package edu.uniandes.ecos.tsp.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase encargada del conteo de LOC.
 * @author Minmose
 * @version 1.0
 */
public class ContadorLOC {

	/**
	 * Se refiere al archivo actual para leer
	 */
	private File archivoFuente;

	/**
	 * Cuenta el total de lineas en el presente 
	 */
	private int lineasTotales;

	/**
	  * Cuenta las lineas efectivas en el archivo
	 */
	private int lineasEfectivas;

	/**
	 * Cuenta el numero de metodos
	 */
	private int numeroDeMetodos;

	/**
	  * Almacena los nombres de los metodos del archivo actual
	 */
	private List<String> nombresDeMetodos;
	
	/**
	 * Almacena las lineas de codigo de cada uno de los metodos
	 * definidos
	 */
	private Map<String, List<String>> lineasDeMetodos;

	/**
	 * Define la cadena para reconocer una lina comentarios
	 */
	private static final String COMENTARIO_LINEA = "//";

	/**
         * Define la cadena para reconocer el comienzo de Multi-limea de comentarios
	 */
	private static final String INICIO_COMENTARIO_MULTIPLE = "/*";

	/**
	 * Define the string to recognize a Multi-Line comment end
	 */
	private static final String FIN_COMENTARIO_MULTIPLE = "*/";

	/**
	 * Almacena la totalidad de las lineas de codigo que contiene el archivo
	 * actual
	 */
	private ArrayList<String> lineasDeCodigo;

	/**
	 * Constructor Publico sin comentarios
	 */
	public ContadorLOC() {
		archivoFuente = null;
		lineasTotales = 0;
		lineasEfectivas = 0;
		numeroDeMetodos = 0;
		nombresDeMetodos = new ArrayList<String>();
		lineasDeCodigo = new ArrayList<String>();
		lineasDeMetodos = new HashMap<String, List<String>>();
	}

	/**
	 * Trata de recuperar e inicialiar el archivo para leer
	 * 
	 * @param rutaDeArchivo
	 *           Ruta del archivo para leer
	 * @return <code>true</code> Si el archivo existe y puede ser inicializado.
	 *         <code>false</code> otros casos.
	 */
	protected boolean inicializarArchivo(String rutaDeArchivo) {
		
		archivoFuente = new File(rutaDeArchivo);
		return (archivoFuente.exists() && archivoFuente.isFile());
	}

	/**
	 * Metodo que lee el numero de LOC de un archivo definido
	 * @param rutaArchivo
	 *            del archivo de entrada para ser leido
	 * @throws IOException excepcion al leer o cargar el archivo.
	 */
	public void contarLineasDeCodigo(String rutaArchivo) throws IOException {
		
		if (!inicializarArchivo(rutaArchivo)) {
			
			throw new IOException("File does not exist or could not be initialized");
		}

		FileReader lectorArchivo = new FileReader(archivoFuente);
		BufferedReader buffer = new BufferedReader(lectorArchivo);
		String lineaActual = null;
		Boolean esComentario = false;

		//Leer lineas del archivo de texto
		while ((lineaActual = buffer.readLine()) != null) {
			
			lineasTotales++;
			lineaActual = lineaActual.trim();
			if (!lineaActual.isEmpty()) {
				
                                // Verificar si la linea actual es parte de un comentario
				if (lineaActual.startsWith(COMENTARIO_LINEA)) {
					
					continue;
				} else if (lineaActual.startsWith(INICIO_COMENTARIO_MULTIPLE)) {
					
					esComentario = true;
					continue;
				} else if (lineaActual.startsWith(FIN_COMENTARIO_MULTIPLE)) {
					
					esComentario = false;
					continue;
					
				} else if (esComentario) {
					
					continue;
				}
				
				lineasEfectivas++;
				verificarMetodo(lineaActual);
				lineasDeCodigo.add(lineaActual);
			}
		}
		
		buffer.close();
		lectorArchivo.close();
		lineasDeMetodos = agruparLineasMetodo();
	}

	/**
	 * Evuala si la linea  pertenece a la declaracion de un metodo
         * 
	 * @param lineaActual linea a analizar
	 */
	private void verificarMetodo(String lineaActual) {
		
		if(esDefinicionMetodo(lineaActual)){
			
			numeroDeMetodos++;
			nombresDeMetodos.add(lineaActual.substring(0,
					lineaActual.indexOf("(")));
		}
	}
	
	/**
	 * Este metodo agrupa en una lista las lineas actuales de codigo que
	 * pertenecen al metodo actual y las adiciona al HashMap de métodos cuando
	 * se haya finalizado
         * @return mapa con el nombre de los metodos como key
         * y como value una lista de lineas del metodo.
	 */
	public Map<String, List<String>> agruparLineasMetodo() {
		
		boolean esMetodo = false;
		int corchetes = 0;
		ArrayList<String> lineasDeMetodo = null;
		HashMap<String, List<String>> lineasPorMetodos = new HashMap<String, List<String>>();
		String metodoActual = null;

		ListIterator<String> iteradorLineasTotales = lineasDeCodigo
				.listIterator();
		
		while (iteradorLineasTotales.hasNext()) {
			
			String lineaActual = iteradorLineasTotales.next();

			// Verificar que la linea actual es el nombre de un metodo
			if (esDefinicionMetodo(lineaActual)) {
				
				esMetodo = true;
				lineasDeMetodo = new ArrayList<String>();
				metodoActual = lineaActual;
				corchetes = 0;
			}

			if (esMetodo) {
				
				lineasDeMetodo.add(lineaActual);
				
				if (lineaActual.endsWith("{")) {
					
					corchetes++;
				} 
				
				else if (lineaActual.endsWith("}")) {
					
					corchetes--;
				}
				// Si llega al corchete final
				if (corchetes == 0 && lineasDeMetodo.size() > 1) {
					
					lineasPorMetodos.put(new String(metodoActual),
							new ArrayList<String>(lineasDeMetodo));
					esMetodo = false;
					metodoActual = null;
					lineasDeMetodo = null;
					continue;
				}
			}
		}
		return lineasPorMetodos;
	}
	
	/**
         * Recupera los nombres d los metodos del archivo actual
	 * 
	 * @return Un simple <code>String</code> con los nombres de los metodos
         *      recuperados
	 */
	public String getResumenNombresMetodos() {
		StringBuffer methodNameBuffer = new StringBuffer();
		for (String currentMethod : this.nombresDeMetodos) {
			methodNameBuffer.append(currentMethod + "\n");
		}
		return methodNameBuffer.toString();
	}

	/**
         * Metodo para obtener la variable TotalLines
	 * 
	 * @return El total de lines de codigo (blancas incluidas)en el archivo
         * actual, o 0 si el archivo no pudo ser leido
         */
	public int getLineasTotales() {
		return this.lineasTotales;
	}

	/**
	 * Metodo para Obtener las variable de lineas efectivas 
         * 
	 * @return Las lineas efectivas de codigo(sin blancos o comentarios) in the7
         * en el archivo actual, o 0 si el archivo no pudo ser leido
         * 
	 */
	public int getLineasEfectivas() {
		return this.lineasEfectivas;
	}

	/**
         * Retorna el nombre del archivo actual para ser contado
	 * 
	 * @return El nombre del actualo archivo fuente
	 */
	public String getNombreArchivoFuente() {
		return this.archivoFuente.getName();
	}

	/**
         * Recupera el numero de metodos contados por el probrama
	 * 
	 * @return El numero de metodos en el archivo actual
	 */
	public int getConteoMetodos() {
		return this.numeroDeMetodos;
	}

	/**
         * Recupera la lista de metodos para el actual contador
	 * 
	 * @return Un <code>ArrayList</code> con los nombres de los metodos
	 */
	public List<String> getNombresDeMetodos() {
		return this.nombresDeMetodos;
	}
	
	/**
	 * Obtiene el HashMap en el que se almacenan las lineas de codigo
	 * correspondientes a los metodos
	 * @return	Una instancia de <code>HashMap</code> cuya clave es el nombre de
	 * cada metodo en la clase y el valor es un <code>ArrayList</code> con las líneas
	 * de codigo del respectivo método
	 */
	public Map<String, List<String>> getLineasDeMetodos(){
		
		return this.lineasDeMetodos;
	}
	
	/**
	 * Evalua si para la línea de código actual se cumple la expresión
	 * regular que define si esta linea corresponde a la definición de un método
	 * @param lineaDeCodigo Línea de Código Actual
	 * @return
	 * 	<code>true</code> si la línea de código actual es una definición de método.
	 * 	<code>false</code> de otro modo.
	 */
	private boolean esDefinicionMetodo(String lineaDeCodigo){
		String regex = "^(public|private|protected)\\s[\\w\\<\\>\\s]+\\({1}";
		Pattern patron = Pattern.compile(regex);
		Matcher matcher = patron.matcher(lineaDeCodigo);
		return matcher.find();
	}


}
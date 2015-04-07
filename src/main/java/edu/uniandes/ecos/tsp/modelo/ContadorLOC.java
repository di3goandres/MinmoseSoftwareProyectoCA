package edu.uniandes.ecos.tsp.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Minmose
 * @version 1.0
 * @created 29-mar-2015 09:00:08 p.m.
 */
public class ContadorLOC {

	/**
	 * Refers to the current file to read
	 */
	private File archivoFuente;

	/**
	 * Counts the total lines present in file
	 */
	private int lineasTotales;

	/**
	 * Counts the effective lines in the file
	 */
	private int lineasEfectivas;

	/**
	 * Counts the number of methods
	 */
	private int numeroDeMetodos;

	/**
	 * Stores the method names of the current file
	 */
	private ArrayList<String> nombresDeMetodos;

	/**
	 * Define the string to recognize a One-Line comment
	 */
	private static final String COMENTARIO_LINEA = "//";

	/**
	 * Define the string to recognize a Multi-Line comment start
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
	 * Public constructor. No arguments
	 */
	public ContadorLOC() {
		archivoFuente = null;
		lineasTotales = 0;
		lineasEfectivas = 0;
		numeroDeMetodos = 0;
		nombresDeMetodos = new ArrayList<String>();
		lineasDeCodigo = new ArrayList<String>();
	}

	/**
	 * Tries to retrieve and initialize the file to read
	 * 
	 * @param rutaDeArchivo
	 *            Path for the file to read
	 * @return <code>true</code> if the file exists and could be initialized.
	 *         <code>false</code> otherwise.
	 */
	protected boolean inicializarArchivo(String rutaDeArchivo) {
		archivoFuente = new File(rutaDeArchivo);
		return (archivoFuente.exists() && archivoFuente.isFile());
	}

	/**
	 * Method that reads the number of LOC from a defined file
	 * 
	 * @param rutaArchivo
	 *            The input file to be read
	 * @throws IOException
	 */
	public void contarLineasDeCodigo(String rutaArchivo) throws IOException {
		if (!inicializarArchivo(rutaArchivo)) {
			System.out
					.println("LOCCounter: Error. File does not exist or could not be initialized");
			return;
		}

		FileReader lectorArchivo = new FileReader(archivoFuente);
		BufferedReader buffer = new BufferedReader(lectorArchivo);
		String lineaActual = null;
		Boolean esComentario = false;

		// Read lines from text file
		while ((lineaActual = buffer.readLine()) != null) {
			lineasTotales++;
			lineaActual = lineaActual.trim();
			if (!lineaActual.isEmpty()) {
				// Verifies if current line is part of a comment
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
		agruparLineasMetodo();
	}

	/**
	 * Evaluates if a line belongs to a method declaration
	 * 
	 * @param lineaActual
	 */
	private void verificarMetodo(String lineaActual) {
		String regex = "^(public|private|protected)\\s[\\w\\<\\>\\s]+\\({1}";
		Pattern patron = Pattern.compile(regex);
		Matcher matcher = patron.matcher(lineaActual);
		if (matcher.find()) {
			numeroDeMetodos++;
			nombresDeMetodos.add(lineaActual.substring(0,
					lineaActual.indexOf("(")));
		}
	}

	/**
	 * Getter method for totalLines variable
	 * 
	 * @return The total lines of code (blanks included) in the current file, or
	 *         0 if file has not been read
	 */
	public int getLineasTotales() {
		return this.lineasTotales;
	}

	/**
	 * Getter method for effectiveLines variable
	 * 
	 * @return The effective lines of code (without blanks or comments) in the
	 *         current file, or 0 if file has not been read
	 */
	public int getLineasEfectivas() {
		return this.lineasEfectivas;
	}

	/**
	 * Returns the name for the current file to be counted
	 * 
	 * @return The name of the current source file
	 */
	public String getNombreArchivoFuente() {
		return this.archivoFuente.getName();
	}

	/**
	 * Retrieves the number of methods counted by the program
	 * 
	 * @return The number of methods in source file
	 */
	public int getMethodCount() {
		return this.numeroDeMetodos;
	}

	/**
	 * Retrieves the names of the methods of the current file
	 * 
	 * @return A single <code>String</code> with the names of the retrieved
	 *         methods
	 */
	public String getMethodNamesSummary() {
		StringBuffer methodNameBuffer = new StringBuffer();
		for (String currentMethod : this.nombresDeMetodos) {
			methodNameBuffer.append(currentMethod + "\n");
		}
		return methodNameBuffer.toString();
	}

	/**
	 * Retrieves the list of methods for current counter
	 * 
	 * @return An <code>ArrayList</code> with the method names
	 */
	public ArrayList<String> getMethodNames() {
		return this.nombresDeMetodos;
	}

	/**
	 * Este método agrupa en una lista las líneas actuales de código que
	 * pertenecen al metodo actual y las adiciona al HashMap de métodos cuando
	 * se haya finalizado
	 */
	public HashMap<String, ArrayList<String>> agruparLineasMetodo() {
		boolean esMetodo = false;
		int corchetes = 0;
		ArrayList<String> lineasDeMetodo = null;
		HashMap<String, ArrayList<String>> lineasPorMetodos = new HashMap<String, ArrayList<String>>();
		String metodoActual = null;

		ListIterator<String> iteradorLineasTotales = lineasDeCodigo
				.listIterator();
		while (iteradorLineasTotales.hasNext()) {
			String lineaActual = iteradorLineasTotales.next();

			// Verificar que la linea actual es el nombre de un metodo
			if (lineaActual.contains("(")
					&& nombresDeMetodos.contains(lineaActual.substring(0,
							lineaActual.indexOf("(")))) {
				esMetodo = true;
				lineasDeMetodo = new ArrayList<String>();
				metodoActual = lineaActual.substring(0,
						lineaActual.indexOf("("));
				corchetes = 0;
			}

			if (esMetodo) {
				lineasDeMetodo.add(lineaActual);
				if (lineaActual.endsWith("{")) {
					corchetes++;
				} else if (lineaActual.endsWith("}")) {
					corchetes--;
				}
				// Si llegó al corchete final
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

}
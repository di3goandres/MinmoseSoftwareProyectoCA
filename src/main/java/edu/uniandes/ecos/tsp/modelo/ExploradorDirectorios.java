package edu.uniandes.ecos.tsp.modelo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de encontrar las clases java para el analisis funcional.
 * @author Minmose
 * @version 1.0
 */
public class ExploradorDirectorios {
	
	/**
	 * Directotio donde se encuentra el programa a analizar.
	 */
	private File directorioDeTrabajo;
	
	/**
	 * Total de lineas del programa a analizar.
	 */
	private int lineasTotales;
	
	/**
	 * Numero de lineas efectivas del proyecto.
	 */
	private int lineasEfectivas;
	
	/**
	 * Nombre del proyecto a analizar.
	 */
	private String nombreProyecto;
	
	/**
	 * Lista de archivos que hacen parte del programa a analizar.
	 */
	private ArrayList<File> listadoArchivos;
	
	/**
	 * Lista de contadores de LOC.
	 */
	private ArrayList<ContadorLOC> listaContadores;
	
	/**
	 * Lineas pertenecientes a un metodo especifico.
	 */
	private Map<String, List<String>> lineasDeMetodos;
	
	/**
	 * Almacena las variables globales del programa
	 */
	private List<String> variablesGlobales;
	
	/**
     * Metodo constructor
	 * @param projectName nombre del proyecto analizado
	 */
	public ExploradorDirectorios(String projectName){
		this();
		this.nombreProyecto = projectName;
	}
	
	/**
	 * Metodo constructor
	 */
	public ExploradorDirectorios(){
		
		directorioDeTrabajo = null;
		listadoArchivos = new ArrayList<File>();
		listaContadores = new ArrayList<ContadorLOC>();
		lineasTotales = 0;
		lineasEfectivas = 0;
		nombreProyecto = "Undefined";
		lineasDeMetodos = new HashMap<String, List<String>>();
		variablesGlobales = new ArrayList<String>();
	}
	
	/**
     * Inicializa el actual directorio de trabajo
	 * @param dirPath La ruta del actual directorio
	 * @return <code>true</code> si el directorio existe. 
	 * <code>false</code> cualquier otro caso
	 */
	protected boolean iniciarDirectorio(String dirPath){
		
		directorioDeTrabajo = new File(dirPath);
		
		return (directorioDeTrabajo.exists() && directorioDeTrabajo.isDirectory());
	}
	
	/**
	 * This method retrieves a <code>FilenameFilter</code> instance
     * Este metodo retorana un <code>FilenameFilter</code> instancia
     * a retornar los fuentes de archivos java de un directorio
	 * @return FilenameFilter
	 */
	private FilenameFilter getJavaFileFilter(){
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");
			}
		};
	}
	
	
	
	/**
     * Cuenta los LOC por cada fuente de archivo java en el directorio de 
     * trabajo
	 * @param directorio La raiz del directorio el actual proyecto
	 */
	public void enviarAContadorLOC(String directorio){
		
		Path dirActual = Paths.get(directorio);
		String dirAbsoluto = dirActual.toAbsolutePath().toString();
		
		if(!iniciarDirectorio(dirAbsoluto)){
			
			System.out.println("ProgramContadorLOC: Error al cargar el directorion actual de trabajo");
			return;
		}
		try {
			recuperarArchivos(dirAbsoluto);
			//Cuenta los LOC dentro del proyecto
			
			Collections.sort(listadoArchivos);
			
			for(File javaFile : listadoArchivos){
				
				ContadorLOC fileContadorLOC = new ContadorLOC();
				fileContadorLOC.contarLineasDeCodigo(javaFile.getAbsolutePath());
				lineasDeMetodos.putAll(fileContadorLOC.getLineasDeMetodos());
				lineasTotales += fileContadorLOC.getLineasTotales();
				lineasEfectivas += fileContadorLOC.getLineasEfectivas();
				listaContadores.add(fileContadorLOC);
				variablesGlobales.addAll(fileContadorLOC.getVariablesGlobales());
			}
		} catch (IOException e) {
			
			System.out.println("ProgramContadorLOC: Excepción al contar las LOC del Proyecto");
			e.printStackTrace();
			return;
			
		} catch (Exception e) {

			System.out.println("ProgramContadorLOC: Excepción general");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Retrieves recursively all Java files from given path
     * Recupera recursivamente todos los archivos Java de una ruta dada
	 * @param rutaArchivo La ruta del directorio, para recuperar los archivos 
     * Java
	 * @throws IOException excepcion al cargar o leer archivos.
	 */
	public void recuperarArchivos(String rutaArchivo) throws IOException{
		
		File archivoActual = new File(rutaArchivo);
		File[] listaArchivosEnActual = archivoActual.listFiles();
		
		for(File proximoArchivo : listaArchivosEnActual){
			
			if(proximoArchivo.isDirectory()){
				recuperarArchivos(proximoArchivo.getAbsolutePath());
			}
		}
		
		File[] javaFiles = archivoActual.listFiles(getJavaFileFilter());
		
		for(File nextJavaFile : javaFiles){
			
			listadoArchivos.add(nextJavaFile);
		}
	}
	
	/**
	 * Recupera la instancia de <code>ContadorLOC</code> recuperada
	 * @return  La lista de los contadores LOC para el proyecto actual
	 */
	public ArrayList<ContadorLOC> getListaContadores(){
		return this.listaContadores;
	}
	
	/**
     * Recupera el String para la ruta absoluta del directorio de trabajo
	 * @return La ruta raiz del directorio de trabajo
	 */
	public String getRutaDirectorioDeTrabajo(){
		return this.directorioDeTrabajo.getAbsolutePath();
	}
	
	/**
     * Recupera el nombre del proyecto actual
	 * @return nombre del proyecto analizado
	 */
	public String getNombreProyecto(){
		return this.nombreProyecto;
	}

	/**
     * Metodo que retorna las lineas de un metodo especifico.
	 * @return mapa con los metodos de una clase y sus lineas
	 */
	public Map<String, List<String>> getLineasDeMetodos() {
		return lineasDeMetodos;
	}
	
	/**
	 * Retrieves the total LOC of the entire project
	 * @return The numbers of total lines
	 */
	public int getLineastotalesProyecto(){
		return this.lineasTotales;
	}
	
	/**
     * Recupera el numero de lineas efectivas LOC de proyecto entero
	 * @return El numero de lineas efectiva
	 */
	public int getLineasEfectivasProyecto(){
		return this.lineasEfectivas;
	}

	public List<String> getVariablesGlobales() {
		return variablesGlobales;
	}

}

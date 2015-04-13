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
 * @created 29-mar-2015 09:00:08 p.m.
 */
public class ExploradorDirectorios {
	
	/**
	 * 
	 */
	private File directorioDeTrabajo;
	
	/**
	 * 
	 */
	private int lineasTotales;
	
	/**
	 * 
	 */
	private int lineasEfectivas;
	
	/**
	 * 
	 */
	private String nombreProyecto;
	
	/**
	 * 
	 */
	private ArrayList<File> listadoArchivos;
	
	/**
	 * 
	 */
	private ArrayList<ContadorLOC> listaContadores;
	
	/**
	 * 
	 */
	private Map<String, List<String>> lineasDeMetodos;
	
	/**
	 * @param projectName
	 */
	public ExploradorDirectorios(String projectName){
		this();
		this.nombreProyecto = projectName;
	}
	
	/**
	 * 
	 */
	public ExploradorDirectorios(){
		
		directorioDeTrabajo = null;
		listadoArchivos = new ArrayList<File>();
		listaContadores = new ArrayList<ContadorLOC>();
		lineasTotales = 0;
		lineasEfectivas = 0;
		nombreProyecto = "Undefined";
		lineasDeMetodos = new HashMap<String, List<String>>();
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
			}
		} catch (IOException e) {
			
			System.out.println("ProgramContadorLOC: Excepción al contar las LOC del Proyecto");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Retrieves recursively all Java files from given path
         * Recupera recursivamente todos los archivos Java de una ruta dada
	 * @param rutaArchivo La ruta del directorio, para recuperar los archivos 
         * Java
	 * @throws IOException
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
	 * @return
	 */
	public String getNombreProyecto(){
		return this.nombreProyecto;
	}

	/**
	 * @return
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

}

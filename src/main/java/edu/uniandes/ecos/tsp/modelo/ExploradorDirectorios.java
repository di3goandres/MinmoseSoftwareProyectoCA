package edu.uniandes.ecos.tsp.modelo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExploradorDirectorios {
	
	private File directorioDeTrabajo;
	
	private int lineasTotales;
	
	private int lineasEfectivas;
	
	private String nombreProyecto;
	
	private ArrayList<File> listadoArchivos;
	
	private ArrayList<ContadorLOC> listaContadores;
	
	public ExploradorDirectorios(String projectName){
		this();
		this.nombreProyecto = projectName;
	}
	
	public ExploradorDirectorios(){
		directorioDeTrabajo = null;
		listadoArchivos = new ArrayList<File>();
		listaContadores = new ArrayList<ContadorLOC>();
		lineasTotales = 0;
		lineasEfectivas = 0;
		nombreProyecto = "Undefined";
	}
	
	/**
	 * Initializes the current working directory
	 * @param dirPath The path to the current directory
	 * @return <code>true</code> if directory exists. 
	 * <code>false</code> otherwise
	 */
	protected boolean iniciarDirectorio(String dirPath){
		directorioDeTrabajo = new File(dirPath);
		return (directorioDeTrabajo.exists() && directorioDeTrabajo.isDirectory());
	}
	
	/**
	 * This method retrieves a <code>FilenameFilter</code> instance
	 * to retrieve the Java source files from a directory. 
	 * @return
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
	 * Retrieves the total LOC of the entire project
	 * @return The numbers of total lines
	 */
	public int getLineastotalesProyecto(){
		return this.lineasTotales;
	}
	
	/**
	 * Retrieves the number of effective LOC of the entire project
	 * @return The numbers of effective lines
	 */
	public int getLineasEfectivasProyecto(){
		return this.lineasEfectivas;
	}
	
	/**
	 * Counts the LOC for each Java source file in the working
	 * directory
	 * @param directorio The root directory of the current project
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
			//Counts the LOC inside project
			for(File javaFile : listadoArchivos){
				ContadorLOC fileContadorLOC = new ContadorLOC();
				fileContadorLOC.contarLineasDeCodigo(javaFile.getAbsolutePath());
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
	 * @param rutaArchivo The directory path to retrieve Java files
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
	 * Retrieves the instances of <code>ContadorLOC</code> retrieved
	 * @return The list of the LOC Counters for current project
	 */
	public ArrayList<ContadorLOC> getListaContadores(){
		return this.listaContadores;
	}
	
	/**
	 * Retrieves the String for the absolute path of the working directory
	 * @return The Path of the root working directory
	 */
	public String getRutaDirectorioDeTrabajo(){
		return this.directorioDeTrabajo.getAbsolutePath();
	}
	
	/**
	 * Retrieves the name of the current Project
	 * @return
	 */
	public String getNombreProyecto(){
		return this.nombreProyecto;
	}

}

package edu.uniandes.ecos.tsp.modelo;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExploradorDirectorios {
	
private File workingDirectory;
	
	private int totalLines;
	
	private int effectiveLines;
	
	private String projectName;
	
	private ArrayList<File> filesToCount;
	
	private ArrayList<ContadorLOC> countersList;
	
	public ExploradorDirectorios(String projectName){
		this();
		this.projectName = projectName;
	}
	
	public ExploradorDirectorios(){
		workingDirectory = null;
		filesToCount = new ArrayList<File>();
		countersList = new ArrayList<ContadorLOC>();
		totalLines = 0;
		effectiveLines = 0;
		projectName = "Undefined";
	}
	
	/**
	 * Initializes the current working directory
	 * @param dirPath The path to the current directory
	 * @return <code>true</code> if directory exists. 
	 * <code>false</code> otherwise
	 */
	protected boolean initDirectory(String dirPath){
		workingDirectory = new File(dirPath);
		return (workingDirectory.exists() && workingDirectory.isDirectory());
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
	public int getProjectTotalLines(){
		return this.totalLines;
	}
	
	/**
	 * Retrieves the number of effective LOC of the entire project
	 * @return The numbers of effective lines
	 */
	public int getProjectEffectiveLines(){
		return this.effectiveLines;
	}
	
	/**
	 * Counts the LOC for each Java source file in the working
	 * directory
	 * @param dirName The root directory of the current project
	 */
	public void sendToContadorLOC(String dirName){
		Path currentPath = Paths.get(dirName);
		String absolutePath = currentPath.toAbsolutePath().toString();
		if(!initDirectory(absolutePath)){
			System.out.println("ProgramContadorLOC: Error loading current work directory");
			return;
		}
		try {
			retrieveFiles(absolutePath);
			//Counts the LOC inside project
			for(File javaFile : filesToCount){
				ContadorLOC fileContadorLOC = new ContadorLOC();
				fileContadorLOC.countLines(javaFile.getAbsolutePath());
				totalLines += fileContadorLOC.getTotalLines();
				effectiveLines += fileContadorLOC.getEfffectiveLines();
				countersList.add(fileContadorLOC);
			}
		} catch (IOException e) {
			System.out.println("ProgramContadorLOC: Error trying to count LOC for Project");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Retrieves recursively all Java files from given path
	 * @param filePath The directory path to retrieve Java files
	 * @throws IOException
	 */
	public void retrieveFiles(String filePath) throws IOException{
		File theFile = new File(filePath);
		File[] filesInside = theFile.listFiles();
		for(File nextFile : filesInside){
			if(nextFile.isDirectory()){
				retrieveFiles(nextFile.getAbsolutePath());
			}
		}
		File[] javaFiles = theFile.listFiles(getJavaFileFilter());
		for(File nextJavaFile : javaFiles){
			filesToCount.add(nextJavaFile);
		}
	}
	
	/**
	 * Retrieves the instances of <code>ContadorLOC</code> retrieved
	 * @return The list of the LOC Counters for current project
	 */
	public ArrayList<ContadorLOC> getCountersList(){
		return this.countersList;
	}
	
	/**
	 * Retrieves the String for the absolute path of the working directory
	 * @return The Path of the root working directory
	 */
	public String getWorkingDirectoryPath(){
		return this.workingDirectory.getAbsolutePath();
	}
	
	/**
	 * Retrieves the name of the current Project
	 * @return
	 */
	public String getProjectName(){
		return this.projectName;
	}

}

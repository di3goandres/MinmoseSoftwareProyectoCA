package edu.uniandes.ecos.tsp.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	private File sourceFile;
	
	/**
	 * Counts the total lines present in file
	 */
	private int totalLines;
	
	/**
	 * Counts the effective lines in the file
	 */
	private int efffectiveLines;
	
	/**
	 * Counts the number of methods
	 */
	private int methods;
	
	/**
	 * Stores the method names of the current file
	 */
	private ArrayList<String> methodNames;
	
	/**
	 * Define the string to recognize a One-Line comment
	 */
	private static final String LINE_COMMENT = "//";
	
	/**
	 * Define the string to recognize a Multi-Line comment start
	 */
	private static final String MULTILINE_COMMENT_START = "/*";
	
	/**
	 * Define the string to recognize a Multi-Line comment end
	 */ 
	private static final String MULTILINE_COMMENT_END = "*/";
	
	/**
	 * Public constructor. No arguments
	 */
	public ContadorLOC(){
		sourceFile = null;
		totalLines = 0;
		efffectiveLines = 0;
		methods = 0;
		methodNames = new ArrayList<String>();
	}
	
	/**
	 * Tries to retrieve and initialize the file to read
	 * @param filePath Path for the file to read
	 * @return <code>true</code> if the file exists and could be initialized.
	 * <code>false</code> otherwise.
	 */
	protected boolean initializeFile(String filePath){
		sourceFile = new File(filePath);
		return (sourceFile.exists() && sourceFile.isFile());
	}
	
	/**
	 * Method that reads the number of LOC from a defined file
	 * @param fileToRead
	 * 		The input file to be read
	 * @throws IOException 
	 */
	public void countLines(String fileToRead) throws IOException{
		if(!initializeFile(fileToRead)){
			System.out.println("LOCCounter: Error. File does not exist or could not be initialized");
			return;
		}
		
		FileReader fileReader = new FileReader(sourceFile);
		BufferedReader buffer = new BufferedReader(fileReader);
		String currentLine = null;
		Boolean commented = false;
		
		//Read lines from text file
		while((currentLine = buffer.readLine()) != null){
			totalLines++;
			currentLine = currentLine.trim();
			if(!currentLine.isEmpty()){
				//Verifies if current line is part of a comment
				if(currentLine.startsWith(LINE_COMMENT)){
					continue;
				}
				else if(currentLine.startsWith(MULTILINE_COMMENT_START)){
					commented = true;
					continue;
				}
				else if(currentLine.startsWith(MULTILINE_COMMENT_END)){
					commented = false;
					continue;
				}
				else if(commented){
					continue;
				}
				efffectiveLines++;
				verifyMethod(currentLine);
			}
		}
		buffer.close();
		fileReader.close();
	}
	
	/**
	 * Evaluates if a line belongs to a method declaration
	 * @param currentLine
	 */
	private void verifyMethod(String currentLine){
		String regexMethodPattern = "^(public|private|protected)\\s[\\w\\<\\>\\s]+\\({1}";
		Pattern pattern = Pattern.compile(regexMethodPattern);
		Matcher matcher = pattern.matcher(currentLine);
		if(matcher.find()){
			methods++;
			methodNames.add(currentLine.substring(0, 
					currentLine.indexOf("(")));
		}
	}
	
	/**
	 * Getter method for totalLines variable
	 * @return The total lines of code (blanks included) in
	 * the current file, or 0 if file has not been read
	 */
	public int getTotalLines(){
		return this.totalLines;
	}
	
	/**
	 * Getter method for effectiveLines variable
	 * @return The effective lines of code (without blanks or comments) in
	 * the current file, or 0 if file has not been read
	 */
	public int getEfffectiveLines(){
		return this.efffectiveLines;
	}
	
	/**
	 * Returns the name for the current file to be counted
	 * @return The name of the current source file
	 */
	public String getSourceFileName(){
		return this.sourceFile.getName();
	}
	
	/**
	 * Retrieves the number of methods counted by the program
	 * @return The number of methods in source file
	 */
	public int getMethodCount(){
		return this.methods;
	}
	
	/**
	 * Retrieves the names of the methods of the current file
	 * @return A single <code>String</code> with the names of the retrieved methods
	 */
	public String getMethodNamesSummary(){
		StringBuffer methodNameBuffer = new StringBuffer();
		for(String currentMethod : this.methodNames){
			methodNameBuffer.append(currentMethod + "\n");
		}
		return methodNameBuffer.toString();
	}
	
	/**
	 * Retrieves the list of methods for current counter
	 * @return An <code>ArrayList</code> with the method names
	 */
	public ArrayList<String> getMethodNames(){
		return this.methodNames;
	}

}
package edu.uniandes.ecos.tsp.controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Esta clase muestra los formularios a presentar de la vista Web
 * @author drenteria (df.renteria10@uniandes.edu.co)
 *
 */
public class ControladorWeb {
	
	/**
	 * Metodo que presenta el formulario de inicio de la vista Web
	 * @param req
	 * 		Request HTTP proveniente del servidor
	 * @param rsp
	 * 		Response HTTP hacia el servidor
	 * @throws IOException
	 */
	public static void mostrarFormularioInicio(HttpServletRequest req, HttpServletResponse rsp) throws IOException{
		String formString = "<html>" +
				"<body>" +
				"<h1>TSP - Analizador Funcional</h1>" +
				"<p>Haga Click para iniciar el Analizador Funcional.</p>" +
				"<form action=\"analisis\" method=\"post\"><br/>" +
				"<input type=\"submit\" value=\"Iniciar Analisis\">" +
				"</body>" +
				"</html>";
		PrintWriter writer = rsp.getWriter();
		writer.write(formString);
	}
	
	/**
	 * Metodo que invoca el calculo del analisis funcional.
	 * @param req
	 * 		HTTP Request proveniente del servidor
	 * @param rsp
	 * 		HTTP Response dirigida al servidor
	 * @throws IOException
	 */
	public static void mostrarReporte(HttpServletRequest req, HttpServletResponse rsp)
	throws IOException {
		Controlador controller = new Controlador();
		controller.realizarAnalisisFuncional();
		String formString = "<html>" +
				"<body>" +
				"<h1>TSP - Analizador Funcional</h1>" +
				"<p>Resultados:</p>" +
				"<p>"+ controller.mostrarReporte().replace("\n", "<br/>") + "</p>" +
				"</body>" +
				"</html>";
		PrintWriter writer = rsp.getWriter();
		writer.write(formString);
	}

}

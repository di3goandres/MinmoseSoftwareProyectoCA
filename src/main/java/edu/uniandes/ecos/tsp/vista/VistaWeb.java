package edu.uniandes.ecos.tsp.vista;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import edu.uniandes.ecos.tsp.controlador.ControladorWeb;

/**
 * Clase que implemtenta la vista en el patron MVC.
 * @author Minmose
 * @version 1.0
 */
public class VistaWeb extends HttpServlet {
	
	/**
	 * Método Main para ejecución del servidor Jetty de vista web
	 * @param args
	 */
	public static void main (String[] args){
		Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new VistaWeb()), "/*");
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(VistaWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse rsp){
		try {
			ControladorWeb.mostrarFormularioInicio(req, rsp);
		} catch (IOException e) {
			Logger.getLogger(VistaWeb.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp){
		try {
			ControladorWeb.mostrarReporte(req, rsp);
		} catch (IOException e) {
			Logger.getLogger(VistaWeb.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
	}

}
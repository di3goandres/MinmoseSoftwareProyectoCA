package edu.uniandes.ecos.tsp.modelo;

import java.util.ArrayList;

/**
 * @author Minmose
 * @version 1.0
 */
public class Reporte {

    /**
     * Representa la cadena final del reporte
     */
    private ArrayList<String> reporte;

    /**
     * Metodo constructor.
     */
    public Reporte() {

        this.reporte = new ArrayList();
    }

    /**
     *  Retorna el encabezado del informe
     */
    private String crearEncabezado() {
        return "Aqui ponemos el encabezado";
    }

    /**
     * Retorna el pie del informe
     */
    private String crearPie(){
        return " Aqui ponemos el Pie ";
    }
    
    /**
     * Crea el reporte del analisis realizado al programa, lo retorna y lo
     * muestra por consula.
     */
    public String mostrarReporte() {
        StringBuilder reporte = new StringBuilder();
        
        reporte.append(this.crearEncabezado());
        reporte.append("\n");
        for (int i = 0; i < this.reporte.size(); i++) {
            reporte.append(this.reporte.get(i));
            reporte.append("\n");

        }
        reporte.append("\n");
        reporte.append(this.crearPie());
        System.out.println(reporte.toString());
        return reporte.toString();
    }

    /**
     * Agrega linea al reporte, con un salto de linea entre el titulo y el
     * valor.
     *
     * @param titulo Titulo de la linea.
     * @param valor Valor de los datos ingresados
     */
    public void agregarReporte(String titulo, String valor) {
        this.reporte.add(titulo + "\n" + valor);
    }

}

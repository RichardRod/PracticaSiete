package sistemaDistribuido.sistema.exclusion.modoUsuario.recursos;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.visual.exclusion.VentanaRegiones;

import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;

public class Recurso {

    //atributos
    public static LinkedList<Memoria> colaMemoria = new LinkedList<>();
    public static LinkedList<Impresora> colaImpresora = new LinkedList<>();
    public static LinkedList<Disco> colaDisco = new LinkedList<>();
    public static LinkedList<Red> colaRed = new LinkedList<>();

    public static boolean agregarMemoria(int procesoSolicitante)
    {
        boolean recursoLibre;

        colaMemoria.offer(new Memoria(procesoSolicitante));
        int indice = colaMemoria.size();
        System.out.println("Indice: " + indice);

        if(indice > 1)
        {
            recursoLibre = false;
        }
        else
        {
            recursoLibre = true;
        }//fin de else


        return recursoLibre;

    }//fin del metodo agregarMemoria

    public static boolean liberarMemoria(int procesoSolicitante)
    {
        int proceso = colaMemoria.getFirst().getIdProcesoSolicitante();

        if(proceso == procesoSolicitante)
        {
            colaMemoria.poll();
        }

        return true;

    }//fin del metodo liberarMemoria

    public static boolean agregarImpresora(int procesoSolicitante)
    {
        boolean recursoLibre;

        //colaImpresora.offer(new Impresora(procesoSolicitante));
        int indice = colaImpresora.size();

        return false;
    }//fin del metodo agregarImpresora

    public static void actualizarMemoria(byte[] respServidor)
    {
        DefaultTableModel modeloRecursoUso = (DefaultTableModel) VentanaRegiones.tablaRecursosUso.getModel();
        Object[] filas = new Object[4];

        if(colaMemoria.size() > 0)
        {
            String indicador = String.valueOf(colaMemoria.getFirst().getIdProcesoSolicitante());
            int idOrigen = colaMemoria.getFirst().getIdProcesoSolicitante();
            indicador = "Proceso: " + indicador;




            filas[0] = indicador;
            System.out.println("Indicador: " + idOrigen);


            modeloRecursoUso.addRow(filas);
            VentanaRegiones.tablaRecursosUso.setModel(modeloRecursoUso);

            if(modeloRecursoUso.getRowCount() > 0)
                modeloRecursoUso.removeRow(0);

            DefaultTableModel modeloMemoria = (DefaultTableModel)VentanaRegiones.tablaMemoria.getModel();
            modeloMemoria.removeRow(0);
        }//fin de if
    }//fin del metodo actualizarMemoria


}//fin de la clase Recurso

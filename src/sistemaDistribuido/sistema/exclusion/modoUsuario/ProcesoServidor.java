package sistemaDistribuido.sistema.exclusion.modoUsuario;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.sistema.exclusion.modoUsuario.recursos.Memoria;
import sistemaDistribuido.sistema.exclusion.modoUsuario.recursos.Recurso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.visual.exclusion.VentanaRegiones;

import javax.swing.table.DefaultTableModel;

public class ProcesoServidor extends Proceso {

    //atributos
    private final byte MEMORIA = 0;
    private final byte IMPRESORA = 1;
    private final byte DISCO = 2;
    private final byte RED = 3;
    private final byte LIBERAR_MEMORIA = 4;
    private final byte LIBERAR_IMPRESORA = 5;
    private final byte LIBERAR_DISCO = 6;
    private final byte LIBERAR_RED = 7;

    byte[] solServidor = new byte[1024];
    byte[] respServidor = new byte[1024];

    //constructor
    public ProcesoServidor(Escribano esc) {
        super(esc);
        start();
    }//fin del constructor

    @Override
    public void run() {
        imprimeln("Servidor administrador de recursos iniciado.");

        byte recurso;
        byte idOrigen;

        boolean correcto;

        while (continuar()) {
            imprimeln("Invocando a receive()");
            Nucleo.receive(dameID(), solServidor);

            recurso = solServidor[8];
            idOrigen = solServidor[0];

            imprimeln("Recurso solicitado: " + recursoCadena(recurso) + " por el proceso: " + idOrigen);
            correcto = establecerAccion(recurso, idOrigen);

            if (correcto) {
                String respuesta = "Recurso asignado";
                //empacar respuesta
                for (int i = 0; i < respuesta.length(); i++) {
                    respServidor[i + 8] = (byte) respuesta.charAt(i);
                }//fin de for

                imprimeln("Senialamiento al nucleo para envio de mensaje");
                Nucleo.send(idOrigen, respServidor);
                imprimeln("Correcto.");
            }//fin de if
            else {
                String respuesta = "Recurso Ocupado";
                //empacar respuesta
                for (int i = 0; i < respuesta.length(); i++) {
                    respServidor[i + 8] = (byte) respuesta.charAt(i);
                }//fin de for

                imprimeln("Senialamiento al nucleo para envio de mensaje");
                Nucleo.send(idOrigen, respServidor);
                imprimeln("Correcto.");
            }//fin de else

        }//fin de while
    }//fin del metodo run

    public boolean establecerAccion(byte recurso, byte idClienteSolicitante) {

        DefaultTableModel modeloRecursoUso = (DefaultTableModel) VentanaRegiones.tablaRecursosUso.getModel();
        Object[] filas = new Object[4];
        boolean correcto = false;

        switch (recurso) {
            case MEMORIA:
                correcto = agregarMemoria(idClienteSolicitante);
                break;

            case LIBERAR_MEMORIA:
                imprimeln("Liberar Recurso: Memoria por el proceso: " + idClienteSolicitante);

                correcto = Recurso.liberarMemoria(idClienteSolicitante);
                filas[0] = " ";

                modeloRecursoUso.removeRow(0);
                modeloRecursoUso.addRow(filas);
                VentanaRegiones.tablaRecursosUso.setModel(modeloRecursoUso);
                VentanaRegiones.tablaRecursosUso.addNotify();
                Recurso.actualizarMemoria(respServidor);
                break;

            case IMPRESORA:

                break;

            case LIBERAR_IMPRESORA:

                break;

            case DISCO:

                break;

            case LIBERAR_DISCO:

                break;

            case RED:

                break;

            case LIBERAR_RED:

                break;
        }//fin de switch

        return correcto;

    }//fin del metodo establecerRecurso

    public String recursoCadena(byte recurso) {
        String rec = "";

        switch (recurso) {
            case MEMORIA:
                rec = "Memoria";
                break;

            case IMPRESORA:
                rec = "Impresora";
                break;

            case DISCO:
                rec = "Disco";
                break;

            case RED:
                rec = "Red";
                break;

            case LIBERAR_MEMORIA:
                rec = "Liberar Memoria";
                break;
        }

        return rec;
    }

    public boolean agregarMemoria(int proceso) {
        DefaultTableModel modeloRecursoUso = (DefaultTableModel) VentanaRegiones.tablaRecursosUso.getModel();
        Object[] filas = new Object[4];
        boolean correcto;
        if (Recurso.agregarMemoria(proceso)) {
            imprimeln("Recurso solicitado disponible.");

            filas[0] = "Proceso: " + proceso;

            if (modeloRecursoUso.getRowCount() > 0)
                modeloRecursoUso.removeRow(0);

            modeloRecursoUso.addRow(filas);
            VentanaRegiones.tablaRecursosUso.setModel(modeloRecursoUso);
            correcto = true;
        }//fin de if
        else {
            imprimeln("Recurso solicitado ocupado.");

            DefaultTableModel modeloMemoria = (DefaultTableModel) VentanaRegiones.tablaMemoria.getModel();
            Object[] filasMemoria = new Object[1];

            filasMemoria[0] = "Proceso: " + proceso;

            modeloMemoria.addRow(filasMemoria);
            VentanaRegiones.tablaMemoria.setModel(modeloMemoria);

            correcto = false;

        }//fin de else

        return correcto;
    }
}

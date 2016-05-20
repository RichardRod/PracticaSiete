package sistemaDistribuido.visual.exclusion;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.exclusion.modoUsuario.ProcesoCliente;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteFrame extends ProcesoFrame implements ActionListener {

    //atributos
    private ProcesoCliente procesoCliente;

    private static final long serialVersionUID = 1;

    public JButton botonSolMemoria;
    public JButton botonSolImpresora;
    public JButton botonSolDisco;
    public JButton botonSolRed;

    public JButton botonLibMemoria;
    public JButton botonLibImpresora;
    public JButton botonLibDisco;
    public JButton botonLibRed;

    private final byte MEMORIA = 0;
    private final byte IMPRESORA = 1;
    private final byte DISCO = 2;
    private final byte RED = 3;
    private final byte LIBERAR_MEMORIA = 4;


    //constructor
    public ClienteFrame(MicroNucleoFrame frameNucleo) {
        super(frameNucleo, "Cliente de Recursos");
        setLayout(new GridLayout(4, 1));
        add(identificador);
        add(informador);
        add(construirPanelSur());
        validate();

        setSize(400, 500);

        procesoCliente = new ProcesoCliente(this);
        fijarProceso(procesoCliente);
    }//fin del constructor

    private JPanel construirPanelSur() {
        JPanel panelContenedor = new JPanel(new GridLayout(4, 2));

        add(BorderLayout.CENTER, panelContenedor);

        botonSolMemoria = new JButton("Solicitar Memoria");
        botonSolMemoria.addActionListener(this);
        panelContenedor.add(botonSolMemoria);

        botonLibMemoria = new JButton("Liberar Memoria");
        //botonLibMemoria.setEnabled(false);
        botonLibMemoria.addActionListener(this);
        panelContenedor.add(botonLibMemoria);

        botonSolImpresora = new JButton("Solicitar Impresora");
        botonSolImpresora.addActionListener(this);
        panelContenedor.add(botonSolImpresora);

        botonLibImpresora = new JButton("Liberar Impresora");
        //botonLibImpresora.setEnabled(false);
        botonLibImpresora.addActionListener(this);
        panelContenedor.add(botonLibImpresora);

        botonSolDisco = new JButton("Solicitar Disco");
        botonSolDisco.addActionListener(this);
        panelContenedor.add(botonSolDisco);

        botonLibDisco = new JButton("Liberar Disco");
        //botonLibDisco.setEnabled(false);
        botonLibDisco.addActionListener(this);
        panelContenedor.add(botonLibDisco);

        botonSolRed = new JButton("Solicitar Red");
        botonSolRed.addActionListener(this);
        panelContenedor.add(botonSolRed);

        botonLibRed = new JButton("Liberar Red");
        //botonLibRed.setEnabled(false);
        botonLibRed.addActionListener(this);
        panelContenedor.add(botonLibRed);

        //bloquearBotonesLiberar();

        return panelContenedor;

    }//fin del metodo construirPanelSur

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
    }//fin del metodo RecursoCadena

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSolMemoria) {
            procesoCliente.establecerRecursoSolicitado(MEMORIA, this);
            imprimeln("Solicitando: " + recursoCadena(MEMORIA));
            Nucleo.reanudarProceso(procesoCliente);
        }//fin de if
        else if (e.getSource() == botonSolImpresora) {
            procesoCliente.establecerRecursoSolicitado(IMPRESORA, this);
            imprimeln("Solicitando: " + recursoCadena(IMPRESORA));
            Nucleo.reanudarProceso(procesoCliente);
        }//fin de else if
        else if (e.getSource() == botonSolDisco) {
            procesoCliente.establecerRecursoSolicitado(DISCO, this);
            imprimeln("Solicitando: " + recursoCadena(DISCO));
            Nucleo.reanudarProceso(procesoCliente);
        }//fin de else if
        else if (e.getSource() == botonSolRed) {
            procesoCliente.establecerRecursoSolicitado(RED, this);
            imprimeln("Solicitando: " + recursoCadena(RED));
            Nucleo.reanudarProceso(procesoCliente);
        }//fin de else if
        else if (e.getSource() == botonLibMemoria) {
            procesoCliente.establecerRecursoSolicitado(LIBERAR_MEMORIA, this);
            imprimeln("Solicitando: " + recursoCadena(LIBERAR_MEMORIA));
            Nucleo.reanudarProceso(procesoCliente);

        }//fin de else if
    }//fin del metodo actionPerformed

    public void bloquearBotonesSolicitar()
    {
        botonSolMemoria.setEnabled(false);
        botonSolImpresora.setEnabled(false);
        botonSolRed.setEnabled(false);
        botonSolDisco.setEnabled(false);
    }

    public void bloquearBotonesLiberar()
    {
        botonLibDisco.setEnabled(false);
        botonLibImpresora.setEnabled(false);
        botonLibMemoria.setEnabled(false);
        botonLibRed.setEnabled(false);
    }

    public void desbloquearBotonesSol()
    {
        botonSolMemoria.setEnabled(true);
        botonSolImpresora.setEnabled(true);
        botonSolRed.setEnabled(true);
        botonSolDisco.setEnabled(true);
    }

    public void activaLiberar(byte recurso)
    {
        bloquearBotonesSolicitar();
        bloquearBotonesSolicitar();

        switch (recurso)
        {
            case MEMORIA:
                botonLibMemoria.setEnabled(true);
                break;

            case IMPRESORA:
                botonLibImpresora.setEnabled(true);
                break;

            case DISCO:
                botonLibDisco.setEnabled(true);
                break;

            case RED:
                botonLibRed.setEnabled(true);
                break;
        }//fin de switch
    }//fin del metodo activaLiberar

    public void activarSolicitar()
    {
        bloquearBotonesLiberar();
        desbloquearBotonesSol();
    }

    public void stanbyRecurso()
    {
        bloquearBotonesLiberar();
        bloquearBotonesSolicitar();
    }

}//fin de la clase ClienteFrame

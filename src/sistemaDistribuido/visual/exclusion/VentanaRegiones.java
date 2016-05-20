package sistemaDistribuido.visual.exclusion;


import sistemaDistribuido.sistema.exclusion.modoUsuario.AdministradorRecursos;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaRegiones extends ProcesoFrame{

    //atributos
    AdministradorRecursos administradorRecursos;

    public JLabel etiquetaUno;
    private JLabel etiquetaDos;
    public static JTable tablaRecursosUso;
    public JScrollPane desplazamientoRecursosUno;
    public static JTable tablaMemoria;
    private JScrollPane desplazamientoMemoria;
    public JTable tablaImpresora;
    private JScrollPane desplazamientoImpresora;
    public JTable tablaDisco;
    private JScrollPane desplazamientoDisco;
    private JTable tablaRed;
    private JScrollPane desplazamientoRed;

    //constructor
    public VentanaRegiones(MicroNucleoFrame frameNucleo) {
        super(frameNucleo, "Recursos del sistema");
        remove(informador);
        remove(identificador);

        setLayout(null);

        etiquetaUno = new JLabel("Recursos en uso");
        etiquetaUno.setBounds(200, 20, 100, 30);
        add(etiquetaUno);

        etiquetaDos = new JLabel("Procesos en cola");
        etiquetaDos.setBounds(200, 110, 100, 30);
        add(etiquetaDos);

        tablaRecursosUso = new JTable(new DefaultTableModel(new Object[][]{},
                new String[]{"Memoria", "Impresora", "Disco", "Red"}));
        tablaRecursosUso.setBounds(10, 60, 480, 60);
        add(tablaRecursosUso);

        desplazamientoRecursosUno = new JScrollPane(tablaRecursosUso);
        desplazamientoRecursosUno.setBounds(10, 60, 480, 60);
        add(desplazamientoRecursosUno);


        tablaMemoria = new JTable(new DefaultTableModel(new Object[][]{},
                new String[]{"Memoria"}));
        tablaMemoria.setBounds(10, 140, 220, 100);
        add(tablaMemoria);

        desplazamientoMemoria = new JScrollPane(tablaMemoria);
        desplazamientoMemoria.setBounds(10, 140, 220, 100);
        add(desplazamientoMemoria);

        tablaImpresora = new JTable(new DefaultTableModel(new Object[][]{},
                new String[]{"Impresora"}));
        tablaImpresora.setBounds(270, 140, 220, 100);
        add(tablaImpresora);

        desplazamientoImpresora = new JScrollPane(tablaImpresora);
        desplazamientoImpresora.setBounds(270, 140, 220, 100);
        add(desplazamientoImpresora);

        tablaDisco = new JTable(new DefaultTableModel(new Object[][]{},
                new String[]{"Disco"}));
        tablaDisco.setBounds(10, 270, 220, 100);
        add(tablaDisco);

        desplazamientoDisco = new JScrollPane(tablaDisco);
        desplazamientoDisco.setBounds(10, 270, 220, 100);
        add(desplazamientoDisco);

        tablaRed = new JTable(new DefaultTableModel(new Object[][]{},
                new String[]{"Red"}));
        tablaRed.setBounds(270, 270, 220, 100);
        add(tablaRed);

        desplazamientoRed = new JScrollPane(tablaRed);
        desplazamientoRed.setBounds(270, 270, 220, 100);
        add(desplazamientoRed);

        setResizable(false);
        setLocationRelativeTo(null);
        setSize(500, 400);
        setVisible(true);

        administradorRecursos = new AdministradorRecursos(this);
        fijarProceso(administradorRecursos);
    }//fin del constructor
}//fin de la clase VentanaRegiones

package sistemaDistribuido.visual.exclusion;

import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.PanelClienteServidor;
import sistemaDistribuido.visual.exclusion.ServidorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ExclusionFrame extends MicroNucleoFrame {

    public Button botonServidor;


    //constructor
    public ExclusionFrame() {
        setTitle("Practica 7 : Algoritmo de exclusion");

    }//fin del constructor

    @Override
    protected Panel construirPanelSur() {
        panelBotones = new PanelClienteServidor();
        panelBotones.agregarActionListener(new ManejadorBotones());
        return panelBotones;
    }//fin del metodo construirPanelSur

    class ManejadorBotones implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            Button botonCliente = panelBotones.dameBotonCliente();
            botonServidor = panelBotones.dameBotonServidor();

            if(e.getSource() == botonCliente)
            {
                levantarProcesoFrame(new ClienteFrame(ExclusionFrame.this));
            }//fin de if
            else if(e.getSource() == botonServidor)
            {
                levantarProcesoFrame(new ServidorFrame(ExclusionFrame.this));
                levantarProcesoFrame(new VentanaRegiones(ExclusionFrame.this));
                botonServidor.setEnabled(false);
            }//fin de else if
        }//fin del metodo actionPerformed
    }//fin de la clase ManejadorBotones

    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExclusionFrame exclusionFrame = new ExclusionFrame();
        exclusionFrame.setVisible(true);
        exclusionFrame.imprimeln("Ventana exclusion iniciada.");
        Nucleo.iniciarSistema(exclusionFrame, 2001, 2002, exclusionFrame);
    }//fin del metodo main
}//fin de la clase ExclusionFrame

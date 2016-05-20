package sistemaDistribuido.visual.exclusion;

import sistemaDistribuido.sistema.exclusion.modoUsuario.ProcesoServidor;
import sistemaDistribuido.visual.clienteServidor.MicroNucleoFrame;
import sistemaDistribuido.visual.clienteServidor.ProcesoFrame;

import java.awt.event.WindowEvent;

public class ServidorFrame extends ProcesoFrame{

    ExclusionFrame exclusionFrame;
    ProcesoServidor procesoServidor;


    public ServidorFrame(MicroNucleoFrame frameNucleo) {
        super(frameNucleo, "Servidor Recursos");

        procesoServidor = new ProcesoServidor(this);
        fijarProceso(procesoServidor);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        super.windowClosed(e);
        exclusionFrame.botonServidor.setEnabled(true);
    }
}

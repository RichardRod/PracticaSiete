package sistemaDistribuido.sistema.exclusion.modoUsuario;

import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;

public class AdministradorRecursos extends Proceso{



    public AdministradorRecursos(Escribano esc) {
        super(esc);
        start();
    }

    @Override
    public void run() {

    }
}

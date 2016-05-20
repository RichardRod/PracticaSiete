package sistemaDistribuido.sistema.exclusion.modoUsuario;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;
import sistemaDistribuido.visual.exclusion.ClienteFrame;

public class ProcesoCliente extends Proceso {

    //atributos
    private byte CODIGO_OPERACION;
    private final byte MEMORIA = 0;
    private final byte IMPRESORA = 1;
    private final byte DISCO = 2;
    private final byte RED = 3;

    private ClienteFrame clienteFrame;
    private byte botonSeleccionado;

    public ProcesoCliente(Escribano esc) {
        super(esc);
        start();
    }

    @Override
    public void run() {
        imprimeln("Inicio de Proceso Cliente: Exclusion");
        imprimeln("Esperando instrucciones para continuar.");

        while (continuar()) {
            Nucleo.suspenderProceso();

            byte[] solCliente = new byte[1024];
            byte[] respCliente = new byte[1024];

            //empacar codigo de operacion
            solCliente[8] = CODIGO_OPERACION;
            solCliente[9] = botonSeleccionado;

            System.out.println(CODIGO_OPERACION);

            imprimeln("Enviando mensaje");
            Nucleo.send(248, solCliente);
            imprimeln("Generando mensaje a ser enviado, llenando los campos necesarios");
            Nucleo.receive(dameID(), respCliente);
            imprimeln("Invocando a receive()");

            String respuestaDesempacada = "";
            for (int i = 0; i < respCliente.length; i++) {
                respuestaDesempacada += (char) respCliente[i];
            }
            respuestaDesempacada = respuestaDesempacada.trim();

            imprimeln("Respuesta del servidor: " + respuestaDesempacada);
            if(respuestaDesempacada.equals("Recurso asignado")) {
                //clienteFrame.bloquearBotonesSolicitar();
                //clienteFrame.activaLiberar(CODIGO_OPERACION);
                System.out.println("Entro asignado");
            }
            else if(respuestaDesempacada.equals("Recurso Ocupadoo")) {
                System.out.println("Entro ocupado");
            }
        }//fin de while

        imprimeln("Fin del proceso");

    }//fin del metodo run

    public void establecerRecursoSolicitado(byte CODIGO_OPERACION, ClienteFrame clienteFrame) {
        this.CODIGO_OPERACION = CODIGO_OPERACION;
        this.clienteFrame = clienteFrame;
    }//fin del metodo establecerRecursoSolicitado

    public ClienteFrame getClienteFrame() {
        return clienteFrame;
    }
}//fin de la clase ProcesoCliente

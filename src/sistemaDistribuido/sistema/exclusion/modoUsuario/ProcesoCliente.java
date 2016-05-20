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
    private boolean peticion;


    public ProcesoCliente(Escribano esc) {
        super(esc);
        start();
    }

    @Override
    public void run() {
        byte[] solCliente = null;
        byte[] respServidor = new byte[1024];

        imprimeln("Inicio de Proceso Cliente: Exclusion");
        imprimeln("Esperando instrucciones para continuar.");

        while (continuar()) {
            Nucleo.suspenderProceso();

            if(peticion == true)
            {
                imprimeln("Solicitando recurso: " + recursoCadena(CODIGO_OPERACION));

                switch (CODIGO_OPERACION)
                {
                    case MEMORIA:
                        solCliente = generaPaquete(Recursos.SOL_MEMORIA);
                        break;

                    case IMPRESORA:
                        solCliente = generaPaquete(Recursos.SOL_IMPRESORA);
                        break;

                    case DISCO:
                        solCliente = generaPaquete(Recursos.SOL_DISCO);
                        break;

                    case RED:
                        solCliente = generaPaquete(Recursos.SOL_RED);
                        break;
                }//fin de switch

                Nucleo.send(248, solCliente);

                imprimeln("Invocando a Receive()");
                Nucleo.receive(dameID(), respServidor);

                if(verificarDisponibilidad(respServidor))
                {
                    imprimeln("Recurso ocupado");
                    clienteFrame.stanbyRecurso();
                    imprimeln("Enviando mensaje");
                    Nucleo.send(248, generaPaquete(Recursos.ESPERAR));
                    imprimeln("Invocando a receive()");
                    Nucleo.receive(dameID(), respServidor);

                }//fin de if

                imprimeln("Recurso concedido: " + recursoCadena(CODIGO_OPERACION));
                clienteFrame.activaLiberar(CODIGO_OPERACION);

            }//fin de if
            else
            {
                imprimeln("Liberar recurso: " + recursoCadena(CODIGO_OPERACION));

                switch (CODIGO_OPERACION)
                {
                    case MEMORIA:
                        solCliente = generaPaquete(Recursos.LIB_MEMORIA);
                        break;

                    case IMPRESORA:
                        solCliente = generaPaquete(Recursos.LIB_IMPRESORA);
                        break;

                    case DISCO:
                        solCliente = generaPaquete(Recursos.LIB_DISCO);
                        break;

                    case RED:
                        solCliente = generaPaquete(Recursos.LIB_RED);
                        break;
                }//fin de switch

                imprimeln("Enviando mensaje");
                Nucleo.send(248, solCliente);

                clienteFrame.activarSolicitar();

            }//fin de else

        }//fin de while

    }//fin del metodo run

    public void establecerRecursoSolicitado(byte CODIGO_OPERACION, ClienteFrame clienteFrame) {
        this.CODIGO_OPERACION = CODIGO_OPERACION;
        this.clienteFrame = clienteFrame;
        this.peticion = true;
    }//fin del metodo establecerRecursoSolicitado

    public ClienteFrame getClienteFrame() {
        return clienteFrame;
    }

    public void liberarRecurso(short recurso)
    {
        this.peticion = false;
    }//fin del metodo liberarRecurso

    private String recursoCadena(byte recurso)
    {
        String cadena = "";

        switch (recurso)
        {
            case MEMORIA:
                cadena = "Memoria";
                break;

            case IMPRESORA:
                cadena = "Impresora";
                break;

            case DISCO:
                cadena = "Disco";
                break;

            case RED:
                cadena = "Red";
                break;
        }//fin de switch

        return cadena;
    }//fin del metodo recursoCadena

    private byte[] generaPaquete(short paquete)
    {
        byte[] solicitud = new byte[12];
        byte[] aux = new byte[2];

        switch (paquete)
        {
            case Recursos.SOL_MEMORIA:
                aux = empacarCorto(Recursos.SOL_MEMORIA);
                break;

            case Recursos.SOL_IMPRESORA:
                aux = empacarCorto(Recursos.SOL_IMPRESORA);
                break;

            case Recursos.SOL_DISCO:
                aux = empacarCorto(Recursos.SOL_DISCO);
                break;

            case Recursos.SOL_RED:
                aux = empacarCorto(Recursos.SOL_RED);
                break;

            case Recursos.LIB_MEMORIA:
                aux = empacarCorto(Recursos.LIB_MEMORIA);
                break;

            case Recursos.LIB_IMPRESORA:
                aux = empacarCorto(Recursos.LIB_IMPRESORA);
                break;

            case Recursos.LIB_DISCO:
                aux = empacarCorto(Recursos.LIB_DISCO);
                break;

            case Recursos.LIB_RED:
                aux = empacarCorto(Recursos.LIB_RED);
                break;
        }//fin de switch

        System.arraycopy(aux, 0, solicitud, 10, 2);

        return solicitud;

    }//fin del metodo empacarSolicitud

    private byte[] empacarCorto(short valor) {
        byte[] arreglo = new byte[2];

        arreglo[0] = (byte) (valor >> 8);
        arreglo[1] = (byte) valor;

        return arreglo;
    }//fin del metodo empacarCorto

    private boolean verificarDisponibilidad(byte[] mensaje)
    {
        short disponible;
        byte[] recursoDisponible = new byte[2];
        System.arraycopy(mensaje, 10, recursoDisponible, 0, 2);
        disponible = desempacarCorto(recursoDisponible);

        if(disponible == Recursos.OCUPADO)
            return true;
        else
            return false;

    }//fin del metodo verificarDisponibilidad

    private short desempacarCorto(byte[] arreglo) {
        short valor;
        valor = (short)((arreglo[1] & 0x00FF) | (arreglo[0] << 8 & 0xFF00));

        return valor;
    }//fin del metodo desempacarCorto

}//fin de la clase ProcesoCliente

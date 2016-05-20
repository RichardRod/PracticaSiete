package sistemaDistribuido.sistema.exclusion.modoUsuario;


import sistemaDistribuido.sistema.clienteServidor.modoMonitor.Nucleo;
import sistemaDistribuido.sistema.clienteServidor.modoUsuario.Proceso;
import sistemaDistribuido.util.Escribano;


public class ProcesoServidor extends Proceso {

    //atributos
    Recursos recursos;

    //constructor
    public ProcesoServidor(Escribano esc) {
        super(esc);
        start();

        recursos = new Recursos();
    }//fin del constructor

    @Override
    public void run() {

        byte[] solServidor = new byte[1024];

        imprimeln("Proceso servidor en ejecucion");

        while(continuar())
        {
            imprimeln("Invocando a receive()");
            Nucleo.receive(dameID(), solServidor);

            imprimeln("Paquete recibido");
            imprimeln("Ejecutando peticion");

            short tipoSolicitud = obtenerSolicitud(solServidor);
            int destino = obtenerCliente(solServidor);

            byte[] respuesta;

            System.out.println("Solicitud: " + tipoSolicitud);
            switch (tipoSolicitud)
            {
                case Recursos.ESPERAR: //esperando recurso
                    imprimeln("Esperando disponibilidad.");
                    break;

                case Recursos.SOL_MEMORIA: //solicitud memoria
                    if(!recursos.isMemoriaLibre())
                    {
                        establecerRecurso(Recursos.MEMORIA);
                        respuesta = empaquetarSolicitud((short)1);
                    }//fin de if
                    else
                    {
                        imprimeln("Recurso solicitado ocupado");
                        guardarSolicitud(Recursos.MEMORIA, solServidor);
                        respuesta = empaquetarSolicitud((short)2);
                    }//fin de else

                    imprimeln("Enviando respuesta al cliente.");
                    Nucleo.send(destino, respuesta);
                    break;

                case 201:

                    break;

                case 202:

                    break;

                case 203:

                    break;
            }//fin de switch


        }//fin de while

    }//fin del metodo run

    private byte[] empaquetarSolicitud(short paquete)
    {
        byte[] respuestaOcupado = new byte[12];
        byte[] respuestaLibre = new byte[2];

        imprimeln("Generando Respuesta");

        if(paquete == 1) {
            respuestaLibre = empacarCorto((short)1); //recurso libre
        } else if(paquete == 2) {
            respuestaLibre = empacarCorto((short)2); //recurso ocupado
        } else {
            imprimeln("Error: " + paquete);
        }

        System.arraycopy(respuestaLibre, 0, respuestaOcupado, 10, 2);

        return respuestaOcupado;
    }//fin del metodo empaquetarSolicitud

    private void guardarSolicitud(int tipoRecurso, byte[] mensaje)
    {
        byte[] solicitud = new byte[mensaje.length];
        System.arraycopy(mensaje, 0, solicitud, 0, mensaje.length);

        switch (tipoRecurso)
        {
            case Recursos.MEMORIA:
                recursos.encolarMemoria(solicitud);
                break;

            case Recursos.IMPRESORA:
                recursos.encolarImpresora(solicitud);
                break;

            case Recursos.DISCO:
                recursos.encolarDisco(solicitud);
                break;

            case Recursos.RED:
                recursos.encolarRed(solicitud);
                break;
        }//fin de switch

    }//fin del metodo guardarSolicitud

    private short obtenerSolicitud(byte[] solicitud)
    {
        short tipoMensaje;
        byte[] mensaje = new byte[2];

        System.arraycopy(solicitud, 10, mensaje, 0, 2);
        tipoMensaje = desempacarCorto(mensaje);

        return tipoMensaje;
    }//fin del metodo obtenerSolicitud

    private void enviarMensaje(int destino, byte[] mensaje)
    {
        imprimeln("Enviando mensaje");
        Nucleo.send(destino, mensaje);
    }//fin del metodo enviarMensaje

    private void establecerRecurso(short recurso)
    {
        imprimeln("Estableciendo recurso");

        switch (recurso)
        {
            case Recursos.MEMORIA:
                imprimeln("Memoria asignada");
                recursos.setMemoriaLibre(true);
                break;

            case Recursos.IMPRESORA:
                imprimeln("Impresora asignada");
                recursos.setImpresoraLibre(true);
                break;

            case Recursos.DISCO:
                imprimeln("Disco asignada");
                recursos.setDiscoLibre(true);
                break;

            case Recursos.RED:
                imprimeln("Red asignada");
                recursos.setRedLibre(true);
                break;
        }//fin de switch
    }//fin del metodo establecerRecurso

    private void liberarRecurso(short recurso)
    {
        imprimeln("Liberando recurso");

        switch (recurso)
        {
            case Recursos.MEMORIA:
                imprimeln("Memoria liberada");
                recursos.setMemoriaLibre(false);
                break;

            case Recursos.IMPRESORA:
                imprimeln("Impresora liberada");
                recursos.setImpresoraLibre(false);
                break;

            case Recursos.DISCO:
                imprimeln("Disco liberado");
                recursos.setDiscoLibre(false);
                break;

            case Recursos.RED:
                imprimeln("Red liberada");
                recursos.setRedLibre(false);
                break;
        }//fin de switch
    }//fin del metodo liberarRecurso

    private byte[] empacarCorto(short valor) {
        byte[] arreglo = new byte[2];

        arreglo[0] = (byte) (valor >> 8);
        arreglo[1] = (byte) valor;

        return arreglo;
    }//fin del metodo empacarCorto

    private short desempacarCorto(byte[] arreglo) {
        short valor;
        valor = (short)((arreglo[1] & 0x00FF) | (arreglo[0] << 8 & 0xFF00));

        return valor;
    }//fin del metodo desempacarCorto

    private int obtenerCliente(byte[] solicitud) {
        byte[] origen = new byte[4];
        System.arraycopy(solicitud, 0, origen, 0, 4);
        return desempacarEntero(origen);
    }

    private int desempacarEntero(byte[] arreglo) {
        int valor = (int)((arreglo[3] & 0x000000FF) | (arreglo[2] << 8 & 0x0000FF00) | (arreglo[1] << 16 & 0x00FF0000) | (arreglo[0] << 24 & 0xFF000000));

        return valor;
    }

}//fin de la clase ProcesoServidor

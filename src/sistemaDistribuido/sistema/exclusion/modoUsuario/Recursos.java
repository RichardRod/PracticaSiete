package sistemaDistribuido.sistema.exclusion.modoUsuario;


import java.util.LinkedList;

public class Recursos {

    //atributos
    private LinkedList<byte[]> colaMemoria;
    private LinkedList<byte[]> colaImpresora;
    private LinkedList<byte[]> colaDisco;
    private LinkedList<byte[]> colaRed;

    private boolean memoriaLibre;
    private boolean impresoraLibre;
    private boolean discoLibre;
    private boolean redLibre;

    //recursos
    public static final short MEMORIA = 0;
    public static final short IMPRESORA = 1;
    public static final short DISCO = 2;
    public static final short RED = 3;

    //tipos de solicitud
    public static final short SOL_MEMORIA = 4;
    public static final short SOL_IMPRESORA = 5;
    public static final short SOL_DISCO = 6;
    public static final short SOL_RED = 7;
    public static final short LIB_MEMORIA = 8;
    public static final short LIB_IMPRESORA = 9;
    public static final short LIB_DISCO = 10;
    public static final short LIB_RED = 11;

    public static final short OCUPADO = 12;
    public static final short ESPERAR = 13;

    //constructor
    public Recursos() {
        colaMemoria = new LinkedList<>();
        colaImpresora = new LinkedList<>();
        colaDisco = new LinkedList<>();
        colaRed = new LinkedList<>();
    }//fin del constructor

    public void encolarMemoria(byte[] solicitud) {
        colaMemoria.offer(solicitud);
    }//fin del metodo encolarMemoria

    public byte[] desencolarMemoria() {
       return colaMemoria.poll();
    }

    public void encolarImpresora(byte[] solicitud){
        colaImpresora.offer(solicitud);
    }

    public byte[] desencolarImpresora(){
        return colaImpresora.poll();
    }

    public void encolarDisco(byte[] solicitud){
        colaDisco.offer(solicitud);
    }

    public byte[] desencolarDisco(){
        return colaDisco.poll();
    }

    public void encolarRed(byte[] solicitud){
        colaRed.offer(solicitud);
    }

    public byte[] desencolarRed(){
        return colaRed.poll();
    }

    public void setImpresoraLibre(boolean impresoraLibre) {
        this.impresoraLibre = impresoraLibre;
    }

    public boolean isImpresoraLibre() {
        return impresoraLibre;
    }

    public void setDiscoLibre(boolean discoLibre) {
        this.discoLibre = discoLibre;
    }

    public boolean isDiscoLibre() {
        return discoLibre;
    }

    public void setMemoriaLibre(boolean memoriaLibre) {
        this.memoriaLibre = memoriaLibre;
    }

    public boolean isMemoriaLibre() {
        return memoriaLibre;
    }

    public void setRedLibre(boolean redLibre) {
        this.redLibre = redLibre;
    }

    public boolean isRedLibre() {
        return redLibre;
    }

}//fin de la clase Recursos

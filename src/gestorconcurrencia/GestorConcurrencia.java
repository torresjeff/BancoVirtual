/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorconcurrencia;


import Utils.TipoProducto;
import Utils.TipoTransaccion;
import Utils.Transaccion;
import banco.IBanco;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manuela
 */
public class GestorConcurrencia extends UnicastRemoteObject implements IGestorConcurrencia {
    
    private String path;
    public final static String VISA_IP="127.0.0.1";
    public final static String VISA_PUERTO="1099";
    public final static String MASTER_IP="127.0.0.1";
    public final static String MASTER_PUERTO="1099";
    public final static String AHORRO_IP="127.0.0.1";
    public final static String AHORRO_PUERTO="1099";
    public final static String CORRIENTE_IP="127.0.0.1";
    public final static String CORRIENTE_PUERTO="1099";
    
    private IBanco bancoVisas;
    private IBanco bancoMastercards;
    private IBanco bancoAhorros;
    private IBanco bancoCorrientes;
    private long transaccionesTotales;
    
    public GestorConcurrencia(String path) throws RemoteException {
        super();
        
        try {
            this.path = path;
            Naming.rebind(path, this);
            
            
            bancoVisas = (IBanco) Naming.lookup("//"+VISA_IP+":"+VISA_PUERTO+"/Visa");
            //bancoMastercards = (IBanco) Naming.lookup("//"+MASTER_IP+":"+MASTER_PUERTO+"/Mastercard");
            //bancoAhorros = (IBanco) Naming.lookup("//"+AHORRO_IP+":"+AHORRO_PUERTO+"/Ahorro");
            //bancoCorrientes = (IBanco) Naming.lookup("//"+CORRIENTE_IP+":"+CORRIENTE_PUERTO+"/Corriente");
            
            transaccionesTotales = 0;
        } catch (MalformedURLException ex) {
            Logger.getLogger(GestorConcurrencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(GestorConcurrencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Transaccion crearTransaccion(String usuario, TipoTransaccion tipoTransaccion, TipoProducto tipoProducto) {
        transaccionesTotales++;
        Transaccion t = new Transaccion(usuario, transaccionesTotales, tipoTransaccion, tipoProducto);
        return t;
    }
    
    //Retorna el ID de la transaccion
    @Override
    public long abrirTransaccion(String idUsuario, String password) throws RemoteException {
        
        System.out.println("Iniciando sesion: idUsuario: " + idUsuario + ", pass: " + password);
        if (bancoVisas == null && bancoMastercards == null && bancoAhorros == null && bancoCorrientes == null) {
            return -1;
        }
        if (bancoVisas != null && !bancoVisas.iniciarSesion(idUsuario, password))
        {
            return -1;
        }
        if (bancoMastercards != null && !bancoMastercards.iniciarSesion(idUsuario, password))
        {
            return -1;
        }
        if (bancoAhorros != null && !bancoAhorros.iniciarSesion(idUsuario, password))
        {
            return -1;
        }
        if (bancoCorrientes != null && !bancoCorrientes.iniciarSesion(idUsuario, password))
        {
            return -1;
        }
        
        Transaccion t = crearTransaccion(idUsuario, TipoTransaccion.LECTURA, TipoProducto.CUENTA_USUARIO);
        t.setUsuario(idUsuario);
        return t.getId();
    }
    

    @Override
    public double consultar(String idUsuario, TipoProducto tipo, int numeroProducto) throws RemoteException {
        Transaccion t = crearTransaccion(idUsuario, TipoTransaccion.LECTURA, tipo);
        t.setUsuario(idUsuario);
        
        switch (tipo)
        {
            case TARJETA_VISA:
                return bancoVisas.consultar(idUsuario, tipo, t);
            case TARJETA_MASTERCARD:
                return bancoMastercards.consultar(idUsuario, tipo, t);
            case CUENTA_AHORRO:
                return bancoAhorros.consultar(idUsuario, tipo, t);
            case CUENTA_CORRIENTE:
                return bancoCorrientes.consultar(idUsuario, tipo, t);
        }
        //TODO: quitar de lista de activas y pasar a validando
        return -1;
    }

    @Override
    public double retirar(String idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) throws RemoteException {
        
        Transaccion t = crearTransaccion(idUsuario, TipoTransaccion.ESCRITURA, tipo);
        t.setUsuario(idUsuario);
        
        switch (tipo)
        {
            case TARJETA_VISA:
                if (bancoVisas.retirar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
            case TARJETA_MASTERCARD:
                if (bancoMastercards.retirar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
            case CUENTA_AHORRO:
                if (bancoAhorros.retirar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
            case CUENTA_CORRIENTE:
                if (bancoCorrientes.retirar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
        }
        
        return -1;
    }

    @Override
    public double depositar(String idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) throws RemoteException {
        Transaccion t = crearTransaccion(idUsuario, TipoTransaccion.ESCRITURA, tipo);
        t.setUsuario(idUsuario);
        
        switch (tipo)
        {
            case TARJETA_VISA:
                if (bancoVisas.depositar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
            case TARJETA_MASTERCARD:
                if (bancoMastercards.depositar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
            case CUENTA_AHORRO:
                if (bancoAhorros.depositar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
            case CUENTA_CORRIENTE:
                if (bancoCorrientes.depositar(idUsuario, tipo, cantidad, t, numeroProducto)) {
                    return cantidad;
                }
                break;
        }
        
        //TODO: quitar de lista de activas y pasar a validando
        
        return -1;
    }
    
    
}

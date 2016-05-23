/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import Utils.Auth;
import Utils.CuentaAhorro;
import Utils.CuentaCorriente;
import Utils.EstadoTransaccion;
import Utils.Producto;
import Utils.TarjetaMasterCard;
import Utils.TarjetaVisa;
import Utils.TipoProducto;
import Utils.TipoTransaccion;
import Utils.Transaccion;
import Utils.Usuario;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author manuela
 */
public class MainBanco extends UnicastRemoteObject implements IBanco{
    private static final String IP = "127.0.0.1"; //Test misma maquina
    private static final int PUERTO = 1099;
    
    private ArrayList<Auth> auths;
    private ArrayList<Usuario> usuarios;
    private ArrayList<TarjetaVisa> visas;
    private ArrayList<TarjetaMasterCard> mastercards;
    private ArrayList<CuentaAhorro> ahorros;
    private ArrayList<CuentaCorriente> corrientes;
    
    private ConcurrentHashMap<Transaccion, Trans> transaccionesActivas;
    private ConcurrentHashMap<Transaccion, Producto> transaccionesValidando;
    private ConcurrentHashMap<Transaccion, Producto> transaccionesConsumadas;

    
    
    private class Trans {
        public TipoProducto recursoAfectado;
        public TipoTransaccion tipoTransaccion;

        public Trans(TipoProducto recursoAfectado, TipoTransaccion tipoTransaccion) {
            this.recursoAfectado = recursoAfectado;
            this.tipoTransaccion = tipoTransaccion;
        }
    }
    
    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", IP);
            LocateRegistry.createRegistry(PUERTO);
            final String url = "rmi://"+IP+":"+PUERTO+"/Visa";
            
            Naming.rebind(url, new MainBanco());
            
            System.out.println("El banco de Visas está ejecutandose....");
            //for(;;) LockSupport.park();
        } catch (RemoteException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public MainBanco() throws RemoteException {
        auths = new ArrayList<>();
        usuarios = new ArrayList<>();
        visas = new ArrayList<>();
        mastercards = new ArrayList<>();
        ahorros = new ArrayList<>();
        corrientes = new ArrayList<>();
        
        transaccionesActivas = new ConcurrentHashMap<>();
        transaccionesValidando = new ConcurrentHashMap<>();
        transaccionesConsumadas = new ConcurrentHashMap<>();
        
        agregarUsuarios();
        agregarProductos();
        System.out.println("Banco creado");
        System.out.println("Auths: " + auths);
    }
    
    private void agregarUsuarios() {
        System.out.println("agregarUsuarios()");
        Auth auth = new Auth("jeff", "abcd");
        auths.add(auth);
    }
    
    private void agregarProductos() {
        int numeroProducto = 1;
        
        for (Auth auth : auths) {
            Usuario u = new Usuario(auth.getId());
            TarjetaVisa tv = new TarjetaVisa(numeroProducto, numeroProducto * 10000, u.getUsuario());
            u.setTarjetaVisa(tv);
            TarjetaMasterCard tm = new TarjetaMasterCard(numeroProducto, numeroProducto * 20000, u.getUsuario());
            u.setTarjetaMastercard(tm);
            CuentaAhorro ca = new CuentaAhorro(numeroProducto, numeroProducto * 30000, u.getUsuario());
            u.setCuentaAhorros(ca);
            CuentaCorriente cc = new CuentaCorriente(numeroProducto, numeroProducto * 40000, u.getUsuario());
            u.setCuentaCorrientes(cc);
            
            usuarios.add(u);
            visas.add(tv);
            mastercards.add(tm);
            ahorros.add(ca);
            corrientes.add(cc);
            numeroProducto++;
        }
        
        System.out.println("\t" + usuarios);
    }

    @Override
    public boolean iniciarSesion(String usuario, String password) throws RemoteException {
        for (Auth auth : auths) {
            if (auth.getId().equals(usuario) && auth.getPasword().equals(password)) {
                System.out.println("Login aceptado");
                return true;
            }
        }
        
        System.out.println("Login rechazado");
        return false;
    }

    @Override
    public double consultar(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        System.out.println("El usuario \"" + usuario + "\" desea consultar el saldo de su " + tipoProducto.toString());
        
        switch (tipoProducto) {
            case TARJETA_VISA:
                for (TarjetaVisa visa : visas) {
                    if (visa.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        System.out.println("\tSaldo: " + visa.consultar());
                        return visa.consultar();
                    }
                }
            case TARJETA_MASTERCARD:
                for (TarjetaMasterCard mastercard : mastercards) {
                    if (mastercard.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        System.out.println("\tSaldo: " + mastercard.consultar());
                        return mastercard.consultar();
                    }
                }
            case CUENTA_AHORRO:
                for (CuentaAhorro ahorro : ahorros) {
                    if (ahorro.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        System.out.println("\tSaldo: " + ahorro.consultar());
                        return ahorro.consultar();
                    }
                }
            case CUENTA_CORRIENTE:
                for (CuentaCorriente corriente : corrientes) {
                    if (corriente.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        System.out.println("\tSaldo: " + corriente.consultar());
                        return corriente.consultar();
                    }
                }
        }
        return -1;
    }

    @Override
    public boolean retirar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t, int numeroProducto) throws RemoteException {
        System.out.println("El usuario \"" + usuario + "\" desea retirar " + cantidad + " pesos de su " + tipoProducto.toString());
        
        switch (tipoProducto) {
            case TARJETA_VISA:
                for (TarjetaVisa producto : visas) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        producto.retirar(cantidad);
                        System.out.println("\tNuevo saldo: " + producto.getSaldo());
                        return true;
                    }
                }
                break;
            case TARJETA_MASTERCARD:
                for (TarjetaMasterCard producto : mastercards) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        producto.retirar(cantidad);
                        System.out.println("\tNuevo saldo: " + producto.getSaldo());
                        return true;
                    }
                }
                break;
            case CUENTA_AHORRO:
                for (CuentaAhorro producto : ahorros) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        producto.retirar(cantidad);
                        System.out.println("\tNuevo saldo: " + producto.getSaldo());
                        return true;
                    }
                }
                break;
            case CUENTA_CORRIENTE:
                for (CuentaCorriente producto : corrientes) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        producto.retirar(cantidad);
                        System.out.println("\tNuevo saldo: " + producto.getSaldo());
                        return true;
                    }
                }
                break;
        }
        System.out.println("\tSaldo insuficiente");
        return false;
    }

    @Override
    public boolean depositar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t, int numeroProducto) throws RemoteException {
        System.out.println("El usuario \"" + usuario + "\" desea depositar " + cantidad + " pesos de su " + tipoProducto.toString());
        Trans trans = new Trans(tipoProducto, t.getTipoTransaccion());
        transaccionesActivas.put(t, trans);
        switch (tipoProducto) {
            case TARJETA_VISA:
                for (TarjetaVisa producto : visas) {
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        try {
                            Producto p = (Producto)producto.clone();
                            p.depositar(cantidad);
                            
                            System.out.println("\tp = " + p);
                            System.out.println("\tvisas = " + visas);
                            System.out.println("\tNuevo saldo: " + producto.getSaldo());
                            
                            transaccionesActivas.remove(t);
                            if (transaccionesActivas.contains(t)) {
                                System.out.println("\tNo se pudo remover la transaccion " + t);
                            }
                            else {
                                System.out.println("\tPasando la transaccion a estado de Validacion");
                            }
                            
                            transaccionesValidando.put(t, p);
                            t.setEstado(EstadoTransaccion.VALIDANDO);
                            return validar(t);
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
            case TARJETA_MASTERCARD:
                for (TarjetaMasterCard producto : mastercards) {
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        try {
                            Producto p = (Producto)producto.clone();
                            p.depositar(cantidad);
                            transaccionesValidando.put(t, p);
                            System.out.println("\tp = " + p);
                            System.out.println("\tmastercards = " + mastercards);
                            System.out.println("\tNuevo saldo: " + producto.getSaldo());
                            t.setEstado(EstadoTransaccion.VALIDANDO);
                            return true;
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                break;
            case CUENTA_AHORRO:
                for (CuentaAhorro producto : ahorros) {
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        producto.depositar(cantidad);
                        System.out.println("\tNuevo saldo: " + producto.getSaldo());
                        return true;
                    }
                }
                break;
            case CUENTA_CORRIENTE:
                for (CuentaCorriente producto : corrientes) {
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        producto.depositar(cantidad);
                        System.out.println("\tNuevo saldo: " + producto.getSaldo());
                        return true;
                    }
                }
                break;
        }
        System.out.println("\tNo se puedo realizar el deposito");
        return false;
    }
    
    private boolean mismoUsuarioYProducto(Transaccion tA, Transaccion tV) {
        return tA.getUsuario().equals(tV.getUsuario()) && (tA.getRecursoAfectado() == tV.getRecursoAfectado());
    }
    
    private boolean hayConflicto(Transaccion t) {
        
        for (Map.Entry<Transaccion, Trans> pair : transaccionesActivas.entrySet()) {
            if ( mismoUsuarioYProducto(pair.getKey(), t) ) {
                if (t.getTipoTransaccion() == TipoTransaccion.ESCRITURA && pair.getKey().getTipoTransaccion() == TipoTransaccion.LECTURA) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean validar(Transaccion t) {
        if (!hayConflicto(t)) {
            try {
                if (commit(t)) {
                    System.out.println("Se hizo commit: " + visas);
                    return true;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    

    @Override
    public boolean puedeCommit(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Producto buscarProducto(String usuario, TipoProducto tipoProducto) {
        switch (tipoProducto) {
            case CUENTA_AHORRO:
                for (CuentaAhorro producto : ahorros) {
                    if (producto.getUsuario().equals(usuario)) {
                        return producto;
                    }
                }
                return null;
            case CUENTA_CORRIENTE:
                for (CuentaCorriente producto : corrientes) {
                    if (producto.getUsuario().equals(usuario)) {
                        return producto;
                    }
                }
                return null;
            case TARJETA_MASTERCARD:
                for (TarjetaMasterCard producto : mastercards) {
                    if (producto.getUsuario().equals(usuario)) {
                        return producto;
                    }
                }
                return null;
            case TARJETA_VISA:
                for (TarjetaVisa producto : visas) {
                    if (producto.getUsuario().equals(usuario)) {
                        return producto;
                    }
                }
                return null;
        }
        return null;
    }
    
    @Override
    public boolean commit(Transaccion t) throws RemoteException {
        Producto p = buscarProducto(t.getUsuario(), t.getRecursoAfectado());
        p = transaccionesValidando.get(t);
        if (transaccionesValidando.remove(t) == null) {
            return false;
        }
        
        t.setEstado(EstadoTransaccion.ACTUALIZANDO);
        transaccionesConsumadas.put(t, p);
        return true;
    }

    @Override
    public boolean rollback(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

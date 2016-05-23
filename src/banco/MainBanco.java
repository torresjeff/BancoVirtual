/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import Utils.Auth;
import Utils.CuentaAhorro;
import Utils.CuentaCorriente;
import Utils.TarjetaMasterCard;
import Utils.TarjetaVisa;
import Utils.TipoProducto;
import Utils.Transaccion;
import Utils.Usuario;
import gestorconcurrencia.IGestorConcurrencia;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;
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
        System.out.println("agregarProductos()");
        int numeroProducto = 1;
        if (auths == null) {
            System.out.println("\tAuths es null");
        }
        if (usuarios == null) {
            System.out.println("\tUsuarios es null");
        }
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
        System.out.println("Iniciando sesion: idUsuario: " + usuario + ", pass: " + password);
        if (auths == null) {
            System.out.println("Auths es null");
        }
        if (usuarios == null) {
            System.out.println("Usuarios es null");
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retirar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean depositar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean puedeCommit(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean commit(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean rollback(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

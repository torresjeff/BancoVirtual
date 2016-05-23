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
import Utils.TarjetaMasterCard;
import Utils.TarjetaVisa;
import Utils.TipoProducto;
import Utils.Transaccion;
import Utils.Usuario;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author torre
 */
public class Banco implements IBanco, Serializable {
    
    private ArrayList<Auth> auths;
    private ArrayList<Usuario> usuarios;
    private ArrayList<TarjetaVisa> visas;
    private ArrayList<TarjetaMasterCard> mastercards;
    private ArrayList<CuentaAhorro> ahorros;
    private ArrayList<CuentaCorriente> corrientes;
    private String path;
    
    public Banco(String path) throws RemoteException {
        super();
        
        try {
            this.path = path;
            Naming.rebind(path, this);
            
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
        } catch (MalformedURLException ex) {
            Logger.getLogger(Banco.class.getName()).log(Level.SEVERE, null, ex);
        }
            
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
            System.out.println("Auths es null");
        }
        if (usuarios == null) {
            System.out.println("Usuarios es null");
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
        System.out.println("El usuario \"" + usuario + "\" desea consultar el saldo de su " + tipoProducto.toString());
        
        switch (tipoProducto) {
            case TARJETA_VISA:
                for (TarjetaVisa visa : visas) {
                    if (visa.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        return visa.consultar();
                    }
                }
            case TARJETA_MASTERCARD:
                for (TarjetaMasterCard mastercard : mastercards) {
                    if (mastercard.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        return mastercard.consultar();
                    }
                }
            case CUENTA_AHORRO:
                for (CuentaAhorro ahorro : ahorros) {
                    if (ahorro.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        return ahorro.consultar();
                    }
                }
            case CUENTA_CORRIENTE:
                for (CuentaCorriente corriente : corrientes) {
                    if (corriente.getUsuario().equals(usuario)) {
                        t.setEstado(EstadoTransaccion.VALIDANDO);
                        return corriente.consultar();
                    }
                }
        }
        return -1;
    }

    @Override
    public boolean retirar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t, int numeroProducto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean depositar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t, int numeroProducto) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean puedeCommit(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean commit(Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean rollback(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

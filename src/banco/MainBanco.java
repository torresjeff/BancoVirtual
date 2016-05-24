/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import Utils.Auth;
import Utils.Cuenta;
import Utils.CuentaAhorro;
import Utils.CuentaCorriente;
import Utils.EstadoTransaccion;
import Utils.ManejadorArchivos;
import Utils.Producto;
import Utils.TarjetaMasterCard;
import Utils.TarjetaVisa;
import Utils.TipoProducto;
import Utils.TipoTransaccion;
import Utils.Transaccion;
import Utils.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author manuela
 */
public class MainBanco extends UnicastRemoteObject implements IBanco{
    public static final String VISA_IP = "127.0.0.1"; //Test misma maquina
    public static final int VISA_PUERTO = 1099;
    public final static String MASTER_IP="127.0.0.1";
    public final static int MASTER_PUERTO = 1099;
    public final static String AHORRO_IP="127.0.0.1";
    public final static int AHORRO_PUERTO = 1099; 
    public final static String CORRIENTE_IP = "127.0.0.1";
    public final static int CORRIENTE_PUERTO = 1099;
    
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
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(reader);
            
            //System.setProperty("java.rmi.server.hostname", VISA_IP);
            //LocateRegistry.createRegistry(VISA_PUERTO);
            
            String url;
            System.out.println("Ingrese el tipo de banco: ");
            System.out.println("1. Visa");
            System.out.println("2. MasterCard");
            System.out.println("3. Cuentas de Ahorro");
            System.out.println("4. Cuentas Corrientes");
            
            int tipo = Integer.parseInt(in.readLine());
            switch (tipo) {
                case 1:
                    url = "rmi://"+VISA_IP+":"+VISA_PUERTO+"/Visa";
                    LocateRegistry.createRegistry(VISA_PUERTO);
                    break;
                case 2:
                    url = "rmi://"+MASTER_IP+":"+MASTER_PUERTO+"/Mastercard";
                    LocateRegistry.createRegistry(MASTER_PUERTO);
                    break;
                case 3:
                    url = "rmi://"+AHORRO_IP+":"+AHORRO_PUERTO+"/Ahorro";
                    LocateRegistry.createRegistry(AHORRO_PUERTO);
                    break;
                case 4:
                    url = "rmi://"+CORRIENTE_IP+":"+CORRIENTE_PUERTO+"/Corriente";
                    LocateRegistry.createRegistry(CORRIENTE_PUERTO);
                    break;
                default:
                    url = "invalid";
                    System.out.println("Opcion invalida");
            }
            System.out.println("URL: " + url);
            
            Naming.rebind(url, new MainBanco());
            
            System.out.println("El servicio del banco se está ejecutando....");
            //for(;;) LockSupport.park();
        } catch (RemoteException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
        //Auth auth = new Auth("jeff", "abcd");
        
        auths.addAll(ManejadorArchivos.leerUsuarios("usr.txt"));
        //auths.add(auth);
    }
    
    private void agregarProductos() {
        //int numeroProducto = 1;
        /*for (Auth auth : auths) {
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
        }*/
        
        visas.addAll(ManejadorArchivos.leerVisa("visas.txt"));
        mastercards.addAll(ManejadorArchivos.leerMasterCard("mastercards.txt"));
        ahorros.addAll(ManejadorArchivos.leerAhorros("ahorros.txt"));
        corrientes.addAll(ManejadorArchivos.leerCorriente("corrientes.txt"));
        
        System.out.println("\tvisas: " + visas);
        System.out.println("\tmastercards: " + mastercards);
        System.out.println("\tahorros: " + ahorros);
        System.out.println("\tcorrientes: " + corrientes);
        
        for (Auth auth : auths) {
            Usuario u = new Usuario(auth.getId());
            
            //Buscar la tarjeta Visa del usuario
            for (TarjetaVisa producto : visas) {
                if (producto.getUsuario().equals(u.getUsuario())) {
                    u.setTarjetaVisa(producto);
                    break;
                }
            }
            
            //Buscar la tarjeta MasterCard del usuario
            for (TarjetaMasterCard producto : mastercards) {
                if (producto.getUsuario().equals(u.getUsuario())) {
                    u.setTarjetaMastercard(producto);
                    break;
                }
            }
            
            //Buscar la cuenta de ahorros del usuario
            for (CuentaAhorro producto : ahorros) {
                if (producto.getUsuario().equals(u.getUsuario())) {
                    u.setCuentaAhorros(producto);
                    break;
                }
            }
            
            //Buscar la cuenta de ahorros del usuario
            for (CuentaCorriente producto : corrientes) {
                if (producto.getUsuario().equals(u.getUsuario())) {
                    u.setCuentaCorrientes(producto);
                    break;
                }
            }
            
            usuarios.add(u);
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
        Trans trans = new Trans(tipoProducto, t.getTipoTransaccion());
        transaccionesActivas.put(t, trans);
        switch (tipoProducto) {
            case TARJETA_VISA:
                for (TarjetaVisa producto : visas) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        try {
                            /*t.setEstado(EstadoTransaccion.VALIDANDO);
                            producto.retirar(cantidad);
                            System.out.println("\tNuevo saldo: " + producto.getSaldo());
                            return true;*/
                            Producto p = (Producto)producto.clone();
                            p.retirar(cantidad);
                            
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tvisas = " + visas);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            
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
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        try {
                            /*t.setEstado(EstadoTransaccion.VALIDANDO);
                            producto.retirar(cantidad);
                            System.out.println("\tNuevo saldo: " + producto.getSaldo());
                            return true;*/
                            Producto p = (Producto)producto.clone();
                            p.retirar(cantidad);
                            
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tmastercards = " + mastercards);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            
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
            case CUENTA_AHORRO:
                for (CuentaAhorro producto : ahorros) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        try {
                            /*t.setEstado(EstadoTransaccion.VALIDANDO);
                            producto.retirar(cantidad);
                            System.out.println("\tNuevo saldo: " + producto.getSaldo());
                            return true;*/
                            Producto p = (Producto)producto.clone();
                            p.retirar(cantidad);
                            
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tahorros = " + ahorros);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            
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
            case CUENTA_CORRIENTE:
                for (CuentaCorriente producto : corrientes) {
                    if (producto.getUsuario().equals(usuario) && producto.getSaldo() >= cantidad) {
                        try {
                            /*t.setEstado(EstadoTransaccion.VALIDANDO);
                            producto.retirar(cantidad);
                            System.out.println("\tNuevo saldo: " + producto.getSaldo());
                            return true;*/
                            Producto p = (Producto)producto.clone();
                            p.retirar(cantidad);
                            
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tcorrientes = " + corrientes);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            
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
        }
        System.out.println("\tSaldo insuficiente");
        return false;
    }

    @Override
    public boolean depositar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t, int numeroProducto) throws RemoteException {
        System.out.println("El usuario \"" + usuario + "\" desea depositar " + cantidad + " pesos de su " + tipoProducto.toString());
        Trans trans = new Trans(tipoProducto, t.getTipoTransaccion());
        transaccionesActivas.put(t, trans);
        if (visas == null) {
            System.out.println("\tvisas = null");
        }
        switch (tipoProducto) {
            case TARJETA_VISA:
                for (TarjetaVisa producto : visas) {
                    if (producto == null) {
                        System.out.println("\tproducto = null");
                    }
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        try {
                            Producto p = (Producto)producto.clone();
                            p.depositar(cantidad);
                            
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tvisas = " + visas);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            
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
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tmastercards = " + mastercards);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            t.setEstado(EstadoTransaccion.VALIDANDO);
                            
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
            case CUENTA_AHORRO:
                for (CuentaAhorro producto : ahorros) {
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        try {
                            Producto p = (Producto)producto.clone();
                            p.depositar(cantidad);
                            transaccionesValidando.put(t, p);
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tmastercards = " + mastercards);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            t.setEstado(EstadoTransaccion.VALIDANDO);
                            
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
            case CUENTA_CORRIENTE:
                for (CuentaCorriente producto : corrientes) {
                    if (producto.getUsuario().equals(usuario) && cantidad > 0) {
                        try {
                            Producto p = (Producto)producto.clone();
                            p.depositar(cantidad);
                            transaccionesValidando.put(t, p);
                            System.out.println("\tcopia temporal = " + p);
                            System.out.println("\tmastercards = " + mastercards);
                            System.out.println("\tNuevo saldo: " + p.getSaldo());
                            t.setEstado(EstadoTransaccion.VALIDANDO);
                            
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
        }
        System.out.println("\tNo se puedo realizar el deposito");
        return false;
    }
    
    private boolean mismoUsuarioYProducto(Transaccion tA, Transaccion tV) {
        return tA.getUsuario().equals(tV.getUsuario()) && (tA.getRecursoAfectado() == tV.getRecursoAfectado());
    }
    
    private boolean hayConflicto(Transaccion Tv) {
        
        for (Map.Entry<Transaccion, Trans> pair : transaccionesActivas.entrySet()) {
            if ( mismoUsuarioYProducto(pair.getKey(), Tv) ) {
                if (Tv.getTipoTransaccion() == TipoTransaccion.ESCRITURA && pair.getKey().getTipoTransaccion() == TipoTransaccion.LECTURA) {
                    System.out.println("Existen conflictos. Abortando transaccion.");
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
                    if (visas == null) {
                        System.out.println("\tvisas = null"); 
                    }
                    System.out.println("\tSe hizo commit: ");
                    System.out.println("\t\tvisas: " + visas);
                    System.out.println("\t\tmastercards: " + mastercards);
                    System.out.println("\t\tahorros: " + ahorros);
                    System.out.println("\t\tcorrientes: " + corrientes);
                    return true;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        transaccionesValidando.remove(t);
        System.out.println("\tNo se pudo commit");
        return false;
    }
    
    
    
    private int buscarProducto(String usuario, TipoProducto tipoProducto) {
        int i = 0;
        switch (tipoProducto) {
            case CUENTA_AHORRO:
                for (CuentaAhorro producto : ahorros) {
                    if (producto.getUsuario().equals(usuario)) {
                        return i;
                    }
                    i++;
                }
                return -1;
            case CUENTA_CORRIENTE:
                for (CuentaCorriente producto : corrientes) {
                    if (producto.getUsuario().equals(usuario)) {
                        return i;
                    }
                    i++;
                }
                return -1;
            case TARJETA_MASTERCARD:
                for (TarjetaMasterCard producto : mastercards) {
                    if (producto.getUsuario().equals(usuario)) {
                        return i;
                    }
                    i++;
                }
                return -1;
            case TARJETA_VISA:
                for (TarjetaVisa producto : visas) {
                    if (producto.getUsuario().equals(usuario)) {
                        return i;
                    }
                    i++;
                }
                return -1;
        }
        return -1;
    }
    
    @Override
    public boolean commit(Transaccion t) throws RemoteException {
        try {
            int index = buscarProducto(t.getUsuario(), t.getRecursoAfectado());
            if (index == -1) {
                return false;
            }
            
            Producto p = transaccionesValidando.remove(t);
            if (p == null) {
                return false;
            }
            
            switch (t.getRecursoAfectado()) {
                case CUENTA_AHORRO:
                    //ahorros.set(index, (CuentaAhorro)p);
                    ahorros.set(index, new CuentaAhorro((Cuenta)p));
                    ManejadorArchivos.escribirAhorros("ahorros.txt", ahorros);
                    break;
                case CUENTA_CORRIENTE:
                    //corrientes.set(index, (CuentaCorriente)p);
                    corrientes.set(index, new CuentaCorriente((Cuenta)p));
                    ManejadorArchivos.escribirCorriente("corrientes.txt", corrientes);
                    break;
                case TARJETA_MASTERCARD:
                    mastercards.set(index, (TarjetaMasterCard)p);
                    ManejadorArchivos.escribirMasterCard("mastercards.txt", mastercards);
                    break;
                case TARJETA_VISA:
                    visas.set(index, (TarjetaVisa)p);
                    ManejadorArchivos.escribirVisa("visas.txt", visas);
                    break;
            }
            
            
            t.setEstado(EstadoTransaccion.ACTUALIZANDO);
            transaccionesConsumadas.put(t, p);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(MainBanco.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean rollback(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException {
        //No hace nada porque en ningun momento se cambiaron los valores originales, simplemente no se hace commit de la transaccion
        return true;
    }

    
}

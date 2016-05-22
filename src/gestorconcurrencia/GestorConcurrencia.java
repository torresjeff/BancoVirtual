/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorconcurrencia;

import Utils.Banco;
import Utils.TipoProducto;
import Utils.Transaccion;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manuela
 */
public class GestorConcurrencia extends UnicastRemoteObject implements IGestorConcurrencia {
    
    private String path;
    private List<Banco> bancos;
    
    public GestorConcurrencia(String path) throws RemoteException {
        super();
        
        try {
            Naming.rebind(path, this);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GestorConcurrencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean iniciarSesion(int idUsuario, String password) {
        System.out.println("idUsuario: " + idUsuario + ", pass: " + password);
        return true;
    }

    @Override
    public double consultar(int idUsuario, TipoProducto tipo, int numeroProducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retirar(int idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean despositar(int idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean commit(Transaccion t) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

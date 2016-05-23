/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import Utils.TipoProducto;
import Utils.Transaccion;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author manuela
 */
public interface IBanco extends Remote {
    public boolean iniciarSesion(String usuario, String password) throws RemoteException;
    public double consultar(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException;
    public boolean retirar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t) throws RemoteException;
    public boolean depositar(String usuario, TipoProducto tipoProducto, double cantidad, Transaccion t) throws RemoteException;
    public boolean puedeCommit(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException;
    public Boolean commit(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException;
    public Boolean rollback(String usuario, TipoProducto tipoProducto, Transaccion t) throws RemoteException;
}

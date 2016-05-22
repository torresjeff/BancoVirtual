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
    public boolean iniciarSesion(Transaccion t) throws RemoteException;
    public double consultar(Transaccion t) throws RemoteException;
    public boolean retirar(Transaccion t) throws RemoteException;
    public boolean despositar(Transaccion t) throws RemoteException;
    public boolean commit(Transaccion t) throws RemoteException;
}

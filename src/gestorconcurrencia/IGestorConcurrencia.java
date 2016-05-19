/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorconcurrencia;

import Utils.TipoProducto;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author manuela
 */
public interface IGestorConcurrencia extends Remote {
    public boolean iniciarSesion(int idUsuario, String password) throws RemoteException;
    public double consultar(int idUsuario, TipoProducto tipo, int numeroProducto) throws RemoteException;
    public boolean retirar(int idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) throws RemoteException;
    public boolean despositar(int idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) throws RemoteException;
}

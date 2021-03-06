/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorconcurrencia;

import Utils.TipoProducto;
import Utils.Transaccion;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author manuela
 */
public interface IGestorConcurrencia extends Remote {
    public long abrirTransaccion(String idUsuario, String password) throws RemoteException;
    public double consultar(String idUsuario, TipoProducto tipo, int numeroProducto) throws RemoteException;
    public double retirar(String idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) throws RemoteException;
    public double depositar(String idUsuario, TipoProducto tipo, int numeroProducto, double cantidad) throws RemoteException;
}

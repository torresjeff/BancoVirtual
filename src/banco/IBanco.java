/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import Utils.TipoProducto;
import java.rmi.Remote;

/**
 *
 * @author manuela
 */
public interface IBanco extends Remote {
    public boolean iniciarSesion(int idUsuario, String password);
    public double consultar(int idUsuario, TipoProducto tipo, int numeroProducto);
    public boolean retirar(int idUsuario, TipoProducto tipo, int numeroProducto, double cantidad);
    public boolean despositar(int idUsuario, TipoProducto tipo, int numeroProducto, double cantidad);
}

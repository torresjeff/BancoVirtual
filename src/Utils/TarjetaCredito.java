/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author manuela
 */
public class TarjetaCredito extends Producto{
    
    public TarjetaCredito() {
        super();
    }

    public TarjetaCredito(int numeroProducto, double saldo, String usuario) {
        super(numeroProducto, saldo, usuario); //To change body of generated methods, choose Tools | Templates.
    }

    public TarjetaCredito(int numeroProducto, String usuario) {
        super(numeroProducto, usuario);
    }

    @Override
    public String toString() {
        return "TarjetaCredito{" + "numeroProducto=" + numeroProducto + ", saldo=" + saldo + ", usuario=" + usuario + '}';
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.Serializable;

/**
 *
 * @author manuela
 */
public class TarjetaMasterCard extends TarjetaCredito implements Serializable {

    public TarjetaMasterCard(int numeroTarjeta, double saldo, String usuario) {
        super(numeroTarjeta, saldo, usuario);
    }

    public TarjetaMasterCard(int numeroTarjeta, String usuario) {
        super(numeroTarjeta, usuario);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new TarjetaMasterCard(numeroProducto, saldo, usuario); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

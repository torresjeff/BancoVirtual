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
public class TarjetaVisa extends TarjetaCredito implements Serializable {
    
    public TarjetaVisa(TarjetaCredito tc) {
        this.numeroProducto = tc.numeroProducto;
        this.saldo = tc.saldo;
        this.usuario = tc.usuario;
    }

    public TarjetaVisa(int numeroTarjeta, double saldo, String usuario) {
        super(numeroTarjeta, saldo, usuario);
    }

    public TarjetaVisa(int numeroTarjeta, String usuario) {
        super(numeroTarjeta, usuario);
    }

    @Override
    public TarjetaVisa clone() throws CloneNotSupportedException {
        return new TarjetaVisa(numeroProducto, saldo, usuario); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

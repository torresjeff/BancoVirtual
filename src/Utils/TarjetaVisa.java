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

    public TarjetaVisa(int numeroTarjeta, double saldo, String usuario) {
        super(numeroTarjeta, saldo, usuario);
    }

    public TarjetaVisa(int numeroTarjeta, String usuario) {
        super(numeroTarjeta, usuario);
    }
    
}

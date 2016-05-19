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
public class TarjetaCredito {
    protected int numeroTarjeta;
    protected double saldo;
    
    public double consultar() {
        return saldo;
    }
    
    public void depositar(double cantidad) {
        saldo += cantidad;
    }
    
    public void retirar(double cantidad) {
        saldo -= cantidad;
    }
}

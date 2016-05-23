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
    protected String usuario;

    public TarjetaCredito(int numeroTarjeta, double saldo, String usuario) {
        this.numeroTarjeta = numeroTarjeta;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public TarjetaCredito(int numeroTarjeta, String usuario) {
        this.numeroTarjeta = numeroTarjeta;
        this.usuario = usuario;
        this.saldo = 0;
    }
    
    
    
    public double consultar() {
        return saldo;
    }
    
    public void depositar(double cantidad) {
        saldo += cantidad;
    }
    
    public void retirar(double cantidad) {
        saldo -= cantidad;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "TarjetaCredito{" + "numeroTarjeta=" + numeroTarjeta + ", saldo=" + saldo + ", usuario=" + usuario + '}';
    }
    
    
}

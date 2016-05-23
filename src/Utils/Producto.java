/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author torre
 */
public class Producto {
    protected int numeroProducto;
    protected double saldo;
    protected String usuario;

    public Producto(int numeroProducto, double saldo, String usuario) {
        this.numeroProducto = numeroProducto;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public Producto(int numeroTarjeta, String usuario) {
        this.numeroProducto = numeroTarjeta;
        this.usuario = usuario;
        this.saldo = 0;
    }
    
    
    
    public double consultar() {
        return saldo;
    }
    
    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
        }
    }
    
    public void retirar(double cantidad) {
        if (saldo >= cantidad) {
            saldo -= cantidad;
        }
    }

    public int getNumeroTarjeta() {
        return numeroProducto;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroProducto = numeroTarjeta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public double getSaldo() {
        return saldo;
    }
}

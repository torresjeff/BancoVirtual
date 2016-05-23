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
public class Cuenta {
    protected int numeroCuenta;
    protected double saldo;
    protected String usuario;

    public Cuenta(int numeroCuenta, double saldo, String usuario) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public Cuenta(int numeroCuenta, String usuario) {
        this.numeroCuenta = numeroCuenta;
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

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "numeroCuenta=" + numeroCuenta + ", saldo=" + saldo + ", usuario=" + usuario + '}';
    }
    
    
}

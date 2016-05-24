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
public class Cuenta extends Producto {
    
    public Cuenta() {
        super();
    }
    public Cuenta(int numeroCuenta, double saldo, String usuario) {
        super(numeroCuenta, saldo, usuario);
    }

    public Cuenta(int numeroCuenta, String usuario) {
        super(numeroCuenta, usuario);
    }

    @Override
    public Cuenta clone() throws CloneNotSupportedException {
        return new Cuenta(numeroProducto, saldo, usuario); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

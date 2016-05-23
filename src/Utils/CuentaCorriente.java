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
public class CuentaCorriente extends Cuenta implements Serializable {

    public CuentaCorriente(int numeroCuenta, double saldo, String usuario) {
        super(numeroCuenta, saldo, usuario);
    }

    public CuentaCorriente(int numeroCuenta, String usuario) {
        super(numeroCuenta, usuario);
    }
    
}

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
public class CuentaAhorro extends Cuenta implements Serializable {

    public CuentaAhorro(int numeroCuenta, double saldo, String usuario) {
        super(numeroCuenta, saldo, usuario);
    }

    public CuentaAhorro(int numeroCuenta, String usuario) {
        super(numeroCuenta, usuario);
    }
    
}

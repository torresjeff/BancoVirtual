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
public class Usuario {
    private String usuario;
    
    private TarjetaVisa tarjetaVisa;
    private TarjetaMasterCard tarjetaMastercard;
    private CuentaAhorro cuentaAhorros;
    private CuentaCorriente cuentaCorrientes;

    public Usuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TarjetaVisa getTarjetaVisa() {
        return tarjetaVisa;
    }

    public void setTarjetaVisa(TarjetaVisa tarjetaVisa) {
        this.tarjetaVisa = tarjetaVisa;
    }

    public TarjetaMasterCard getTarjetaMastercard() {
        return tarjetaMastercard;
    }

    public void setTarjetaMastercard(TarjetaMasterCard tarjetaMastercard) {
        this.tarjetaMastercard = tarjetaMastercard;
    }

    public CuentaAhorro getCuentaAhorros() {
        return cuentaAhorros;
    }

    public void setCuentaAhorros(CuentaAhorro cuentaAhorros) {
        this.cuentaAhorros = cuentaAhorros;
    }

    public CuentaCorriente getCuentaCorrientes() {
        return cuentaCorrientes;
    }

    public void setCuentaCorrientes(CuentaCorriente cuentaCorrientes) {
        this.cuentaCorrientes = cuentaCorrientes;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuario=" + usuario + ", tarjetaVisa=" + tarjetaVisa + ", tarjetaMastercard=" + tarjetaMastercard + ", cuentaAhorros=" + cuentaAhorros + ", cuentaCorrientes=" + cuentaCorrientes + '}';
    }
    
    
    
}

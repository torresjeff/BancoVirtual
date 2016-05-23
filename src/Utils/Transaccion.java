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
public class Transaccion {
    private long id;
    private long hora;
    private EstadoTransaccion estado;
    private TipoTransaccion tipoTransaccion;
    private String usuario;

    public Transaccion(long id) {
        this.id = id;
        this.estado = EstadoTransaccion.TRABAJANDO;
        this.hora = System.currentTimeMillis();
    }

    public Transaccion(long id, TipoTransaccion tipoTransaccion) {
        this.id = id;
        this.tipoTransaccion = tipoTransaccion;
        this.estado = EstadoTransaccion.TRABAJANDO;
        this.hora = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TipoTransaccion getTipoTransaccion() {
        return tipoTransaccion;
    }
    
    
    
    
    
}

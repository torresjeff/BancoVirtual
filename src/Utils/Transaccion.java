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
public class Transaccion implements Serializable {
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

    public EstadoTransaccion getEstado() {
        return estado;
    }

    public void setEstado(EstadoTransaccion estado) {
        this.estado = estado;
    }

    public long getHora() {
        return hora;
    }
    
}

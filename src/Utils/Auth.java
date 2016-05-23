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

public class Auth {
    private String id;
    private String pasword;

    public Auth(String id, String pasword) {
        this.id = id;
        this.pasword = pasword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    @Override
    public String toString() {
        return "Auth{" + "id=" + id + ", pasword=" + pasword + '}';
    }
}

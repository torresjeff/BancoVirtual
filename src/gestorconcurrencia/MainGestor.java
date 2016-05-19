/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorconcurrencia;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manuela
 */
public class MainGestor {
    private static final String IP = "192.168.0.3"; //IP de este computador
    private static final int PUERTO = 8080;
    
    public static void main(String[] args) {
        
        try {
            LocateRegistry.createRegistry(PUERTO);
            GestorConcurrencia gestor = new GestorConcurrencia("rmi://"+IP+":"+PUERTO+"/GestorConcurrencia");
            
        } catch (RemoteException ex) {
            Logger.getLogger(MainGestor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

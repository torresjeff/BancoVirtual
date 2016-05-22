/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import gestorconcurrencia.IGestorConcurrencia;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manuela
 */
public class MainCliente {
    private static final String IP = "192.168.0.15"; //IP de este computador
    private static final int PUERTO = 8080;
    
    private static final String IP_GESTOR = "192.168.0.3";
    private static final int PUERTO_GESTOR = 8080;
    
    public static void main(String[] args) {
        try {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(reader);
            LocateRegistry.createRegistry(PUERTO);
            
            IGestorConcurrencia gestor =
                    (IGestorConcurrencia)Naming.lookup("//"+IP_GESTOR+":"+PUERTO_GESTOR+"/GestorConcurrencia");
            
            // INICIAR SESION
            /*System.out.println("Ingrese su usuario: ");
            int usuario = Integer.parseInt(in.readLine());
            System.out.println("Ingrese su contraseña: ");
            String pass = in.readLine();
            gestor.iniciarSesion(usuario, pass);*/
            
            // CONSULTAR
            
        } catch (RemoteException ex) {
            Logger.getLogger(MainCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MainCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

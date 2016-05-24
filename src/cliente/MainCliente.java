/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import Utils.TipoProducto;
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
    //private static final String IP = "192.168.0.15"; //IP de este computador
    //private static final int PUERTO = 8080;
    
    //private static final String IP_GESTOR = "192.168.0.3";
    private static final String IP_GESTOR = "127.0.0.1"; //Test misma maquin
    private static final int PUERTO_GESTOR = 8080;
    
    public static void main(String[] args) {
        try {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(reader);
            //LocateRegistry.createRegistry(PUERTO); //TODO: uncomment
            
            IGestorConcurrencia gestor =
                    (IGestorConcurrencia)Naming.lookup("//"+IP_GESTOR+":"+PUERTO_GESTOR+"/GestorConcurrencia");
            
            
            boolean quit = false;
            
            while (!quit) {
                System.out.println("*** MENU PRINCIPAL ***");
                System.out.println("1. Iniciar sesion");
                System.out.println("2. Salir");
                int opcion = Integer.parseInt(in.readLine());
                
                switch (opcion) {
                    case 1:
                        System.out.println("Ingrese su usuario: ");
                        String usuario = in.readLine();
                        System.out.println("Ingrese su contraseña: ");
                        String pass = in.readLine();
                        long resultado = gestor.abrirTransaccion(usuario, pass);
                        
                        if (resultado < 0) {
                            System.out.println("Usuario o contraseña incorrectos");
                            quit = true;
                            break;
                        }
                        
                        boolean innerQuit = false;
                
                        while (!innerQuit) {
                            System.out.println("*** MENU PRODUCTOS ***");
                            System.out.println("1. Tarjeta Visa");
                            System.out.println("2. Tarjeta MasterCard");
                            System.out.println("3. Cuenta de Ahorros");
                            System.out.println("4. Cuenta Corriente");
                            System.out.println("5. Atras");
                            opcion = Integer.parseInt(in.readLine());
                            
                            TipoProducto tp;
                            
                            if (opcion == 5) {
                                innerQuit = true;
                            }
                            
                            if (innerQuit) {
                                break;
                            }
                            
                            switch (opcion) {
                                case 1:
                                    tp = TipoProducto.TARJETA_VISA;
                                    break;
                                case 2:
                                    tp = TipoProducto.TARJETA_MASTERCARD;
                                    break;
                                case 3:
                                    tp = TipoProducto.CUENTA_AHORRO;
                                    break;
                                case 4:
                                    tp = TipoProducto.CUENTA_CORRIENTE;
                                    break;
                                default:
                                    tp = TipoProducto.TARJETA_VISA;
                                    break;
                                    
                            }
                            
                            boolean innerQuit2 = false;
                            
                            while (!innerQuit2) {
                                System.out.println("*** MENU OPERACIONES ***");
                                System.out.println("1. Consultar saldo");
                                System.out.println("2. Retirar dinero");
                                System.out.println("3. Depositar dinero");
                                System.out.println("4. Atras");
                                opcion = Integer.parseInt(in.readLine());
                                
                                switch (opcion) {
                                case 1:
                                    double consulta = gestor.consultar(usuario, tp, 1);
                                    System.out.println("Consulta: " + consulta);
                                    break;
                                case 2:
                                    System.out.print("Ingrese la cantidad que desea retirar: ");
                                    double retiro = Double.parseDouble(in.readLine());
                                    retiro = gestor.retirar(usuario, tp, 1, retiro);
                                    System.out.println("Retiro: " + retiro);
                                    break;
                                case 3:
                                    System.out.print("Ingrese la cantidad que desea depositar: ");
                                    double deposito = Double.parseDouble(in.readLine());
                                    deposito = gestor.depositar(usuario, TipoProducto.TARJETA_VISA, 1, deposito);
                                    System.out.println("Deposito: " + deposito);
                                    break;
                                case 4:
                                    innerQuit2 = true;
                                    break;
                                }
                            }
                                
                        }
                        break;
                    case 2:
                        quit = true;
                        break;
                    default:
                        
                        break;
                }
                
            }
            // VISA
            /*// INICIAR SESION
            System.out.println("Ingrese su usuario: ");
            String usuario = in.readLine();
            System.out.println("Ingrese su contraseña: ");
            String pass = in.readLine();
            gestor.abrirTransaccion(usuario, pass);
            
            // CONSULTAR
            double consulta = gestor.consultar(usuario, TipoProducto.TARJETA_VISA, 1);
            System.out.println("consulta: " + consulta);
            // RETIRAR
            double retiro = gestor.retirar(usuario, TipoProducto.TARJETA_VISA, 1, 5000);
            System.out.println("retiro: " + retiro);
            // DEPOSITAR
            double deposito = gestor.depositar(usuario, TipoProducto.TARJETA_VISA, 1, 55000);
            System.out.println("deposito: " + deposito);*/
            
            // MASTERCARD
            // INICIAR SESION
            /*System.out.println("Ingrese su usuario: ");
            String usuario = in.readLine();
            System.out.println("Ingrese su contraseña: ");
            String pass = in.readLine();
            gestor.abrirTransaccion(usuario, pass);
            
            // CONSULTAR
            double consulta = gestor.consultar(usuario, TipoProducto.TARJETA_MASTERCARD, 1);
            System.out.println("consulta: " + consulta);
            // DEPOSITAR
            double deposito = gestor.depositar(usuario, TipoProducto.TARJETA_MASTERCARD, 1, 55000);
            System.out.println("deposito: " + deposito);
            // RETIRAR
            double retiro = gestor.retirar(usuario, TipoProducto.TARJETA_MASTERCARD, 1, 5000);
            System.out.println("retiro: " + retiro);*/
            
            // CUENTA AHORROS
            // INICIAR SESION
            /*System.out.println("Ingrese su usuario: ");
            String usuario = in.readLine();
            System.out.println("Ingrese su contraseña: ");
            String pass = in.readLine();
            gestor.abrirTransaccion(usuario, pass);
            
            // CONSULTAR
            double consulta = gestor.consultar(usuario, TipoProducto.CUENTA_AHORRO, 1);
            System.out.println("consulta: " + consulta);
            // RETIRAR
            double retiro = gestor.retirar(usuario, TipoProducto.CUENTA_AHORRO, 1, 5000);
            System.out.println("retiro: " + retiro);
            // DEPOSITAR
            double deposito = gestor.depositar(usuario, TipoProducto.CUENTA_AHORRO, 1, 55000);
            System.out.println("deposito: " + deposito);*/
            
            
            //CUENTA CORRIENTE
            // INICIAR SESION
            /*System.out.println("Ingrese su usuario: ");
            String usuario = in.readLine();
            System.out.println("Ingrese su contraseña: ");
            String pass = in.readLine();
            gestor.abrirTransaccion(usuario, pass);
            
            // CONSULTAR
            double consulta = gestor.consultar(usuario, TipoProducto.CUENTA_CORRIENTE, 1);
            System.out.println("consulta: " + consulta);
            // RETIRAR
            double retiro = gestor.retirar(usuario, TipoProducto.CUENTA_CORRIENTE, 1, 5000);
            System.out.println("retiro: " + retiro);
            // DEPOSITAR
            double deposito = gestor.depositar(usuario, TipoProducto.CUENTA_CORRIENTE, 1, 55000);
            System.out.println("deposito: " + deposito);*/
        } catch (RemoteException ex) {
            Logger.getLogger(MainCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(MainCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}

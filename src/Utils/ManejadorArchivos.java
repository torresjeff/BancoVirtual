/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author sebas
 */
public class ManejadorArchivos {
    
    public static ArrayList<Auth> leerUsuarios(String nombreArchivo){
        Auth auth;
        ArrayList<Auth> authArch = new ArrayList<Auth>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(nombreArchivo));
            String str;
            str = in.readLine();
            String[] ar = str.split(",");
            authArch.add(auth=new Auth(ar[0],ar[1]));
            while ((str = in.readLine()) != null) {
                String[] ar1 = str.split(",");           
                authArch.add(auth=new Auth(ar1[0],ar1[1]));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo usuarios...");
        }
           
        return authArch;
    }
    
    public static ArrayList<TarjetaVisa> leerVisa(String nombreArchivo){
        ArrayList<TarjetaVisa> visas = new ArrayList<TarjetaVisa>();
        TarjetaVisa tv;        
        try {
            BufferedReader in = new BufferedReader(new FileReader(nombreArchivo));
            String str;
            str = in.readLine();
            String[] ar = str.split(",");
            visas.add(tv=new TarjetaVisa(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            while ((str = in.readLine()) != null) {
                String[] ar1 = str.split(",");           
                visas.add(tv=new TarjetaVisa(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo productos...");
        }
        return visas;
    }
    
    public static ArrayList<TarjetaMasterCard> leerMasterCard(String nombreArchivo){
        ArrayList<TarjetaMasterCard> mastercards = new ArrayList<TarjetaMasterCard>();
        TarjetaMasterCard tmc;        
        try {
            BufferedReader in = new BufferedReader(new FileReader(nombreArchivo));
            String str;
            str = in.readLine();
            String[] ar = str.split(",");
            mastercards.add(tmc=new TarjetaMasterCard(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            
            while ((str = in.readLine()) != null) {
                String[] ar1 = str.split(",");           
                mastercards.add(tmc=new TarjetaMasterCard(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo productos...");
        }
        return mastercards;
    }
    
    public static ArrayList<CuentaAhorro> leerAhorros(String nombreArchivo){
        ArrayList<CuentaAhorro> ahorros = new ArrayList<CuentaAhorro>();
        CuentaAhorro ca;   
        try {
            BufferedReader in = new BufferedReader(new FileReader(nombreArchivo));
            String str;
            str = in.readLine();
            String[] ar = str.split(",");
            ahorros.add(ca=new CuentaAhorro(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            
            while ((str = in.readLine()) != null) {
                String[] ar1 = str.split(",");           
                ahorros.add(ca=new CuentaAhorro(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo productos...");
        }
        return ahorros;
    }
    
    public static ArrayList<CuentaCorriente> leerCorriente(String nombreArchivo){
        ArrayList<CuentaCorriente> corrientes = new ArrayList<CuentaCorriente>();
        CuentaCorriente cc;   
        try {
            BufferedReader in = new BufferedReader(new FileReader(nombreArchivo));
            String str;
            str = in.readLine();
            String[] ar = str.split(",");
            corrientes.add(cc=new CuentaCorriente(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            
            while ((str = in.readLine()) != null) {
                String[] ar1 = str.split(",");           
                corrientes.add(cc=new CuentaCorriente(Integer.parseInt(ar[0]), Double.parseDouble(ar[1]), ar[2]));
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo productos...");
        }
        return corrientes;
    }   
    
    public static void escribirUsuarios(String nombreArchivo, ArrayList<Auth>usr) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
        for (int i = 0; i < usr.size(); i++) {
            pw.write(usr.get(i).getId() +","+usr.get(i).getPasword());
        }
        System.out.println("Archivo usuarios escrito satisfactoriamente...");
        pw.close();
    }
    
     public static void escribirVisa(String nombreArchivo, ArrayList<TarjetaVisa>visas) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
        for (int i = 0; i < visas.size(); i++) {
            pw.write(visas.get(i).numeroProducto+","+visas.get(i).saldo+","+visas.get(i).getUsuario());
        }
        System.out.println("Archivo visas escrito satisfactoriamente...");
        pw.close();
    }
     public static void escribirMasterCard(String nombreArchivo, ArrayList<TarjetaMasterCard>mastercards) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
        for (int i = 0; i < mastercards.size(); i++) {
            pw.write(mastercards.get(i).numeroProducto+","+mastercards.get(i).saldo+","+mastercards.get(i).getUsuario());
        }
        System.out.println("Archivo MasterCard escrito satisfactoriamente...");
        pw.close();
    }
     public static void escribirAhorros(String nombreArchivo, ArrayList<CuentaAhorro>ahorros) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
        for (int i = 0; i < ahorros.size(); i++) {
            pw.write(ahorros.get(i).numeroProducto+","+ahorros.get(i).saldo+","+ahorros.get(i).getUsuario());
        }
         System.out.println("Archivo ahorros escrito satisfactoriamente...");
        pw.close();
    }
     public static void escribirCorriente(String nombreArchivo, ArrayList<CuentaCorriente>corrientes) throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
        for (int i = 0; i < corrientes.size(); i++) {
            pw.write(corrientes.get(i).numeroProducto+","+corrientes.get(i).saldo+","+corrientes.get(i).getUsuario());
        }
        System.out.println("Archivo corrientes escrito satisfactoriamente...");
        pw.close();
    }
}

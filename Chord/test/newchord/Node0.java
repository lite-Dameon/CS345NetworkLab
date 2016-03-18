/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchord;

/**
 *
 * @author khyati
 */
public class Node0 {
    
    public static void main(String[] args) {
        Node a = new Node(3,0);
        a.ipAddress = "127.0.0.1";
        a.port = 1452;
        
        a.node_initialize();
        a.first_node(); 
    }
}

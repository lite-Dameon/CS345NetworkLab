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
public class Testing {
      
    
      static Node a = new Node(3,0);
      public static void main(String[] args) {
        a.ipAddress = "127.0.0.1";
        a.port = 1424;
        a.node_initialize();
      //  a.first_node();
        a.sample_finger_entry(1, 3, 0);
        find_successor_test();
    }
      
      static void find_successor_test(){
          System.out.println("find_successor " ); 
          a.find_successor(3,2);
      }  
      
      static void closeset_finger_test(){
          System.out.println("closest preceding finger table " + a.closest_preceding_finger(1));
      }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newchord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author khyati
 */
public class Node {

    GUI frame;
    
    int predecessor_node_id = -1;
    String predecessor_node_ip = null;
    int predecessor_node_port = -1;
    
    int successor_node_id = -1;
    String successor_node_ip = null;
    int successor_node_port = -1;
    
    int known_node_id = -1;
    String known_node_ip = null;
    int known_node_port = -1;
    
    
    ArrayList<FingerTableRecord> fingerTable;
    ArrayList<Integer> keys ;
    int m = 0;
    int number_of_nodes = 0;
    int node_id = -1;
    String ipAddress = null;
    int port = -1;
    int count =0;
    int set = 1;
    ServerSocket socket;
    Thread t;
    boolean single_node = false;
    
    Node(){
    }
   
    Node(int m, int node_id){
        this.node_id = node_id;
        this.m = m;
        this.number_of_nodes = (int)Math.pow(2,m);
       // node_initialize();
    }
    
    void setFrame(GUI jframe){
        this.frame = jframe;
    }
    void node_initialize(){
        fingerTable = new ArrayList<>();
        keys = new ArrayList<>();
        add_M_FTR();
        listenPort(this.port);
        auto_stablize();
        frame.set_value_of_node_id(this.node_id);
    }
    
    void add_key(int key_count){
        for(int i=0; i<key_count;i++){
         //   int random_key = (int)(Math.random() * 200);
         //   keys.add(random_key);
            keys.add(i);
        }
        frame.update_key_container(keys);
        System.out.println("Added keys");
    }
    
    
    void ask_for_keys(){
        // send my keys bt me an d my predecessor
        String reply = this.node_id+ "$"+ this.predecessor_node_id;
        if(this.node_id != -1 && this.predecessor_node_id != -1)
            send(-5,9,reply, successor_node_ip, successor_node_port);
    }
    
    void sample_finger_entry(int a, int b, int c){
           fingerTable.get(0).node_id = a;
           fingerTable.get(1).node_id = b;
           fingerTable.get(2).node_id = c;
           successor_node_id = a;
    }
    void printFIngerTable(){
        count++; 
        P("Predecessor: "+ predecessor_node_id + "$" + predecessor_node_ip + "$" + predecessor_node_port);
        P("Successor: " + successor_node_id + "$" + successor_node_ip + "$" + successor_node_port);
        for(int i =0; i<m;i++){
            P(i + "->"+ fingerTable.get(i).toString());
        }
        System.err.println("Prinitng completed count " + count);
    }
    
    void find_successor(int id,int index){
        printFIngerTable();
        find_predecessor(id,index);
    }
    
    void find_predecessor(int id, int index){
        String msg = id +"";
        send(index,1,msg,ipAddress,port);      
    }
    
    void join(int known_node_id, String known_node_ip, int known_node_port){
        // set predecessor null already null
        // findsuccessor f given node
        send(-10,1,this.node_id+"",known_node_ip,known_node_port);
       
    }
    
    void fix_fingers(){
        P("fixing fingers start");
        for(int i = 0; i<m;i++){
            FingerTableRecord ftr = fingerTable.get(i);
            int start = ftr.start;
//            if(update_test(this.node_id, successor_node_id, start) || start == successor_node_id){
//                P(fingerTable.get(i)+"");
//                fingerTable.get(i).node_id = successor_node_id;
//                fingerTable.get(i).node_ip = successor_node_ip;
//                fingerTable.get(i).node_port = successor_node_port;
//                P(fingerTable.get(i)+"");
//            }
//            else{
//                if(!single_node){
                    P("*******************************"
                    + "***************************************************"
                    + "****************************************"
                    + "***Hiyeee  sending for " + start);
                    send(i, 1, start+"", known_node_ip, known_node_port);
                    P("sent for "+ start);
       //         }
        //    }
        }
        P("fixing fingers complete staring auto_satblize");
        
        printFIngerTable();
    }
    
    
    void stablize_1(){
        P("CAlled stablize");
        printFIngerTable();
        ask_node_its_predecessor(1, "nothing", successor_node_ip, successor_node_port);
    }
    void stablize_2(int node_id, String node_ip, int node_port){
        printFIngerTable();
        P("Chance of modifying successor");
        if(update_test(this.node_id, successor_node_id, node_id)){
          
            successor_node_id = node_id;
            successor_node_ip = node_ip;
            successor_node_port = node_port;
            
            fingerTable.get(0).node_id = node_id;
            fingerTable.get(0).node_ip = node_ip;
            fingerTable.get(0).node_port  = node_port;
     
            frame.table_update_item(0, node_id, node_ip, node_port);
            
            frame.update_successor(node_id, node_ip, node_port);
            
            this.known_node_id = node_id;
            this.known_node_ip = node_ip;
            this.known_node_port = node_port;
                    
            P("Successor modified ");
            stablize_1();
      //      fix_fingers();
        }
        // notify the successor that is i am ur predecesssor
        notify_successor(successor_node_id, successor_node_ip, successor_node_port);
    }
    
    void notify_successor(int node_id, String node_ip, int node_port){
        printFIngerTable();
        P("Notifying " + node_id + "that " + this.node_id + "is your successor");
        String reply = this.node_id+"$" +this.ipAddress + "$" + this.port;
        send(-10,5,reply,node_ip,node_port);
    }
    
    void notify_successor_1(int node_id, String node_ip, int node_port){
        printFIngerTable();
        P("CHancce of upadting predecessor");
        if(predecessor_node_id == -1 || update_test(predecessor_node_id, this.node_id, node_id)){
            predecessor_node_id = node_id;
            predecessor_node_ip = node_ip;
            predecessor_node_port = node_port;
            frame.update_predecessor(node_id, node_ip, node_port);
            P("Updated");
            stablize_1();
            ask_for_keys();
          //  fix_fingers();
        }
        printFIngerTable();
    }
    
    
    void auto_stablize(){
          Timer t = new Timer();
          int seconds = 10;
          TimerTask task= new TimerTask() {
              @Override
              public void run() {
                  stablize_1();
                  fix_fingers();
                  ask_for_keys();
                  P("calling my methods");
                  frame.call_my_methods();
                  P("call ended my methods");
              }
          };
          t.scheduleAtFixedRate(task,0, seconds*1000);
    }
    
    void handler(String msg){
        P("Received "+ msg);
        String comp[] = msg.split(":");
        String receiver_ip = comp[0];
        int receiver_port = Integer.parseInt(comp[1]);
        int q_type = Integer.parseInt(comp[2]);
        int q_index =Integer.parseInt(comp[3]);
        String q_msg = comp[4];
        
        
        if(q_type == 1){
            int id = Integer.parseInt(comp[4]);
            P(this.node_id+" " + successor_node_id + " " + id);
            
            if(update_test(this.node_id, successor_node_id, id) || id == successor_node_id){
                String reply = this.node_id+"$"+this.ipAddress+"$"+this.port;
                send(q_index, 2, reply, receiver_ip, receiver_port);
            }
            else{
                int ft_index = this.closest_preceding_finger(id);
                String pred = ftr_to_string(ft_index);
                String pred_address = pred.split("\\$")[1];
                int pred_port = Integer.parseInt(pred.split("\\$")[2]);
 //               send(q_index, q_type, q_msg, pred_address, pred_port);
                String forward = receiver_ip+":"+ receiver_port+":1:"+id;
                System.out.println("forwarded message is " + forward);
                sendMessage(pred_address, pred_port, msg);
            }
        }
        else if(q_type == 2){
            
            String node_detils[] = q_msg.split("\\$");
            int node_id= Integer.parseInt(node_detils[0]);
            String node_ip = node_detils[1];
            int node_port = Integer.parseInt(node_detils[2]);
            // the received one is predecessor ask him the sucessor
            // just send it another query for sucessor    
            ask_node_its_successor(q_index,msg,node_ip,node_port);

        }
        else if(q_type == 3){
            String reply = predecessor_node_id+"$"+predecessor_node_ip+"$"+predecessor_node_port;
            send(q_index, 4, reply, receiver_ip, receiver_port);
        }
        else if(q_type == 4){
            String n_detils[] = q_msg.split("\\$");
            int node_id= Integer.parseInt(n_detils[0]);
            String node_ip = n_detils[1];
            int node_port = Integer.parseInt(n_detils[2]);
            
            stablize_2(node_id, node_ip, node_port);
        }
        else if(q_type == 6){
            // send my successor to receiver
            String reply = successor_node_id + "$"+ successor_node_ip+"$"+ successor_node_port;
            send(q_index,7,reply,receiver_ip,receiver_port);
        }
        else if(q_type == 7){
                P("received");
                String n_detils[] = q_msg.split("\\$");
                int node_id= Integer.parseInt(n_detils[0]);
                String node_ip = n_detils[1];
                int node_port = Integer.parseInt(n_detils[2]);
                if(q_index >= 0){
                    fingerTable.get(q_index).node_id = node_id;
                    fingerTable.get(q_index).node_ip = node_ip;
                    fingerTable.get(q_index).node_port  = node_port;
          
                    frame.table_update_item(q_index, node_id, node_ip, node_port);
                }
                else if(q_index == -10){
                    // successor modified
                    P("Successor modified here in handler");
                    successor_node_id = node_id;
                    successor_node_ip = node_ip;
                    successor_node_port = node_port;
                    P("Finger table 0  updated");
                    
                    printFIngerTable();
                    
                    fingerTable.get(0).node_id = node_id;
                    fingerTable.get(0).node_ip = node_ip;
                    fingerTable.get(0).node_port  = node_port;
                    
                    frame.update_successor(node_id, node_ip, node_port);
                    
                    frame.table_update_item(0, node_id, node_ip, node_port);
                    
                    printFIngerTable();
                    
                    this.known_node_id = node_id;
                    this.known_node_ip = node_ip;
                    this.known_node_port = node_port;
                    
                    stablize_1();
                   // fix_fingers();
                }
                E("Succ: ");
                P(q_msg);
        }
        else if(q_type == 5){
            String n_detils[] = q_msg.split("\\$");
            int node_id= Integer.parseInt(n_detils[0]);
            String node_ip = n_detils[1];
            int node_port = Integer.parseInt(n_detils[2]);
            notify_successor_1(node_id, node_ip, node_port);
        }
        else if(q_type == 9){
            System.out.println("Received query for asking for keys");
            String []reply = comp[4].split("\\$");
            int key_start = Integer.parseInt(reply[0]);
            int key_end = Integer.parseInt(reply[1]);
            String keys_to_send = "";
            ArrayList<Integer> to_be_removed = new ArrayList<>();
            for(int i = 0; i < keys.size();i++){
               int mapped_key = keys.get(i)%number_of_nodes;
               if(mapped_key <= key_start && mapped_key > key_end){
                   keys_to_send += keys.get(i)+"$";
                   to_be_removed.add(i);
               }  
            }
            
            for(int i = 0; i < to_be_removed.size();i++){
                keys.remove(to_be_removed.get(i));
            }
            to_be_removed.clear();
                  
             if(keys_to_send.length() > 2){
                keys_to_send = keys_to_send.substring(0, keys_to_send.length()-1);
                send(-10, 10, keys_to_send, receiver_ip, receiver_port);
                frame.update_key_container(keys);
             }
        }
        else if(q_type == 10){
            System.out.println("I have received my keys");
            String []reply = comp[4].split("\\$");
            System.out.println("my received keysa are : "+ comp[4]);
            
            for(int i =0; i < reply.length;i++){
                if(!keys.contains(reply[i]))
                    keys.add(Integer.parseInt(reply[i]));
            }
            frame.update_key_container(keys);
        }
    }
    
    
    void ask_node_its_successor(int queryFingerIndex,String id,String ip,int port){
        send(queryFingerIndex,6,id,ip,port);
    }
    
    void ask_node_its_predecessor(int queryFingerIndex,String id,String ip,int port){
        send(queryFingerIndex,3,id,ip,port);
    }
     void first_node(){
        single_node = true;
        for(int i =0; i<m;i++){
            //set every entry in finger table to itself
            fingerTable.get(i).node_id = this.node_id;
            fingerTable.get(i).node_ip = this.ipAddress;
            fingerTable.get(i).node_port = this.port;
            
            frame.table_update_item(i, this.node_id, this.ipAddress, this.port);
        }
        predecessor_node_id = this.node_id;
        successor_node_id = this.node_id;
       
        predecessor_node_ip = this.ipAddress;
        successor_node_ip = this.ipAddress;
        
        predecessor_node_port = this.port;
        successor_node_port = this.port;
        
        frame.update_predecessor(predecessor_node_id, predecessor_node_ip, predecessor_node_port);
        frame.update_successor(successor_node_id, successor_node_ip, successor_node_port);
        
        printFIngerTable();
    }   
   
    int closest_preceding_finger(int id){
        for(int i = m; i>=1;i--){
            FingerTableRecord ftr = this.fingerTable.get(i-1);
            // format for input for test of precedding finger is (n,id,tobefind)
        //    p(update_test(this.node_id, id, ftr.node_id)+"");
            if(update_test(this.node_id, id, ftr.node_id)){
                return i-1;
            }
        }
        return -1;
    }
    String ftr_to_string(int index){
        String detail="";
        P("index : " + index);
        if(index == -1){
            detail = this.node_id+"$"+this.ipAddress+"$"+this.port;
        }
        else{
            detail= fingerTable.get(index).convert_to_string();
        }     
        return detail;
    }
    void add_M_FTR(){      
        for(int i =0; i<m;i++){
            
            int start = ((int)(node_id + Math.pow(2, i))) % ((int) number_of_nodes);   
            FingerTableRecord ftr = new FingerTableRecord(start);
            fingerTable.add(ftr);   
            
            // addding to table of GUI frames
            frame.table_add_item(ftr.start, ftr.node_id, ftr.node_ip, ftr.node_port);
        }
    }
    boolean update_test(int start, int end, int middle){
      
        if(start <= end){
           // System.out.println("1");
            if(middle > start && middle < end){
               // System.out.println("1");
                return true;
            }
        }
        if(start >= end){
          //  System.out.println("2");
            if(!(end <= middle && middle <=start)){
            //    System.out.println("2");
                return true;
            }
        }
        return false;
    }
    void listenPort(int listen){
        try {
            socket = new ServerSocket(listen);

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    DataInputStream din = null;
                    while(true){
                        try {
                            P("waiting ...........  ");
                            Socket s = socket.accept();
                            din = new DataInputStream(s.getInputStream());
                            String msg = din.readUTF();
                            P("Mesage Received "+ msg);
                            // hadle message
                            if(!msg.contains("debug"))
                                handler(msg);
                            else
                                P("Received message " + msg);
                            P("Processing of mesage completed "+ msg);
                            s.close(); 
                        }
                        catch (IOException ex) {
                        }
                    }
                }
            };
            t = new Thread(r);
            t.start();
        } catch (IOException ex) {
            
        }   
    }
    void send(int queryingFingerIndex,int type, String msg,String receiver_ip, int receiver_port){   
            // msg format sender_ip:sender_port:querytype:queryingFingerIndex:msg      ## msg may contain returned ip and port of node
            String new_msg = this.ipAddress+":"+this.port+":"+type+":"+ queryingFingerIndex +":"+msg;
            sendMessage(receiver_ip, receiver_port, new_msg);
            P("Sent successfluy to "+ receiver_ip + " and " + receiver_port);
     
    }
    
    void sendMessage(String ip, int port, String msg){
        if(port == -1)
            return;
        try {       
            Socket s = new Socket(ip,port);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(msg);
            s.close();
            P("Sent " + msg);
        }
        catch (IOException ex) {
            P("connectin denied Exception" + ex.getStackTrace());   
            //sendMessage(ip, port, msg);
        }  
    }
    
    void E(String msg){
        System.err.println(msg);
    }
    void e(String msg){
        System.err.println(msg);
    }
    void P(String msg){
        System.out.println(msg);
    }  
    void p(String msg){
        System.out.println(msg);
    }

    @Override
    public String toString() {
        return this.node_id + "$"+ this.ipAddress + "$"+this.port;
    }
    
}

class FingerTableRecord{
    String node_ip = "";
    int node_port = -1;
    int node_id = -1;
    int start = -1;

    public FingerTableRecord() {
    
    } 
    FingerTableRecord(int start) {
        this.start = start;
    }
    
    void setNodeDetails(int id,String ip, int port){
        this.node_id = id;
        this.node_ip = ip;
        this.node_port = port;
    }
    String convert_to_string(){
        return node_id+"$"+node_ip+"$"+node_port;
    }
    @Override
    public String toString() {
        return start + "  $  "+node_id+ "  $  "+node_ip+"  $  "+ node_port;
    }
}
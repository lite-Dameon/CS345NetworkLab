/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chord;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayank
 */
public class Node {
    
    Node predecessor;
    ArrayList<FingerTableRecord> fingerTable;
    int m = 3;
    int nodeId;
    String ipAddress;
    int port;
    int count =0;
      
    ServerSocket socket;
    Thread t;
    
    Node(){
        this.nodeId = -1;
    }
    
    
    void firstNode(){
        for(int i =0; i<m;i++){
            //set every entry in finger table to itself
            fingerTable.get(i).node = this;
        }
        printFIngerTable();
        predecessor  = this;
        run_update_utilities();
    }
    
    void printFIngerTable(){
        count++;
        P("*******************************************************************************************************************");
        E("Details: ");
        P1("Predecessor: "+ predecessor);
      //  System.err.println("Initialised Finger Table: "+ m + " Count : " + count);
        for(int i =0; i<m;i++){
            P(i + "");
            P2("---> " + fingerTable.get(i).toString());
        }
   //     System.err.println("Prinitng completed count " + count);
        P("*******************************************************************************************************************");
        
    }
    
    void sample_finger_entry(Node a, Node b, Node c){
        P("Setting finger table hardcoded");
           fingerTable.get(0).node = a;
           fingerTable.get(1).node = b;
           fingerTable.get(2).node = c;
        
        P("Hradcoded completed");
    }
    
    void node_initialize(){
        fingerTable = new ArrayList<>();
        add_M_FTR();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        printFIngerTable();
        listenPort(this.port);
    }
    
    void find_successor(int id, int queringFingerIndex){
        find_predecessor(id,queringFingerIndex);
        // when answer will be received send the answer
    }
    
    void ask_node_its_successor(int queryFingerIndex,String id,String ip,int port){
        P1("Sending successor request");
        send(queryFingerIndex,6,id,ip,port);
        P1("Sent successor request");
    }
    
    void P(String msg){
        //System.out.println(msg);
    }
    
    void P2(String msg){
        System.out.println(msg);
    }
    
     void P1(String msg){
        System.out.println(msg);
    }
    void E(String msg){
       //System.err.println(msg);
    }
    
    Node closest_preceding_finger(int id,int limit){
        P1("Closest Preecding finger");
        for(int i = limit-1; i>=1;i--){
           
            FingerTableRecord ftr = this.fingerTable.get(i-1);
            
            P1("Interval :" +ftr.node.nodeId  +" --- "+this.nodeId  + "  &&& " + id);
            boolean test = interval_test(this.nodeId, id, ftr.node.nodeId);
            if(ftr.node.nodeId !=-1 && (ftr.node.nodeId >  this.nodeId && ftr.node.nodeId < id) || test){
                P1("found");
                return ftr.node;
            }
        }
        P1("ZReturning myself");
        return this;
    }
    
    boolean interval_test(int n, int id, int ftr){
        P(id +" < " + n + " && " + ftr+ " > " + n + " && " + ftr +" <" +  Math.pow(2,m) + "||" + ftr + ">= 0 " + " && " + ftr + "<"+  id);
             
        boolean res = (id <= n) && ((ftr > n && ftr < Math.pow(2,m)) || ( ftr >= 0 && ftr < id));
        return res;
    }
    
    void find_predecessor(int id,int queryingFingerIndex){
        Node n_new = this;
        n_new  = n_new.closest_preceding_finger(id,queryingFingerIndex); 
      //  while(id < n_new.nodeId && id >= n_new.fingerTable.get(0).node.nodeId){
      //        this node all details for further evaluation
      //        n_new  = n_new.closest_preceding_finger(id);     
      //  }
      // above equivalent code
      // query node n_new  finger table
      // send message *************************88888
        String msg = id +"";
        send(queryingFingerIndex,1,msg, n_new.ipAddress, n_new.port);
    }
    
    // add m empty records to node
    void add_M_FTR(){
        
        for(int i =0; i<m;i++){
            //add mepty records
            int start = ((int)(nodeId + Math.pow(2, i))) % ((int) Math.pow(2,m));
            int end = (int)(nodeId+Math.pow(2, i+1)) % ((int) Math.pow(2,m));
            FingerTableRecord ftr = new FingerTableRecord(start,end);
            fingerTable.add(ftr);
            
        }
    }
    
    
    void join(Node n_known){
        predecessor = null;
        // use n_know to find sucessor of n
        P("Sent request for join");
        send(-10,1,""+this.nodeId,n_known.ipAddress,n_known.port);
        P("Request successfuly sent");
        run_update_utilities();
    }
    
    void stablize(){
        stablize_1();
    }
    
    void stablize_1(){
        P("In stablise 1");
        Node succ = fingerTable.get(1-1).node;
        P("Sucessor details : " + succ);
        ask_successor_its_predecessor(succ);
        P("Sent request for its node sucessor");
        P("Out Stablise 1");
    }
    
    void ask_successor_its_predecessor(Node succ) {
        // query type of node succ
        // query type is 3 and its solution is of type 4
        String msg = ""+this.nodeId;
      //  if(!succ.ipAddress.equals(this.ipAddress) && succ.port != this.port)
            send(-1,3,msg,succ.ipAddress,succ.port);
      //  else
      //      E("It's same are you seeing this");
    }
    
    
    void stablize_2(Node n_new){
        P("In stablise2");
        int succ_id = fingerTable.get(1-1).node.nodeId;
        boolean test = interval_test(this.nodeId, succ_id, n_new.nodeId);
        if( (n_new.nodeId > this.nodeId && n_new.nodeId < succ_id) || test){
            E("------------------------------------------------------Setted new successor " + n_new );
            fingerTable.get(1-1).node = n_new;
        }
       // now notify the new sucessor as you being its predecdssor
        notify(n_new);
        Node succ = fingerTable.get(1-1).node;
        String msg = this.nodeId + "$"+ this.ipAddress+"$"+ this.port;
        P("Sending request");
       // if(!succ.ipAddress.equals(this.ipAddress) && succ.port != this.port)
            send(-1,5,msg,succ.ipAddress,succ.port);
       // else
       //     E("It's same are you seeing this");
        P("Sent request");
        P("out stablise2"); 
    }
    
    void notify(Node n_new){
        // i might be our precedecessor
        P("notiying started");
        P("predecessor can be " + n_new);
        
      //  P(n_new.nodeId +" <  " + this.nodeId+ " && " + n_new.nodeId+ " > "+  predecessor.nodeId);
        if(predecessor == null){
            P(predecessor + "");
            predecessor = n_new;
            P("new predecessor defined  "+ predecessor);
        }
        else if((n_new.nodeId <  this.nodeId && n_new.nodeId >  predecessor.nodeId) || interval_test(predecessor.nodeId, this.nodeId, n_new.nodeId)){
            predecessor = n_new;
            P("updated previous predecessor");
        }
//        else if(predecessor.nodeId == fingerTable.get(1-1).node.nodeId){
//            P("Predecesoor forcefully  modified as one node only");
//            P(predecessor + "");
//            predecessor = n_new;
//            P("new predecessor defined  "+ predecessor);
//        }
        P("notifying finished");
        printFIngerTable();
    }
    
    void fix_fingers(){
        P1("Fixing fingers in");
        int random_number_finger_index = 0;
        for(;random_number_finger_index < m; random_number_finger_index++){
            P1("Fixing entry " + random_number_finger_index);
            int start = fingerTable.get(random_number_finger_index).startInterval;
            P1("finding successor of "+start);
            Node succ = fingerTable.get(1-1).node;
            if(this.nodeId < start && start <= succ.nodeId)
            {
                P1("Lied in this range");
                fingerTable.get(random_number_finger_index).node = succ;
            }
            else{
                P1("Ask other persons to find the my successor " + random_number_finger_index);
                find_successor(start,random_number_finger_index);
                P1("Sent query to everyone");
            }
            P1("fixing out");
        }
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
                            Socket s = socket.accept();
                            din = new DataInputStream(s.getInputStream());
                            String msg = din.readUTF();
                            P1("Mesage Received "+ msg);
                            // hadle message
                            handler(msg);
                            P1("Processing of mesage completed "+ msg);
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
    
    void handler(String msg){
        P1("In Hnadler\nmessage is "+ msg);
        // handle different message
        String comp[] = msg.split(":");
        String receiver_ip = comp[0];
        int receiver_port = Integer.parseInt(comp[1]);
        int type = Integer.parseInt(comp[2]);
        int queryingfingerIndex =Integer.parseInt(comp[3]);
        P1("ID is "+ comp[4]);
        
       
        // all odd are questions and even are answers for i th question i+1 is answer
        if(type == 1){
            int id = Integer.parseInt(comp[4]);
            boolean test = interval_test( this.nodeId, this.fingerTable.get(0).node.nodeId, id);
            P1("Result" + this.nodeId+" < "+ id + "&&"+  id +"<=" + this.fingerTable.get(0).node.nodeId +" || "+ test);
            if((this.nodeId < id && id <= this.fingerTable.get(0).node.nodeId) || test){
                // solution find send and to querying node and type is 2
                String reply = this.nodeId + "$"+ this.ipAddress+"$"+this.port; 
                P1("Solution Found Forwarding Query from " + this + " to " + receiver_ip + " -> " + receiver_port);
               // if(!receiver_ip.equals(this.ipAddress) || receiver_port != this.port)
                    send(queryingfingerIndex,2, reply,receiver_ip,receiver_port);
               // else
               //     P1("Type 1 It's same are you seeing this");
            }
            else{
                P1("Type 1");
                Node n_new = closest_preceding_finger(id,m);
                P1("------**closest precedding finger is  **-----" + n_new);
                P1("Forwarding query " + this.nodeId + "&& Message is " + msg );
               // if(!n_new.ipAddress.equals(this.ipAddress) || n_new.port != this.port)
                    send(queryingfingerIndex,1, comp[4], n_new.ipAddress, n_new.port);
               // else
               //     E("Type 1It's same are you seeing this");
            }
        }
        
        else if(type == 2){
            P("Type 2");
            //update successor at finger table at querying finger index
            if(queryingfingerIndex == -10){
                //need to run update thread for this node
                P("Successor Received " +comp[4]);
                msg = comp[4];  
                String pred_detils[] = msg.split("\\$");
                int node_id= Integer.parseInt(pred_detils[0]);
                String node_ip = pred_detils[1];
                int node_port = Integer.parseInt(pred_detils[2]);

                Node n_new = new Node();
                n_new.nodeId = node_id;
                n_new.ipAddress = node_ip;
                n_new.port = node_port;
                //set up node details
                
                fingerTable.get(0).node = n_new;
                P("Successor done "+ fingerTable.get(0).node );
                run_update_utilities();
            }
           
            else
            {
                msg = comp[4];
                String node_detils[] = msg.split("\\$");
                int node_id= Integer.parseInt(node_detils[0]);
                String node_ip = node_detils[1];
                P1("My predecessor is " + node_id);
                int node_port = Integer.parseInt(node_detils[2]);
                // the received one is predecessor ask him the sucessor
                // just send it another query for sucessor
                P1("Asking for successor of the predecessor");
                ask_node_its_successor(queryingfingerIndex,msg,node_ip,node_port);
                
            }
        }
        else if(type == 3){
            P("Type 3");
            // asked for my predecessor reply with type 4 and send predecessor id and ip
            String reply=predecessor.nodeId + "$"+ predecessor.ipAddress+"$"+predecessor.port;
           // if(!receiver_ip.equals(this.ipAddress) || receiver_port != this.port)
                send(-1,4,reply,receiver_ip,receiver_port);
          //  else
                E("It's same are you seeing this");
          //  if(!this.ipAddress.equals(receiver_ip) || this.port != receiver_port)
          //      P("sending to myself");
            P("Sent successful for type 3 " + reply);            
        }
        
        
        else if(type == 4){
            P("Type 4");
            msg = comp[4];
            // call stablize_2 and give node as parameter with nodeId as msg
            String node_detils[] = msg.split("\\$");
            int node_id= Integer.parseInt(node_detils[0]);
            String node_ip = node_detils[1];
            int node_port = Integer.parseInt(node_detils[2]);
            Node n_new = new Node();
            // setup node details
            n_new.nodeId = node_id;
            n_new.ipAddress = node_ip;
            n_new.port = node_port;
            stablize_2(n_new);
        }
        else if(type == 5){
            P("Type 5");
            msg = comp[4];
            // i might be your predecessor check this
            String node_details[] = msg.split("\\$");
            E("***********See here here here ********");
            P(node_details[0]+"");
            int node_id= Integer.parseInt(node_details[0]);
            String node_ip = node_details[1];
            int node_port = Integer.parseInt(node_details[2]);
            Node n_new = new Node();
            // set up node details
            n_new.nodeId = node_id;
            n_new.ipAddress = node_ip;
            n_new.port = node_port;
            notify(n_new); 
            P("Successfuly completed 5");
        }
        else if(type == 6){
        // send my successor to receiver
        P1("Type 6");
            Node my_succ = fingerTable.get(0).node;
            String reply = my_succ.nodeId + "$"+ my_succ.ipAddress+"$"+ my_succ.port;
          //  if(!receiver_ip.equals(this.ipAddress) || receiver_port != this.port)
                send(queryingfingerIndex,7,reply,receiver_ip,receiver_port);
         //   else
         //       P1("It's same are you seeing this");
            P1("Sended the successor");
        }
        else if(type == 7){
        // received successor to query
                P1("Type 7");
                msg = comp[4];  
                String pred_detils[] = msg.split("\\$");
                int node_id= Integer.parseInt(pred_detils[0]);
                String node_ip = pred_detils[1];
                int node_port = Integer.parseInt(pred_detils[2]);

                Node n_new = new Node();
                n_new.nodeId = node_id;
                n_new.ipAddress = node_ip;
                n_new.port = node_port;
                //set up node details
                P1("Received the successor "+ queryingfingerIndex+"  <-> " + n_new);
                if(queryingfingerIndex >=  0)
                    fingerTable.get(queryingfingerIndex).node = n_new;
                else
                    E("Finger index is -negative");
        }
    }
    
    void run_update_utilities(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(count < 500){    
                        stablize();
                        fix_fingers();
                    }
            }
        };
        Thread t = new Thread(r);
        t.start();
        P("Update utilities started");
    }
 
    void send(int queryingFingerIndex,int type, String msg,String receiver_ip, int receiver_port){   
        if(type == 1 || type == 2 || type == 4 || type == 6){// query finger table
            // msg format sender_ip:sender_port:querytype:queryingFingerIndex:id
            String new_msg = ipAddress+":"+port+":"+type+":"+ queryingFingerIndex +":"+msg;
            sendMessage(receiver_ip, receiver_port, new_msg);
            P("sent successful by 1");
        }
        else if(type == 3 || type == 5 || type == 7){
            String new_msg = ipAddress+":"+port+":"+type+":"+ queryingFingerIndex +":"+msg;
            sendMessage(receiver_ip, receiver_port, new_msg);
            P("sent successful by 2");
        }
    }
    
    void sendMessage(String ip, int port, String msg){
        try {
             P("Message Sending " + msg);
            Socket s = new Socket(ip,port);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(msg);
            s.close();
            P("Message Sent " + msg);
        }
        catch (IOException ex) {
            
        }  
    }

    @Override
    public String toString() {
        return this.nodeId + "**"+ this.ipAddress + "**" + this.port;
    }
}



class FingerTableRecord{
    Node node;
    int startInterval;
    int endInterval;

    public FingerTableRecord() {
    }

    
    FingerTableRecord(int start, int end) {
        this.node = new Node();
        startInterval = start;
        endInterval = end;
    }

    void updateNode(Node node) {

    }

    @Override
    public String toString() {
        return startInterval + "$$$$$$$$$$$$$$$$$$"+endInterval+"$$$$$$$$$$"+ node.nodeId;
    }
}
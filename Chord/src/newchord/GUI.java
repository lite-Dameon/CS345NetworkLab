package newchord;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import newchord.Node;
import newchord.Node;

/**
 *
 * @author khyati
 */
public class GUI extends javax.swing.JFrame {

    private Point initialClick;
    int m = 3;
   Node a,b;
   boolean toggle_pink = false;
   boolean toggle_dirty = false;
   boolean toggle_yellow = false;
   boolean toogle_table = false;
    /**
     * Creates new form GUI
     */
    public GUI() {
        this.setUndecorated(true);
        initComponents();
        this.setBackground(new Color(0,0,1,0));
        this.setLocationRelativeTo(null);
        
        
        
         node.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
                System.out.println("Pressed");
            }
        });

        node.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                // get location of Window
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                System.out.println("Dragging");

                // Determine how much the mouse moved since the initial click
                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });
        yellow.setVisible(false);
        dirty.setVisible(false);
        pinky.setVisible(false);
        fing_table.setVisible(false);
        predecessor_details.setVisible(false);
        successor_details.setVisible(false);
        jLabel3.setVisible(false);
        succ_back.setVisible(false);
        fing_table.setRowHeight(20);
        fing_table.getParent().getParent().setVisible(false);
        key_container.setVisible(false);
        key_container.getParent().setVisible(false);
        key_container.getParent().getParent().setVisible(false);
        initFont();
        
        key_container.setEditable(false);
        //blue.setText("S");
        pinkyText.setText("P");
        yellowText.setText("S");
        dirtyText.setText("K");
        
        
        Enumeration e;
        try {
            e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()){
                    InetAddress i = (InetAddress) ee.nextElement();
                    if(i instanceof Inet4Address){                      
                        System.out.println(i.getHostAddress());
                        node_ip.addItem(i.getHostAddress());  
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void update_key_container(ArrayList<Integer> keys){
        key_container.setText("");
        String str = "";
        for(int i =0 ; i < keys.size(); i++){
            str += keys.get(i);
            if(i != keys.size()-1)
                str += " , ";
        }
        key_container.setText(str);
    }
    
    void call_my_methods(){
        System.out.println("Called me ----------------->>>>>>>>>>");
    }
    
    private void initFont() {
        try {
            Font font_2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/newchord/font/digital-7.ttf")).deriveFont(25f);
    
            Font font_1 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/newchord/font/digital-7.ttf")).deriveFont(35f);
    
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/newchord/font/digital-7.ttf")).deriveFont(50f);
    
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/newchord/font/digital-7.ttf")).deriveFont(70f);
            Font font3 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/newchord/font/digital-7.ttf")).deriveFont(60f);
            connect_button.setFont(font_2);
            blue.setFont(font);
            yellowText.setFont(font);
            pinkyText.setFont(font);
            dirtyText.setFont(font);
            node_id_label.setFont(font2);
            node_id_value.setFont(font3);
            myDetails.setFont(font_1);
            known_details.setFont(font_1);
        } catch (FontFormatException | IOException ex) {
            //Logger.getLogger(ClockView.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        close = new javax.swing.JLabel();
        predecessor_details = new javax.swing.JLabel();
        successor_details = new javax.swing.JLabel();
        succ_back = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pinkyText = new javax.swing.JLabel();
        dirtyText = new javax.swing.JLabel();
        yellowText = new javax.swing.JLabel();
        yellow = new javax.swing.JLabel();
        dirty = new javax.swing.JLabel();
        pinky = new javax.swing.JLabel();
        connect_button = new javax.swing.JButton();
        myDetails = new javax.swing.JLabel();
        node_ip = new javax.swing.JComboBox<>();
        known_node_id = new javax.swing.JTextField();
        known_node_ip = new javax.swing.JTextField();
        known_node_port = new javax.swing.JTextField();
        known_details = new javax.swing.JLabel();
        node_id = new javax.swing.JTextField();
        node_port = new javax.swing.JTextField();
        node_id_value = new javax.swing.JLabel();
        node_id_label = new javax.swing.JLabel();
        node = new javax.swing.JLabel();
        blue = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fing_table = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        key_container = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newchord/images/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 150, -1, -1));

        predecessor_details.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        predecessor_details.setForeground(new java.awt.Color(255, 255, 204));
        predecessor_details.setText("predecessor");
        getContentPane().add(predecessor_details, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 270, 50));

        successor_details.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        successor_details.setForeground(new java.awt.Color(255, 255, 204));
        successor_details.setText("successor");
        getContentPane().add(successor_details, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 580, 280, 50));

        succ_back.setIcon(new javax.swing.ImageIcon("C:\\Users\\khyati\\Pictures\\successor.png")); // NOI18N
        getContentPane().add(succ_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 560, -1, 90));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\khyati\\Pictures\\predecessor.png")); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        pinkyText.setText("pinky");
        getContentPane().add(pinkyText, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 50, 40, 50));

        dirtyText.setText("dirty");
        getContentPane().add(dirtyText, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 310, 40, 40));

        yellowText.setText("yellow");
        getContentPane().add(yellowText, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, 40, 50));

        yellow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newchord/images/yellow.png"))); // NOI18N
        yellow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                yellowMouseClicked(evt);
            }
        });
        getContentPane().add(yellow, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 560, -1, -1));

        dirty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newchord/images/dirty.png"))); // NOI18N
        dirty.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dirtyMouseClicked(evt);
            }
        });
        getContentPane().add(dirty, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, -1, -1));

        pinky.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newchord/images/pink.png"))); // NOI18N
        pinky.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pinkyMouseClicked(evt);
            }
        });
        getContentPane().add(pinky, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 140, 50));

        connect_button.setForeground(new java.awt.Color(28, 104, 197));
        connect_button.setText("Connect");
        connect_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connect_buttonMouseClicked(evt);
            }
        });
        getContentPane().add(connect_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, 170, 40));

        myDetails.setForeground(new java.awt.Color(255, 255, 204));
        myDetails.setText("My Details");
        getContentPane().add(myDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 250, 260, 50));

        node_ip.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));
        getContentPane().add(node_ip, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 300, 130, 30));

        known_node_id.setText("-1");
        getContentPane().add(known_node_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 390, 120, 30));

        known_node_ip.setText("nothing");
        known_node_ip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                known_node_ipActionPerformed(evt);
            }
        });
        getContentPane().add(known_node_ip, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 110, 30));

        known_node_port.setText("-1");
        known_node_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                known_node_portActionPerformed(evt);
            }
        });
        getContentPane().add(known_node_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 390, 120, 30));

        known_details.setForeground(new java.awt.Color(255, 255, 204));
        known_details.setText("Known Node Details");
        getContentPane().add(known_details, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 300, 50));
        getContentPane().add(node_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 120, 30));

        node_port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                node_portActionPerformed(evt);
            }
        });
        getContentPane().add(node_port, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 300, 120, 30));

        node_id_value.setForeground(new java.awt.Color(255, 255, 204));
        getContentPane().add(node_id_value, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 60, 100));

        node_id_label.setForeground(new java.awt.Color(255, 255, 204));
        node_id_label.setText("NODE");
        getContentPane().add(node_id_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 130, 200, 60));

        node.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newchord/images/node.png"))); // NOI18N
        node.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                nodeMouseMoved(evt);
            }
        });
        node.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nodeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nodeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                nodeMouseExited(evt);
            }
        });
        getContentPane().add(node, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 570, 580));

        blue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newchord/images/blue.png"))); // NOI18N
        blue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blueMouseClicked(evt);
            }
        });
        getContentPane().add(blue, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 320, -1, -1));

        fing_table.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        fing_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "START", "ID", "IP", "PORT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        fing_table.setAutoscrolls(false);
        fing_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fing_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(fing_table);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 140, 380, 430));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(150, 176));

        key_container.setColumns(20);
        key_container.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        key_container.setRows(5);
        key_container.setAutoscrolls(false);
        key_container.setMaximumSize(new java.awt.Dimension(200, 200));
        key_container.setPreferredSize(new java.awt.Dimension(200, 174));
        jScrollPane1.setViewportView(key_container);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 370, 390));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nodeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodeMouseEntered
        // TODO add your handling code here:
        
        yellow.setVisible(true);
        dirty.setVisible(true);
        pinky.setVisible(true);
        if(fing_table.getRowCount() > 0){
            fing_table.setVisible(true);
            fing_table.getParent().getParent().setVisible(true);
        }
    }//GEN-LAST:event_nodeMouseEntered

    void table_add_item(int start, int id, String ip, int port){
        DefaultTableModel model = (DefaultTableModel) fing_table.getModel();
        model.addRow(new Object[]{start,id,ip,port});
    }
    
    void table_update_item(int index,int id, String ip, int port){
         DefaultTableModel model = (DefaultTableModel) fing_table.getModel();
         model.setValueAt(id, index, 1);
         model.setValueAt(ip, index, 2);
         model.setValueAt(port, index, 3);
    }
    
    private void nodeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodeMouseExited
        // TODO add your handling code here:
        if(!toggle_yellow)
        {
            yellow.setVisible(false);
            successor_details.setVisible(false);
            succ_back.setVisible(false);
        }
        if(!toggle_dirty)
        {
            dirty.setVisible(false);
            key_container.setVisible(false);
            key_container.getParent().setVisible(false);  
            key_container.getParent().getParent().setVisible(false);
        }
        if(!toggle_pink)
        {
            pinky.setVisible(false);
            predecessor_details.setVisible(false);
            jLabel3.setVisible(false);
        }
        if(!toogle_table)
        {
            fing_table.setVisible(false);
            fing_table.getParent().getParent().setVisible(false);
        }
        
        
    }//GEN-LAST:event_nodeMouseExited

    
    void update_successor(int id, String ip, int port){
        successor_details.setText(id+ " - " +ip+ " - " +port);
    }
    
    void update_predecessor(int id, String ip, int port){
        predecessor_details.setText(id+" - " +ip+ " - " +port);
    }
    
    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void node_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_node_portActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_node_portActionPerformed

    private void known_node_ipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_known_node_ipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_known_node_ipActionPerformed

    private void known_node_portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_known_node_portActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_known_node_portActionPerformed

    boolean verify(){
        try{
            Integer.parseInt(node_id.getText().toString());
            Integer.parseInt(known_node_id.getText().toString());
            if(node_ip.equals("") || known_node_ip.equals(""))
                return false;
            Integer.parseInt(node_port.getText().toString());
            Integer.parseInt(known_node_port.getText().toString());
        }
        catch(Exception ex){
            return false;
        }
        return true;
    }
    
    private void connect_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connect_buttonMouseClicked
        // TODO add your handling code here:
        if(verify())
        {
            node_id.setEditable(false);
            node_ip.setEditable(false);
            node_port.setEditable(false);

            known_node_id.setEditable(false);
            known_node_ip.setEditable(false);
            known_node_port.setEditable(false);
            connect_button.setEnabled(false);
            
            //a = new Node(m,Integer.parseInt(node_id.getText().toString()));
            
            if(known_node_ip.getText().toString().equals("nothing") 
               && Integer.parseInt(known_node_port.getText().toString()) == -1
               && Integer.parseInt(known_node_id.getText().toString()) == -1){
                System.out.println("First Node");
                a = new Node(m,Integer.parseInt(node_id.getText().toString()));    
                a.ipAddress = node_ip.getSelectedItem().toString();
                a.port = Integer.parseInt(node_port.getText().toString());
                
                a.setFrame(this);
                
                a.node_initialize();
                a.add_key(8);
                a.first_node();
//                a.node_id = Integer.parseInt(node_id.getText().toString());
//                a.ipAddress = node_ip.getSelectedItem().toString();
//                a.port = Integer.parseInt(node_port.getText().toString());
//                a.node_initialize();
//                a.first_node();
            }
            else{
                int id_a = Integer.parseInt(known_node_id.getText().toString());
                int id_b = Integer.parseInt(node_id.getText().toString());
                a = new Node(m,id_a);
                b = new Node(m,id_b);
                System.out.println("Join node");
                a.ipAddress =  known_node_ip.getText().toString();
                a.port = Integer.parseInt(known_node_port.getText().toString());
                
                b.ipAddress =  node_ip.getSelectedItem().toString();
                b.port = Integer.parseInt(node_port.getText().toString());;
                
                
                b.setFrame(this);
                b.node_initialize();
                b.join(a.node_id, a.ipAddress, a.port);
// 
            }
        }
        else 
                System.out.println("Invalid Details");
        
    }//GEN-LAST:event_connect_buttonMouseClicked

    void set_value_of_node_id(int id){
        node_id_value.setText("" + id);
    }
    
    private void pinkyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pinkyMouseClicked
        // TODO add your handling code here:
        toggle_pink = !toggle_pink;
    }//GEN-LAST:event_pinkyMouseClicked

    private void yellowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_yellowMouseClicked
        // TODO add your handling code here:
        toggle_yellow = !toggle_yellow;
    }//GEN-LAST:event_yellowMouseClicked

    private void dirtyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dirtyMouseClicked
        // TODO add your handling code here:
        toggle_dirty = !toggle_dirty;
    }//GEN-LAST:event_dirtyMouseClicked

    private void blueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blueMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_blueMouseClicked

    private void fing_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fing_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fing_tableMouseClicked

    private void nodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodeMouseClicked
        // TODO add your handling code here:
        toogle_table = !toogle_table;
        System.out.println("Clicked over table toggle ----------->>>>>>>>>>>>>");
    }//GEN-LAST:event_nodeMouseClicked

    private void nodeMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nodeMouseMoved
        // TODO add your handling code here:
        if(toggle_pink){
            predecessor_details.setVisible(true);
            jLabel3.setVisible(true);
        }
        
        if(toggle_yellow)
        {
            succ_back.setVisible(true);
            successor_details.setVisible(true);
        }
        if(toggle_dirty){
            key_container.setVisible(true);
            key_container.getParent().setVisible(true);
            key_container.getParent().getParent().setVisible(true);
        }
    }//GEN-LAST:event_nodeMouseMoved
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blue;
    private javax.swing.JLabel close;
    private javax.swing.JButton connect_button;
    private javax.swing.JLabel dirty;
    private javax.swing.JLabel dirtyText;
    private javax.swing.JTable fing_table;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea key_container;
    private javax.swing.JLabel known_details;
    private javax.swing.JTextField known_node_id;
    private javax.swing.JTextField known_node_ip;
    private javax.swing.JTextField known_node_port;
    private javax.swing.JLabel myDetails;
    private javax.swing.JLabel node;
    private javax.swing.JTextField node_id;
    private javax.swing.JLabel node_id_label;
    private javax.swing.JLabel node_id_value;
    private javax.swing.JComboBox<String> node_ip;
    private javax.swing.JTextField node_port;
    private javax.swing.JLabel pinky;
    private javax.swing.JLabel pinkyText;
    private javax.swing.JLabel predecessor_details;
    private javax.swing.JLabel succ_back;
    private javax.swing.JLabel successor_details;
    private javax.swing.JLabel yellow;
    private javax.swing.JLabel yellowText;
    // End of variables declaration//GEN-END:variables
}

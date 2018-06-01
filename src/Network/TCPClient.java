/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Network;

import CF.Protocol;
import TypeConverter.ByteTobyte;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caovi
 */
public class TCPClient implements Runnable{
    private final String hostname;
    private final int port;
    private Socket socket;
    private static DataInputStream is;
    private static DataOutputStream os;
    
    //constructor
    public TCPClient(String hostname,int port){
        this.hostname=hostname;
        this.port=port;
    }
    
    //exec after constructor
    @Override
    public void run() {
        try {
            socket=new Socket(hostname,port);
            is=new DataInputStream(socket.getInputStream());
            os=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Can't connect.");
        }
        
    }
    
    
    public static void send(int DeviceID,int channel){
        try {
            byte[] cmd=Protocol.RelayToggle(DeviceID, channel);
            os.write(cmd);
            
            readRespond();
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void readRespond() throws IOException {
        byte[] buffer=new byte[100];
        int ResLength=is.read(buffer);//return number of byte are read
        if(ResLength>0){
            buffer=new byte[ResLength];
            is.read(buffer);
            String strRes="";
            for(byte i:buffer){
                strRes +=TCPClient.decimal2hex(i);
            }
            System.out.print(strRes);
        }
    }
    public static String decimal2hex(int d) {
        String digits = "0123456789ABCDEF";
        String hex = "";
        char ch;
        if (d <= 0) {
            d = 256 + (d);
            int base = 16; // flexible to change in any base under 16
            
            while (d > 0) {
                int digit = d % base; // rightmost digit
                hex = digits.charAt(digit) + hex; // string concatenation
                d = d / base;
            }
            return hex;
        } else {
            return String.valueOf((char) d);
        }
        
    }
    
    
}

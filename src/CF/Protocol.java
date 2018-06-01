/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CF;

import TypeConverter.CharByteConverter;
import TypeConverter.StringHexToByteHex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author caovi
 */

public class Protocol {
    public static byte[] RelayToggle(int DeviceID,int channel){
        String data="P0"+Integer.toString(channel)+":T";
        String cmd=makeCFCmd(DeviceID,"TRLYSET",data);
        return new StringHexToByteHex(cmd.toUpperCase()).getByteHex();
    }

    private static String makeCFCmd(int DeviceID,String command,String data) {
        String cmd="f2"+Integer.toString(DeviceID)+"f3"+command+"f4"+data+"f5f5";
        return cmd;
    }
    
}

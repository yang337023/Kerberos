package Client;

import wgl.Renzheng;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class PrepareConn {

	public PrepareConn(){
	}

	public static boolean returnKerberos(long id , String pwd) throws Exception{
		byte[] receiveBuffer = new byte[8216];
		receiveBuffer=ClientOperation.clientToAS(id, 2);
		receiveBuffer=ClientOperation.clientToTgs(id,pwd,3,receiveBuffer);
		byte num=ClientOperation.clientToPSever(6,receiveBuffer);



		if (num==7){
			return true;
		}

		return false;
	}
}

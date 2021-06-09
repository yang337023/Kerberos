package wgl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import Message.*;
import Security.DES.Des;
import APP.Application;
import Databean.User;


public class GpChat extends JFrame implements ActionListener{

	private User from;
	private JTextPane jtWord = new JTextPane();
	private JTextPane TextPane = new JTextPane();
	private JScrollPane scrollTex;
	
	private JButton Exit = new JButton();
	private JButton Send = new JButton();
	Border b = new LineBorder(Color.GRAY, 1); 
	
	public GpChat(final User from){
		this.from = from;
		this.setResizable(false); 		//�����޸Ĵ�С
		this.setTitle(from.toString());
		this.setSize(500,600);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-350)/2+260,(int)(screenSize.height-600)/2);
		
		JPanel p = new JPanel();
		add(p);
		p.setLayout(null);
		
		TextPane.setForeground(Color.BLACK);
		TextPane.setEnabled(false);
		scrollTex = new JScrollPane(TextPane);
		scrollTex.setBounds(10, 10, 480, 320);
		
		jtWord.setBounds(10, 340, 480, 80);
		jtWord.setFont(new Font("����",Font.BOLD,16));
		jtWord.setVisible(true);
		jtWord.setBorder(b);
		jtWord.setBackground(Color.white);
		jtWord.setOpaque(true);	//�������͸��
		jtWord.addKeyListener(new KeyAdapter(){//���̼�����ť
			public void keyPressed(KeyEvent e)
			{
		       if(e.getKeyCode()==KeyEvent.VK_ENTER){	//enter�������ı�
		    	   if(jtWord.getText().equals("")){//������Ϣ����Ϊ��
						JOptionPane.showMessageDialog(null, "������Ϣ����Ϊ��");
					}	
					else{
						String words = jtWord.getText().trim(); 			
						String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
															
						insert("   " + time  + '\n', 18);
					   byte[] bytes1 = String2Byte.String2Bina(words);
					   String miwen1 = "";
					   for(int i=0;i<bytes1.length;i++){
						   miwen1 = miwen1 + bytes1[i];
					   }
					   insert("   " + "���͵ļ�����ϢΪ"  + miwen1+'\n', 12);
						insert("  ��:  " + words  + '\n',18);

						jtWord.setText(null);  
						byte[] message = Message.getRespondMessage(from.getId(), Application.user.getId(), (byte)1, Application.CHAT, words.getBytes());
						try {
							message = Des.encrypt(message);

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						Application.cm.getConn().send(message);
						
					} 
		       }	          
		   }		             
		 });
		
		Exit.setText("�ر�");
		Exit.setFont(new Font("Dialog",0,12));
		Exit.setBounds(100, 430, 100, 30);
		Exit.setBackground(getBackground());
		Exit.setBackground(Color.white);
		Exit.setBorder(b);
		Exit.addActionListener(this);
		
		Send.setText("����");
		Send.setFont(new Font("Dialog",0,12));
		Send.setBounds(300, 430, 100, 30);
		Send.setBackground(Color.white);
		Send.setVisible(true);
		Send.setBorder(b);
		Send.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Send.addActionListener(this);
		
		p.add(scrollTex);
		p.add(jtWord);
		p.add(Exit);
		p.add(Send);
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Exit){
			setVisible(false);
		}
		if(e.getSource() == Send){
			if(jtWord.getText().equals("")){//������Ϣ����Ϊ��
				JOptionPane.showMessageDialog(null, "������Ϣ����Ϊ��");
			}	
			else{
				String words = jtWord.getText().trim();
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());								
				insert("   " + time  + '\n', 12);
				insert("  ��:  " + words  + '\n',18);
				jtWord.setText("");
				//�˴�����Ϣ�����߼�
				byte[] message = Message.getRespondMessage(from.getId(), Application.user.getId(), (byte)1, Application.CHAT, words.getBytes());
				try {
					message = Des.encrypt(message);
//					System.out.print("��������:");
//					for(byte xx:message){
//						System.out.print(xx);
//					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Application.cm.getConn().send(message);
				
			}
		}
	}
	
	private void insert(String text ,int textFont)//���ݴ������ɫ�����֣������ֲ����ı���
	{
		 SimpleAttributeSet set = new SimpleAttributeSet();
		 StyleConstants.setFontSize(set, textFont);//���������С
		 StyleConstants.setForeground(set, Color.BLACK);
		 Document doc = TextPane.getStyledDocument();		 
		 try {
				doc.insertString(doc.getLength(), text, set);//��������
		 } catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
	}
	
	/**
	 * ��Ի�����ʾһ����Ϣ
	 * @param words
	 * @param from
	 */
	
	public void displayInfo(String words){
		byte[] bytes = String2Byte.String2Bina(words);
		String miwen = "";
		for(int i=0;i<bytes.length;i++){
			miwen=miwen+bytes[i];
		}
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
		insert("   " + time  + '\n', 18);




		insert("  "+"�յ�������Ϊ: "+ miwen  + '\n',12);
		insert("  "+"-----����Ϊ-----"+ '\n',12);
		insert("  "+from.getName()+":  " + words  + '\n',18);
	}
}

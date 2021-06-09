package wgl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Client.ConnManger;
import Client.SocketConn;
import Message.Message;

public class AddFrd extends JFrame implements ActionListener{

	/**
	 * @author yuxue
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel back;
	private JLabel text=new JLabel();
	private JTextField usrName = new JTextField("�û�id");		//�������г�ʼ���ı����ı������
	private JButton AddBtn=new JButton();
	private JButton Exit=new JButton();
	
	Border b = new LineBorder(Color.GRAY, 1); 	//��ť�߽��ߵ�����
	
	public AddFrd(){
		
		this.setResizable(false); 		//�����޸Ĵ�С
		this.getContentPane().setLayout(null);
		this.setTitle("��Ӻ���");
		this.setSize(450,300);
		
		//��������λ�ã��ǶԻ������
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-350)/2,(int)(screenSize.height-600)/2+45);
		
		//������屳��
		back=new JLabel();
		ImageIcon icon=new ImageIcon(this.getClass().getResource("���.jpg"));
		back.setIcon(icon);
		back.setBounds(0, -15, 450, 350);
		
		//��ʾ�û�������Ϣ
		text.setBounds(205, 60, 160, 50);
		text.setFont(new Font("Serif",Font.PLAIN,18));
		text.setText("Ҫ��ӵ��û�ID:");
		
		//���������û���
		usrName.setForeground(Color.gray);
		usrName.setBounds(185, 120, 180, 30);
		usrName.setFont(new Font("Serif",Font.PLAIN,12));
		
		//��ӡ���ӡ���ť
		AddBtn.setText("���");
		AddBtn.setFont(new Font("Dialog",0,12));
		AddBtn.setBounds(189, 180, 60, 30);
		AddBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		AddBtn.setBackground(Color.white);
		AddBtn.setBorder(b);
		AddBtn.setVisible(true);

		//��ӡ��رա���ť
		Exit.setText("�ر�");
		Exit.setFont(new Font("Dialog",0,12));		
		Exit.setBounds(300, 180, 60, 30);
		Exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Exit.setBackground(Color.WHITE);
		Exit.setBorder(b);
		Exit.setVisible(true);
		
		//��Ӱ�ť�����¼�
		AddBtn.addActionListener(this);
		Exit.addActionListener(this);
			
		this.getContentPane().add(usrName);
		this.getContentPane().add(text);	
		this.getContentPane().add(AddBtn);
		this.getContentPane().add(Exit);
		this.getContentPane().add(back);
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new AddFrd();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==AddBtn){//����İ�ť�����
			
			String Content = usrName.getText();	//��ȡ�ı�������				
			
			if(Content.equals("")){
				JOptionPane.showMessageDialog(null, "������������Ϣ!");
			}
			else{
				//�����������߼�
				
			}
		}
		else if(e.getSource()==Exit){//����İ�ť��b2
			this.setVisible(false);
		}
	}
}


package wgl;

import APP.Application;
import Client.ConnManger;
import Client.PrepareConn;
import Client.SocketConn;
import Databean.User;
import Message.ContentTool;
import Message.Message;
import Security.DES.Des;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Login extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel back;
	private JLabel jt1=new JLabel();
	private JLabel jt2=new JLabel();
	private JTextField jt = new JTextField("");		//�������г�ʼ���ı����ı������
	private JPasswordField jp=new JPasswordField(20);
	private JButton xa=new JButton();
	private JButton xb=new JButton();
	
	public Login(){
		this.setResizable(false); 		//�����޸Ĵ�С
		this.getContentPane().setLayout(null);
		this.setTitle("��½");
		this.setSize(450,350);
		
		//��������λ�ã��ǶԻ������
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-350)/2,(int)(screenSize.height-600)/2+45);

		//��½1
		back=new JLabel();
	//	ImageIcon icon=new ImageIcon("D:\\Users\\Lays\\Desktop\\Kerberos-authentication-master\\Kerberos\\KerberosClientModel\\src\\wgl\\��½1.jpg");
	//	back.setIcon(icon);
		back.setBounds(-0, 0, 450, 350);
		
		jt.setForeground(Color.gray);
		jt.setBounds(95, 100, 150, 30);
		jt.setFont(new Font("Serif",Font.PLAIN,12));
		
		
		jt1.setBounds(40, 90, 80, 50);
		jt1.setFont(new Font("����",Font.PLAIN,16));
		jt1.setForeground(Color.BLACK);
		jt1.setText("�û���:");
		
		//���������
		jp.setFont(new Font("Serif",Font.PLAIN,12));
		jp.setBounds(95, 150, 150, 30);
		jp.setVisible(true);
		
		jt2.setBounds(40, 140, 80, 50);
		jt2.setFont(new Font("����",Font.PLAIN,16));
		jt2.setForeground(Color.BLACK);
		jt2.setText("��  � ");
		
		xa.setText("��½");
		xa.setFont(new Font("Dialog",0,12));
		xa.setBounds(95, 200, 150, 30);
		xa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		xa.setBackground(getBackground());
		xa.setBackground(Color.white);
		Border b = new LineBorder(Color.white, 2); 
		xa.setBorder(b);
		xa.setVisible(true);

		xb.setText("ע��");
		xb.setFont(new Font("Dialog",0,12));
		xb.setBounds(245, 200, 60, 30);
		xb.setBackground(Color.WHITE);
		xb.setVisible(false);
		xb.setBorder(b);
		xb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		xa.addActionListener(this);
		xb.addActionListener(this);
	
		this.getContentPane().add(jt);
		this.getContentPane().add(jt1);
		this.getContentPane().add(jt2);
		this.getContentPane().add(jp);	
		this.getContentPane().add(xa);
		this.getContentPane().add(xb);
		
		this.getContentPane().add(back);
		this.setVisible(true);


		//������
	/*	b.addActionListener(new ActionListener(){
			boolean checkedpass = true;
			public void actionPerformed(ActionEvent e){
				checkedpass = true;

				if(checkedpass){
					String model = "%s���%s%s�����ˣ����˵��ϰ�%s�Ժ��ζģ�Ƿ����%s���ڣ�"
							+ "��������С��������!����û�а취������%s�ֹ���!ԭ�۶���һ%s�ࡢ��%s�ࡢ��%s���%s��"
							+ "����ȫ��ֻ����ʮ�飬ͳͳֻҪ��ʮ��!%s���˵����㲻����!����������������˴���꣬"
							+ "�㲻�����ʣ��㻹��Ѫ��Ǯ������Ѫ��Ǯ!";
					String result= String.format(model);
					ta.setText("");
					ta.append(result);
				}

			}

		});*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/**
		 * ���Kerberos ��֤����
		 */
	/*	int gap = 10;
		JFrame f = new JFrame("Kerberos");
		f.setSize(410, 400);
		f.setLocation(200, 200);
		f.setLayout(null);
		JPanel pInput = new JPanel();
		pInput.setBounds(gap, gap, 375, 120);
		pInput.setLayout(new GridLayout(4,3,gap,gap));

		//�ı���
		JTextArea ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setBounds(gap, 150+60, 375, 120);
		f.add(pInput);
		f.add(ta);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		String model = "";
		String result= String.format(model);
		ta.setText("111");
		ta.append(result);*/

		// TODO Auto-generated method stub
		if(e.getSource()==xa){//����İ�ť�ǵ�¼

			String usr=jt.getText().toString();	//��ȡ�ı�������			
			char[] passwords = jp.getPassword();			
			String password =String.valueOf(passwords);	//��ȡ���������
			String Content=usr+password;

			new Kerberos().start();
			
			if(usr.equals("")||password.equals("")){
				//System.out.println("������������Ϣ!");
				JOptionPane.showMessageDialog(null, "������������Ϣ!");
			}
			else{

				/*try {
					Thread.sleep(150);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}*/

				xb.setVisible(false);
				xa.setText("���ڵ�½...");
				xa.setBounds(95, 200, 150, 30);
				this.setVisible(true);
				
				long usrId = Long.parseLong(jt.getText());

				boolean goon = false;
				try {
					if(PrepareConn.returnKerberos(usrId, password)){
						goon = true;
					}else{
						JOptionPane.showMessageDialog(null, "��֤����!");
					}
					
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//Client Send Ticket-server to Server Successfully!!!


				/**
				 *
				 */
				System.out.println("goon: "+goon);
				if(goon){  //CHATSERVER
				Application.cm = new ConnManger("chatserver");
				SocketConn conn = Application.cm.getConn();
				if(conn == null){
					System.out.println("null");
				}
				byte[] buffer = new byte[8216];
				byte[] message = Message.getRespondMessage(Application.PSERVERCHAT, usrId, (byte)1, Application.ON_LINE, null);


				try {
				//	System.out.println("1111: "+new String(message));
					message = Des.encrypt(message); //Des����
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		 		conn.send(message);
				conn.receive(buffer);


				try {
					buffer = Des.decrypt(buffer); //Des����
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

					/**
					 *
					 */
				if(Message.getRespond(buffer) == Application.SUCCESS){
					Application.user = new User(usrId, new String(Message.getContent(buffer)));
					message = Message.getRespondMessage(Application.PSERVERCHAT, usrId, (byte)1, Application.GET_FRIEND, null);
					try {
						message = Des.encrypt(message); //Des����
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					conn.send(message);
					conn.receive(buffer);
					try {
						buffer = Des.decrypt(buffer); //Des����
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					this.setVisible(false);
					List<User> user = ContentTool.getUsser(Message.getContent(buffer));
					Chat main =  Chat.getInstance(Application.user.getName(),user);
				}
				else{//������ʾ
					JOptionPane.showMessageDialog(null, "����������һ������!");
				}
				
				}
			}
		}
		else if(e.getSource()==xb){//����İ�ť��b2
			new Regist();
			setVisible(true);
		}
	}
}

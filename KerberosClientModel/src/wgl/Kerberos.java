package wgl;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Kerberos extends Thread{
//    public static void main(String[] args) throws InterruptedException {
//        new Kerberos();
//    }
    @Override
    public void run() {
            int gap = 10;
            JFrame f = new JFrame("Kerberos");
            f.setSize(840, 700);
            f.setLocation(0, 0);
            f.setLayout(null);

            JPanel pInput = new JPanel();
            pInput.setBounds(gap, gap, 500, 300);
            pInput.setLayout(new GridLayout(4,3,gap,gap));

            JButton b = new JButton("����");

            //�ı���
            JTextArea ta = new JTextArea();
            ta.setLineWrap(true);
            //    b.setBounds(100, 120 + 30, 120, 50)



            f.add(pInput);
            f.add(b);
            f.add(ta);
            f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JScrollPane jsp = new JScrollPane(ta);
        //���þ��δ�С.��������Ϊ(�������ϽǺ�����x,�������Ͻ�������y�����γ��ȣ����ο��)
        jsp.setBounds(13, 10, 350, 340);
        //Ĭ�ϵ������ǳ����ı���Ż���ʾ�����������������ù�����һֱ��ʾ
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //�ѹ�������ӵ���������
        f.add(jsp);
        f.setSize(400, 400);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


            f.setVisible(true);
            ta.append("Client Send Login Request to AS Successfully!!!\n");

            ta.paintImmediately(ta.getBounds());
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ta.append("Login Successfully!\n The Ticket-tgs from AS to Client, Receive Successfully!!!\n");
            ta.paintImmediately(ta.getBounds());

        ta.append("TGS��֤��Ϣ��"+Renzheng.C2TGS+"\n");
            ta.paintImmediately(ta.getBounds());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ta.append("Client Send Ticket-request to TGS Successfully!!!\n");
            ta.paintImmediately(ta.getBounds());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ta.append("Check Successfully!\n The Ticket-server from TGS to Client, Receive Successfully!!!\n");
        ta.paintImmediately(ta.getBounds());

        ta.append("Server��֤��Ϣ��"+Renzheng.C2Server+"\n");
        ta.paintImmediately(ta.getBounds());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ta.append("Client Send Ticket-server to Server Successfully!!!\n");
        ta.paintImmediately(ta.getBounds());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //������

        }
}



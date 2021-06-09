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

            JButton b = new JButton("生成");

            //文本域
            JTextArea ta = new JTextArea();
            ta.setLineWrap(true);
            //    b.setBounds(100, 120 + 30, 120, 50)



            f.add(pInput);
            f.add(b);
            f.add(ta);
            f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JScrollPane jsp = new JScrollPane(ta);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp.setBounds(13, 10, 350, 340);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //把滚动条添加到容器里面
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

        ta.append("TGS认证消息："+Renzheng.C2TGS+"\n");
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

        ta.append("Server认证消息："+Renzheng.C2Server+"\n");
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
        //鼠标监听

        }
}



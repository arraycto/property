package com.mlb.orderserviceconsumer.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author mlb
 */
public class MessageUtil {
    public MessageUtil(){
        //做链接前的准备工作  也就是参数初始化
        Properties properties = new Properties();
        //发送邮箱服务器
        properties.setProperty("mail.smtp.host","smtp.qq.com");
        //发送端口
        properties.setProperty("mail.smtp.port","465");
        //是否开启权限控制
        properties.setProperty("mail.smtp.auth","true");
        //true 打印信息到控制台
        properties.setProperty("mail.debug","true");
        //发送的协议是简单的邮件传输协议
        properties.setProperty("mail.transport","smtp");
        properties.setProperty("mail.smtp.ssl.enable","true");
        //建立两点之间的链接
        System.out.println("执行了2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1031468560@qq.com","ihqbqsqdbugwbchh");
            }
        });
        System.out.println("执行了3");
        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("1031468560@qq.com"));

            //设置收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress("3163246646@qq.com"));//收件人
            //设置主题
            message.setSubject("验证码");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent("2569658","text/html;charset=UTF-8");
            //发送一封邮件
            Transport transport = session.getTransport();
            transport.connect("1031468560@qq.com","ihqbqsqdbugwbchh");
            Transport.send(message);
            System.out.println("执行了");
        } catch (MessagingException e) {
            e.printStackTrace();
        }finally {

        }

    }

    public static void main(String[] args) {
        System.out.println("执行了1");
        MessageUtil messageUtil =  new MessageUtil();
    }
}

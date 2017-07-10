package com.ywd.tool.email.impl;

import com.ywd.tool.email.MailSender;
import com.ywd.tool.email.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by admin on 2017/7/7.
 */
@Component
public class MailSenderImpl implements MailSender {

    /**
     * 发送一封简单邮件
     * @return
     * @throws Exception
     */
    @Override
    public boolean sendSimpleMail(String to) throws Exception {
        return false;
    }

    /**
     * 发送一个带附件的邮件
     * @param policyPDF   保单文件
     * @param to        收件人
     * @return
     * @throws Exception
     */
    @Override
    public boolean sendAttachmentsMail(File policyPDF, String to, HashMap<String,Object> map) throws Exception {
        String mailHost = "自己填写就好";
        String send = "自己填写就好";
        String password = "自己填写就好";
        String subString = String.format(MailUtil.MAIL_SUBJECT, map.get("policyNumber"));
        String context =  String.format(MailUtil.MAIL_CONTEXT, map.get("policyNumber"));
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", mailHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);                            // 设置为debug模式, 可以查看详细的发送 log
        MimeMessage message = MailUtil.createMimeMessage(session, send, to, policyPDF, subString, context);
        Transport transport = session.getTransport();
        transport.connect(send, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return true;
    }
}

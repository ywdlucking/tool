package com.ywd.tool.email;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;

/**
 * Created by admin on 2017/7/7.
 */
public class MailUtil {

    public final static String MAIL_SUBJECT = "民生e生无忧费用补偿医疗保险电子保单-%s";

    public final static String MAIL_CONTEXT = "亲爱的客户：<br/>    &emsp;&emsp;请查收您购买的民生e生无忧费用补偿医疗保险电子保单，保单号为%s，谢谢！";

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, File filePdf, String subString, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "民生健康", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "用户", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject(subString, "UTF-8");


        MimeBodyPart text = new MimeBodyPart();
        text.setContent(content, "text/html;charset=UTF-8");

        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource(filePdf));  // 读取本地文件
        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));              // 设置附件的文件名（需要编码）

        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(attachment);
        mm.setSubType("mixed");

        // 5. Content: 邮件内容
        message.setContent(mm);
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
}

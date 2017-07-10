package com.ywd.tool.email;

import java.io.File;
import java.util.HashMap;

public interface MailSender {

    boolean sendSimpleMail(String to) throws Exception;

    boolean sendAttachmentsMail(File policyPDF, String to, HashMap<String,Object> map) throws  Exception;
}
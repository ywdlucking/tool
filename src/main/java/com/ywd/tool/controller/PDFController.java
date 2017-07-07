package com.ywd.tool.controller;

import com.ywd.tool.pdf.PdfToImg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by admin on 2017/7/6.
 */
@Controller
@RequestMapping("/pdf")
public class PDFController {

    @RequestMapping(value = "/pdfbox", method = RequestMethod.GET)
    @ResponseBody
    public void pdfbox() throws Exception {
        PdfToImg.pdfToImageTheOne();
    }

    @RequestMapping(value = "/icepdf", method = RequestMethod.GET)
    @ResponseBody
    public void icepdf() throws Exception {
        PdfToImg.pdfToImageTheTwo();
    }
}

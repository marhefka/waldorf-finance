package hu.waldorf.finance.controllers;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WebController {
    @RequestMapping("/")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/import")
    public String importPage(){
        return "import";
    }

    @RequestMapping("/upload")
    public String processImport(@RequestParam("file") MultipartFile file) throws Exception{
        PDDocument document = PDDocument.load(file.getBytes());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        System.out.println(pdfStripper.getText(document));
        return "redirect:import";
    }
}

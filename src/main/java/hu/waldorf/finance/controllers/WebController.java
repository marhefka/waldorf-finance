package hu.waldorf.finance.controllers;


import hu.waldorf.finance.service.ErstePDFImportService;
import hu.waldorf.finance.service.ErsteXMLImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class WebController {
    @Autowired
    private ErstePDFImportService pdfImportService;
    @Autowired
    private ErsteXMLImportService xmlImportService;

    @RequestMapping("/")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/import")
    public String importPage(){
        return "import";
    }

    @RequestMapping("/processImport")
    public String processImport(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) throws Exception{
        if(file==null){
            // TODO error handling
        }

        switch (type){
            case "1":
                importErste(file);
                break;
            case "2":
                importMagnet(file);
                break;
            default:
                // TODO error handling
        }

        return "redirect:import";
    }

    private void importMagnet(MultipartFile file) {

    }

    private void importErste(MultipartFile file) throws Exception {
        if(file.getOriginalFilename().endsWith(".xml")){
            // TODO: send the MultipartFile to the service, and then convert to file there if needed
            File convFile = new File(file.getOriginalFilename());
            file.transferTo(convFile);
            xmlImportService.importErsteDataFile(convFile,"???");
        }
        else if(file.getOriginalFilename().endsWith(".pdf")){
            pdfImportService.importErsteDataFile(file, "???");
        }
        else{
            // TODO error handling
        }
    }
}

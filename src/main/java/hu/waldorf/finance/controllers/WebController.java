package hu.waldorf.finance.controllers;


import hu.waldorf.finance.service.ErstePDFImportService;
import hu.waldorf.finance.service.ErsteXMLImportService;
import hu.waldorf.finance.service.ImportResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView processImport(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) throws Exception{
        if(file==null){
            return new ModelAndView("redirect:/import?error=file:required");
        }

        switch (type){
            case "1":
                ImportResult result= importErste(file);
                if(!result.success()){
                    return new ModelAndView("redirect:/import?error=file:"+result.getError());
                }
                // TODO: numofresultot success eseten kiirni
                return new ModelAndView("redirect:/import?success&rows="+result.getNumImported());
            case "2":
                importMagnet(file);
                return new ModelAndView("redirect:/import?success"); // TODO
            default:
                return new ModelAndView("redirect:/import?error=type:invalid");
        }
    }

    private void importMagnet(MultipartFile file) {

    }

    private ImportResult importErste(MultipartFile file) throws Exception {
        if(file.getOriginalFilename().endsWith(".xml")){
            // TODO: send the MultipartFile to the service, and then convert to file there if needed
            File convFile = new File(file.getOriginalFilename());
            file.transferTo(convFile);
            xmlImportService.importErsteDataFile(convFile,"???");
            return ImportResult.error("TODO");
        }
        else if(file.getOriginalFilename().endsWith(".pdf")){
            return pdfImportService.importErsteDataFile(file);
        }
        else{
            return ImportResult.error("invalidext");
        }
    }
}

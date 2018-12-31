package hu.waldorf.finance.controllers;


import hu.waldorf.finance.service.ErstePDFImportService;
import hu.waldorf.finance.service.ErsteXMLImportService;
import hu.waldorf.finance.service.ImportResult;
import hu.waldorf.finance.service.MagnetImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    @Autowired
    private ErstePDFImportService erstePDFImportService;
    @Autowired
    private ErsteXMLImportService ersteXMLImportService;
    @Autowired
    private MagnetImportService magnetXMLImportService;

    @RequestMapping("/")
    public String indexPage() {
        return "index";
    }

    @RequestMapping("/import")
    public String importPage() {
        return "import";
    }

    @RequestMapping("/processImport")
    public ModelAndView processImport(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) throws Exception {
        if (file == null || file.getOriginalFilename()==null) {
            return new ModelAndView("redirect:/import?error=file:required");
        }

        ImportResult result;

        switch (type) {
            case "1":
                result = importErste(file);
                break;
            case "2":
                result = importMagnet(file);
                break;
            default:
                return new ModelAndView("redirect:/import?error=type:invalid");
        }

        if (result.success()) {
            return new ModelAndView("redirect:/import?success&rows=" + result.getNumImported());
        }
        return new ModelAndView("redirect:/import?error=file:" + result.getError());
    }

    private ImportResult importMagnet(MultipartFile file) throws Exception{
        return magnetXMLImportService.importMagnetDataFile(file.getBytes(),file.getOriginalFilename());
    }

    private ImportResult importErste(MultipartFile file) throws Exception {
        if (file.getOriginalFilename().endsWith(".xml")) {
            return ersteXMLImportService.importErsteDataFile(file.getBytes(),file.getOriginalFilename(), "11994002-02405425-00000000");
        } else if (file.getOriginalFilename().endsWith(".pdf")) {
            return erstePDFImportService.importErsteDataFile(file.getBytes(),file.getOriginalFilename());
        } else {
            return ImportResult.error("invalidext");
        }
    }
}

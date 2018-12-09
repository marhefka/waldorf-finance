package hu.waldorf.finance.service;

public class ImportResult {
    private String error;
    private int numImported;

    private ImportResult(){}

    public static ImportResult success(int numImported){
        ImportResult thiz=new ImportResult();
        thiz.numImported=numImported;
        return thiz;
    }

    public static ImportResult error(String error){
        ImportResult thiz=new ImportResult();
        thiz.error=error;
        return thiz;
    }

    public boolean success(){
        return error==null;
    }

    public String getError() {
        return error;
    }

    public int getNumImported() {
        return numImported;
    }
}

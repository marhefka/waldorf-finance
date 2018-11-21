package hu.waldorf.finance;

import hu.waldorf.finance.import_.Befizetes;
import hu.waldorf.finance.import_.Diak;
import hu.waldorf.finance.import_.Szerzodes;

import java.util.List;
import java.util.Locale;

public class Hint {
    private final static Locale locale = Locale.forLanguageTag("hu-HU");

    private List<Diak> diakok;
    private Szerzodes szerzodes;
    private int confidencePoint;

    public Hint(List<Diak> diakok, Szerzodes szerzodes) {
        this.diakok = diakok;
        this.szerzodes = szerzodes;
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append(szerzodes.getId());
        sb.append(": ");
        sb.append(szerzodes.getTamogato());
        sb.append(" (");
        sb.append(szerzodes.getMukodesiKoltsegTamogatas());
        sb.append(", ");
        sb.append(szerzodes.getEpitesiHozzajarulas());
        sb.append(") - ");
        sb.append(getGyerekek());
        sb.append(" [");
        sb.append(getConfidencePoint());
        sb.append("]: ");
        return sb.toString();
    }

    private String getGyerekek() {
        StringBuilder stringBuffer = new StringBuilder();

        boolean first = true;
        for (Diak diak : diakok) {
            if (!first) {
                stringBuffer.append(", ");
            }

            stringBuffer.append(diak.getNev());
            stringBuffer.append("(");
            stringBuffer.append(diak.getOsztaly());
            stringBuffer.append(")");

            first = false;
        }

        return stringBuffer.toString();
    }


    public int getConfidencePoint() {
        return confidencePoint;
    }

    public void calculateConfidencePoint(Befizetes befizetes) {
        confidencePoint = 0;

        if (befizetes.getOsszeg() == szerzodes.getMukodesiKoltsegTamogatas()) {
            confidencePoint += 10;
        }

        if (befizetes.getOsszeg() == szerzodes.getEpitesiHozzajarulas()) {
            confidencePoint += 10;
        }

        if (befizetes.getBefizetoNev().startsWith("PSZICHO")) {
            confidencePoint=confidencePoint;
        }
        String befizetoNev = befizetes.getBefizetoNev().toLowerCase(locale);
        befizetoNev = befizetoNev.replace("dr ", "");
        befizetoNev = befizetoNev.replace("dr.", "");
        befizetoNev = befizetoNev.trim();

        String tamogato = szerzodes.getTamogato().toLowerCase(locale);
        confidencePoint += areSimilar(befizetoNev, tamogato);

        String kozlemeny = befizetes.getKozlemeny().toLowerCase(locale);
        kozlemeny = kozlemeny.replace("működési", "");
        kozlemeny = kozlemeny.replace("támogatás", "");
        kozlemeny = kozlemeny.replace("tagdíj", "");
        kozlemeny = kozlemeny.replace("tandíj", "");
        kozlemeny = kozlemeny.replace("szövetségi", "");
        kozlemeny = kozlemeny.replace("waldorf", "");
        kozlemeny = kozlemeny.replace("havi", "");
        kozlemeny = kozlemeny.replace("vállalas", "");
        kozlemeny = kozlemeny.replace("alapítványi", "");
        kozlemeny = kozlemeny.replace("alapítvány", "");
        kozlemeny = kozlemeny.replace("hozzájárulás", "");
        kozlemeny = kozlemeny.replace("+", "");
        kozlemeny = kozlemeny.replace("0", "");
        kozlemeny = kozlemeny.replace("1", "");
        kozlemeny = kozlemeny.replace("2", "");
        kozlemeny = kozlemeny.replace("3", "");
        kozlemeny = kozlemeny.replace("4", "");
        kozlemeny = kozlemeny.replace("5", "");
        kozlemeny = kozlemeny.replace("6", "");
        kozlemeny = kozlemeny.replace("7", "");
        kozlemeny = kozlemeny.replace("8", "");
        kozlemeny = kozlemeny.replace("9", "");
        kozlemeny = kozlemeny.replace("(", "");
        kozlemeny = kozlemeny.replace(")", "");

        while (kozlemeny.indexOf("  ") > 0) {
            kozlemeny = kozlemeny.replaceAll("  ", " ");
        }

        kozlemeny = kozlemeny.trim();

        int max = 0;
        for (Diak diak : diakok) {
            max = Math.max(max, areSimilar(kozlemeny, diak.getNev()));
            max = Math.max(max, areSimilar(befizetoNev, diak.getNev()));
        }
        confidencePoint += max;
    }

    private int areSimilar(String a, String b) {
        a = removeAccents(a.toLowerCase());
        b = removeAccents(b.toLowerCase());

        if (a.contains(b) || b.contains(a)) {
            return 100;
        }

        return (int) Math.round(50.0 * startsWith(a, b) / Math.min(a.length(), b.length()));
//        return (int) Math.round(50.0 * longestSubstring(a, b).length() / Math.min(a.length(), b.length()));
    }

    private String removeAccents(String s) {
        s = s.replaceAll("á", "a");
        s = s.replaceAll("é", "e");
        s = s.replaceAll("í", "i");
        s = s.replaceAll("ó", "o");
        s = s.replaceAll("ö", "o");
        s = s.replaceAll("ő", "o");
        s = s.replaceAll("ú", "u");
        s = s.replaceAll("ü", "u");
        s = s.replaceAll("ű", "u");
        return s;
    }

    private static int startsWith(String a, String b) {
        int x = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                break;
            }
            x++;
        }
        return x;
    }

    private static String longestSubstring(String str1, String str2) {
        StringBuilder sb = new StringBuilder();
        if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
            return "";

        int[][] num = new int[str1.length()][str2.length()];
        int maxlen = 0;
        int lastSubsBegin = 0;

        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    if ((i == 0) || (j == 0))
                        num[i][j] = 1;
                    else
                        num[i][j] = 1 + num[i - 1][j - 1];

                    if (num[i][j] > maxlen) {
                        maxlen = num[i][j];
                        // generate substring from str1 => i
                        int thisSubsBegin = i - num[i][j] + 1;
                        if (lastSubsBegin == thisSubsBegin) {
                            //if the current LCS is the same as the last time this block ran
                            sb.append(str1.charAt(i));
                        } else {
                            //this block resets the string builder if a different LCS is found
                            lastSubsBegin = thisSubsBegin;
                            sb = new StringBuilder();
                            sb.append(str1.substring(lastSubsBegin, i + 1));
                        }
                    }
                }
            }
        }

        return sb.toString();
    }

}

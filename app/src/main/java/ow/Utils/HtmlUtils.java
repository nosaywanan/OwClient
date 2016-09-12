package ow.Utils;

import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2016-07-22.
 */

public class HtmlUtils {
    public static boolean isDocumentHas(Document document, String clazz) {
        return document.getElementsByClass(clazz).size() == 0 ? false : true;
    }

    public static String getAllContentHtmlLink(String link) {
        String singlepage = "_1.shtml?__stay_on_pc";
        String allpage = "_all.shtml?__stay_on_pc";
        link=link.substring(0, link.length() - singlepage.length());
        return link + allpage;
    }

}

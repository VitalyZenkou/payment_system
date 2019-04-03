package helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathHelper {

    private static final String PREFIX = "/WEB-INF/view/";
    private static final String SUFFIX = ".jsp";

    public static String getPath(String pageName) {
        return PREFIX + pageName + SUFFIX;
    }
}

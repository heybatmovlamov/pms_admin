package web_pms_core.util;

import java.util.List;

public class Checker {

    public static void checkThrowDataNotFound(String errorMethodName, Object object) {
        if (object == null) {
            throw new RuntimeException(errorMethodName + " DATA NOT FOUND");
        }
        if (object instanceof List<?> list) {
            if (list.isEmpty()) {
                throw new RuntimeException(errorMethodName + " DATA NOT FOUND");
            }
        }
    }
}

package com.schfoo.force.helper.util;

import com.schfoo.force.helper.exception.ResException;
import com.schfoo.force.helper.exception.TrxException;

import java.util.List;

public class ExceptionUtil {

    public static <T extends RuntimeException> T thr(String msg, boolean isTrx) {
        return thr(List.of(msg), isTrx);
    }

    public static <T extends RuntimeException> T thr(List<String> msg, boolean isTrx) {
        if (isTrx) {
            return (T) new TrxException(msg);
        }
        return (T) new ResException(msg);
    }

}

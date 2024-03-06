package com.schfoo.force.helper.util;

import com.schfoo.force.helper.exception.ResException;
import com.schfoo.force.helper.exception.TrxException;

public class ExceptionUtil {

    public static <T extends RuntimeException> T thr(String msg, boolean isTrx) {
        if (isTrx) {
            return (T) TrxException.build(msg);
        }
        return (T) ResException.build(msg);
    }

}

package com.schfoo.force.helper.controller;

import com.schfoo.force.helper.model.PageDataResponse;
import com.schfoo.force.helper.model.WebResponse;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public abstract class BaseController {

    protected <T> ResponseEntity<?> wrap(Supplier<T> buildWrap) {
        T t = buildWrap.get();
        WebResponse<?> res;

        if (t instanceof PageDataResponse) {
            res = new WebResponse<>().build(
                    ((PageDataResponse<?>) t).getData(), ((PageDataResponse<?>) t).getResult());
        } else {
            res = new WebResponse<>().build(t, null);
        }

        return ResponseEntity.ok(res);
    }

}

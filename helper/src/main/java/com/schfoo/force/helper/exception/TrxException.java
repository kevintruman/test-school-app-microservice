package com.schfoo.force.helper.exception;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class TrxException extends RuntimeException {

    private String msg;

    public static TrxException build(String msg) {
        return TrxException.builder().msg(msg).build();
    }

}

package com.schfoo.force.mwgateway.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class UnauthorizedException extends RuntimeException {

    private String msg;

    public static UnauthorizedException build(String msg) {
        return UnauthorizedException.builder().msg(msg).build();
    }

}

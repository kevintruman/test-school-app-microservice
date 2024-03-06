package com.schfoo.force.helper.exception;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ResException extends RuntimeException {

    private String msg;

    public static ResException build(String msg) {
        return ResException.builder().msg(msg).build();
    }

}

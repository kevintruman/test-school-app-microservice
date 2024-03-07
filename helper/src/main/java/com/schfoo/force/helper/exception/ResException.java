package com.schfoo.force.helper.exception;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ResException extends RuntimeException {

    private List<String> errorsMessage;

    public ResException(List<String> errorsMessage) {
        super(String.join("|", errorsMessage));
        setErrorsMessage(errorsMessage);
    }

}

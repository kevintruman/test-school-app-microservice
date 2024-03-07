package com.schfoo.force.helper.exception;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TrxException extends RuntimeException {

    private List<String> errorsMessage;

    public TrxException(List<String> errorsMessage) {
        super(String.join("|", errorsMessage));
        setErrorsMessage(errorsMessage);
    }

}

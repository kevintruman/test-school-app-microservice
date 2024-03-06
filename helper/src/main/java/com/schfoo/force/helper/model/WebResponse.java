package com.schfoo.force.helper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> implements Serializable {

    private T data;
    private ResultResponse result;
    private List<String> errors;

    @JsonIgnore
    public WebResponse<T> build(T data, ResultResponse result) {
        this.data = data;
        this.result = result;
        return this;
    }

}

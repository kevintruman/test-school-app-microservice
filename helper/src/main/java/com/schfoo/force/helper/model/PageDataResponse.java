package com.schfoo.force.helper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Streamable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDataResponse<T> implements Serializable {

    private List<?> data;
    private ResultResponse result;

    public PageDataResponse(Page<T> page) {
        this.data = Optional.ofNullable(page).map(Streamable::toList).orElse(new ArrayList<>());
        this.result = new ResultResponse(page);
    }

    public PageDataResponse(Page<?> page, List<T> data) {
        this.data = Optional.ofNullable(data).orElse(new ArrayList<>());
        this.result = new ResultResponse(page);
    }

}

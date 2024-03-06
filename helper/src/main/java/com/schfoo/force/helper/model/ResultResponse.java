package com.schfoo.force.helper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.io.Serializable;
import java.util.Optional;

@Data
public class ResultResponse implements Serializable {

    private Long page;
    private Long size;
    private Long total;

    @JsonIgnore
    public ResultResponse(Page<?> pageable) {
        page = Optional.ofNullable(pageable).map(Page::getPageable)
                .map(Pageable::getPageNumber).map(e -> e + 1L).orElse(1L);
        size = Optional.ofNullable(pageable).map(Slice::getPageable)
                .map(Pageable::getPageSize).map(e -> (long) e).orElse(10L);
        total = Optional.ofNullable(pageable).map(Page::getTotalElements).orElse(0L);
    }

}

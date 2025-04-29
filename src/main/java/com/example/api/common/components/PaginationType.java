package com.example.api.common.components;

import com.example.api.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class PaginationType<T, R> {
    public Page<R> findAll(Page<T> entityPage, int page, int size, Function<T, R> mapper) {
        if (page >= entityPage.getTotalPages()) throw new ResourceNotFoundException("Page number out of bounds");

        List<R> dtoList = entityPage.getContent().stream().map(mapper).toList();

        return new PageImpl<>(dtoList, PageRequest.of(page, size), entityPage.getTotalElements());
    }
}

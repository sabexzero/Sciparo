package com.example.rockpaperscissorsultimate.web.mappers.interfaces;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DtoMapper<E,D> {
    D toDto(
        E entity
    );
    
    List<D> toDto(
            List<E> entities
    );
    
}

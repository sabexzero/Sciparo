package com.example.rockpaperscissorsultimate.web.mappers.interfaces;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CreateRequestMapper<E,D>{
    E toEntity(
            D createRequest
    );
    
    List<E> toEntity(
            List<D> createRequests
    );
}

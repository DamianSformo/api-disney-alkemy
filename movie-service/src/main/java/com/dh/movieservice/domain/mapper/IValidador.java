package com.dh.movieservice.domain.mapper;

import com.dh.movieservice.Exceptions.ResourceNotFoundException;

public interface IValidador<T>{

    String msjError = "La búsqueda no arrojó resultados con id: ";

    T checkId(Long c) throws ResourceNotFoundException;

}

package com.dh.moviecharacterservice.domain.mapper;

import com.dh.moviecharacterservice.Exceptions.ResourceNotFoundException;

public interface IValidador<T>{

    String msjError = "La búsqueda no arrojó resultados con id: ";

    T checkId(Long c) throws ResourceNotFoundException;

}

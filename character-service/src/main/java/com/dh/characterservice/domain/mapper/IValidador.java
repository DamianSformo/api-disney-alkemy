package com.dh.characterservice.domain.mapper;

import com.dh.characterservice.Exceptions.ResourceNotFoundException;

public interface IValidador<T>{

    String msjError = "La búsqueda no arrojó resultados con id: ";

    T checkId(Long c) throws ResourceNotFoundException;

}

package com.dh.userservice.domain.mapper;

import com.dh.userservice.Exceptions.ResourceNotFoundException;

public interface IValidador<T>{

    String msjError = "La búsqueda no arrojó resultados con id: ";

    T checkId(Long c) throws ResourceNotFoundException;


}

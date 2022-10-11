package com.dh.characterservice.shared;

import com.dh.characterservice.Exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface GenericServiceAPI<T, ID extends Serializable> {

    T save(T entity);

    void delete(ID id) throws ResourceNotFoundException;

    JpaRepository<T, ID> getRepository();
}

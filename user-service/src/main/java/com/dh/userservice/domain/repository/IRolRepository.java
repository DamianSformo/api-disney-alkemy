package com.dh.userservice.domain.repository;

import com.dh.userservice.domain.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository  extends JpaRepository<Rol,Long> {

}

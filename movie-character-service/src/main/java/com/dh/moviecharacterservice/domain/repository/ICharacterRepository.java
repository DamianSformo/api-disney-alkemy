package com.dh.moviecharacterservice.domain.repository;

import com.dh.moviecharacterservice.domain.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICharacterRepository extends JpaRepository<Character, Long> {

}

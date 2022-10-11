package com.dh.characterservice.domain.repository;

import com.dh.characterservice.domain.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ICharacterRepository extends JpaRepository<Character, Long> {

    @Query("SELECT c FROM Character c WHERE c.name = ?1")
    Optional<Character> getByName(String name);

    @Query("SELECT c FROM Character c WHERE c.age = ?1")
    List<Character> getByAge(Integer age);

    @Query("SELECT c FROM Character c WHERE c.weight BETWEEN ?1 AND ?2")
    List<Character> getByWeight(Double weightMin, Double weightMax);

    @Query("SELECT c FROM Character c WHERE c.age = ?1 AND c.weight BETWEEN ?2 AND ?3")
    List<Character> filter(Integer age, Double weightMin, Double weightMax);
}

package com.dh.movieservice.domain.repository;

import com.dh.movieservice.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, Long> {
}

package com.dh.movieservice.domain.model;

import java.util.List;

public record Character(Long id, String image, String name, Integer age, Double weight, String history, List<Movie> movies) {}


package com.dh.characterservice.domain.model;

import java.util.Date;
import java.util.List;

public record Movie (Long id, String image, String title, Integer rating, Date dateCreated, List<Character> characters) {}


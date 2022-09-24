package com.amefastforward.cardapi.repository;

import com.amefastforward.cardapi.model.CardOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardOriginRepository extends JpaRepository<CardOrigin, Long> {
}

package com.amefastforward.cardapi.service;

import com.amefastforward.cardapi.controller.request.CreateCardRequest;
import com.amefastforward.cardapi.controller.request.UpdateCardRequest;
import com.amefastforward.cardapi.exception.EntityNotFoundException;
import com.amefastforward.cardapi.model.Card;
import com.amefastforward.cardapi.repository.CardOriginRepository;
import com.amefastforward.cardapi.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    private final CardOriginRepository cardOriginRepository;

    @Autowired
    public CardService(CardRepository cardRepository, CardOriginRepository cardOriginRepository) {
        this.cardRepository = cardRepository;
        this.cardOriginRepository = cardOriginRepository;
    }

    public Card findById(long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card origin de id [" + id +"] não encontrado."));
    }

    public Card createCard(CreateCardRequest cardRequest) {

        var origin = cardOriginRepository.findById(cardRequest.getOriginId())
                .orElseThrow(() -> new EntityNotFoundException("Card origin de id [" + cardRequest.getOriginId() +"] não encontrado."));

        var card = new Card();
        card.setName(cardRequest.getName());
        card.setDescription(cardRequest.getDescription());
        card.setStrength(cardRequest.getStrength());
        card.setSpeed(cardRequest.getSpeed());
        card.setSkill(cardRequest.getSkill());
        card.setGear(cardRequest.getGear());
        card.setIntellect(cardRequest.getIntellect());
        card.setImageUrl(cardRequest.getImageUrl());
        card.setOrigin(origin);
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());

        return cardRepository.save(card);
    }

    public void deleteCard(long id) {
        var card = findById(id);
        cardRepository.delete(card);
        //cardRepository.deleteById(id);
    }

    public Card update(long id, UpdateCardRequest updateCardRequest) {
        var card = findById(id);
        card.setName(updateCardRequest.getName());
        card.setDescription(
                updateCardRequest.getDescription().isBlank() ? card.getDescription() : updateCardRequest.getDescription()
        );
        card.setStrength(updateCardRequest.getStrength());
        card.setSpeed(updateCardRequest.getSpeed());
        card.setSkill(updateCardRequest.getSkill());
        card.setGear(updateCardRequest.getGear());
        card.setIntellect(updateCardRequest.getIntellect());
        card.setImageUrl(updateCardRequest.getImageUrl());

        if (updateCardRequest.getOriginId() != 0L && updateCardRequest.getOriginId() != card.getOrigin().getId()) {
            var origin = cardOriginRepository.findById(updateCardRequest.getOriginId())
                    .orElseThrow(() -> new EntityNotFoundException("Card origin de id [" + updateCardRequest.getOriginId() +"] não encontrado."));;
            card.setOrigin(origin);
        }

        return cardRepository.save(card);
    }
}

package com.amefastforward.cardapi.service;


import com.amefastforward.cardapi.controller.request.CreateCardOriginRequest;
import com.amefastforward.cardapi.controller.request.UpdateCardOriginRequest;
import com.amefastforward.cardapi.exception.EntityNotFoundException;
import com.amefastforward.cardapi.model.Card;
import com.amefastforward.cardapi.model.CardOrigin;
import com.amefastforward.cardapi.repository.CardOriginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CardOriginService {
    private final CardOriginRepository cardOriginRepository;

    @Autowired
    public CardOriginService(CardOriginRepository cardOriginRepository) {
        this.cardOriginRepository = cardOriginRepository;
    }

    public CardOrigin findById(long id) {
        return this.cardOriginRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card origin id [" + id + "] not found."));
    }

    public CardOrigin createCardOrigin(CreateCardOriginRequest cardOriginRequest) {
        var cardOrigin = new CardOrigin();

        cardOrigin.setName(cardOriginRequest.getName());
        cardOrigin.setDescription(cardOriginRequest.getDescription());
        cardOrigin.setCreator(cardOriginRequest.getCreator());

        cardOrigin.setCreatedAt(LocalDateTime.now());
        cardOrigin.setUpdatedAt(LocalDateTime.now());

        return cardOriginRepository.save(cardOrigin);
    }

    public void deleteCard(long id) {
        var card = findById(id);
        cardOriginRepository.delete(card);
        //cardRepository.deleteById(id);
    }

    public CardOrigin update(long id, UpdateCardOriginRequest updateCardOriginRequest) {
        var cardOrigin = findById(id);

        cardOrigin.setName(updateCardOriginRequest.getName());
        cardOrigin.setDescription(updateCardOriginRequest.getDescription());
        cardOrigin.setCreator(updateCardOriginRequest.getCreator());

        return cardOriginRepository.save(cardOrigin);
    }
}

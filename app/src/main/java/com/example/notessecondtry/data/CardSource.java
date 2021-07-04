package com.example.notessecondtry.data;

public interface CardSource {
    CardSource init(CardsSourceResponse cardsSourceResponse);
    Notes getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int position, Notes cardData);
    void addCardData(Notes cardData);
    void clearCardData();
}

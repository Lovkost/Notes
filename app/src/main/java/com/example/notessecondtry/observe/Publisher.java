package com.example.notessecondtry.observe;

import com.example.notessecondtry.data.Notes;

import java.util.ArrayList;
import java.util.List;


public class Publisher {
    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifySingle(Notes cardData) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData);
            unsubscribe(observer);
        }
    }

}

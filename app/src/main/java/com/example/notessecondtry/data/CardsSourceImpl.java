package com.example.notessecondtry.data;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.notessecondtry.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardsSourceImpl implements CardSource {
    private final List<Notes> dataSource;
    private final Resources resources;

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardSource init(CardsSourceResponse cardsSourceResponse){
        // строки заголовков из ресурсов
        String[] titles = resources.getStringArray(R.array.noteTitles);
        // строки описаний из ресурсов
        String[] descriptions = resources.getStringArray(R.array.description_title);
        // изображения
        int[] pictures = getImageArray();
        // заполнение источника данных
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new Notes(titles[i], descriptions[i], pictures[i], Calendar.getInstance().getTime()));
        }
        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }
        return this;
    }

    private int[] getImageArray(){
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);
        int length = pictures.length();
        int[] answer = new int[length];
        for(int i = 0; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    public Notes getCardData(int position) {
        return dataSource.get(position);
    }

    public int size(){
        return dataSource.size();
    }
    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, Notes cardData) {
        dataSource.set(position, cardData);
    }

    @Override
    public void addCardData(Notes cardData) {
        dataSource.add(cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }
}

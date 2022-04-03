package com.example.notessecondtry.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsSourceFirebaseImpl implements CardSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    // Загружаемый список карточек
    private List<Notes> cardsData = new ArrayList<Notes>();

    @Override
    public CardSource init(final CardsSourceResponse cardsSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Дата»
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // При удачном считывании данных загрузим список карточек
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<Notes>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Notes cardData = CardDataMapping.toCardData(id, doc);
                                cardsData.add(cardData);
                            }
                            Log.d(TAG, "success " + cardsData.size() + " qnt");
                            cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public Notes getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null){
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void updateCardData(int position, Notes cardData) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void addCardData(final Notes cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                cardData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearCardData() {
        for (Notes cardData: cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<Notes>();
    }
}


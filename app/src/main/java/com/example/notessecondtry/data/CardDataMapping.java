package com.example.notessecondtry.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {

    public static class Fields{
        public final static String PICTURE = "picture";
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String LIKE = "like";
    }

    public static Notes toCardData(String id, Map<String, Object> doc) {
        long indexPic = (long) doc.get(Fields.PICTURE);
        Timestamp timeStamp = (Timestamp)doc.get(Fields.DATE);
        Notes answer = new Notes((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                PictureIndexConverter.getPictureByIndex((int) indexPic),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Notes cardData){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.PICTURE, PictureIndexConverter.getIndexByPicture(cardData.getPicture()));
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }
}

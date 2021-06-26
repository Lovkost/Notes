package com.example.notessecondtry.data;
//Обработка нажатий
public class Notes {
    private String title;       // заголовок
    private String description; // описание
    private int picture;        // изображение

    public Notes(String title, String description, int picture){
        this.title = title;
        this.description=description;
        this.picture=picture;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }
}

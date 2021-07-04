package com.example.notessecondtry.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.notessecondtry.MainActivity;
import com.example.notessecondtry.R;
import com.example.notessecondtry.data.Notes;
import com.example.notessecondtry.data.PictureIndexConverter;
import com.example.notessecondtry.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class CardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";

    private Notes cardData;      // Данные по карточке
    private Publisher publisher;    // Паблишер, с его помощью обмениваемся данными

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    // Для редактирования данных
    public static CardFragment newInstance(Notes cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
        // если cardData пустая, то это добавление
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views
    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(cardData);
    }

    private Notes collectCardData(){
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();
        if (cardData != null){
            Notes answer;
            answer = new Notes(title, description, cardData.getPicture(), date);
            answer.setId(cardData.getId());
            return answer;
        } else {
            int picture = PictureIndexConverter.getPictureByIndex(PictureIndexConverter.randomPictureIndex());
            return new Notes(title, description, picture,  date);
        }
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView(){
        title.setText(cardData.getTitle());
        description.setText(cardData.getDescription());
        initDatePicker(cardData.getDate());
    }

    // Установка даты в DatePicker
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}
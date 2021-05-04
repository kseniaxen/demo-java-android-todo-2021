package org.tyaa.demo.java.android.mytodolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* класс активити добавления новой задачи */
public class FormActivity extends AppCompatActivity {

    private TodoItem currentTodoItem = null;
    private String selectedDateString =
            new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    // вызывается автоматически каждый раз, когда создается экземпляр активити
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // обязательный вызов родительского конструктора
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        // установка представления всего активити
        setContentView(R.layout.activity_form);
        // находим кнопку добавления новой задачи
        Button addButton = findViewById(R.id.addButton);
        // находим поле ввода заголовка новой задачи
        EditText titleEditText = findViewById(R.id.newItemTitleEditText);
        EditText descriptionEditText = findViewById(R.id.newItemDescriptionEditText);
        //CalendarView calendarView = findViewById(R.id.calendarView);
        DatePicker datePickerView = findViewById(R.id.datePickerView);

        TextView newItemName = findViewById(R.id.newItemName);

        /*
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // номер первого месяца - 0,
            // поэтому номер любого месяца увеличиваем на 1
            month++;
            String monthZero = month < 10 ? "0" : "";
            String dayZero = dayOfMonth < 10 ? "0" : "";
            final String formattedSelectedDate =
                    String.format("%s%s.%s%s.%s", dayZero, dayOfMonth, monthZero, month, year);
            Log.d("MyTag: OnDateChangeListener", formattedSelectedDate);
            selectedDateString = formattedSelectedDate;
        });
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerView.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                String monthZero = monthOfYear < 10 ? "0" : "";
                String dayZero = dayOfMonth < 10 ? "0" : "";
                final String formattedSelectedDate =
                        String.format("%s%s.%s%s.%s", dayZero, dayOfMonth, monthZero, monthOfYear, year);
                Log.d("MyTag: OnDateChangeListener", formattedSelectedDate);
                selectedDateString = formattedSelectedDate;
            }
        });
        // если интент, открывший данную активность, содержит
        // расширение - ИД модели для редактирования
        if (intent.hasExtra("editedItemId")) {
            Long editedItemId = intent.getLongExtra("editedItemId", 0L);
            newItemName.setText("Edit Item");
            addButton.setText("Edit");
            if (!editedItemId.equals(0L)) {
                // найти по ИД модель из списка
                for (TodoItem item : Global.items) {
                    if (item.getId() == editedItemId) {
                        currentTodoItem = item;
                        // заполнить виджеты ввода данных из текущей модели
                        titleEditText.setText(currentTodoItem.getTitle());
                        descriptionEditText.setText(currentTodoItem.getDescription());
                        /*
                        try {
                            calendarView.setDate(
                                    new SimpleDateFormat("dd.MM.yyyy")
                                            .parse(currentTodoItem.getDate())
                                            .getTime()
                            );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                         */
                        Date date;
                        try {
                            date =  new SimpleDateFormat("dd.MM.yyyy")
                                    .parse(currentTodoItem.getDate());
                            calendar.setTime(date);
                            datePickerView.updateDate(
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                                    );
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            newItemName.setText("Add A New Item");
            addButton.setText("Add");
        }
        // установка обработчика события клик по кнопке добавления задачи
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentTodoItem == null) {
                    // Global.items - обращение к статическому полю
                    // списка моделей задач
                    // add - вызов метода добавления нового объекта в список
                    // new TodoItem - вызов конструктора класса модели задачи
                    // titleEditText.getText().toString() - из виджета titleEditText
                    // считываем заголовок задачи, который ввел пользователь
                    // и передаем как аргумент конструктора модели задачи
                    //Log.d("MyTag 0: date", new SimpleDateFormat("dd.MM.yyyy")
                    //        .format(new Date(calendarView.getDate())));
                    Global.items.add(
                            new TodoItem(
                                    titleEditText.getText().toString(),
                                    descriptionEditText.getText().toString(),
                                    selectedDateString
                            )
                    );
                } else {
                    currentTodoItem.setTitle(titleEditText.getText().toString());
                    currentTodoItem.setDescription(descriptionEditText.getText().toString());
                    currentTodoItem.setDate(selectedDateString);
                }
                // установит код положительного завершения активити
                setResult(RESULT_OK);
                // самоуничтожение активити
                finish();
            }
        });
    }
}
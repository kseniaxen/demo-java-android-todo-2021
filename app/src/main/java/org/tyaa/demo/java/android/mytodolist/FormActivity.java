package org.tyaa.demo.java.android.mytodolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/* класс активити добавления новой задачи */
public class FormActivity extends AppCompatActivity {

    // вызывается автоматически каждый раз, когда создается экземпляр активити
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // обязательный вызов родительского конструктора
        super.onCreate(savedInstanceState);
        // установка представления всего активити
        setContentView(R.layout.activity_form);
        // находим кнопку добавления новой задачи
        Button addButton = findViewById(R.id.addButton);
        // находим поле ввода заголовка новой задачи
        EditText titleEditText = findViewById(R.id.newItemTitleEditText);
        EditText descriptionEditText = findViewById(R.id.newItemDescriptionEditText);
        // установка обработчика события клик по кнопке добавления задачи
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Global.items - обращение к статическому полю
                // списка моделей задач
                // add - вызов метода добавления нового объекта в список
                // new TodoItem - вызов конструктора класса модели задачи
                // titleEditText.getText().toString() - из виджета titleEditText
                // считываем заголовок задачи, который ввел пользователь
                // и передаем как аргумент конструктора модели задачи
                Global.items.add(
                    new TodoItem(
                            titleEditText.getText().toString(),
                            descriptionEditText.getText().toString()
                    )
                );
                // установит код положительного завершения активити
                setResult(RESULT_OK);
                // самоуничтожение активити
                finish();
            }
        });
    }
}
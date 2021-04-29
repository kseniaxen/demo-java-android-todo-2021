package org.tyaa.demo.java.android.mytodolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

// import sun.java2d.d3d.D3DGraphicsDevice;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // константа: код запроса на переход к активити с формой
    // добавления новой задачи
    public static final int FORM_ACTIVITY_REQUEST_CODE = 0;

    private TodoListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // представление списка
        ListView todoItemsListView = findViewById(R.id.todoItemsListView);
        // заполнение списка моделей данных двумя демонстрационными объектами
        Global.items.add(new TodoItem("Task 1", "Nunc dapibus vestibulum odio, vitae pellentesque ipsum lobortis varius"));
        Global.items.add(new TodoItem("Task 2", "Suspendisse sit amet est ut libero pulvinar cursus auctor at justo. In aliquet arcu imperdiet, cursus augue ut, lobortis tellus."));
        // пользовательский соединитель представления со списком данных
        adapter =
                new TodoListAdapter(this, R.layout.todo_list_item, Global.items);
        // подключение списка моделей данных к представлению списка
        todoItemsListView.setAdapter(adapter);
        // найти главную кнопку действия (переход на экран добавления нового пункта списка)
        FloatingActionButton fab = findViewById(R.id.addTodoItemFab);
        // создание объекта окна сообщения с текстом - заголовок первой задачи
        // из списка моделей задач - методом makeText,
        // и отображение методом show
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // создание объекта намерения перейти от активити MainActivity на активити FormActivity
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                // выполнение намерения прехода от активити MainActivity на активити FormActivity
                MainActivity.this.startActivityForResult(intent, FORM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    // переопределение метода, который страбатывает после возврата с любой другой активити,
    // на которую пользователь был направлен методом startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // проверяем, что возврат произошел с активити с формой
        // добавления новой задачи, то есть, с той активити, переходя на которую,
        // мы сообщили код перехода FORM_ACTIVITY_REQUEST_CODE
        if (resultCode == RESULT_OK && requestCode == FORM_ACTIVITY_REQUEST_CODE) {
            // команда адаптеру заново прочесть список моделей задач,
            // в который пользователь добавил еще одну задачу
            // и обновить вид виджета списка задач
            adapter.notifyDataSetChanged();
        }
    }
}
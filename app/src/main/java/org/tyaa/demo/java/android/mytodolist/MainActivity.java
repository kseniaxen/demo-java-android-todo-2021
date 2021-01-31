package org.tyaa.demo.java.android.mytodolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TodoListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // представление списка
        ListView todoItemsListView = findViewById(R.id.todoItemsListView);
        // заполнение списка моделей данных двумя демонстрационными объектами
        Global.items.add(new TodoItem(1, "Task 1"));
        Global.items.add(new TodoItem(2, "Task 2"));
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
                MainActivity.this.startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            adapter.notifyDataSetChanged();
        }
    }
}
package org.tyaa.demo.java.android.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // представление списка
        ListView todoItemsListView = findViewById(R.id.todoItemsListView);
        // список моделей данных
        List<TodoItem> items = new ArrayList<>();
        items.add(new TodoItem(1, "Task 1"));
        items.add(new TodoItem(2, "Task 2"));
        // соединитель представления со списком данных
        /* ArrayAdapter<TodoItem> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items); */
        // подключение списка моделей данных к представлению списка
        // todoItemsListView.setAdapter(adapter);
        // пользовательский соединитель представления со списком данных
        TodoListAdapter adapter =
                new TodoListAdapter(this, R.layout.todo_list_item, items);
        // подключение списка моделей данных к представлению списка
        todoItemsListView.setAdapter(adapter);
        // найти главную кнопку действия (переход на экран добавления нового пункта списка)
        FloatingActionButton fab = findViewById(R.id.addTodoItemFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_LONG).show();
            }
        });
    }
}
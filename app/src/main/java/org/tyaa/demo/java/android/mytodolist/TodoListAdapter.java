package org.tyaa.demo.java.android.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

// получаем по наследству от класса ArrayAdapter
// готовые возможности адаптера
public class TodoListAdapter extends ArrayAdapter<TodoItem> {

    private LayoutInflater inflater;
    private int itemLayout;
    private List<TodoItem> items;

    // конструктор, который принимает ссылку на активити, дескриптор представления,
    // список моделей данных
    public TodoListAdapter(@NonNull Context context, int resource, @NonNull List<TodoItem> objects) {
        // вызов родительской версии конструктора
        super(context, resource, objects);
        // инициализация поля инфлейтера объектом инфлейтера, настроенным
        // на работу с определенной активити при помощи аргумента context
        this.inflater = LayoutInflater.from(context);
        // инициализация поля дескриптора представления
        // дескриптором макета пункта списка
        this.itemLayout = resource;
        // инициализация поля списка моделей данных
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            view = inflater.inflate(this.itemLayout, parent, false);
        } else {
            view = convertView;
        }
        // ссылки на виджеты макета пункта списка задач
        // TextView idView = view.findViewById(R.id.todoListItemIdTextView);
        TextView titleView = view.findViewById(R.id.todoListItemTitleTextView);
        TextView descriptionView = view.findViewById(R.id.todoListItemDescriptionTextView);
        // по порядковому номеру (индексу, начиная с 0)
        // находим очередной объект модели из списка моделей задач
        TodoItem item = items.get(position);
        // инициализируем атрибут "текст" виджета идентификатора задачи
        // значением из поля ИД текущего объекта модели
        // idView.setText(String.valueOf(item.getId()));
        // инициализируем атрибут "текст" виджета заголовка задачи
        // значением из поля Заголовок текущего объекта модели
        titleView.setText(item.getTitle());
        descriptionView.setText(item.getDescription());
        // возврат экземпляра макета пункта списка задач,
        // заполненными данными - об иденитификаторе и заголовке задачи
        return view;
    }
}

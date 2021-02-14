package org.tyaa.demo.java.android.mytodolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private Context context;

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
        // инициализация поля графического контекста
        this.context = context;
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
        CheckBox doneView = view.findViewById(R.id.todoListItemDoneCheckBox);
        Button editItemButton = view.findViewById(R.id.todoListItemEditButton);
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
        // обработчик изменения выбора чекбокса "done"
        doneView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                item.setDone(b);
            }
        });
        // обработчик клика по кнопке edit на одном пункте списка задач
        editItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // создание объекта намерения перейти от активити MainActivity на активити FormActivity
                // - для редактирования задачи
                Intent intent = new Intent(context, FormActivity.class);
                // добавляем в объект интента расширение:
                // идентификатор пункта списка, данные которого нужно редактировать
                intent.putExtra("editedItemId", item.getId());
                // выполнение намерения прехода от активити MainActivity на активити FormActivity
                ((MainActivity)context).startActivityForResult(intent, MainActivity.FORM_ACTIVITY_REQUEST_CODE);
            }
        });
        // возврат экземпляра макета пункта списка задач,
        // заполненными данными - об иденитификаторе и заголовке задачи
        return view;
    }
}

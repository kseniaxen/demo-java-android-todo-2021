package org.tyaa.demo.java.android.mytodolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
        TextView dateView = view.findViewById(R.id.todoListItemDateTextView);
        CheckBox doneView = view.findViewById(R.id.todoListItemDoneCheckBox);
        Button showDetailsItemButton = view.findViewById(R.id.todoListItemDetailsButton);
        Button editItemButton = view.findViewById(R.id.todoListItemEditButton);
        Button deleteItemButton = view.findViewById(R.id.todoListItemDeleteButton);
        // по порядковому номеру (индексу, начиная с 0)
        // находим очередной объект модели из списка моделей задач
        TodoItem item = items.get(position);
        // в заисимости от того, выполнена ли задача,
        // устанавливаем светло-зеленый фон всему виджету формируемого пункта списка
        if (item.isDone()) {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
        } else {
            view.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        // инициализируем атрибут "текст" виджета идентификатора задачи
        // значением из поля ИД текущего объекта модели
        // idView.setText(String.valueOf(item.getId()));
        // инициализируем атрибут "текст" виджета заголовка задачи
        // значением из поля Заголовок текущего объекта модели
        titleView.setText(item.getTitle());
        // в экземпляр виджета Description выводим текст из поля description текущей модели item
        descriptionView.setText(
            // проверка: больше ли длина строки description, чем 50 символов
            (item.getDescription().length() > 75)
                // если да - подставляем вместо всей строки первые 50 символов (от 0 до 49)
                ? item.getDescription().substring(0, 74) + "..."
                // если нет - подставляем строку целиком
                : item.getDescription()
            );
        dateView.setText(item.getDate());
        // обработчик изменения выбора чекбокса "done"
        doneView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // установка значения поля "выполнена ли задача" в текущую модель -
                // в зависимости от того, выбрал пользователь чекбокс (true)
                // или снял выделение (false)
                item.setDone(b);
                TodoListAdapter.this.notifyDataSetChanged();
            }
        });
        // обработчик клика по кнопке details на одном пункте списка задач
        showDetailsItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // подготовка объекта окна с кнопкой "Ок"
                // для показа загловка и полного описания выбранной задачи
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(item.getTitle() + " (" + (item.isDone() ? "Done" : "Active") + ")")
                    .setMessage(item.getDescription())
                    .setPositiveButton("Ok", null)
                    .show();
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
                Log.d("MyTag", item.getId().toString());
                // выполнение намерения прехода от активити MainActivity на активити FormActivity
                ((MainActivity)context).startActivityForResult(intent, MainActivity.FORM_ACTIVITY_REQUEST_CODE);
            }
        });
        // обработчик клика по кнопке delete на одном пункте списка задач
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // подготовка обработчика события "клик по кнопке Согласиться или Отменить"
                // для установки в будущее окно подстверждения действия пользователя
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // когда пользователь подтвердил действие,
                                // вызываем на списке моделей метод удаления,
                                // передавая ему аргумент - ссылку на текущий объект модели item
                                Global.items.remove(item);
                                // издание события,
                                // которое приводит к перерисовке списка адаптером
                                TodoListAdapter.this.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // когда пользователь отменил действие
                                // - не делаем ничего
                                break;
                        }
                    }
                };
                // подготовка объекта окна с кнопками выбора "Delete" или "Cancel"
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?")
                    .setPositiveButton("Delete", dialogClickListener)
                    .setNegativeButton("Cancel", dialogClickListener)
                    .show();
            }
        });
        // возврат экземпляра макета пункта списка задач,
        // заполненными данными - об иденитификаторе и заголовке задачи
        return view;
    }
}

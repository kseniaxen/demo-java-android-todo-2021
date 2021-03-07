package org.tyaa.demo.java.android.mytodolist;

import com.orm.SugarRecord;

public class TodoItem extends SugarRecord {

    // private static int lastId = 0;
    // последний назначенный идентификатор
    private static Long lastId;

    // статический конструктор
    // выполняется при загрузке приложения -
    // при загрузке класса в память
    static {
        lastId = 0L;
    }

    // идентификатор задачи (целое число)
    private Long id;
    // загловок задачи (строка)
    private String title;
    // описание задачи (строка)
    private String description;
    // завершена ли задача
    private Boolean done;

    // конструктор объекта модели задачи
    public TodoItem(String title, String description) {
        // сохранение (копирование) значений аргументов в поля объекта модели задачи
        this.id = ++TodoItem.lastId;
        this.title = title;
        this.description = description;
        this.done = false;
    }

    // конструктор объекта модели задачи
    public TodoItem(Long id, String title, String description, Boolean done) {
        // сохранение (копирование) значений аргументов в поля объекта модели задачи
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

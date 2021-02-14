package org.tyaa.demo.java.android.mytodolist;

public class TodoItem {

    // private static int lastId = 0;
    // последний назначенный идентификатор
    private static int lastId;

    // статический конструктор
    // выполняется при загрузке приложения -
    // при загрузке класса в память
    static {
        lastId = 0;
    }

    // идентификатор задачи (целое число)
    private int id;
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
    public TodoItem(int id, String title, String description, Boolean done) {
        // сохранение (копирование) значений аргументов в поля объекта модели задачи
        this.id = id;
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

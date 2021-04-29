package org.tyaa.demo.java.android.mytodolist;

import java.util.List;

public interface ITodoDao {
    List<TodoItem> findAll ();
    TodoItem findById (Long id);
    void save (TodoItem item);
    void delete (Long id);
}

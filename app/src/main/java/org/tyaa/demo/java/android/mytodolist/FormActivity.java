package org.tyaa.demo.java.android.mytodolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Button addButton = findViewById(R.id.addButton);
        EditText titleEditText = findViewById(R.id.newItemTitleTextView);
        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Global.items.add(new TodoItem(0, titleEditText.getText().toString()));
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
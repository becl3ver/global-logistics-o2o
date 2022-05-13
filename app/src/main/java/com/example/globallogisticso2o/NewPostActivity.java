package com.example.globallogisticso2o;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.globallogisticso2o.domain.PostData;
import com.google.gson.Gson;

public class NewPostActivity extends AppCompatActivity {
    private static final String TAG = NewPostActivity.class.getSimpleName();

    private Button buttonSubmit, buttonAttach;
    private EditText editTextTitle, editTextContent;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //TODO : 액션바 추가하기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonAttach = (Button) findViewById(R.id.buttonAttach);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextContent = (EditText) findViewById(R.id.editTextContent);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                PostData postData = new PostData();
                postData.setPostTitle(title);
                postData.setPostContent(content);

                Gson gson = new Gson();
                String str = gson.toJson(postData);
                //TODO : 서버 통신
            }
        });

        buttonAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("작성하시던 글이 있습니다. 임시저장 하시겠습니까?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("title", editTextTitle.getText().toString());
                intent.putExtra("content", editTextContent.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        builder.show();
    }
}
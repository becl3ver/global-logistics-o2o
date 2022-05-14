package com.example.globallogisticso2o;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.globallogisticso2o.domain.PostData;
import com.example.globallogisticso2o.domain.PostRequest;

import java.util.ArrayList;

/**
 * Intent를 통해 전달받은 번호의 게시판과 글 출력
 * 백엔드에서 JSON으로 받아온 글들을 클래스로 파싱해 리사이클러뷰로 출력
 * @author 최재훈
 * @version 1.0, 스레드 부분 미구현
 */
public class PostListActivity extends AppCompatActivity { //TODO : 새 글 작성하는 플로팅 버튼 만들기
    // 디버깅 시 로그 출력에 사용되는 태그
    private static final String TAG = PostListActivity.class.getSimpleName();

    // API 사용 주소
    private static final String API_URL = "";
    private static final String[] CATEGORIES = {""};

    // 임시 글과, 북마크된 글 번호들을 저장하기 위한 SQLite 데이터베이스 이름
    private static final String DATABASE_NAME = "post_info";

    // startActivityForResult, onActivityResult 메서드에서 식별을 위해 사용
    private static final int REQUEST_NEW_POST = 101;
    private static final int RESULT_SAVED = 1;

    // 무한 스크롤을 위한 리사이클러 뷰, 데이터를 받아와서 저장하기 위한 리스트
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<PostData> postDatas;
    private boolean isLoading = false;

    private Intent postListIntent;
    private int category;
    private SQLiteDatabase database;
    private ActionBar bar;


    // 검색할 정보를 담아서 보내기 위한 객체
    private PostRequest postRequest;

    /**
     * 인텐트에서 현재 게시판의 카테고리 추출
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        bar = getSupportActionBar();
        bar.setLogo(R.drawable.ic_baseline_directions_boat_24);
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);

        postListIntent = getIntent();
        category = postListIntent.getIntExtra("category", -1);

        if(category == -1 || category >= CATEGORIES.length) {
            Toast.makeText(this, "Loading failed", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Loading failed");
            finish();
        }

        setRecyclerView();
    }

    /**
     * 액션바 아이템 선택시 동작 처리
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_refresh:
                Toast.makeText(this, "새로 고침 메뉴 선택", Toast.LENGTH_SHORT).show();
                // TODO : 리사이클러뷰 갱신
                break;
            case R.id.menu_search:
                Toast.makeText(this, "검색 메뉴 선택", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
                break;
        }

        return true;
    }

    /**
     * NewPostActivity를 호출했을 때, 받은 결과 처리
     * 임시 글 저장이 필요한 경우 temporary_post 테이블에 저장
     * @param requestCode REQUEST_NEW_POST 단일
     * @param resultCode RESULT_OK : 리사이클러뷰 갱신, RESULT_SAVED : 데이터베이스에 임시글 저장
     * @param data 임시글 정보(제목, 내용) Intent에 저장
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_NEW_POST) { // 새 글 작성
            if(resultCode == RESULT_OK) {
                setRecyclerView();
            }
            else if(resultCode == RESULT_SAVED) {
                setTemporaryTable();
                addTemporaryTable(data.getStringExtra("title"), data.getStringExtra("content"));
            }
        }
    }

    /**
     * 리사이클러뷰 설정
     * category 0 : uid를 통해 글을 검색해서 불러 옴
     * category 1 : 데이터베이스에서 북마크한 글 ID를 읽고 해당 ID의 글 백엔드에서 읽어 옴
     * category 2 : 데이터베이스에 저장된 임시 글 읽어 옴
     * category 3 ~ : 해당 분류의 글들 읽어 옴
     */
    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        switch(category) {
            case 0:
                int userId = 0; // 실제 유저 아이디로
                postRequest = new PostRequest();
                postRequest.setId(userId);
                // TODO : 본인이 작성한 글 로딩
                break;
            case 1:
                setBookmarkTable();
                postRequest = new PostRequest();
                postRequest.setPostIds(getBookmarkTable());
                // TODO : postRequest로 글 읽어 옴
                break;
            case 2:
                setTemporaryTable();
                postDatas = getTemporaryTable();
                break;
            default:
                postRequest = new PostRequest();
                postRequest.setCategory(category);
        }

        adapter = new RecyclerViewAdapter(postDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(!isLoading) {
                    if(layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == postDatas.size() - 1) {
                        loadPosts();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadPosts() {
        //TODO : 스레드로 글 로드할 수 있도록 처리
    }

    /**
     * 데이터베이스 생성 또는 사용
     */
    private void setDatabase() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    /**
     * temporary_post 테이블 생성
     */
    private void setTemporaryTable() {
        if(database == null) {
            setDatabase();
        }

        database.execSQL("create table if not exists temporary_post" + "("
                + " _id integer primary key autoincrement, "
                + " category integer, "
                + " title text, "
                + " content text)");
    }

    /**
     * temporary_post 테이블에 튜플 삽입
     * @param title 글 제목
     * @param content 글 내용
     */
    private void addTemporaryTable(String title, String content) {
        if(database == null) {
            setDatabase();
        }

        String sql = "insert into temporary_post(category, title, content) values " + "("
                + Integer.toString(category) + ", '"
                +  title + "', '"
                + content + "')";
        database.execSQL(sql);
    }

    /**
     * temporary_table의 모든 튜플 읽어와서 리스트로 반환
     * @return
     */
    private ArrayList<PostData> getTemporaryTable() {
        if(database == null) {
            setDatabase();
        }

        ArrayList<PostData> list = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from temporary_post", null);
        int recordCount = cursor.getCount();

        for(int i = 0; i < recordCount; i++) {
            cursor.moveToNext();

            PostData postData = new PostData();
            postData.setCategory(cursor.getInt(1));
            postData.setPostTitle(cursor.getString(2));
            postData.setPostContent(cursor.getString(3));

            list.add(postData);
        }

        cursor.close();
        return list;
    }

    /**
     * bookmark 테이블 생성
     */
    private void setBookmarkTable() {
        if(database == null) {
            setDatabase();
        }

        database.execSQL("create table if not exists bookmark" + "(" +
                "_id integer primary key)");
    }

    /**
     * 북마크 테이블의 모든 튜플 읽어와서 리스트로 반환
     * @return 북마크된 글 아이디 리스트
     */
    private ArrayList<Integer> getBookmarkTable() {
        if(database == null) {
            setDatabase();
        }

        ArrayList<Integer> list = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from bookmark", null);
        int recordCount = cursor.getCount();

        for(int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            list.add(cursor.getInt(0));
        }

        return list;
    }

    /**
     * 북마크 테이블에 튜블 삽입
     * @param postId
     */
    private void addBookmarkTable(int postId) {
        if(database == null) {
            setDatabase();
        }

        String sql = "insert into bookmark(_id) values(" + Integer.toString(postId) + ")";
        database.execSQL(sql);
    }
}
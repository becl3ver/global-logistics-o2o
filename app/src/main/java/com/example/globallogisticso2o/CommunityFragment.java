package com.example.globallogisticso2o;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 유저가 작성한 글, 북마크, 임시 저장 글 리스트 등에 접근
 * 기타 게시판 접근
 * @author 최재훈
 * @version 1.0, 버튼을 통해 글 목록을 출력하는 엑티비티 시작
 */

public class CommunityFragment extends Fragment {
    // 디버깅 시 로그 출력에 사용하는 태그
    private static final String TAG = CommunityFragment.class.getSimpleName();

    // onCreateView의 파라미터를 클래스 멤버 변수로 저장하기 위한 변수들
    private LayoutInflater inflater;
    private ViewGroup container;
    private ActionBar bar;

    private Button buttonMyPost, buttonBookmark, buttonTemporary;
    private Button buttonNotice, buttonPopular, buttonFree, buttonQnA;
    private Button[] buttons = new Button[5];

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        inflater = layoutInflater;
        container = viewGroup;

        View view = inflater.inflate(R.layout.fragment_community, container, false);

        buttonMyPost = (Button) view.findViewById(R.id.buttonMyPost);
        buttonBookmark = (Button) view.findViewById(R.id.buttonBookmark);
        buttonTemporary = (Button) view.findViewById(R.id.buttonTemporary);

        buttonNotice = (Button) view.findViewById(R.id.buttonNotice);
        buttonPopular = (Button) view.findViewById(R.id.buttonPopular);
        buttonFree = (Button) view.findViewById(R.id.buttonFree);
        buttonQnA = (Button) view.findViewById(R.id.buttonQnA);

        buttonMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(0);
            }
        });

        buttonBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(1);
            }
        });

        buttonTemporary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(2);
            }
        });

        buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(3);
            }
        });

        buttonPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(4);
            }
        });

        buttonFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(5);
            }
        });

        buttonQnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToPostList(6);
            }
        });

        // 기타 버튼들은 반복문으로 처리
        for(int i = 0; i < 5; i++) {
            final int idx = i;
            int butttonId = getResources().getIdentifier("buttonCommunity" + Integer.toString(i + 1),
                    "id", getActivity().getPackageName());

            buttons[i] = view.findViewById(butttonId);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToPostList(idx + 7);
                }
            });
        }

        return view;
    }

    /**
     * 게시글들을 보여주는 엑티비티 호출, Intent를 통해 해당되는 게시판 번호 전달
     * @param val 게시판 번호
     */
    public void moveToPostList(int val) {
        Intent intent = new Intent(getActivity().getApplicationContext(), PostListActivity.class);
        intent.putExtra("category", val);
        startActivity(intent);
    }
}
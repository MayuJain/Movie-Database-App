package com.example.homework04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListbyRatingActivity extends AppCompatActivity {

    TextView tv_titleValue;
    TextView tv_descValue;
    TextView tv_genreValue;
    TextView tv_ratingValue;
    TextView tv_imbdValue;
    TextView tv_yearValue;
    Button bt_finish;
    ImageView im_first;
    ImageView im_prev;
    ImageView im_last;
    ImageView im_next;
    int currentIndex;
    ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listby_rating);

        tv_titleValue = findViewById(R.id.tv_title_value);
        tv_descValue = findViewById(R.id.tv_desc_value);
        tv_genreValue = findViewById(R.id.tv_genre_value);
        tv_ratingValue = findViewById(R.id.tv_rating_value);
        tv_imbdValue = findViewById(R.id.tv_imdb_value);
        tv_yearValue = findViewById(R.id.tv_year_value);
        bt_finish = findViewById(R.id.bt_finish);
        im_first = findViewById(R.id.im_first);
        im_prev = findViewById(R.id.im_prev);
        im_last = findViewById(R.id.im_last);
        im_next = findViewById(R.id.im_next);

        if(getIntent() != null && getIntent().getExtras() != null){

            movieList = (ArrayList<Movie>) getIntent().getExtras().getSerializable(MainActivity.MAIN_MOVIE_LIST_RATING_KEY);
            currentIndex = 0;
            Movie m = movieList.get(currentIndex);
            tv_titleValue.setText(m.name);
            tv_descValue.setText(m.description);
            tv_genreValue.setText(m.genre);
            tv_ratingValue.setText(m.rating+"/5");
            tv_imbdValue.setText(m.imdb);
            tv_yearValue.setText(String.valueOf(m.year));

        }

        im_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = getindex("first", currentIndex, movieList.size());
                setFields(currentIndex);
            }
        });

        im_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = getindex("last", currentIndex, movieList.size());
                setFields(currentIndex);
            }
        });

        im_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = getindex("next", currentIndex, movieList.size());
                setFields(currentIndex);
            }
        });

        im_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = getindex("prev", currentIndex, movieList.size());
                setFields(currentIndex);
            }
        });

        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private int getindex(String direction,int currentIndex,int size)
    {
        if(direction=="prev" && currentIndex !=0)
            currentIndex = currentIndex-1;
        else if( direction=="prev" && currentIndex==0)
            currentIndex = size-1;
        else if( direction=="next" && currentIndex!=size-1)
            currentIndex = currentIndex+1;
        else if( direction=="next" && currentIndex==size-1)
            currentIndex = 0;
        else if( direction=="first")
            currentIndex = 0;
        else if( direction=="last")
            currentIndex = size-1;
        return currentIndex;
    }

    public void setFields(int index){
        Movie m = movieList.get(currentIndex);
        tv_titleValue.setText(m.name);
        tv_descValue.setText(m.description);
        tv_genreValue.setText(m.genre);
        tv_ratingValue.setText(m.rating+"/5");
        tv_imbdValue.setText(m.imdb);
        tv_yearValue.setText(String.valueOf(m.year));
    }
}

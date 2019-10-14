package com.example.homework04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Array;
import java.util.Arrays;

public class EditActivity extends AppCompatActivity {

    static final String EDIT_MOVIE_EDIT_KEY = "Edit_MovieEditKey";
    Button bt_editmovie;
    EditText etmoviename_edit;
    EditText etIMDB_edit;
    EditText etmoviedescription_edit;
    EditText etYear_edit;
    SeekBar ratingSeekbar_edit;
    Spinner genredropdown_edit;

    String moviename_edit;
    String IMDB_edit;
    String movieDescription_edit;
    int ratingvalue_edit;
    int year_edit;
    String genre_edit ;
    String genreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etmoviename_edit=findViewById(R.id.etmoviename_edit);
        etmoviedescription_edit=findViewById(R.id.etmoviedescription_edit);
        genredropdown_edit=findViewById(R.id.genredropdown_edit);
        ratingSeekbar_edit=findViewById(R.id.ratingseekbar_edit);
        etIMDB_edit=findViewById(R.id.etIMDB_edit);
        etYear_edit=findViewById(R.id.etYear_edit);
        bt_editmovie=findViewById(R.id.btn_editMovie);

        if(getIntent()!= null && getIntent().getExtras() != null){
            Movie m = (Movie) getIntent().getExtras().getSerializable(MainActivity.MAIN_MOVIE_EDIT_KEY);

            etmoviename_edit.setText(m.name);
            etmoviedescription_edit.setText(m.description);
            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.movie_genre, R.layout.support_simple_spinner_dropdown_item);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genredropdown_edit.setAdapter(dataAdapter);
            int indexGenre = getGenre(m.genre,getResources().getStringArray(R.array.movie_genre));
            Log.d("demo", "index of " +m.genre+": " +indexGenre);
            genredropdown_edit.setSelection(indexGenre);
            ratingSeekbar_edit.setProgress(m.rating);
            etYear_edit.setText(String.valueOf(m.year));
            etIMDB_edit.setText(m.imdb);
        }

        genredropdown_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genreValue = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bt_editmovie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean isError = false;

                movieDescription_edit = etmoviedescription_edit.getText().toString();
                moviename_edit = etmoviename_edit.getText().toString();
                IMDB_edit = etIMDB_edit.getText().toString();
                ratingvalue_edit = ratingSeekbar_edit.getProgress();

                if(etYear_edit.getText().toString().isEmpty() || Integer.parseInt(etYear_edit.getText().toString()) == 0){
                    etYear_edit.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }else {
                    year_edit = Integer.parseInt(etYear_edit.getText().toString());
                }


                if(moviename_edit.isEmpty()){
                    etmoviename_edit.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }
                if(movieDescription_edit.isEmpty()){
                    etmoviedescription_edit.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }
                if(IMDB_edit.isEmpty()){
                    etIMDB_edit.setError(getResources().getString(R.string.invalidInput));
                    isError = true;
                }

                if(!isError) {
                    Movie newmovie = new Movie(moviename_edit, movieDescription_edit, genreValue, year_edit, ratingvalue_edit, IMDB_edit);
                    Log.d("demo", "before editing " + newmovie.toString());
                    Intent intent = new Intent();
                    intent.putExtra(EDIT_MOVIE_EDIT_KEY, newmovie);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(EditActivity.this, getResources().getString(R.string.invalidInput), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public int getGenre(String value, String[] options){
        int result = 0;
        for(int i = 0; i< options.length; i++){
            Log.d("demo", options[i]+": "+ value);
            if(options[i].equals(value)){
                Log.d("demo", String.valueOf(i));
                result = i;
                break;
            }
        }
        return result;
    }
}

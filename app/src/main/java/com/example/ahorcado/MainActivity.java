package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    /**Se instancian los elementos de la pantalla y variables**/
    private Button btn_start;
    private Spinner sp_difficulties;
    private CheckBox cb_randomWord;
    private EditText ed_word;
    private TextView tv_wellcome;
    private TextView tv_difficulty;

    private String word;
    private ArrayList<String> players = new ArrayList<>();
    private boolean randomWord;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        sp_difficulties = findViewById(R.id.sp_difficulties);
        cb_randomWord = findViewById(R.id.cb_randomWord);
        ed_word = findViewById(R.id.ed_word);
        tv_wellcome = findViewById(R.id.tv_wellcome);
        tv_difficulty = findViewById(R.id.tv_difficulty);

        /**Con typeface cargo la fuente de la carpeta assets y se la pongo al tv_wellcome**/
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/RioGrande.ttf");
        tv_wellcome.setTypeface(tf);

        /**
         * Se cargan los elementos del Spinner que sera la dificultad del juego
         * segun las posiciones del spinner
         * 0 facil
         * 1 medio
         * 2 dificil
         * **/
        players.add("Facil");
        players.add("Medio");
        players.add("Dificil");
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, players);
        sp_difficulties.setAdapter(ad);
        sp_difficulties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficulty=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Se almacena el valor del checkbox en la variable randomWord
         * eligiendo el tipo de juego
         * y se bloquean o habilitan los elementos de la pantalla en funcion
         * del tipo de juego
         *************************************************************/
        cb_randomWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    ed_word.setVisibility(View.VISIBLE);
                    sp_difficulties.setVisibility(View.INVISIBLE);
                    tv_difficulty.setVisibility(View.INVISIBLE);
                    randomWord = false;
                } else {
                    ed_word.setVisibility(View.INVISIBLE);
                    sp_difficulties.setVisibility(View.VISIBLE);
                    tv_difficulty.setVisibility(View.VISIBLE);
                    randomWord = true;

                }
            }
        });

        ed_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                word = ed_word.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Al hacer click en Start, si se cumplen las condiciones,
                 * y en funcion del tipo de juego elegido
                 * se iniciara intent para enviar los datos a la mainActivity2
                 * se preparan los datos con putExtra, nombre que le pongas y variable a enviar
                 * en este caso envio el objeto game (se implementa serializable en la clase game)
                 * se incia la siguiente actividad con startActivity que hace el cambio de pantalla
                 * en caso de que el tipo de juego sea con palbra aleatoria tambien se llama a la
                 * clase readFile que lee un fichero txt con palabras y devuelve un array con ellas
                 **********************************************************************************/
                if (word!= null && !randomWord){
                    Intent intent = new Intent(MainActivity.this , MainActivity2.class);
                    Game game = new Game(word);
                    intent.putExtra("Object", game);
                    startActivity(intent);
                } else if (randomWord) {
                    Intent intent = new Intent(MainActivity.this , MainActivity2.class);
                    ReadFile read = new ReadFile(MainActivity.this);
                    Game game = new Game(read.getWords(),difficulty);
                    intent.putExtra("Object", game);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Debes indicar una palabra", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }
}
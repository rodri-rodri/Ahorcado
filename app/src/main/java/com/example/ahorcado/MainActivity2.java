package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    /**se instancian los elemento de la clase y las variables que se necesitaran
     * a parte se instancia un array text view sin iniciar que sera el que se
     * pinte en la pantalla con los huecos de la palabra a adivinar y el layout
     * donde se colocaran los text view**/
    private Button btn_newGame;
    private Button btn_check;
    private EditText et_letter;
    private EditText et_wordComplete;
    private Switch sw_completeWord;
    private LinearLayout lyh_textViews;
    private TextView[] tv_word;
    private TextView tv_attemps;
    private ImageView img_attemps;

    private Game game;
    private String[] letters;
    private boolean completeWord;
    private ArrayList<String> usedLetters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_newGame = findViewById(R.id.btn_newGame);
        btn_check = findViewById(R.id.btn_sendLetter);
        et_letter = findViewById(R.id.et_letter);
        et_wordComplete = findViewById(R.id.et_wordComplete);
        sw_completeWord = findViewById(R.id.sw_completeWord);
        lyh_textViews = findViewById(R.id.lyh_textViews);
        tv_attemps = findViewById(R.id.tv_attemps);
        img_attemps = findViewById(R.id.img_attemps);

        /**ejecuto el metodo de llegada de datos y el metodo que coloca el array de text view en el layout**/
        dataArraival();
        printTextViewArray();

        /**Con este switch habilito o desabilito la opcion de completar palabra,
         * a parte se guada el valor de isChecked en una variable boolean
         * para controlar que metodo emplear al darle al boton comprobar.
         * **/
        sw_completeWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (completeWord=isChecked){
                    et_wordComplete.setVisibility(View.VISIBLE);
                    et_letter.setVisibility(View.GONE);
                } else {
                    et_wordComplete.setVisibility(View.INVISIBLE);
                    et_letter.setVisibility(View.VISIBLE);
                }
            }
        });

        /**boton que comprueba si la palabra o letra indicada es correcta
         * si esta activado el switch ira al metodo checkWord
         * si no ira al metodo checkletter
         * al final pregunta si se debe continuar el juego
         * al metodo continueGame de la clase game en base a los intentos restantes
         * y al metogo checkWin de la clase game en base a la puntuacion
         ******************************************************************/
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!completeWord){
                    if (et_letter.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Debes indicar una letra", Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        checkLetter(et_letter.getText().toString());

                        et_letter.setText("");
                    }
                } else {
                    if (et_wordComplete.getText().toString().equals("")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Debes indicar una palabra", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        checkWord(et_wordComplete.getText().toString());
                    }

                }

                if (!game.continueGame()){
                    btn_check.setEnabled(false);
                    btn_newGame.setEnabled(true);
                    sw_completeWord.setEnabled(false);
                }
                if (game.checkWin()){
                    Toast.makeText(getApplicationContext(), "Enhorabuena! Has ganado!", Toast.LENGTH_SHORT).show();
                    btn_check.setEnabled(false);
                    btn_newGame.setEnabled(true);
                    sw_completeWord.setEnabled(false);
                }


            }
        });

        /**este boton solo estara activo al finalizar el juego y regresa a la pantalla de inicio
         ****************************************************************************************/
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }

    /**Metodo que comprueba las letras que se van introduciendo.
     * las guarda en un array aciertes o no para no poder volver a utilizarlas
     * en caso de acierto pintara en el array de text views las letras que se hayan acertado
     * y se sumara un punto por letra descubierta a points de la clase game
     * en caso de error restara un intento y cambiara la imagen
     * **/
    public void checkLetter(String letter){

        boolean letterUsed=false;
        for (int j=0;j<usedLetters.size();j++){
            if (letter.equals(usedLetters.get(j))){
                letterUsed=true;
            }
        }
        usedLetters.add(letter);

        if (letterUsed){
            Toast.makeText(getApplicationContext(),"Esta letra ya ha sido usada!", Toast.LENGTH_LONG).show();
        } else {
            boolean restarIntentos = true;
            for (int i=0;i<game.getWord().length();i++){
                if (letters[i].equals(letter)){
                    tv_word[i].setText(letters[i]);
                    game.setPoints(game.getPoints()+1);
                    restarIntentos=false;
                }
            }
            if (restarIntentos){
                game.setAttempts(game.getAttempts()-1);
                changeImg();
                tv_attemps.setText(String.valueOf(game.getAttempts()));
            }
        }
    }

    /**Metodo que comprueba si la palabra es correcta
     * en caso de victoria pintara la palabra entera en el array de text views
     * y bloqueara el juego dando solo opcion a iniciar una nueva partida
     * en caso de error restara un intento y cambiara la imagen.
     * **/
    public void checkWord(String word){
        if(word.equals(game.getWord())){
            Toast.makeText(getApplicationContext(), "Enhorabuena! Has acertado!", Toast.LENGTH_LONG).show();
            for (int i=0;i<tv_word.length;i++){
                System.out.println(word.charAt(i));
                tv_word[i].setText(String.valueOf(word.charAt(i)));
                btn_check.setEnabled(false);
                btn_newGame.setEnabled(true);
                sw_completeWord.setEnabled(false);
                et_wordComplete.setEnabled(false);
            }
        } else {
            Toast.makeText(getApplicationContext(), "FALLASTE! :S", Toast.LENGTH_SHORT).show();
            game.setAttempts(game.getAttempts()-1);
            changeImg();
            et_wordComplete.setText("");
            tv_attemps.setText(String.valueOf(game.getAttempts()));

        }

    }

    /**
     * Metodo de lllegada de datos del MainActivity que recibe el objeto game ya iniciado
     * con el tipo de juego y la palabra a adivinar
     * a parte de valor al text view con los intentos restantes
     * y carga un array que contendra cada una de las letras de la palabra
     **********************************************************************************************/
    public void dataArraival(){
        Bundle bundle = getIntent().getExtras();
        game= (Game)bundle.get("Object");
        letters = game.getLetters();
        tv_attemps.setText(String.valueOf(game.getAttempts()));
    }

    /**Metodo con el que se cambia de imagen en funcion de los intentos que quedan**/
    public void changeImg(){
        if (game.getAttempts()==5)
            img_attemps.setImageResource(R.drawable.ahorcado_5);
        if (game.getAttempts()==4)
            img_attemps.setImageResource(R.drawable.ahorcado_4);
        if (game.getAttempts()==3)
            img_attemps.setImageResource(R.drawable.ahorcado_3);
        if (game.getAttempts()==2)
            img_attemps.setImageResource(R.drawable.ahorcado_2);
        if (game.getAttempts()==1)
            img_attemps.setImageResource(R.drawable.ahorcado_1);
        if (game.getAttempts()==0)
            img_attemps.setImageResource(R.drawable.ahorcado_0);
    }

    /**
     * metodo que inicializa el array de textview con tamaño de la palabra
     * y lo pinta en el layout horizontal
     * se cambia la fuente del textView creando un objeto TypeFace que llama a la fuente en assets
     * a parte se cambia el tamaña de la fuente en funcion del tamaño de la palabra a adivinar
     * **/
    public void printTextViewArray(){

        tv_word = new TextView[game.getWord().length()];
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Cowboys.otf");

        for (int i = 0 ; i < tv_word.length;i++) {

            tv_word[i] = new TextView(getApplicationContext());
            tv_word[i].setText(" .. ");
            tv_word[i].setTypeface(tf);
            tv_word[i].setTextColor(Color.BLACK);
            if (game.getWord().length() <= 10){
                tv_word[i].setTextSize(50);
            } else {
                tv_word[i].setTextSize(25);
            }

            lyh_textViews.addView(tv_word[i]);
        }
    }

}
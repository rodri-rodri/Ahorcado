package com.example.ahorcado;

import android.util.Log;

import java.io.Serializable;

public class Game implements Serializable {

    /**Creo las variables de la clase que seran la palabra a adivinar
     * un array con cada letra de la palabra,
     * numero de intentos,
     * puntos,
     * y dificultad
     * tambien se crea un array de palabras que se recibira en caso de que el tipo de juego
     * sea con palabra aleatoria
     * **/
    private String word;
    private String[] letters;
    private int attempts;
    private int points;
    private int difficulty;
    private String[] randomWords;

    /**se define valor por defecto de intentos y puntos**/
    private static final int defaultAttemps = 6;
    private static final int defaultPoints = 0;

    /**constructor que se llamara en caso de que se elija la palabra a adivinar**/
    public Game(String word){
        this.word=word;
        this.attempts=defaultAttemps;
        this.points=defaultPoints;
        loadLetters();
    }
    /**constructor con palabras aleatorias
     * recibe un array de palabras y elige la palabra
     * en funcion de la difucultad
     * **/
    public Game(String[] randomWords,int difficulty){

        this.randomWords=randomWords;
        this.difficulty = difficulty;
        this.word = randomWord();
        this.attempts=defaultAttemps;
        this.points=defaultPoints;
        loadLetters();
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getWord() {
        return word;
    }

    public String[] getLetters() {
        return letters;
    }

    /**metodo que carga cada letra de la palabra en un array**/
    public void loadLetters(){

        letters = new String[word.length()];

        for (int i=0;i<word.length();i++){
            letters[i] = String.valueOf(word.charAt(i));
        }

        System.out.println(letters[0]);
    }

    /**metodo que indica si el juego continua
     * en funcion de los intentos que quedan
     ****************************************/
    public boolean continueGame(){
        if (attempts==0){
            return false;
        }
        return true;
    }

    /**metodo que indica si se ha hacertado
     * la palabra averiguando
     *  letra a letra
     *************************/
    public boolean checkWin(){

        if (points==word.length()){
            return true;
        }
        return false;
    }

    /**metodo que selecciona una palabra aleatoria
     * en funcion del tipo de dificultad segun la cantidad de caracteres
     * de la palabra
     ***********************************/
    public String randomWord(){

        int number = 0;
        if (difficulty ==0){
            do {
                number = (int) (Math.random()*randomWords.length);
                Log.i("easy", "randomWord: "+randomWords[number]);
            } while (randomWords[number].length()>5);
        }
        if (difficulty ==1){
            do {
                number = (int) (Math.random()*randomWords.length);
                Log.i("medium", "randomWord: "+randomWords[number]);
            } while (randomWords[number].length()>11);
        }
        if (difficulty ==2){
            do {
                number = (int) (Math.random()*randomWords.length);
                Log.i("hard", "randomWord: "+randomWords[number]);
            } while (randomWords[number].length()<10);
        }

        return randomWords[number].toUpperCase();
    }

}


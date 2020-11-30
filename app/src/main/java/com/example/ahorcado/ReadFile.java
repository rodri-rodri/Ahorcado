package com.example.ahorcado;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
/**CLASE CON LA QUE SE LEE UN TXT DE LA CARPETA ASSETS**/
public class ReadFile {

    /**se crean las variables
     * hay que crear un contexto para poder llamar a la carpeta del txt
     *******************************************************************/
    private Context context;
    private String[] words;

    /**constructor que recibe el contexto de la mainActivity1
     * y guarda el contenido del txt en un array de palabras
     ***/
    public ReadFile(Context context){
        this.context=context;
        this.words=readFile();
    }

    public String[] getWords() {
        return words;
    }

    /**metodo con el que se lee el archivo**/
    public String[] readFile() {

        try {
            InputStream inputStream =context.getAssets().open("words.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<String> words = new ArrayList<>();
            String word;
            while ((word=bf.readLine())!= null){
                words.add(word);
            }

            this.words = new String[words.size()];
            this.words = words.toArray(this.words);

        } catch (IOException e){
            e.printStackTrace();
        }
        return words;
    }
}

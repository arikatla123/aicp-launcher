package com.aicloudpods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamReaderRunnable implements Runnable {

    public InputStream inputStream;
    public String input;

    public InputStreamReaderRunnable(InputStream inputStream, String input) {
        this.inputStream = inputStream;
        this.input = input;
    }


    @Override
    public void run() {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String currentName = input;
        try {
            String line = bufferedReader.readLine();

            while (line !=null){
                line = bufferedReader.readLine();
                System.out.println((currentName + ":" + line));
            }
            inputStreamReader.close();
            bufferedReader.close();
        } catch  (IOException e) {
            e.printStackTrace();
        }
    }
}

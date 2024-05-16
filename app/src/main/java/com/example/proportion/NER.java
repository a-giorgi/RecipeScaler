package com.example.proportion;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.MultiAutoCompleteTextView;

import org.tensorflow.lite.Interpreter;


import java.io.BufferedReader;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class NER {
    public static final String model = "NER.tflite";
    public static final String word_index = "tokenizer_word_index.json";
    private Interpreter interpreter;
    private HashMap<String, String> tokenizer;

    private HashMap<String, String> tokenToTag;
    private final int SENTENCE_LENGTH = 72;

    private static NER instance = null;

    public static NER getInstance(Context context) throws IOException{
        if(instance == null) {
            instance = new NER(context);
        }
        return instance;
    }

    private NER(Context context) throws IOException{
        AssetManager assetManager = context.getAssets();
        interpreter = new Interpreter(loadModelFile(assetManager, model));
        tokenizer = loadJSONToHashMap(assetManager,word_index);
        loadTokenToTag();
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void loadTokenToTag(){
        tokenToTag = new HashMap<>();
        tokenToTag.put("0","O");
        tokenToTag.put("1","QUANTITY");
        tokenToTag.put("2","UNIT");
        tokenToTag.put("3","FOOD");
    }

    private HashMap<String, String> loadJSONToHashMap(AssetManager assetManager, String jsonPath)
            throws IOException {
        InputStream inputStream = assetManager.open(jsonPath);
        int jsonSize = inputStream.available();
        byte[] buffer = new byte[jsonSize];
        inputStream.read(buffer);
        inputStream.close();
        String jsonData = new String(buffer, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Type type = new com.google.gson.reflect.TypeToken<HashMap<String, String>>(){}.getType();
        return gson.fromJson(jsonData, type);
    }

    public String[] predict(String sentence){
        int[][] input = tokenize(sentence);
        //[][] input = new float[1][SENTENCE_LENGTH];
        //input[0] = tokens;
        //float[][] in = transposeMatrix(input);

        float[][][] output = new float[1][SENTENCE_LENGTH][4];
        interpreter.run(input, output);
        printOutput(output[0]);
        return convertToTags(output[0]);
    }

    private String[] convertToTags(float[][] prediction){
        String[] output = new String[SENTENCE_LENGTH];
        for (int i = 0; i < SENTENCE_LENGTH; i++) {
            int tag = argmax(prediction[i]);
            output[i] = tokenToTag.get(String.valueOf(tag));
        }
        return output;
    }

    private int[][] tokenize(String sentence) {
        int[][] tokens = new int[1][SENTENCE_LENGTH];
        String[] words = sentence.split("\\s+");
        for (int i = 0; i < SENTENCE_LENGTH; i++) {
            int token = 0;
            if (i < words.length) {
                words[i] = words[i].replaceAll("[^\\w]", "");
                if (tokenizer.containsKey(words[i])) {
                    token = Integer.parseInt(tokenizer.get(words[i]));
                }
            }
            tokens[0][i] = token;
        }
        return tokens;
    }

    public static float[][] transposeMatrix(float[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;

        float[][] transposedMatrix = new float[n][m];

        for(int x = 0; x < n; x++) {
            for(int y = 0; y < m; y++) {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }

        return transposedMatrix;
    }

    public int argmax(float[] array){
        if ( array == null || array.length == 0 )
            return -1;
        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest;
    }

    public void printOutput(float[][] array){
        for(int i = 0; i<array.length; i++){
            Log.d("Debug", Arrays.toString(array[i]));
        }

    }



}

package com.example.proportion;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.widget.MultiAutoCompleteTextView;

import org.tensorflow.lite.Interpreter;


import java.io.BufferedReader;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class NER {
    public static final String model = "NEF.tflite";
    public static final String word_index = "tokenizer_word_index.json";
    private Interpreter interpreter;
    private HashMap<String, String> tokenizer;
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
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private HashMap<String, String> loadJSONToHashMap(AssetManager assetManager, String jsonPath)
            throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(jsonPath);
        String str = "";
        StringBuilder buf = new StringBuilder();
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((str = reader.readLine()) != null) {
            buf.append(str).append("\n");
        }
        String jsonData = buf.toString();
        Gson gson = new Gson();
        Type type = new com.google.gson.reflect.TypeToken<HashMap<String, String>>(){}.getType();
        return gson.fromJson(jsonData, type);
    }



}

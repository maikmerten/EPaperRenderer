package com.example.epaper.renderserver.tagdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class TagDb {

    Map<String, TagInfo> tagMap;

    public TagDb() {
        File dbFile = new File("." + File.separator + "config" + File.separator + "tags.json");
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(dbFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Type type = new TypeToken<Map<String, TagInfo>>(){}.getType();
        this.tagMap = gson.fromJson(reader, type);
    }

    public TagInfo getTagInfo(String mac) {
        return tagMap.get(mac);
    }

    public Map<String, TagInfo> getTagMap() {
        return tagMap;
    }
  
}

package com.cavetale.mytems.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class Json {
    public static final Gson GSON = new GsonBuilder().setLenient().disableHtmlEscaping().create();
    public static final Gson PRETTY = new GsonBuilder().setLenient().disableHtmlEscaping().setPrettyPrinting().create();
    private static final Pattern SIMPLE = Pattern.compile("[0-9a-zA-Z_-]+");

    private Json() { }

    public static <T> T load(final File file, Class<T> type, Supplier<T> dfl) {
        if (!file.exists()) {
            return dfl.get();
        }
        try (FileReader fr = new FileReader(file)) {
            return GSON.fromJson(fr, type);
        } catch (FileNotFoundException fnfr) {
            return dfl.get();
        } catch (IOException ioe) {
            throw new IllegalStateException("Loading " + file, ioe);
        }
    }

    public static <T> T load(final File file, Class<T> type) {
        return load(file, type, () -> null);
    }

    public static void save(final File file, Object obj, boolean pretty) {
        try (FileWriter fw = new FileWriter(file)) {
            Gson gs = pretty ? PRETTY : GSON;
            gs.toJson(obj, fw);
        } catch (IOException ioe) {
            throw new IllegalStateException("Saving " + file, ioe);
        }
    }

    public static void save(final File file, Object obj) {
        save(file, obj, false);
    }

    public static String serialize(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T deserialize(String json, Class<T> type) {
        return GSON.fromJson(json, type);
    }

    public static <T> T deserialize(String json, Class<T> type, Supplier<T> dfl) {
        T t;
        try {
            t = GSON.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            t = null;
        }
        return t != null ? t : dfl.get();
    }

    public static String simplified(Object in) {
        if (in == null) {
            return "null";
        } else if (in instanceof JsonElement) {
            JsonElement jsonElement = (JsonElement) in;
            if (jsonElement.isJsonNull()) {
                return jsonElement.toString();
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                List<String> stringList = new ArrayList<>();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    stringList.add(simplified(entry.getKey()) + ":" + simplified(entry.getValue()));
                }
                return "{" + String.join(",", stringList) + "}";
            } else if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                List<String> stringList = new ArrayList<>(jsonArray.size());
                for (JsonElement it : jsonArray) {
                    stringList.add(simplified(it));
                }
                return "[" + String.join(",", stringList) + "]";
            } else if (jsonElement.isJsonPrimitive()) {
                JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
                if (jsonPrimitive.isString()) return simplified(jsonPrimitive.getAsString());
                return jsonPrimitive.toString();
            } else {
                return GSON.toJson(jsonElement);
            }
        } else if (in instanceof String) {
            String string = (String) in;
            if (!SIMPLE.matcher(string).matches()) return serialize(in);
            if (Objects.equals(deserialize(string, Object.class), string)) return string;
            return serialize(in);
        } else if (in instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) in;
            List<String> stringList = new ArrayList<>(map.size());
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                stringList.add(simplified(entry.getKey()) + ":" + simplified(entry.getValue()));
            }
            return "{" + String.join(",", stringList) + "}";
        } else if (in instanceof List) {
            List list = (List) in;
            List<String> stringList = new ArrayList<>(list.size());
            for (Object it : list) {
                stringList.add(simplified(it));
            }
            return "[" + String.join(",", stringList) + "]";
        } else if (in instanceof Integer || in instanceof Long || in instanceof Short || in instanceof Byte || in instanceof Boolean) {
            return in.toString();
        } else if (in instanceof Number) {
            return serialize((Number) in);
        } else {
            return simplified(GSON.toJsonTree(in));
        }
    }
}

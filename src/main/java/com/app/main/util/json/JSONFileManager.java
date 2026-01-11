package com.app.main.util.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for managing JSON files using Jackson.
 * 
 * The JSONFileManager provides methods to read, write, and create JSON
 * files,
 * as well as to manipulate nested keys using dot notation. It supports
 * automatic creation
 * of intermediate keys and handles both maps and lists in the JSON structure.
 * 
 * 
 * Read and write values using dot notation keys (e.g., "tile1.i").
 * Automatically creates intermediate objects when writing.
 * Handles both maps and lists in the JSON structure.
 * Pretty-prints JSON when saving to file.
 * 
 *
 * Example :
 * 
 * 
 * JSONFileManager manager = new JSONFileManager("level.json");
 * manager.create();
 * manager.writeLine("tile1.i", 2);
 * Object value = manager.read("tile1.i");
 *
 * @author Mohamed IBRIR
 * @since 2025-03-22
 */

interface JSONFileManagerInterface {
    Object read(String key);

    void writeLine(String key, Object value);

    void create();
}

public class JSONFileManager implements JSONFileManagerInterface {
    private String path;
    private final ObjectMapper objectMapper;
    private Map<String, Object> jsonObject;

    /**
     * Constructs a JSONFileManager for the specified file path.
     * Initializes the internal JSON object as an empty map.
     *
     * @param path The path to the JSON file to manage.
     */
    public JSONFileManager(String path) {
        this.path = path;
        this.objectMapper = new ObjectMapper();
        this.jsonObject = new HashMap<>();
    }

    /**
     * Initializes a new empty JSON object and creates the file if necessary.
     * Also creates any missing parent directories for the file.
     */
    public void create() {
        this.jsonObject = new HashMap<>();
        Path filePath = Paths.get(this.path);

        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            this.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a value to the JSON structure at the specified key.
     * Supports dot notation for nested keys (e.g., "tile1.i").
     * Automatically creates intermediate objects if they do not exist.
     *
     * @param key   The key (dot notation supported).
     * @param value The value to write.
     */
    @SuppressWarnings("unchecked")
    public void writeLine(String key, Object value) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = jsonObject;

        // Création automatique des clés intermédiaires si elles n'existent pas
        for (int i = 0; i < keys.length - 1; i++) {
            if (!current.containsKey(keys[i]) || !(current.get(keys[i]) instanceof Map)) {
                current.put(keys[i], new HashMap<>());
            }
            current = (Map<String, Object>) current.get(keys[i]);
        }

        // Mise à jour de la clé finale
        current.put(keys[keys.length - 1], value);
        this.saveToFile();
    }

    /**
     * Reads a value from the JSON file using the specified key.
     * Supports dot notation for nested keys (e.g., "tile1.i").
     *
     * @param key The key (dot notation supported).
     * @return The value found, or null if not present.
     */
    public Object read(String key) {
        this.open();
        return readValueByKey(this.jsonObject, key);
    }

    /**
     * Reads a value from the provided root map using a dot notation key.
     * Supports array access with brackets (e.g., "array[0].key").
     *
     * @param root The root map.
     * @param key  The key in dot notation.
     * @return The value found, or null if not present.
     */
    @SuppressWarnings("unchecked")
    private Object readValueByKey(Map<String, Object> root, String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> current = root;

        for (int i = 0; i < keys.length - 1; i++) {
            String keyPart = keys[i];

            if (keyPart.contains("[")) {
                String arrayName = keyPart.substring(0, keyPart.indexOf("["));
                int index = Integer.parseInt(keyPart.substring(keyPart.indexOf("[") + 1, keyPart.indexOf("]")));

                Object obj = current.get(arrayName);
                if (obj instanceof List) {
                    List<?> list = (List<?>) obj;
                    if (index < list.size()) {
                        current = (Map<String, Object>) list.get(index);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                Object obj = current.get(keyPart);
                if (obj instanceof Map) {
                    current = (Map<String, Object>) obj;
                } else {
                    return obj;
                }
            }
        }
        return current.get(keys[keys.length - 1]);
    }

    /**
     * Opens and loads the JSON file into memory.
     * If the file does not exist or is invalid, initializes an empty JSON object.
     */
    private void open() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String content = Files.readString(Paths.get(this.path));
            this.jsonObject = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            this.jsonObject = new HashMap<>();
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }
    }

    /**
     * Loads the JSON file into memory.
     * If the file does not exist, initializes an empty JSON object.
     */
    public void loadFile() {
        File file = new File(this.path);
        if (file.exists()) {
            try {
                jsonObject = objectMapper.readValue(file, new TypeReference<>() {
                });
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
                jsonObject = new HashMap<>();
            }
        } else {
            jsonObject = new HashMap<>();
        }
    }

    /**
     * Saves the current JSON object to the file, pretty-printed.
     */
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(this.path)) {
            writer.write(toJsonString(this.jsonObject, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the given object (Map, List, String, or primitive) to a
     * pretty-printed JSON string.
     *
     * @param obj         The object to convert.
     * @param indentLevel The indentation level for pretty-printing.
     * @return The JSON string representation of the object.
     */
    private String toJsonString(Object obj, int indentLevel) {
        String indent = "\t".repeat(indentLevel);
        String indentInner = "\t".repeat(indentLevel + 1);

        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder("{\n");
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                sb.append(indentInner)
                        .append("\"").append(entry.getKey()).append("\": ")
                        .append(toJsonString(entry.getValue(), indentLevel + 1))
                        .append(",\n");
            }
            if (!map.isEmpty())
                sb.delete(sb.length() - 2, sb.length());
            sb.append("\n").append(indent).append("}");
            return sb.toString();
        } else if (obj instanceof List) {
            StringBuilder sb = new StringBuilder("[\n");
            List<?> list = (List<?>) obj;
            for (Object item : list) {
                sb.append(indentInner)
                        .append(toJsonString(item, indentLevel + 1))
                        .append(",\n");
            }
            if (!list.isEmpty())
                sb.delete(sb.length() - 2, sb.length());
            sb.append("\n").append(indent).append("]");
            return sb.toString();
        } else if (obj instanceof String) {
            return "\"" + obj + "\"";
        } else {
            return obj.toString();
        }
    }
}

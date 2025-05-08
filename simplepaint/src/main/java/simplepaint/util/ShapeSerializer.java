package simplepaint.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import simplepaint.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShapeSerializer {
    public static void saveToJson(List<Shape> shapes) {
        try (FileWriter writer = new FileWriter("shapes.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray array = new JsonArray();
            for (Shape shape : shapes) {
                JsonObject obj = gson.toJsonTree(shape).getAsJsonObject();
                obj.addProperty("type", shape.getType());
                array.add(obj);
            }
            gson.toJson(array, writer);
            System.out.println("Saved to shapes.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Shape> loadFromJson() {
        List<Shape> shapes = new ArrayList<>();
        try (FileReader reader = new FileReader("shapes.json")) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            Gson gson = new Gson();
            for (JsonElement el : array) {
                JsonObject obj = el.getAsJsonObject();
                String type = obj.get("type").getAsString();
                switch (type) {
                    case "line":
                        shapes.add(gson.fromJson(obj, Line.class));
                        break;
                    case "circle":
                        shapes.add(gson.fromJson(obj, Circle.class));
                        break;
                    default:
                        System.err.println("Unknown shape type: " + type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shapes;
    }
    
}
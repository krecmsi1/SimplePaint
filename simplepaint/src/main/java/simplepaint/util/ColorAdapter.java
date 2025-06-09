package simplepaint.util;

import com.google.gson.*;
import java.awt.Color;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("r", color.getRed());
        json.addProperty("g", color.getGreen());
        json.addProperty("b", color.getBlue());
        json.addProperty("a", color.getAlpha());
        return json;
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int r = obj.get("r").getAsInt();
        int g = obj.get("g").getAsInt();
        int b = obj.get("b").getAsInt();
        int a = obj.has("a") ? obj.get("a").getAsInt() : 255;
        return new Color(r, g, b, a);
    }
}

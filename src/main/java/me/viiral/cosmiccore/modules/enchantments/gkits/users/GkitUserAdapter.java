package me.viiral.cosmiccore.modules.enchantments.gkits.users;

import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GkitUserAdapter extends TypeAdapter<GkitUser> {

    @Override
    public void write(JsonWriter jsonWriter, GkitUser user) {
        try {

            jsonWriter.beginObject();
            for (Map.Entry<String, JsonElement> entry : user.getCooldownMap().entrySet()) {
                jsonWriter.name(entry.getKey()).value(entry.getValue().getAsLong());
            }
            jsonWriter.endObject();

            jsonWriter.flush();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GkitUser read(JsonReader jsonReader) {
        try {
            jsonReader.beginObject();
            Map<String, Long> map = new HashMap<>();
            while (jsonReader.hasNext()) {

                JsonToken token = jsonReader.peek();
                if (token == JsonToken.END_OBJECT) {
                    break;
                }
                if (token == JsonToken.NAME) {
                    String nextName = jsonReader.nextName();
                    long cooldown = jsonReader.nextLong();
                    map.put(nextName, cooldown);

                }
            }
            jsonReader.endObject();
            return new GkitUser(map);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
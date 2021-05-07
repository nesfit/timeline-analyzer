/**
 * PlasoParser.java
 *
 * Created on 29. 3. 2020, 20:38:02 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

/**
 *
 * @author burgetr
 */
public class PlasoJsonParser extends PlasoParser
{

    @Override
    public List<PlasoEntry> parseInputStream(InputStream is) throws IOException
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlasoEntry.class, new EntryDeserializer())
                .create();
        
        PlasoEntry[] entries = gson.fromJson(new InputStreamReader(is), PlasoEntry[].class);
        
        return new ArrayList<>(Arrays.asList(entries));
    }

    public PlasoEntry parseSingleEntry(InputStream is) throws IOException
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlasoEntry.class, new EntryDeserializer())
                .create();
        
        PlasoEntry entry = gson.fromJson(new InputStreamReader(is), PlasoEntry.class);
        
        return entry;
    }
    
    private static class EntryDeserializer implements JsonDeserializer<PlasoEntry>
    {

        @Override
        public PlasoEntry deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException
        {
            JsonArray jroot = json.getAsJsonArray();
            if (jroot.size() == 2)
            {
                String source = jroot.get(0).getAsString();
                JsonArray jdata = jroot.get(1).getAsJsonArray();
                if (jdata.size() == 2)
                {
                    JsonObject jEvent = jdata.get(0).getAsJsonObject();
                    Map<String, Object> event = objectToMap(jEvent);
                    JsonObject jEventData = jdata.get(1).getAsJsonObject();
                    Map<String, Object> eventData = objectToMap(jEventData);
                    
                    return new PlasoEntry(event, eventData, source);
                }
            }
            
            return new PlasoEntry();
        }
        
        private Map<String, Object> objectToMap(JsonObject obj)
        {
            Map<String, Object> ret = new HashMap<>();
            for (Map.Entry<String,JsonElement> entry : obj.entrySet())
            {
                if (entry.getValue().isJsonPrimitive())
                {
                    JsonPrimitive val = entry.getValue().getAsJsonPrimitive();
                    if (val.isBoolean())
                    {
                        ret.put(entry.getKey(), entry.getValue().getAsJsonPrimitive().getAsBoolean());
                    }
                    else if (val.isNumber())
                    {
                        String s = entry.getValue().getAsJsonPrimitive().getAsString();
                        Object num = null;
                        // try long
                        try {
                            num = Long.parseLong(s);
                        } catch (NumberFormatException e) {
                        }
                        // try double
                        if (num == null)
                        {
                            try {
                                num = Double.parseDouble(s);
                            } catch (NumberFormatException e) {
                            }
                        }
                        // all failed, use the string
                        if (num == null)
                            num = s;
                        ret.put(entry.getKey(), num);
                    }
                    else
                    {
                        ret.put(entry.getKey(), entry.getValue().getAsJsonPrimitive().getAsString());
                    }
                }
            }
            return ret;
        }
        
        
        
    }
    
}

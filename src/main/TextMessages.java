package main;

import objects.AllBaseObject;

import java.io.*;
import java.util.*;

public class TextMessages
{
    Map<Enum, List<String>> listMessages = new HashMap<>();

    public TextMessages()
    {
        listMessages.put(AllBaseObject.Key, loadMessages("/assets/Strings/Key.txt"));
        listMessages.put(AllBaseObject.Boots, loadMessages("/assets/Strings/Boots.txt"));
        listMessages.put(AllBaseObject.Door, loadMessages("/assets/Strings/Door.txt"));
        listMessages.put(AllBaseObject.Box, new ArrayList<>());
    }

    public String getMessage(Enum e)
    {
        return listMessages.get(e).get((int) (Math.random() * listMessages.get(e).size()));
    }

    private List<String> loadMessages( String path)
    {
        List<String> list = new ArrayList<>();
        InputStream stream = Objects.requireNonNull(getClass().getResourceAsStream(path));
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream)))
        {
            while ((line = reader.readLine()) != null)
                list.add(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }
}

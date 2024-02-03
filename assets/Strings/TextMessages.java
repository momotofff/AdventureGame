package assets.Strings;

import objects.AllBaseObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMessages
{
    Map<Enum, List<String>> listMessages = new HashMap<>();

    public TextMessages()
    {
        listMessages.put(AllBaseObject.Key,loadMessages("./assets/Strings/Key.txt"));
        listMessages.put(AllBaseObject.Boots, loadMessages("./assets/Strings/Boots.txt"));
        listMessages.put(AllBaseObject.Door, loadMessages("./assets/Strings/Door.txt"));
        listMessages.put(AllBaseObject.Box, new ArrayList<>());
    }

    public String getMessage(Enum e)
    {
        return listMessages.get(e).get((int) (Math.random() * listMessages.get(e).size()));
    }

    private List<String> loadMessages( String path)
    {
        List<String> list = new ArrayList<>();

        try
        {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                list.add(line);
            }

            bufferedReader.close();
            fileReader.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return list;
    }
}

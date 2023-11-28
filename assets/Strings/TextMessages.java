package assets.Strings;

import java.util.ArrayList;
import java.util.List;

public class TextMessages
{
    private List<String> keyMessages = new ArrayList<>();

    private List<String> doorMessages = new ArrayList<>();

    private List<String> boxMessages = new ArrayList<>();

    private final String CONGRATULATION;
    private final String FINISH;

    public TextMessages()
    {
        CONGRATULATION = "Поздравляю!!! Ты стал ближе к мировому богатству.";
        FINISH = "Конец";

        keyMessages.add("Хмммм... Этот ключ может быть от чего-то важного.");
        keyMessages.add("Этот ключ может тебе пригодиться.");
        keyMessages.add("Этот ключ тебе поможет в будущем.");
        keyMessages.add("Думаешь это последний ключ?");

        doorMessages.add("Не факт что у тебя есть нужный ключ.");
        doorMessages.add("У тебя нет подходящего ключа.");
        doorMessages.add("Тебе нужен подходящий ключ.");
        doorMessages.add("Ключ не подходит");

    }

    public String getKeyMessage()
    {
        return keyMessages.get((int) (Math.random() * keyMessages.size()));
    }

    public String getBoxMessage()
    {
        return keyMessages.get((int) (Math.random() * keyMessages.size()));
    }


}

package main;

import java.util.ArrayList;

public class AbstractDialogue
{
    final private ArrayList<String> phrases;
    private int currentPhrase = 0;

    public AbstractDialogue(ArrayList<String> phrases)
    {
        this.phrases = phrases;
    }

    public String getText()
    {
        if (isFinished())
            return "";

        return phrases.get(currentPhrase);
    }

    public void onKeyPressed()
    {
        ++currentPhrase;
    }

    public boolean isFinished()
    {
        return currentPhrase >= phrases.size();
    }
}

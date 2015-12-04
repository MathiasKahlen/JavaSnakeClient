package GUI.CustomComponents;

import javafx.scene.control.TextField;

/**
 * Created by Kahlen on 04-12-2015.
 */
public class NumberTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return ("".equals(text) || text.matches("[0-9]"));
    }
}

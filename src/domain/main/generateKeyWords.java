package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class generateKeyWords
{
    private final List<Keywords> keywordsList = new ArrayList<>();

    public generateKeyWords()
    {
        nothing();
        generateRaserei();
        generateJaeger();
        generateGift();
        generateRaffiniert();
        generateRobust();
    }

    public List<Keywords> getKeywordsList() {
        return Collections.unmodifiableList(keywordsList);
    }

    public void nothing()
    {
        keywordsList.add(new Keywords("",""));
    }

    private void generateRaserei()
    {
        keywordsList.add(new Keywords("Raserei", ""));
    }

    private void generateJaeger()
    {
        keywordsList.add(new Keywords("Jaeger", ""));
    }

    private void generateGift()
    {
        keywordsList.add(new Keywords("Gift",""));
    }

    private void generateRaffiniert()
    {
        keywordsList.add(new Keywords("Raffiniert",""));
    }

    private void generateRobust()
    {
        keywordsList.add(new Keywords("Robust", ""));
    }
}

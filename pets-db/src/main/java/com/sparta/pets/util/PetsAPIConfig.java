package com.sparta.pets.util;

public class PetsAPIConfig extends Config
{
    public void LoadFromFile()
    {
        super.LoadFromFile("src/test/resources/config.properties");
    }
}

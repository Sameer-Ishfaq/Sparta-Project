package com.sparta.pets.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
    private final Properties config = new Properties();

    public void LoadFromFile(String path)
    {
        try (var in = new FileInputStream(path))
        {
            config.load(in);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String GetProperty(String property)
    {
        return config.getProperty(property);
    }
}

package com.example.hashimoto_app.backend;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileManager
{
    /**
     * This method saves a new version of the user data to the memory
     * @param path
     * @param content
     * @param context
     */
    public static void saveFile(String path, String content ,Context context)
    {
        try (FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE))
        {
            fos.write(content.getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     * @param context
     * @return returns the user data from the memory as string
     */
    public static String getFileAsString(String path, Context context)
    {
        FileInputStream fis = null;
        try
        {
            fis = context.openFileInput(path);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader))
        {
            String line = reader.readLine();
            while (line != null)
            {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            // Error occurred when opening raw file for reading.
        }
        finally
        {
            String contents = stringBuilder.toString();
            return contents;
        }
    }
}

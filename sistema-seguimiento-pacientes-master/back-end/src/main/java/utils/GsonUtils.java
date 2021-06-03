/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;
import java.time.LocalDateTime;

/**
 * Nos devuelve el objeto Gson ya configurado para no tener que configurarlo en cada clase
 * @author Nagamine
 */
public class GsonUtils {
    public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) .create();
    
    
    
}

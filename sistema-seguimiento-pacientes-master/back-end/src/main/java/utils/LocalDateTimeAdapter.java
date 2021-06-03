/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author Nagamine
 */
class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return Instant.ofEpochMilli(jsonReader.nextLong()).atZone(ZoneOffset.ofHours(-5)).toLocalDateTime();
        }
    }

    @Override
    public void write(JsonWriter writer, LocalDateTime localDate) throws IOException {
         if (localDate == null) {
            writer.nullValue();
        } else {
            writer.value(localDate.toInstant(ZoneOffset.ofHours(-5)).toEpochMilli());
        }
    }
    
}
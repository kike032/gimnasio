/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author kikej
 */




public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter out, LocalDate fecha) throws IOException {
        if (fecha == null) {
            out.nullValue();
        } else {
            out.value(fecha.toString());
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String fecha = in.nextString();
        return LocalDate.parse(fecha);
    }
}
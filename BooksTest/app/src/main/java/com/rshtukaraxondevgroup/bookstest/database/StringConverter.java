package com.rshtukaraxondevgroup.bookstest.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringConverter {
    @TypeConverter
    public String fromString(List<String> strings) {
        return strings.stream().collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<String> toString(String data) {
        return Arrays.asList(data.split(","));
    }
}

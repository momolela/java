package com.momolela.webservice.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;

public class JSONUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T parse(String value, Class<T> clz) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        try {
            return (T) mapper.readValue(value, clz);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T parse(byte[] bytes, Class<T> clz) {
        try {
            return (T) mapper.readValue(bytes, clz);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T parse(InputStream ins, Class<T> clz) {
        try {
            return (T) mapper.readValue(ins, clz);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T parse(Reader reader, Class<T> clz) {
        try {
            return (T) mapper.readValue(reader, clz);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T update(String value, T object) {
        try {
            return (T) mapper.readerForUpdating(object).readValue(value);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String writeValueAsString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void write(OutputStream outs, Object o) {
        try {
            mapper.writeValue(outs, o);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void write(Writer writer, Object o) {
        try {
            mapper.writeValue(writer, o);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String toString(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String toString(Object o, Class<?> clz) {
        try {
            return mapper.writerFor(clz).writeValueAsString(o);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] toBytes(Object o) {
        try {
            return mapper.writeValueAsBytes(o);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

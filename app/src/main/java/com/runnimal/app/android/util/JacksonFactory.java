package com.runnimal.app.android.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class JacksonFactory {

    private final ObjectMapper mapper;

    public JacksonFactory() {
        this(getDefaultMapper());
    }

    public static ObjectMapper getDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false) //
                .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false) //
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) //
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true) //
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true) //
                .setSerializationInclusion(JsonInclude.Include.NON_ABSENT);

        return mapper;
    }

    @SneakyThrows
    public JacksonFactory registerJavaTime() {
        Module module = (Module) Class.forName(JavaTimeModule.class.getName()).newInstance();
        mapper.registerModule(module);
        return this;
    }

    @SneakyThrows
    public JsonNode toJsonNode(String json) {
        return mapper.readTree(json);
    }

    @SneakyThrows
    public JsonNode toJsonNode(InputStream json) {
        return mapper.readTree(json);
    }

    public JsonNode toJsonNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    public Stream<JsonNode> toJsonNodeStream(JsonNode jsonNode) {
        if (!jsonNode.isNull() && jsonNode.isArray()) {
            Spliterator<JsonNode> splitIterator = ((ArrayNode) jsonNode).spliterator();
            return StreamSupport.stream(splitIterator, false);
        }
        return Stream.empty();
    }

    public Stream<JsonNode> toJsonNodeStream(String json) {
        return toJsonNodeStream(toJsonNode(json));
    }

    public Stream<JsonNode> toJsonNodeStream(InputStream json) {
        return toJsonNodeStream(toJsonNode(json));
    }

    public Stream<JsonNode> toJsonNodeStream(Object obj) {
        return toJsonNodeStream(toJsonNode(obj));
    }

    public Map<String, JsonNode> toJsonNodeMap(JsonNode jsonNode) {
        Map<String, JsonNode> map = new HashMap<>();
        if (!jsonNode.isNull() && jsonNode.isObject()) {
            jsonNode.fields() //
                    .forEachRemaining(kv -> map.put(kv.getKey(), kv.getValue()));
        }
        return map;
    }

    public Map<String, JsonNode> toJsonNodeMap(InputStream json) {
        return toJsonNodeMap(toJsonNode(json));
    }

    public Map<String, JsonNode> toJsonNodeMap(String json) {
        return toJsonNodeMap(toJsonNode(json));
    }

    public Map<String, JsonNode> toJsonNodeMap(Object obj) {
        return toJsonNodeMap(toJsonNode(obj));
    }

    @SneakyThrows
    public <T> T toObject(String json, Class<T> clss) {
        return mapper.readValue(json, clss);
    }

    @SneakyThrows
    public <T> T toObject(InputStream json, Class<T> clss) {
        return mapper.readValue(json, clss);
    }

    @SneakyThrows
    public <T> T toObject(JsonNode jsonNode, Class<T> clss) {
        return mapper.treeToValue(jsonNode, clss);
    }

    public <T> Stream<T> toStream(String json, Class<T> clss) {
        return toJsonNodeStream(json) //
                .map(itemJsonNode -> toObject(itemJsonNode, clss));
    }

    public <T> Stream<T> toStream(InputStream json, Class<T> clss) {
        return toJsonNodeStream(json) //
                .map(itemJsonNode -> toObject(itemJsonNode, clss));
    }

    public <T> Stream<T> toStream(JsonNode jsonNode, Class<T> clss) {
        return toJsonNodeStream(jsonNode) //
                .map(itemJsonNode -> toObject(itemJsonNode, clss));
    }

    public <T> List<T> toList(String json, Class<T> clss) {
        return toStream(json, clss).collect(Collectors.toList());
    }

    public <T> List<T> toList(InputStream json, Class<T> clss) {
        return toStream(json, clss).collect(Collectors.toList());
    }

    public <T> List<T> toList(JsonNode jsonNode, Class<T> clss) {
        return toStream(jsonNode, clss).collect(Collectors.toList());
    }

    public <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return toJsonNodeMap(json) //
                .entrySet() //
                .stream() //
                .collect(Collectors.toMap(e -> toObject(encodeString(e.getKey()), keyClass), e -> toObject(e.getValue(), valueClass)));
    }

    public <K, V> Map<K, V> toMap(InputStream json, Class<K> keyClass, Class<V> valueClass) {
        return toJsonNodeMap(json) //
                .entrySet() //
                .stream() //
                .collect(Collectors.toMap(e -> toObject(encodeString(e.getKey()), keyClass), e -> toObject(e.getValue(), valueClass)));
    }

    public <K, V> Map<K, V> toMap(JsonNode jsonNode, Class<K> keyClass, Class<V> valueClass) {
        return toJsonNodeMap(jsonNode) //
                .entrySet() //
                .stream() //
                .collect(Collectors.toMap(e -> toObject(encodeString(e.getKey()), keyClass), e -> toObject(e.getValue(), valueClass)));
    }

    private static String encodeString(String s) {
        return '"' + s.replace("\"", "\\\"") + '"';
    }
}

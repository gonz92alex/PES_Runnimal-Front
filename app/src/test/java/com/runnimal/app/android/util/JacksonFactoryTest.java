package com.runnimal.app.android.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.Data;

public class JacksonFactoryTest {

    private enum TestEnum {
        NAME,
        SURNAME
    }

    @Data
    private static class TestModel {

        @Data
        private static class Person {

            private String name;
            private String surname;
            private Instant instant;
            private LocalDateTime date;
        }

        private final List<Person> people = new ArrayList<>();
    }

    private static final String SIMPLE_EXAMPLE_FILE = "json/simple_example.json";
    private static final String LIST_EXAMPLE_FILE = "json/list_example.json";
    private static final String MAP_EXAMPLE_FILE = "json/map_example.json";
    private static final ZoneId MADRID_ZONE_ID = ZoneId.of("Europe/Madrid");

    private JacksonFactory factory;

    @Before
    public void init() {
        factory = new JacksonFactory() //
                .registerJavaTime();
    }

    @Test
    public void testToJsonNodeFromString() {
        JsonNode jsonNode = factory.toJsonNode("{\"a\": \"a\", \"b\": null}");
        Assert.assertEquals("a", jsonNode.get("a").asText());
        Assert.assertTrue(jsonNode.get("b").isNull());
        Assert.assertNull(jsonNode.get("c"));
    }

    @Test
    public void testToJsonNodeFromStringWithSingleQuotes() {
        JsonNode jsonNode = factory.toJsonNode("{'a': 'a', 'b': null}");
        Assert.assertEquals("a", jsonNode.get("a").asText());
        Assert.assertTrue(jsonNode.get("b").isNull());
    }

    @Test
    public void testToJsonNodeFromStringWithUnquotedFieldNames() {
        JsonNode jsonNode = factory.toJsonNode("{a: 'a', b: null}");
        Assert.assertEquals("a", jsonNode.get("a").asText());
        Assert.assertTrue(jsonNode.get("b").isNull());
    }

    @Test
    public void testToJsonNodeFromFileInputStream() {
        JsonNode jsonNode = factory.toJsonNode(IOUtils.getResource(SIMPLE_EXAMPLE_FILE));
        Assert.assertEquals(2, jsonNode.get("people").size());
        Assert.assertEquals("name1", jsonNode.get("people").get(0).get("name").asText());
        Assert.assertEquals("surname1", jsonNode.get("people").get(0).get("surname").asText());
        Assert.assertEquals("name2", jsonNode.get("people").get(1).get("name").asText());
        Assert.assertEquals("surname2", jsonNode.get("people").get(1).get("surname").asText());
    }

    @Test
    public void testToJsonNodeFromObject() {
        TestModel.Person person = new TestModel.Person();
        person.setName("name");
        person.setSurname("surname");
        LocalDateTime date = LocalDateTime.parse("2019-04-07T23:22:05");
        person.setInstant(date.atZone(MADRID_ZONE_ID).toInstant());
        JsonNode jsonNode = factory.toJsonNode(person);
        Assert.assertEquals("name", jsonNode.get("name").asText());
        Assert.assertEquals("surname", jsonNode.get("surname").asText());
        Assert.assertEquals(date, LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonNode.get("instant").asLong()), MADRID_ZONE_ID));
    }

    @Test
    public void testToJsonNodeFromObjectWithNullAttribute() {
        TestModel.Person person = new TestModel.Person();
        person.setName("name");
        JsonNode jsonNode = factory.toJsonNode(person);
        Assert.assertEquals("name", jsonNode.get("name").asText());
        Assert.assertNull(jsonNode.get("surname"));
    }

    @Test
    public void toJsonNodeStreamFromJsonNode() {
        List<String> list = new ArrayList<>();
        list.add("element1");
        list.add("element2");
        Stream<JsonNode> jsonNodeList = factory.toJsonNodeStream(factory.toJsonNode(list));
        Assert.assertEquals(2, jsonNodeList.count());
    }

    @Test
    public void toJsonNodeStreamFromString() {
        Stream<JsonNode> jsonNodeList = factory.toJsonNodeStream("['element1', 'element2', 'element3', 'element4']");
        Assert.assertEquals(4, jsonNodeList.count());
    }

    @Test
    public void toJsonNodeStreamFromInputStream() {
        Stream<JsonNode> jsonNodeList = factory.toJsonNodeStream(IOUtils.getResource(LIST_EXAMPLE_FILE));
        Assert.assertEquals(3, jsonNodeList.count());
    }

    @Test
    public void toJsonNodeStreamFromObject() {
        List<String> list = new ArrayList<>();
        list.add("element1");
        list.add("element2");
        Stream<JsonNode> jsonNodeList = factory.toJsonNodeStream(list);
        Assert.assertEquals(2, jsonNodeList.count());
    }

    @Test
    public void toJsonNodeMapFromJsonNode() {
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put("name", "nombre");
        jsonNode.put("surname", "apellido");
        Map<String, JsonNode> jsonNodeMap = factory.toJsonNodeMap(jsonNode);
        Assert.assertEquals(2, jsonNodeMap.size());
        Assert.assertEquals(jsonNodeMap.get("name").asText(), "nombre");
        Assert.assertEquals(jsonNodeMap.get("surname").asText(), "apellido");
    }

    @Test
    public void testToJsonNodeoMapFromFileInputStream() {
        Map<String, JsonNode> map = factory.toJsonNodeMap(IOUtils.getResource(MAP_EXAMPLE_FILE));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("name", map.get("NAME").asText());
        Assert.assertEquals("surname", map.get("SURNAME").asText());
    }

    @Test
    public void toJsonNodeMapFromString() {
        Map<String, JsonNode> map = factory.toJsonNodeMap("{\"name\": \"name\", \"surname\": \"surname\"}");
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("name", map.get("name").asText());
        Assert.assertEquals("surname", map.get("surname").asText());
    }

    @Test
    public void toJsonNodeMapFromObject() {
        Map<String, String> mapObject = new HashMap<>();
        mapObject.put("name", "name");
        mapObject.put("surname1", "surname 1");
        mapObject.put("surname2", "surname 2");
        Map<String, JsonNode> map = factory.toJsonNodeMap(mapObject);
        Assert.assertEquals("name", map.get("name").asText());
        Assert.assertEquals("surname 1", map.get("surname1").asText());
        Assert.assertEquals("surname 2", map.get("surname2").asText());
    }

    @Test
    public void testToObjectFromString() {
        TestModel.Person person = factory.toObject("{\"name\": \"name\", \"surname\": \"surname\"}", TestModel.Person.class);
        Assert.assertEquals("name", person.getName());
        Assert.assertEquals("surname", person.getSurname());
    }

    @Test
    public void testToObjectFromFileInputStream() {
        TestModel model = factory.toObject(IOUtils.getResource(SIMPLE_EXAMPLE_FILE), TestModel.class);
        Assert.assertEquals(2, model.getPeople().size());
        Assert.assertEquals("name1", model.getPeople().get(0).getName());
        Assert.assertEquals("surname1", model.getPeople().get(0).getSurname());
        Assert.assertEquals(Instant.ofEpochMilli(1554672125000L), model.getPeople().get(0).getInstant());
        Assert.assertEquals(LocalDateTime.parse("2019-04-07T23:22:05"), LocalDateTime.ofInstant(model.getPeople().get(0).getInstant(), MADRID_ZONE_ID));
        Assert.assertEquals("name2", model.getPeople().get(1).getName());
        Assert.assertEquals("surname2", model.getPeople().get(1).getSurname());
        Assert.assertEquals(LocalDateTime.parse("2019-04-03T08:03:12"), model.getPeople().get(1).getDate());
    }

    @Test
    public void testToObjectFromJsonNode() {
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put("name", "name");
        jsonNode.put("surname", "surname");
        TestModel.Person person = factory.toObject(jsonNode, TestModel.Person.class);
        Assert.assertEquals("name", person.getName());
        Assert.assertEquals("surname", person.getSurname());
    }

    @Test
    public void testToStreamFromString() {
        Stream<String> stream = factory.toStream("['element1', 'element2', 'element3', 'element4']", String.class);
        Assert.assertEquals(4, stream.count());
    }

    @Test
    public void testToStreamFromFileInputStream() {
        Stream<TestModel.Person> stream = factory.toStream(IOUtils.getResource(LIST_EXAMPLE_FILE), TestModel.Person.class);
        Assert.assertEquals(3, stream.count());
    }

    @Test
    public void testToStreamFromJsonNode() {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        arrayNode.add("name1");
        arrayNode.add("name2");
        Stream<String> stream = factory.toStream(arrayNode, String.class);
        Assert.assertEquals(2, stream.count());
    }

    @Test
    public void testToListFromString() {
        List<String> list = factory.toList("['element1', 'element2', 'element3', 'element4']", String.class);
        Assert.assertEquals(4, list.size());
        Assert.assertEquals("element1", list.get(0));
        Assert.assertEquals("element2", list.get(1));
        Assert.assertEquals("element3", list.get(2));
        Assert.assertEquals("element4", list.get(3));
    }

    @Test
    public void testToListFromFileInputStream() {
        List<TestModel.Person> list = factory.toList(IOUtils.getResource(LIST_EXAMPLE_FILE), TestModel.Person.class);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals("name1", list.get(0).getName());
        Assert.assertEquals("surname1", list.get(0).getSurname());
        Assert.assertEquals("name2", list.get(1).getName());
        Assert.assertEquals("surname2", list.get(1).getSurname());
        Assert.assertEquals("name3", list.get(2).getName());
        Assert.assertEquals("surname3", list.get(2).getSurname());
    }

    @Test
    public void testToListFromJsonNode() {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        arrayNode.add("name1");
        arrayNode.add("name2");
        List<String> list = factory.toList(arrayNode, String.class);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("name1", list.get(0));
        Assert.assertEquals("name2", list.get(1));
    }

    @Test
    public void testToMapFromString() {
        Map<String, String> map = factory.toMap("{\"name\": \"nombre\", \"surname1\": \"apellido 1\", \"surname2\": \"apellido 2\"}", String.class, String.class);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("nombre", map.get("name"));
        Assert.assertEquals("apellido 1", map.get("surname1"));
        Assert.assertEquals("apellido 2", map.get("surname2"));
    }

    @Test
    public void testToMapFromFileInputStream() {
        Map<TestEnum, String> map = factory.toMap(IOUtils.getResource(MAP_EXAMPLE_FILE), TestEnum.class, String.class);
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("name", map.get(TestEnum.NAME));
        Assert.assertEquals("surname", map.get(TestEnum.SURNAME));
    }

    @Test
    public void testToMapFromJsonNode() {
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put("a", "1");
        jsonNode.put("b", "3");
        Map<String, String> map = factory.toMap(jsonNode, String.class, String.class);
        Assert.assertEquals(2, map.size());
        Assert.assertEquals(map.get("a"), "1");
        Assert.assertEquals(map.get("b"), "3");
    }
}

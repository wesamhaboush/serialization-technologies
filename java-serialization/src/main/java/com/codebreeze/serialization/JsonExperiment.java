package com.codebreeze.serialization;

import com.codebreeze.serialization.json.model.Json;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class JsonExperiment {
    private static final Consumer<Json.AddressBook> ADDRESS_BOOK_SINK_HOLE = addressBook -> System.out.println(addressBook);
    private static final Consumer<byte[]> BYTE_ARRAY_SINK_HOLE = addressBookByteArray -> {};
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

    public static final void main(final String...args) throws JsonProcessingException {
        final Json.AddressBook addressBook = createJsonAddressBook();
        BYTE_ARRAY_SINK_HOLE.accept(OBJECT_MAPPER.writeValueAsBytes(addressBook));
        ADDRESS_BOOK_SINK_HOLE.accept(addressBook);
    }

    private static Json.AddressBook createJsonAddressBook() {
        final String description = randomAlphanumeric(300);
        final Json.Person person1 = createJsonPerson();
        final Json.Person person2 = createJsonPerson();
        final Json.Person person3 = createJsonPerson();
        return new Json.AddressBook(description, asList(person1, person2, person3));
    }

    private static Json.Person createJsonPerson() {
        final Json.PhoneNumber phoneNumber1 = createJsonPhoneNumber();
        final Json.PhoneNumber phoneNumber2 = createJsonPhoneNumber();
        final String name = randomAlphabetic(25);
        final String email = randomAlphanumeric(20);
        final int id = nextInt(0, Integer.MAX_VALUE);
        return new Json.Person(name, id, email, asList(phoneNumber1, phoneNumber2));
    }

    private static Json.PhoneNumber createJsonPhoneNumber() {
        final String phoneNumberString = randomNumeric(10);
        final Json.PhoneType phoneType = Json.PhoneType.values()[nextInt(0, Json.PhoneType.values().length)];
        return new Json.PhoneNumber(phoneNumberString, phoneType);
    }
}

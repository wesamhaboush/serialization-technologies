package com.codebreeze.serialization;

import com.codebreeze.serialization.json.model.Json;
import com.codebreeze.serialization.json.model.Json.AddressBook;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

import static com.codebreeze.serialization.Experiments.BYTE_ARRAY_SINK_HOLE;
import static com.codebreeze.serialization.Experiments.OBJECT_SINK_HOLE;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class JsonMsgPackExperiment {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new MessagePackFactory())
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

    public static final void main(final String...args) throws IOException {
        final AddressBook addressBook = randomAddressBook();
        BYTE_ARRAY_SINK_HOLE.accept(createByteArray(addressBook));
        OBJECT_SINK_HOLE.accept(createAddressBook(createByteArray(addressBook)));
    }

    public static AddressBook randomAddressBook(){
        return createJsonAddressBook();
    }

    public static AddressBook createAddressBook(final byte[] data) throws IOException {
        return OBJECT_MAPPER.readValue(data, AddressBook.class);
    }

    public static byte[] createByteArray(final AddressBook addressBook) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsBytes(addressBook);
    }

    private static AddressBook createJsonAddressBook() {
        final String description = randomAlphanumeric(300);
        final Json.Person person1 = createJsonPerson();
        final Json.Person person2 = createJsonPerson();
        final Json.Person person3 = createJsonPerson();
        return new AddressBook(description, asList(person1, person2, person3));
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

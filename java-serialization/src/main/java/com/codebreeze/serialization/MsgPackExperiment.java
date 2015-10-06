package com.codebreeze.serialization;

import com.codebreeze.serialization.json.model.Json;
import com.codebreeze.serialization.msgpack.model.MsgPack;
import org.msgpack.MessagePack;

import java.io.IOException;

import static com.codebreeze.serialization.Experiments.BYTE_ARRAY_SINK_HOLE;
import static com.codebreeze.serialization.Experiments.OBJECT_SINK_HOLE;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class MsgPackExperiment {
    private static final MessagePack MESSAGE_PACK = new MessagePack();

    public static final void main(final String...args) throws IOException {
        final MsgPack.AddressBook addressBook = randomAddressBook();
        BYTE_ARRAY_SINK_HOLE.accept(createByteArray(addressBook));
        OBJECT_SINK_HOLE.accept(createAddressBook(createByteArray(addressBook)));
    }

    public static MsgPack.AddressBook randomAddressBook(){
        return createJsonAddressBook();
    }

    public static MsgPack.AddressBook createAddressBook(final byte[] data) throws IOException {
        return MESSAGE_PACK.read(data, MsgPack.AddressBook.class);
    }

    public static byte[] createByteArray(final MsgPack.AddressBook addressBook) throws IOException {
        return MESSAGE_PACK.write(addressBook);
    }

    private static MsgPack.AddressBook createJsonAddressBook() {
        final String description = randomAlphanumeric(300);
        final MsgPack.Person person1 = createJsonPerson();
        final MsgPack.Person person2 = createJsonPerson();
        final MsgPack.Person person3 = createJsonPerson();
        return new MsgPack.AddressBook(description, asList(person1, person2, person3));
    }

    private static MsgPack.Person createJsonPerson() {
        final MsgPack.PhoneNumber phoneNumber1 = createJsonPhoneNumber();
        final MsgPack.PhoneNumber phoneNumber2 = createJsonPhoneNumber();
        final String name = randomAlphabetic(25);
        final String email = randomAlphanumeric(20);
        final int id = nextInt(0, Integer.MAX_VALUE);
        return new MsgPack.Person(name, id, email, asList(phoneNumber1, phoneNumber2));
    }

    private static MsgPack.PhoneNumber createJsonPhoneNumber() {
        final String phoneNumberString = randomNumeric(10);
        final MsgPack.PhoneType phoneType = MsgPack.PhoneType.values()[nextInt(0, Json.PhoneType.values().length)];
        return new MsgPack.PhoneNumber(phoneNumberString, phoneType);
    }
}

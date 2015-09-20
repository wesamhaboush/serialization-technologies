package com.codebreeze.serialization;

import com.codebreeze.proto.simple.model.Spec;
import com.google.protobuf.InvalidProtocolBufferException;

import static com.codebreeze.serialization.Experiments.BYTE_ARRAY_SINK_HOLE;
import static com.codebreeze.serialization.Experiments.OBJECT_SINK_HOLE;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class ProtobuffExperiment {

    public static final void main(final String...args) throws InvalidProtocolBufferException {
        final Spec.AddressBook addressBook = randomAddressBook();
        BYTE_ARRAY_SINK_HOLE.accept(createByteArray(addressBook));
        OBJECT_SINK_HOLE.accept(createAddressBook(createByteArray(addressBook)));
    }

    public static Spec.AddressBook randomAddressBook(){
        return createSpecAddressBook();
    }

    public static Spec.AddressBook createAddressBook(final byte[] data) throws InvalidProtocolBufferException {
        return Spec.AddressBook.parseFrom(data);
    }

    public static byte[] createByteArray(final Spec.AddressBook addressBook) {
        return addressBook.toByteArray();
    }

    private static Spec.AddressBook createSpecAddressBook() {
        final String description = randomAlphanumeric(300);
        final Spec.Person person1 = createSpecPerson();
        final Spec.Person person2 = createSpecPerson();
        final Spec.Person person3 = createSpecPerson();
        return Spec.AddressBook.newBuilder()
                .addPerson(person1)
                .addPerson(person2)
                .addPerson(person3)
                .setDescription(description)
                .build();
    }

    private static Spec.Person createSpecPerson() {
        final Spec.PhoneNumber phoneNumber1 = createSpecPhoneNumber();
        final Spec.PhoneNumber phoneNumber2 = createSpecPhoneNumber();
        final String name = randomAlphabetic(25);
        final String email = randomAlphanumeric(20);
        final int id = nextInt(0, Integer.MAX_VALUE);
        return Spec.Person.newBuilder()
                .setEmail(email)
                .setName(name)
                .setId(id)
                .addPhone(phoneNumber1)
                .addPhone(phoneNumber2)
                .build();
    }

    private static Spec.PhoneNumber createSpecPhoneNumber() {
        final String phoneNumberString = randomNumeric(10);
        final Spec.PhoneType phoneType = Spec.PhoneType.values()[nextInt(0, Spec.PhoneType.values().length)];
        return Spec.PhoneNumber.newBuilder()
                .setNumber(phoneNumberString)
                .setType(phoneType)
                .build();
    }
}

package com.codebreeze.serialization;

import com.codebreeze.proto.simple.model.Spec;

import java.util.function.Consumer;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class ProtobuffExperiment {
    private static final Consumer<Spec.AddressBook> ADDRESS_BOOK_SINK_HOLE = addressBook -> System.out.println(addressBook);
    private static final Consumer<byte[]> BYTE_ARRAY_SINK_HOLE = addressBookByteArray -> {};

    public static final void main(final String...args){
        final Spec.AddressBook addressBook = createSpecAddressBook();
        BYTE_ARRAY_SINK_HOLE.accept(addressBook.toByteArray());
        ADDRESS_BOOK_SINK_HOLE.accept(addressBook);
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

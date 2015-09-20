package com.codebreeze.serialization.json.model;

import com.codebreeze.xml.simple.model.AddressBook;
import com.codebreeze.xml.simple.model.Person;
import com.codebreeze.xml.simple.model.PhoneNumber;
import com.codebreeze.xml.simple.model.PhoneType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.function.Consumer;

import static com.google.common.base.Throwables.propagate;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class XmlExperiment {
    private static final Consumer<AddressBook> ADDRESS_BOOK_SINK_HOLE = addressBook -> System.out.println(addressBook);
    private static final Consumer<byte[]> BYTE_ARRAY_SINK_HOLE = addressBookByteArray -> {
    };
    private static final Marshaller MARSHALLER = marshaller();

    public static final void main(final String... args) throws JAXBException {
        final AddressBook addressBook = createXmlAddressBook();
        final StringWriter writer = new StringWriter();
        MARSHALLER.marshal(addressBook, writer);
        BYTE_ARRAY_SINK_HOLE.accept(writer.toString().getBytes());
        ADDRESS_BOOK_SINK_HOLE.accept(addressBook);
    }

    private static AddressBook createXmlAddressBook() {
        final String description = randomAlphanumeric(300);
        final Person person1 = createXmlPerson();
        final Person person2 = createXmlPerson();
        final Person person3 = createXmlPerson();
        return new AddressBook(description, asList(person1, person2, person3));
    }

    private static Person createXmlPerson() {
        final PhoneNumber phoneNumber1 = createXmlPhoneNumber();
        final PhoneNumber phoneNumber2 = createXmlPhoneNumber();
        final String name = randomAlphabetic(25);
        final String email = randomAlphanumeric(20);
        final int id = nextInt(0, Integer.MAX_VALUE);
        return new Person(name, id, email, asList(phoneNumber1, phoneNumber2));
    }

    private static PhoneNumber createXmlPhoneNumber() {
        final String phoneNumberString = randomNumeric(10);
        final PhoneType phoneType = PhoneType.values()[nextInt(0, Json.PhoneType.values().length)];
        return new PhoneNumber(phoneNumberString, phoneType);
    }

    public byte[] convertToXml(Object source, Class... type) {
        try {
            final JAXBContext context = JAXBContext.newInstance("com.codebreeze.xml.simple.model");
            final Marshaller marshaller = context.createMarshaller();
            final StringWriter sw = new StringWriter();
            marshaller.marshal(source, sw);
            return sw.toString().getBytes();
        } catch (JAXBException e) {
            throw propagate(e);
        }
    }

    private static Marshaller marshaller() {
        try {
            return JAXBContext
                    .newInstance("com.codebreeze.xml.simple.model")
                    .createMarshaller();
        } catch (JAXBException e) {
            throw propagate(e);
        }
    }
}

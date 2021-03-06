package com.codebreeze.serialization;

import com.codebreeze.serialization.json.model.Json;
import com.codebreeze.xml.simple.model.AddressBook;
import com.codebreeze.xml.simple.model.Person;
import com.codebreeze.xml.simple.model.PhoneNumber;
import com.codebreeze.xml.simple.model.PhoneType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import static com.codebreeze.serialization.Experiments.BYTE_ARRAY_SINK_HOLE;
import static com.codebreeze.serialization.Experiments.OBJECT_SINK_HOLE;
import static com.google.common.base.Throwables.propagate;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class XmlExperiment {
    private static final Marshaller MARSHALLER = marshaller();
    private static final Unmarshaller UNMARSHALLER = unmarshaller();

    public static final void main(final String... args) throws JAXBException {
        final AddressBook addressBook = randomAddressBook();
        BYTE_ARRAY_SINK_HOLE.accept(createByteArray(addressBook));
        OBJECT_SINK_HOLE.accept(createAddressBook(createByteArray(addressBook)));
    }

    public static AddressBook randomAddressBook(){
        return createXmlAddressBook();
    }

    public static AddressBook createAddressBook(final byte[] data) throws JAXBException {
        return AddressBook.class.cast(UNMARSHALLER.unmarshal(new ByteArrayInputStream(data)));
    }

    public static byte[] createByteArray(final AddressBook addressBook) throws JAXBException {
        final StringWriter writer = new StringWriter();
        MARSHALLER.marshal(addressBook, writer);
        return writer.toString().getBytes();
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

    private static Marshaller marshaller() {
        try {
            return JAXBContext
                    .newInstance("com.codebreeze.xml.simple.model")
                    .createMarshaller();
        } catch (JAXBException e) {
            throw propagate(e);
        }
    }

    private static Unmarshaller unmarshaller() {
        try {
            return JAXBContext
                    .newInstance("com.codebreeze.xml.simple.model")
                    .createUnmarshaller();
        } catch (JAXBException e) {
            throw propagate(e);
        }
    }
}

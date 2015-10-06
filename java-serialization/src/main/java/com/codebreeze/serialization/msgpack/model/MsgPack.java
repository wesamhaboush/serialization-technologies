package com.codebreeze.serialization.msgpack.model;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.msgpack.annotation.Message;

import java.util.List;

public class MsgPack {

    public static final RecursiveToStringStyle STYLE = new RecursiveToStringStyle();

    @Message
    public static class AddressBook {
        public final String description;
        public final List<Person> person;

        public AddressBook(){
            description = null;
            person = null;
        }

        public AddressBook(final String description, final List<Person> person) {
            this.description = description;
            this.person = person;
        }

        @Override
        public String toString(){
            return new ReflectionToStringBuilder(this, STYLE).toString();
        }
    }

    @Message
    public static class Person {
        public final String name;
        public final int id;
        public final String email;
        public final List<PhoneNumber> phoneNumber;

        public Person() {
            this.id = 0;
            this.name = null;
            this.email = null;
            this.phoneNumber = null;
        }

        public Person(final String name, final int id, final String email, final List<PhoneNumber> phoneNumber) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }
    }

    @Message
    public static class PhoneNumber {
        public final String number;
        public final PhoneType phoneType;

        public PhoneNumber() {
            this.number = null;
            this.phoneType = null;
        }

        public PhoneNumber(final String number, final PhoneType phoneType) {
            this.number = number;
            this.phoneType = phoneType;
        }
    }

    public enum PhoneType {
        MOBILE,
        HOME,
        WORK;
    };
}

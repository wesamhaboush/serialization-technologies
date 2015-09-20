package com.codebreeze.serialization.json.model;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

public class Json {

    public static final RecursiveToStringStyle STYLE = new RecursiveToStringStyle();

    public static class AddressBook {
        private final String description;
        private final List<Person> person;

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

    public static class Person {
        private final String name;
        private final int id;
        private final String email;
        private final List<PhoneNumber> phoneNumber;

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

    public static class PhoneNumber {
        private final String number;
        private final PhoneType phoneType;

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

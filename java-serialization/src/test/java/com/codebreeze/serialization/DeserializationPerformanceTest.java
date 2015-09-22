package com.codebreeze.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.apache.commons.lang3.BooleanUtils.toBoolean;

@Measurement(iterations = 5, time = 1)
@Warmup(iterations = 5, time = 1)
@Fork(3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class DeserializationPerformanceTest {
    private static final Consumer<Object> CONSUMER =
            consumer();

    private static Consumer<Object> consumer() {
        return toBoolean(System.getProperty("performance", "false"))
                ? (s -> {})
                : (s -> System.out.println(s));
    }

    byte[] xmlAddressBookBytes;
    byte[] protobuffAddressBookBytes;
    byte[] jsonAddressBookBytes;


    @Setup
    public void setup() throws JAXBException, JsonProcessingException {
        xmlAddressBookBytes = XmlExperiment.createByteArray(XmlExperiment.randomAddressBook());
        protobuffAddressBookBytes = ProtobuffExperiment.createByteArray(ProtobuffExperiment.randomAddressBook());
        jsonAddressBookBytes = JsonExperiment.createByteArray(JsonExperiment.randomAddressBook());
    }

    @Benchmark
    public void xml() throws JAXBException {
        CONSUMER.accept(XmlExperiment.createAddressBook(xmlAddressBookBytes));
    }

    @Benchmark
    public void proto() throws InvalidProtocolBufferException {
        CONSUMER.accept(ProtobuffExperiment.createAddressBook(protobuffAddressBookBytes));
    }

    @Benchmark
    public void json() throws IOException {
        CONSUMER.accept(JsonExperiment.createAddressBook(jsonAddressBookBytes));
    }
}
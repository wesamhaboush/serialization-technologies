package com.codebreeze.serialization;

import com.codebreeze.proto.simple.model.Spec;
import com.codebreeze.serialization.json.model.Json;
import com.codebreeze.xml.simple.model.AddressBook;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.openjdk.jmh.annotations.*;

import javax.xml.bind.JAXBException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.apache.commons.lang3.BooleanUtils.toBoolean;

@Measurement(iterations = 5, time = 1)
@Warmup(iterations = 5, time = 1)
@Fork(3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class SerializationPerformanceTest {
    private static final Consumer<byte[]> CONSUMER =
            consumer();

    private static Consumer<byte[]> consumer() {
        return toBoolean(System.getProperty("performance", "false"))
                ? (s -> {})
                : (s -> System.out.println(String.valueOf(s)));
    }

    AddressBook xmlAddressBook;
    Spec.AddressBook protobuffAddressBook;
    Json.AddressBook jsonAddressBook;


    @Setup
    public void setup() {
        xmlAddressBook = XmlExperiment.randomAddressBook();
        protobuffAddressBook = ProtobuffExperiment.randomAddressBook();
        jsonAddressBook = JsonExperiment.randomAddressBook();
    }

    @Benchmark
    public void xml() throws JAXBException {
        CONSUMER.accept(XmlExperiment.createByteArray(xmlAddressBook));
    }

    @Benchmark
    public void proto() {
        CONSUMER.accept(ProtobuffExperiment.createByteArray(protobuffAddressBook));
    }

    @Benchmark
    public void json() throws JsonProcessingException {
        CONSUMER.accept(JsonExperiment.createByteArray(jsonAddressBook));
    }
}
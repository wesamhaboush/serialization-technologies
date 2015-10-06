package com.codebreeze.serialization;

import java.util.function.Consumer;

public class Experiments {
    public static final Consumer<Object> OBJECT_SINK_HOLE = System.out::println;
    public static final Consumer<byte[]> BYTE_ARRAY_SINK_HOLE = byteArray -> System.out.println(byteArray.length);
}

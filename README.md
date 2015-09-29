# Comparing serialization and Deserialization Technologies

# For node:

    Name                      Operations per second    Average time, ms
    json serialization        11                       90                  ==========>
    xml serialization         3                        320                 ===>
    protobuf serialization    3                        374                 ==>

    Name                        Operations per second    Average time, ms
    protobuf deserialization    15                       66                  ==========>
    json deserialization        8                        132                 =====>
    xml deserialization         0.81                     1.2 x 10^3          =>


# For java

    Benchmark                              Mode  Cnt        Score       Error  Units
    DeserializationPerformanceTest.json   thrpt   15   223419.395 ± 22160.646  ops/s
    DeserializationPerformanceTest.proto  thrpt   15   717882.827 ± 30588.006  ops/s
    DeserializationPerformanceTest.xml    thrpt   15    27943.346 ±   447.763  ops/s
    SerializationPerformanceTest.json     thrpt   15   327297.128 ±  3859.165  ops/s
    SerializationPerformanceTest.proto    thrpt   15  1743841.348 ± 59085.015  ops/s
    SerializationPerformanceTest.xml      thrpt   15    58688.283 ±  1149.141  ops/s

# To Do: 

* Test message pack for java (for node, it is reportedly dismal, and so there is hardly any point).

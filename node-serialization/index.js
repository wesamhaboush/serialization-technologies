// node.js 
var microBenchmark = require('micro-benchmark');
var json = require('./json.js');
var xml = require('./xml.js');
var protobuf = require('./protobuffer.js');

var jsonObject = json.randomAddressBook();
var jsonBinary = json.toString(jsonObject);
var xmlObject = xml.randomAddressBook();
var xmlBinary = xml.toString(xmlObject);
var protobufObject = protobuf.randomAddressBook();
var protobufBinary = protobuf.toString(protobufObject);

var testObject = {
 jsonObject: jsonObject,
 jsonBinary: jsonBinary,
 xmlObject: xmlObject,
 xmlBinary: xmlBinary,
 protobufObject: protobufObject,
 protobufBinary: protobufBinary
}
console.log("test result:");
console.log(JSON.stringify(testObject, null, "  "));

function consume(result){
//   console.log(JSON.stringify(result, null, "   "));
}
 
// node.js & browser 
var N = 1e4;
//var N = 2;
var result1 = microBenchmark.suite({
    duration: 100, // optional 
    maxOperations: 1000, // optional 
    specs: [
    {
        name: 'json serialization',
        fn: function () {
            for (var i = 0; i < N; ++i) consume(json.toString(jsonObject));
        }
    }, {
        name: 'xml serialization',
        fn: function () {
            for (var i = 0; i < N; ++i) consume(xml.toString(xmlObject));
        }
    }, {
        name: 'protobuf serialization',
        fn: function () {
            for (var i = 0; i < N; ++i) consume(protobuf.toString(protobufObject));
        }
    }
    ]
});
 
var report1 = microBenchmark.report(result1, { chartWidth: 10 /* 30 is default */ });
console.log(report1);

var result2 = microBenchmark.suite({
    duration: 100, // optional 
    maxOperations: 1000, // optional 
    specs: [{
        name: 'json deserialization',
        fn: function () {
            for (var i = 0; i < N; ++i) consume(json.toAddressBook(jsonBinary));
        }
    }, {
        name: 'xml deserialization',
        fn: function () {
            for (var i = 0; i < N; ++i) consume(xml.toAddressBook(xmlBinary));
        }
    }, {
        name: 'protobuf deserialization',
        fn: function () {
            for (var i = 0; i < N; ++i) consume(protobuf.toAddressBook(protobufBinary)); 
        }
    }]  
});
 
var report2 = microBenchmark.report(result2, { chartWidth: 10 /* 30 is default */ }); 
console.log(report2);

 

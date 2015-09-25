// node.js 
var microBenchmark = require('micro-benchmark');
var json = require('json.js');
var xml = require('xml.js');
var protobuf = require('protobuffer.js');
 
// node.js & browser 
var N = 1e4;
var result1 = microBenchmark.suite({
    duration: 100, // optional 
    maxOperations: 1000, // optional 
    specs: [{
        name: 'json serialization',
        fn: function () {
            for (var i = 0; i < N; ++i) Math.sin(i);
        }
    }, {
        name: 'xml serialization',
        fn: function () {
            for (var i = 0; i < N; ++i) Math.sqrt(i);
        }
    }, {
        name: 'protobuf serialization',
        fn: function () {
            for (var i = 0; i < N; ++i) Math.pow(i, 2);
        }
    }]
});
 
var report = microBenchmark.report(result1, { chartWidth: 10 /* 30 is default */ });
console.log(report);

var result2 = microBenchmark.suite({
    duration: 100, // optional 
    maxOperations: 1000, // optional 
    specs: [{
        name: 'json deserialization',
        fn: function () {
            for (var i = 0; i < N; ++i) Math.sin(i);
        }
    }, {
        name: 'xml deserialization',
        fn: function () {
            for (var i = 0; i < N; ++i) Math.sqrt(i);
        }
    }, {
        name: 'protobuf deserialization',
        fn: function () {
            for (var i = 0; i < N; ++i) Math.pow(i, 2); 
        }
    }]  
});
 
var report = microBenchmark.report(result1, { chartWidth: 10 /* 30 is default */ }); 

 

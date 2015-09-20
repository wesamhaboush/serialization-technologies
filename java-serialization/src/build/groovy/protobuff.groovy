def targetGenDirName = "${project.basedir}/target/generated-proto"

println "creating dir if does not exist: $targetGenDirName"

def targetGenDir = new File(targetGenDirName as String)
if (!targetGenDir.exists()) {
    def dirCreated = targetGenDir.mkdirs()
    if (!dirCreated) {
        throw new RuntimeException("could not create dir $targetGenDirName")
    }
}

println 'executing protocol buffer code generation ...'

def javaOut = "${project.basedir}/target/generated-proto"
def protopath = "${project.basedir}/src/main/resources/proto"
def protofile = "${project.basedir}/src/main/resources/proto/spec.proto"

def cmd = "protoc --java_out=$javaOut --proto_path=$protopath $protofile"
println cmd
def sout = new StringBuffer()
def serr = new StringBuffer()
def proc = cmd.execute()
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(1000)

if (!"$sout".trim().isEmpty()) {
    println "sout> $sout"
}

if (!"$serr".trim().isEmpty()) {
    println "serr> $serr"
    throw new RuntimeException("some error happened generating protobuff class, with message: $serr")
}


package com.jvm.me;

public class JvmTest1 {

    /**
     * /usr/local/java/bin/java -Dsun.misc.URLClassPath.disableJarChecking=true
     * -Xms2304m -Xmx2304m
     * -javaagent:/opt/cat-agent-1.0.10.jar -Dapplication=array -DcatDomain=cat.xxx.com.cn
     * -javaagent:/opt/jmx_agent.jar=18080:-
     * -XX:NewRatio=2 -XX:G1HeapRegionSize=8m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:MaxTenuringThreshold=10
     * -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=45 -XX:MaxGCPauseMillis=200
     * -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintReferenceGC -XX:+PrintAdaptiveSizePolicy -XX:+UseGCLogFileRotation
     * -XX:NumberOfGCLogFiles=6 -XX:GCLogFileSize=32m -Xloggc:./var/run/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./var/run/jvm_oom_error.hprof -Dfile.encoding=UTF-8
     * -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.ssl=false
     * -Dcom.sun.management.jmxremote.authenticate=false -jar /opt/application.jar
     *
     * -XX:+TraceClassLoading -XX:+TraceClassUnloading
     * @param args
     */
    static final int SIZE = 2 * 1024 * 1024;

    /**
     * -Xms12m -Xmx12m -XX:NumberOfGCLogFiles=6 -XX:GCLogFileSize=32m -Xloggc:./gc.log.202103232138 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./jvm_oom_error.hprof -Dfile.encoding=UTF-8
     */
    public static void main(String[] args) {
        int[] i = new int[SIZE];
    }
}

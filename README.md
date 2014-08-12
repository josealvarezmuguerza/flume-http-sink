Flume  Http Sink
===============

Flume-ng Http post sink.
This is another Flume Sink, it takes events form the Flume-ng channel and send them via REST POST calls. This sink aims to enable queue messages to overpass the company's firewalls and the internet itself. On the other side another Flume agent (via Http Source) could be listening the incoming events or simply a web server. 


![image](https://github.com/josealvarezmuguerza/flume-http-sink/raw/master/image.png)


Sink Configuration
===============
In flume-conf.properties:
```java

# Http Post Sink - custom
###############################
myagent-sender.sinks.http-sink.type = org.apache.flume.sink.HttpSink
myagent-sender.sinks.http-sink.channel = channel_1
myagent-sender.sinks.http-sink.endpoint = http://my-webservice-in-a-web-server.com
myagent-sender.sinks.http-sink.port = 8080
```

Installation
===============

```java
git clone git@github.com:josealvarezmuguerza/flume-http-sink.git
cd flume-http-sink
mvn clean install
cp target/flume-http-sink-{version}.jar {flume_home}/lib/flume-http-sink-{version}.jar
```

####Maven Dependency Tree
```java
------------------------------------------------------------------------
Building flume-http-sink 0.0.1-SNAPSHOT
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ flume-http-sink ---
org.apache.flume.sink:flume-http-sink:jar:0.0.1-SNAPSHOT
\- org.apache.flume:flume-ng-core:jar:1.5.0.1:compile
   +- org.apache.flume:flume-ng-sdk:jar:1.5.0.1:compile
   +- org.apache.flume:flume-ng-configuration:jar:1.5.0.1:compile
   +- org.slf4j:slf4j-api:jar:1.6.1:compile
   +- com.google.guava:guava:jar:11.0.2:compile
   |  \- com.google.code.findbugs:jsr305:jar:1.3.9:compile
   +- commons-io:commons-io:jar:2.1:compile
   +- commons-codec:commons-codec:jar:1.8:compile
   +- log4j:log4j:jar:1.2.17:compile
   +- org.slf4j:slf4j-log4j12:jar:1.6.1:compile
   +- commons-cli:commons-cli:jar:1.2:compile
   +- commons-lang:commons-lang:jar:2.5:compile
   +- org.apache.avro:avro:jar:1.7.3:compile
   |  +- org.codehaus.jackson:jackson-core-asl:jar:1.8.8:compile
   |  +- org.codehaus.jackson:jackson-mapper-asl:jar:1.8.8:compile
   |  +- com.thoughtworks.paranamer:paranamer:jar:2.3:compile
   |  \- org.xerial.snappy:snappy-java:jar:1.0.4.1:compile
   +- org.apache.avro:avro-ipc:jar:1.7.3:compile
   |  \- org.apache.velocity:velocity:jar:1.7:compile
   |     \- commons-collections:commons-collections:jar:3.2.1:compile
   +- io.netty:netty:jar:3.5.12.Final:compile
   +- joda-time:joda-time:jar:2.1:compile
   +- org.mortbay.jetty:servlet-api:jar:2.5-20110124:compile
   +- org.mortbay.jetty:jetty-util:jar:6.1.26:compile
   +- org.mortbay.jetty:jetty:jar:6.1.26:compile
   +- com.google.code.gson:gson:jar:2.2.2:compile
   +- org.apache.thrift:libthrift:jar:0.7.0:compile
   |  \- org.apache.httpcomponents:httpclient:jar:4.0.1:compile
   |     +- org.apache.httpcomponents:httpcore:jar:4.0.1:compile
   |     \- commons-logging:commons-logging:jar:1.1.1:compile
   \- org.apache.mina:mina-core:jar:2.0.4:compile
```



License
=======
Apache License, Version 2.0
http://www.apache.org/licenses/LICENSE-2.0


# Introduction
${pbaInfo.pba.name} this is base streaming template project generated from the AIA Portal.

# Prerequisites:

- Maven 3.0.0+
- Java 1.8
- Docker
- Vagrant

# Installation:

If using Vagrant as standalone instead of Docker set the below parameter
```
export schemaRegistry.address=http://IP:8081
```
## Authenticating with Private Registries(Maven setting for Docker):

To push to a private Docker image registry that requires authentication, you can put your
credentials in your Maven's global `settings.xml` as below.
```
<server>
	<id>armdocker.rnd.ericsson.se</id>
	<username>id</username>
	<password>Password</password>
	<configuration>
		<email>email@ericsson.com</email>
	</configuration>
</server>
```
#### Docker Setting parameters for Window:
* export DOCKER_CERT_PATH='PATH'
* export DOCKER_HOST=tcp://IP:2376
* export DOCKER_MACHINE_NAME=default
* export DOCKER_TLS_VERIFY=1
* export DOCKER_TOOLBOX_INSTALL_PATH='PATH'


## Using Docker config file for authentication

Another option to authenticate with private repositories is using dockers ~/.docker/config.json.
This makes it also possible to use in cooperation with cloud providers like AWS or Google Cloud which store the user's
credentials in this file, too. **<useConfigFile>true</useConfigFile>**

    <plugin>
      <plugin>
        <groupId>com.spotify</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>VERSION GOES HERE</version>
        <configuration>
          [...]
          <useConfigFile>true</useConfigFile>
        </configuration>
      </plugin>
    </plugins>

**Hint:** The build will fail, if the config file doesn't exist.

## Quick Start:

List of Extension points -- input adapters
| EP Adapters  	|   Support	|   Usuage		|
|---			|---		|---			|
|  Active MQ 	|   NA		|  	amq 		|
|  DRILL 		|   NA		|   drill		|
|  FILE System 	|   Beta	|   file		|
|   HIVE		|   NA		|   hive		|
|   Spark Sql	|   NA		|   spark-sql	|
|   Spark Batch	|   NA		|   spark-batch	|
|   Zero MQ		|   NA		|   zmq			|
|   HDFS		|   Beta	|   hdfs		|
|   KAFKA		|   Beta	|   kafka		|
|Spark Streaming|   Beta	|   spark-streaming	|
|   Alluxio		|   Beta	|   alluxio		|


List of Extension points -- output adapters
| EP Adapters  	|   Support	|   Usuage		|
|---			|---		|---			|
|  Active MQ 	|   NA		|  	amq 		|
|  DRILL 		|   NA		|   drill		|
|  FILE System 	|   Beta	|   file		|
|   HIVE		|   Beta	|   hive		|
|   Spark Sql	|   NA		|   spark-sql	|
|   Spark Batch	|   NA		|   spark-batch	|
|   Zero MQ		|   NA		|   zmq			|
|   HDFS		|   Beta	|   hdfs		|
|   KAFKA		|   NA		|   kafka		|
|Spark Streaming|   Beta	|   spark-streaming	|
|   Alluxio		|   Beta	|   alluxio		|

### Flow.xml

Kafka Input Attributes Usage:
:   **radio-stream** stream to be accessed in code
:   **kafka://** repressnts Kafka Input Adapter
:   **radio_up** represents Kafka Topic name
:   **win.length** represents window interval
:   **slide.win.length** represents sliding window interval
:   **group.id** represents Kafka Topic group id

As mentioned below:
```
<input name="radio-stream">
	<attribute name="uri" value="kafka://radio_up"/>
	<attribute name ="metadata.broker.list" value="localhost:9092(kakfa server url)"/>
	<attribute name="win.length" value="300000"/>
	<attribute name="slide.win.length" value="300000"/>	
	<attribute name="group.id" value="radio_session"/>
</input>
```

Spark Streaming Attributes Usage:
:   **ups-spark-streaming** represents Spark streaming
:   **master.url** represents Spark master
:   **driver-class** represents Spark driver class

Optional Attributes Usage:
:   **spark.externalBlockStore.url** represents Spark external blockstore url
:   **spark.externalBlockStore.baseDir** represents Spark external blockstore uri
:   **streaming.checkpoint** represents Spark streaming checkpoint url

As mentioned below:
```
<step name="ups-spark-streaming">
	<attribute name="uri" value="spark-streaming://${metaInfo.pbaNameInCamelCase}"/>
	<attribute name="master.url" value="spark://localhost:7077"/>
	<attribute name="spark.externalBlockStore.url" value="alluxio://localhost:19998"/>
	<attribute name="spark.externalBlockStore.baseDir" value="alluxio://localhost:19998/staging/ups/"/>
	<attribute name="streaming.checkpoint" value="alluxio://localhost:19998/ups-checkpoint/kafka"/>
	<attribute name="driver-class" value="com.ericsson.aia.bps.streaming.correlations.${metaInfo.pbaNameInCamelCase}" />
</step>
```

Alluxio Output Attributes Usage:
:   **alluxio-store** represents Alluxio output adapter
:   **RADIO_USER_SESSION** represents Alluxio output directory
:   **master-url** represents Alluxio server url

As mentioned below:

```
<output name="alluxio-store">
	<attribute name="uri" value="alluxio://RADIO_USER_SESSION"/>
	<attribute name="master-url" value="localhost:19998"/>        
</output>
```

### This section(<Path>) signifies flow:

#### Default flow is like this:
1. Kafka producers will be publishing messages to Kafka server(Default:radio_up )
2. Spark streaming application will be streaming Kafka topic(Default:radio_up )
3. Spark Streaming application writes outputs to Alluxio server periodically for every 30 minutes(Default: ``<attribute name="win.length" value="300000"/>``) 

```
<path>
	<from uri="radio-stream" />
	<to uri="spark-streaming" />
	<to uri="alluxio-store" />
</path>
```
### Build locally

You can build an image with the above configurations by running this command.
Run this Docker Pull before running maven build
```
docker pull armdocker.rnd.ericsson.se/aia/base/spark_base_1.6.0
```
```bash
    mvn clean package docker:build
```

### To run Docker image locally (ctrl+c to stop)

```bash
    docker run --net host -e "schemaRegistry.address=http://IP:8081" -i armdocker.rnd.ericsson.se/aia/aia-${pbaInfo.pba.name}
```

### To Publish to Docker repository
Simple push
```bash
mvn docker:push
```
Tags-to-be-pushed can also be specified directly on the command line with
```bash
    mvn clean package docker:build -DpushImageTags -DdockerImageTag=latest -DdockerImageTag=another-tag
```

To push the image you just built to the registry, specify the `pushImage` flag.
```bash
    mvn clean package docker:build -DpushImage
```

To push only specific tags of the image to the registry, specify the `pushImageTag` flag.

```bash
    mvn clean package docker:build -DpushImageTag
```


### Publish back to app manager
* After publish, you may find your application listed at:
* $appBaseUrl/#aia/ecosystem

```bash
./bin/publish_app.sh
```


### Unpublish from app manager
```bash
./bin/unpublish_app.sh
```


## Additional Information:

BPS service consists of three module:
* Core
* Adapters
* Engine

Engine uses--> Adapters uses--> Core
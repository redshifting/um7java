# UM7 Java Adapter

This library provides an interface to interact with the UM7 device using a serial port.
It provides both high-level operations to execute UM7 commands and read data along with a 
low-level communication interface.



## Build
This section describes a build process.

### Required software
The library is packaged using the Maven tool. Therefore, the following tools are required to 
build it:

* JDK 1.8 and above
* Maven 3

### Environment preparation

To build the library the following environment variables shoudl be set:
* JAVA_HOME - should point to the location of the JDK
* MAVEN_HOME - should point to the location of the Maven distribution

### Compiling the code
Once required steps are performed, the library can be built using 
the following commands

```
cd <location-of-the-sources>
mvn clean package
```

Once the code is compiled, a JAR file will be placed to <location-of-the-sources>/target

### Skipping tests

Tests have an assumption that the device is connected to the COM3 port.
In a case if it's not correct on the build host, tests can be skipped
to still get a compiled JAR file:

```
mvn clean package -Dmaven.skip.test=true
```

## Test

To run tests the following command can be used:
```
mvn clean test
```

## Local Maven repo installation


The library can be installed to the local Maven repository to be used by other Maven artifacts using the following command:
```
mvn clean install
```
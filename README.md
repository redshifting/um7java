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

To build the library the following environment variables should be set:

* *JAVA_HOME* - should point to the location of the JDK
* *MAVEN_HOME* - should point to the location of the Maven distribution

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

### Test

To run tests the following command can be used:
```
mvn clean test
```

### Local Maven repo installation


The library can be installed to the local Maven repository to be used by other Maven artifacts using the following command:
```
mvn clean install
```

 
## Library usage

### Java
Add **um7j-adapter.jar** to the classpath. Import and use it:

```
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.impl.DefaultUM7;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

// ...


// Create an instance of the DefaultUM7Client to establish the connection with the device:
final UM7Client um7Client = new DefaultUM7Client("UM7", "COM3");
// Create an UM7 instance to perform high-level commands
final UM7 um7 = new DefaultUM7(um7Client, new String[0]);

// Reset the device
um7.zeroGyros()

// Read current data
final UM7DataSample sample = um7.readState();


// Disconnect from the device to free the port
um7Client.disconnect();

```


### Processing

To use the library from the Processing, it should be added to the sketch first. The simplest way to do it is to drag-n-drop the library
to the Editor window.


Here is an example of the simple Processing script that uses the library and displays a message box with the firmware version:

```
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.impl.DefaultUM7;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;
try{
// Create an instance of the DefaultUM7Client to establish the connection with the device:
final UM7Client um7Client = new DefaultUM7Client("UM7", "COM3");
// Create an UM7 instance to perform high-level commands
final UM7 um7 = new DefaultUM7(um7Client, new String[0]);

// Reset the device
um7.zeroGyros();

// Read current data
final UM7DataSample sample = um7.readState();
javax.swing.JOptionPane.showMessageDialog(null, "Firmware version: " + um7.getFirmwareVersion());

// Disconnect from the device to free the port
um7Client.disconnect();

} catch (final Exception e){
  System.out.println("Error");
}
```

### Data rates and data callbacks

UM7 device provides two modes of data transmission:
1. Binary
2. NMEA
According to these modes device can transmit binary and NMEA packets.
To catch samples you should do 2 things:
1. Configure data rate of packet
2. Set listener callback

## Examples

Example for configuring NMEA Health Packet
```
    //set frequency of NMEA.Health packet to 1 Hz
    client.setDataRate(UM7Attributes.NMEA.Health, UM7Attributes.Frequency.NMEA.Freq1_HZ);

    client.registerCallback(UM7Attributes.NMEA.Health, new DataCallback() {
      @Override
      public void onPacket(UM7Packet packet) {
        if ((boolean)packet.getAttributes().get(UM7Attributes.NMEA.Health.ComOverflow) == true) {
          System.out.println("Com overflow");
        } else {
          System.out.println("Com ok");
        }
      }
    });
```

Example for configuring Binary Temperature packet
```
    //set frequency of Temperature packet to 1 Hz
    client.setDataRate(UM7Attributes.Temperature, 1);

    client.registerCallback(UM7Attributes.UM7Attributes.Temperature, new DataCallback() {
      @Override
      public void onPacket(UM7Packet packet) {
        String temp = (String)packet.getAttributes().get(UM7Attributes.Temperature.Value);
        String time = (String)packet.getAttributes().get(UM7Attributes.Temperature.Time);

        System.out.println(String.format("Current temperature is [%s] at [%s]", temp, time));
      }
    });
```


## Frequency values for `setDataRate` method
Different packets can accept different frequency values
1. if packet is UM7Attributes.Health, rate param should be one of UM7Attributes.Frequency.HealthRate.*
Example:
```
//enable binary Health packet at 1 Hz rate
client.setDataRate(UM7Attributes.Health, UM7Attributes.Frequency.HealthRate.Freq1_HZ);

//disable binary Health packet
client.setDataRate(UM7Attributes.Health, UM7Attributes.Frequency.HealthRate.FreqOFF);
```

2. Fon all NMEA packets (UM7Attributes.NMEA.*) `rate` should be one of UM7Attributes.Frequency.NMEA.*
```
//enable NMEA Health packet at 1 Hz rate
client.setDataRate(UM7Attributes.NMEA.Health, UM7Attributes.Frequency.NMEA.Freq1_HZ);

//disable NMEA Health packet
client.setDataRate(UM7Attributes.NMEA.Health, UM7Attributes.Frequency.NMEA.FreqOFF);
```

3. For GPS `UM7Attributes.Gps` and GSP Satelite Details `UM7Attributes.GpsSateliteDetails` packet rate should be one of
`UM7Attributes.Frequency.Gps.*` (only 2 values)

```
//enable GPS packet
client.setDataRate(UM7Attributes.Gps, UM7Attributes.Frequency.Gps.On);

//disable GPS packet
client.setDataRate(UM7Attributes.Gps, UM7Attributes.Frequency.Gps.Off);
```

4. For all other attributes rate is integer from 0 to 255 that defines frequency in Hz

```
//enable Temperature packet at 123 Hz rate
client.setDataRate(UM7Attributes.Temperature, 123);

//disable Temperature packet
client.setDataRate(UM7Attributes.Temperature, 0);
```
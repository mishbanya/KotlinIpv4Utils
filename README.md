# KotlinIpv4Utils

**Author**: @mishbanya (Degtyarev M. S.) 
**Project Description**:  
This project provides utilities for working with IPv4 addresses and subnets. It includes classes and methods for validating, converting, and comparing IPv4 addresses and subnet masks in different formats. The project also provides helper functions for calculating subnet borders and working with IP ranges.

## Classes and Objects Overview

### `Ipv4Classes`
Contains classes representing IPv4 addresses and subnet masks in different formats:
- `DecimalIp`: Represents an IPv4 address in decimal format (e.g., "192.168.0.1").
- `DecimalMask`: Represents a subnet mask in decimal format (e.g., "255.255.255.0").
- `BinaryIp`: Represents an IPv4 address in binary format (e.g., "11000000.10101000.00000000.00000001").
- `BinaryMask`: Represents a subnet mask in binary format.
- `ByteIp`: Represents an IPv4 address as a byte array.
- `ByteMask`: Represents a subnet mask as a byte array.

### `Ipv4Comparer`
An object providing methods for comparing IPv4 addresses and subnets:
- Compares two IPs for equality.
- Checks if an IP is within a specified interval.

### `Ipv4Converter`
An object providing methods for converting between different IPv4 formats:
- Converts between decimal, binary, and byte array representations of IP addresses.
- Converts between decimal, binary, byte and prefixLength representations of subnet masks.

### `Ipv4Subnet`
A class representing an IPv4 subnet with methods to:
- Get the minimal and maximal IP addresses in the subnet.
- Check if a given IP address is contained within the subnet.

### `Ipv4Validator`
An object for validating IPv4 addresses and subnet masks in different formats:
- Validates decimal IPs, binary IPs, subnet masks.

### `RegexConstants`
An object holding regex patterns for validating IPv4 and subnet mask formats.

## Examples of Usage

### Example 1: Validating and Converting IP Addresses

```kotlin

fun main() {

    val ip = "192.168.0.1"

    // Convert decimal IP to binary
    val binaryIp = Ipv4Converter.convertDecimalToBinary(ip)
    println("Binary format: $binaryIp")

    // Convert binary IP back to decimal
    val decimalIp = Ipv4Converter.convertBinaryToDecimal(binaryIp)
    println("Decimal format: $decimalIp")
}

```

### Example 2: Library supports java.net.InterfaceAddress

```kotlin

fun main() {

    val ip = "192.168.0.1"
    val subnets : MutableList<Ipv4Subnet> = emptyList<Ipv4Subnet>().toMutableList()

    //get subnets from InterfaceAddresses
    for(netInt in NetworkInterface.getNetworkInterfaces()){
        for(address in netInt.interfaceAddresses.filter { it.address is Inet4Address }) {
            subnets.add(Ipv4Subnet(address))
        }
    }
    // Check if decimal ip in any subnet
    for(subnet in subnets){
        if(subnet.subnetContains(DecimalIp(ip)))
            println("YaHoo!")
    }

}

```

## Installation

### At settings.gradle.kts

```kotlin

includeBuild("KotlinIpv4Utils")

```

### At build.gradle.kts (:app)

```kotlin

dependencies {
    implementation("com.example.kotlinipv4utils:ipv4-utils:1.0.0")
}

```
package com.example.kotlinnetworkutils

import com.example.kotlinnetworkutils.Ipv4Converter.convertBinaryIpAddressToIntValue
import com.example.kotlinnetworkutils.Ipv4Converter.convertByteIpAddressToIntValue
import com.example.kotlinnetworkutils.Ipv4Converter.convertDecimalIpAddressToIntValue
import com.example.kotlinnetworkutils.Ipv4Validator.isBinaryIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isByteIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalIpLegal

/**
 * Utility object for comparing IPv4 addresses and subnet masks.
 *
 * Provides methods for:
 * - Comparing two IPv4 addresses to determine equality or ordering.
 * - Checking if an IPv4 address belongs beetween two others.
 */
object Ipv4Comparer {

    /**
     * Compares two IP addresses represented as byte arrays.
     *
     * @param firstByteIp the first IP address as a byte array of size 4
     * @param secondByteIp the second IP address as a byte array of size 4
     * @return `true` if the first IP address is greater than the second, `false` otherwise
     * @throws IllegalArgumentException if either IP address is not exactly 4 bytes
     */
    @Throws(IllegalArgumentException::class)
    fun compareByteIps(firstByteIp: ByteArray, secondByteIp: ByteArray): Boolean {

        isByteIpLegal(firstByteIp)
        isByteIpLegal(secondByteIp)

        val firstValue = convertByteIpAddressToIntValue(firstByteIp).toUInt()
        val secondValue = convertByteIpAddressToIntValue(secondByteIp).toUInt()

        return firstValue > secondValue
    }

    /**
     * Compares two decimal IP addresses represented as strings.
     *
     * @param firstDecimalIp the first IP address in decimal format (e.g., "192.168.0.1")
     * @param secondDecimalIp the second IP address in decimal format (e.g., "192.168.1.1")
     * @return `true` if the first IP address is greater than the second, `false` otherwise
     * @throws IllegalArgumentException if either IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun compareDecimalIps(firstDecimalIp: String, secondDecimalIp: String): Boolean {

        isDecimalIpLegal(firstDecimalIp)
        isDecimalIpLegal(secondDecimalIp)

        val firstValue = convertDecimalIpAddressToIntValue(firstDecimalIp).toUInt()
        val secondValue = convertDecimalIpAddressToIntValue(secondDecimalIp).toUInt()

        return firstValue > secondValue
    }

    /**
     * Compares two binary IP addresses represented as strings.
     *
     * @param firstBinaryIp the first IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @param secondBinaryIp the second IP address in binary format (e.g., "11000000.10101000.00000001.00000001")
     * @return `true` if the first IP address is greater than the second, `false` otherwise
     * @throws IllegalArgumentException if either IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun compareBinaryIps(firstBinaryIp: String, secondBinaryIp: String): Boolean {

        isBinaryIpLegal(firstBinaryIp)
        isBinaryIpLegal(secondBinaryIp)

        val firstValue = convertBinaryIpAddressToIntValue(firstBinaryIp).toUInt()
        val secondValue = convertBinaryIpAddressToIntValue(secondBinaryIp).toUInt()

        return firstValue > secondValue
    }

    /**
     * Checks if a byte IP address is between two other byte IP addresses.
     *
     * @param byteIp the IP address to check, in byte array format
     * @param startIp the starting IP address, in byte array format
     * @param endIp the ending IP address, in byte array format
     * @return `true` if the `ip` is between `startIp` and `endIp` (inclusive), `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses are invalid
     */
    @Throws(IllegalArgumentException::class)
    fun isByteIpInBetweenOtherTwo(byteIp: ByteArray, startIp: ByteArray, endIp: ByteArray): Boolean {

        isByteIpLegal(byteIp)
        isByteIpLegal(startIp)
        isByteIpLegal(endIp)

        val ipValue = convertByteIpAddressToIntValue(byteIp).toUInt()
        val startValue = convertByteIpAddressToIntValue(startIp).toUInt()
        val endValue = convertByteIpAddressToIntValue(endIp).toUInt()

        return ipValue in startValue..endValue
    }

    /**
     * Checks if a binary IP address is between two other binary IP addresses.
     *
     * @param binaryIp the IP address to check, in binary string format (e.g., "11000000.10101000.00000000.00000001")
     * @param startIp the starting IP address, in binary string format
     * @param endIp the ending IP address, in binary string format
     * @return `true` if the `ip` is between `startIp` and `endIp` (inclusive), `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses are invalid
     */
    @Throws(IllegalArgumentException::class)
    fun isBinaryIpInBetweenOtherTwo(binaryIp: String, startIp: String, endIp: String): Boolean {

        isBinaryIpLegal(binaryIp)
        isBinaryIpLegal(startIp)
        isBinaryIpLegal(endIp)

        val ipValue = convertBinaryIpAddressToIntValue(binaryIp).toUInt()
        val startValue = convertBinaryIpAddressToIntValue(startIp).toUInt()
        val endValue = convertBinaryIpAddressToIntValue(endIp).toUInt()

        return ipValue in startValue..endValue
    }

    /**
     * Checks if a decimal IP address is between two other decimal IP addresses.
     *
     * @param decimalIp the IP address to check, in decimal string format (e.g., "192.168.0.1")
     * @param startIp the starting IP address, in decimal string format
     * @param endIp the ending IP address, in decimal string format
     * @return `true` if the `ip` is between `startIp` and `endIp` (inclusive), `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses are invalid
     */
    @Throws(IllegalArgumentException::class)
    fun isDecimalIpInBetweenOtherTwo(decimalIp: String, startIp: String, endIp: String): Boolean {

        isDecimalIpLegal(decimalIp)
        isDecimalIpLegal(startIp)
        isDecimalIpLegal(endIp)

        val ipValue = convertDecimalIpAddressToIntValue(decimalIp).toUInt()
        val startValue = convertDecimalIpAddressToIntValue(startIp).toUInt()
        val endValue = convertDecimalIpAddressToIntValue(endIp).toUInt()

        return ipValue in startValue..endValue
    }
}
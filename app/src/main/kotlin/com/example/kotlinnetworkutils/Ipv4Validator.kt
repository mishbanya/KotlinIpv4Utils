package com.example.kotlinnetworkutils

import com.example.kotlinnetworkutils.RegexConstants.binaryIpRegex
import com.example.kotlinnetworkutils.RegexConstants.decimalIpRegex
import com.example.kotlinnetworkutils.RegexConstants.decimalMaskRegex

/**
 * Utility object for validating IPv4 addresses and subnet masks.
 *
 * Provides methods to ensure:
 * - IPv4 addresses are correctly formatted in binary, decimal, or byte array formats.
 * - Subnet masks are valid and adhere to the CIDR standard (e.g., contiguous bits).
 * - Validation of address and mask compatibility (e.g., mask length matches address format).
 *
 * This object is essential for ensuring data integrity before performing operations on IPv4 addresses and subnets.
 */
object Ipv4Validator {

    /**
     * Validates whether the given prefix length is legal.
     *
     * @param prefixLength the length of the prefix to be validated.
     * @throws IllegalArgumentException if the length of the prefix exceeds 32 or is less than 0.
     */
    @Throws(IllegalArgumentException::class)
    fun isPrefixLegal(prefixLength: Int) {
        if (prefixLength > 32 || prefixLength < 0)
            throw IllegalArgumentException("Invalid prefix length: $prefixLength")
    }

    /**
     * Validates whether the given decimal mask is legal.
     *
     * @param decimalMask mask in decimal format (e.g., "255.255.254.0").
     * @throws IllegalArgumentException if the decimal mask does not match the expected format.
     */
    @Throws(IllegalArgumentException::class)
    fun isDecimalMaskLegal(decimalMask: String) {
        if (!decimalMask.matches(decimalMaskRegex)) {
            throw IllegalArgumentException("Invalid decimal mask: $decimalMask")
        }
    }

    /**
     * Validates whether the given binary IP address is legal.
     *
     * @param binaryIp the IP address in binary string format (e.g., "11000000101010000000000000000001").
     * @throws IllegalArgumentException if the binary IP does not match the expected format.
     */
    @Throws(IllegalArgumentException::class)
    fun isBinaryIpLegal(binaryIp: String) {
        if (!binaryIp.matches(binaryIpRegex)) {
            throw IllegalArgumentException("Invalid binary IP: $binaryIp")
        }
    }

    /**
     * Validates whether the given decimal IP address is legal.
     *
     * @param decimalIp the IP address in decimal format (e.g., "192.168.0.1").
     * @throws IllegalArgumentException if the decimal IP does not match the expected format.
     */
    @Throws(IllegalArgumentException::class)
    fun isDecimalIpLegal(decimalIp: String) {
        if (!decimalIp.matches(decimalIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $decimalIp")
        }
    }

    /**
     * Validates whether the given byte array represents a legal IP address.
     *
     * @param byteIp the IP address in byte array format (expected size: 4 bytes).
     * @throws IllegalArgumentException if the byte array size is not 4.
     */
    @Throws(IllegalArgumentException::class)
    fun isByteIpLegal(byteIp: ByteArray) {
        if (byteIp.size != 4) {
            throw IllegalArgumentException("Invalid byte array size: ${byteIp.size}. Expected size is 4.")
        }
    }

}
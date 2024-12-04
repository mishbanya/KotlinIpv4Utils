package com.example.kotlinnetworkutils

import com.example.kotlinnetworkutils.Ipv4Converter.convertBinaryIpAddressToByteIpAddress
import com.example.kotlinnetworkutils.Ipv4Converter.convertBinaryIpAddressToDecimalIpAddress
import com.example.kotlinnetworkutils.Ipv4Converter.convertByteIpAddressToDecimalIpAddress
import com.example.kotlinnetworkutils.Ipv4Converter.convertDecimalIpAddressToBinaryIpAddress
import com.example.kotlinnetworkutils.Ipv4Converter.convertDecimalIpAddressToByteIpAddress
import com.example.kotlinnetworkutils.Ipv4Validator.isBinaryIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isByteIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalMaskLegal

/**
 * Represents an IP address in binary format.
 *
 * @property binaryIpAddress the binary IP address (e.g., "11000000.10101000.00000000.00000001").
 * @throws IllegalArgumentException if the binary IP address does not match the expected format.
 */
class BinaryIp(val binaryIpAddress: String) {
    init {
        isBinaryIpLegal(binaryIpAddress)
    }

    /**
     * Converts the binary IP address to a decimal IP address.
     *
     * @return a `DecimalIp` object representing the equivalent decimal format of the binary IP address.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toDecimal(): DecimalIp {
        return DecimalIp(convertBinaryIpAddressToDecimalIpAddress(binaryIpAddress))
    }

    /**
     * Converts the binary IP address to a byte array format.
     *
     * @return a `ByteIp` object representing the equivalent byte array format of the binary IP address.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toByte(): ByteIp {
        return ByteIp(convertBinaryIpAddressToByteIpAddress(binaryIpAddress))
    }
}

/**
 * Represents an IP address in decimal format.
 *
 * @property decimalIpAddress the decimal IP address (e.g., "192.168.0.1").
 * @throws IllegalArgumentException if the decimal IP address does not match the expected format.
 */
class DecimalIp(val decimalIpAddress: String) {
    init {
        isDecimalIpLegal(decimalIpAddress)
    }

    /**
     * Converts the decimal IP address to a binary format.
     *
     * @return a `BinaryIp` object representing the equivalent binary format of the decimal IP address.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toBinary(): BinaryIp {
        return BinaryIp(convertDecimalIpAddressToBinaryIpAddress(decimalIpAddress))
    }

    /**
     * Converts the decimal IP address to a byte array format.
     *
     * @return a `ByteIp` object representing the equivalent byte array format of the decimal IP address.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toByte(): ByteIp {
        return ByteIp(convertDecimalIpAddressToByteIpAddress(decimalIpAddress))
    }
}

/**
 * Represents an IP address in byte array format.
 *
 * @property byteIpAddress the byte array representing the IP address (expected size: 4 bytes).
 * @throws IllegalArgumentException if the byte array size is not 4.
 */
class ByteIp(val byteIpAddress: ByteArray) {
    init {
        isByteIpLegal(byteIpAddress)
    }

    /**
     * Converts the byte array IP address to a decimal format.
     *
     * @return a `DecimalIp` object representing the equivalent decimal format of the byte array IP address.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toDecimal(): DecimalIp {
        return DecimalIp(convertByteIpAddressToDecimalIpAddress(byteIpAddress))
    }

    /**
     * Converts the byte array IP address to a binary format.
     *
     * @return a `BinaryIp` object representing the equivalent binary format of the byte array IP address.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toBinary(): BinaryIp {
        return BinaryIp(convertDecimalIpAddressToBinaryIpAddress(toDecimal().decimalIpAddress))
    }
}

/**
 * Represents a subnet mask in binary format.
 *
 * @property binaryMaskAddress the binary subnet mask (e.g., "11111111.11111111.11111111.00000000").
 * @throws IllegalArgumentException if the binary mask does not match the expected format.
 */
class BinaryMask(val binaryMaskAddress: String) {
    init {
        isBinaryIpLegal(binaryMaskAddress)
    }

    /**
     * Converts the binary mask to a decimal mask.
     *
     * @return a `DecimalMask` object representing the equivalent decimal format of the binary mask.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toDecimal(): DecimalMask {
        return DecimalMask(convertBinaryIpAddressToDecimalIpAddress(binaryMaskAddress))
    }

    /**
     * Converts the binary mask to a byte array format.
     *
     * @return a `ByteMask` object representing the equivalent byte array format of the binary mask.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toByte(): ByteMask {
        return ByteMask(convertBinaryIpAddressToByteIpAddress(binaryMaskAddress))
    }
}

/**
 * Represents a subnet mask in decimal format.
 *
 * @property decimalMaskAddress the decimal subnet mask (e.g., "255.255.254.0").
 * @throws IllegalArgumentException if the decimal mask does not match the expected format.
 */
class DecimalMask(val decimalMaskAddress: String) {
    init {
        isDecimalMaskLegal(decimalMaskAddress)
    }

    /**
     * Converts the decimal mask to a binary format.
     *
     * @return a `BinaryMask` object representing the equivalent binary format of the decimal mask.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toBinary(): BinaryMask {
        return BinaryMask(convertDecimalIpAddressToBinaryIpAddress(decimalMaskAddress))
    }

    /**
     * Converts the decimal mask to a byte array format.
     *
     * @return a `ByteMask` object representing the equivalent byte array format of the decimal mask.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toByte(): ByteMask {
        return ByteMask(convertDecimalIpAddressToByteIpAddress(decimalMaskAddress))
    }
}

/**
 * Represents a subnet mask in byte array format.
 *
 * @property byteMaskAddress the byte array representing the subnet mask (expected size: 4 bytes).
 * @throws IllegalArgumentException if the byte array size is not 4.
 */
class ByteMask(val byteMaskAddress: ByteArray) {
    init {
        isByteIpLegal(byteMaskAddress)
    }

    /**
     * Converts the byte array mask to a decimal format.
     *
     * @return a `DecimalMask` object representing the equivalent decimal format of the byte array mask.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toDecimal(): DecimalMask {
        return DecimalMask(convertByteIpAddressToDecimalIpAddress(byteMaskAddress))
    }

    /**
     * Converts the byte array mask to a binary format.
     *
     * @return a `BinaryMask` object representing the equivalent binary format of the byte array mask.
     * @throws IllegalArgumentException if the conversion fails.
     */
    fun toBinary(): BinaryMask {
        return BinaryMask(convertDecimalIpAddressToBinaryIpAddress(toDecimal().decimalMaskAddress))
    }
}

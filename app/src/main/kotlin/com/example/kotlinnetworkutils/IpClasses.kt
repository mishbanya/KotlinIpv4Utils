package com.example.kotlinnetworkutils

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
}

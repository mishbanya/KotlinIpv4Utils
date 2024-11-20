package com.example.kotlinnetworkutils

/**
 * Contains regular expressions for validating IPv4-related data formats.
 *
 * Provides regex patterns to validate:
 * - Binary IP address format (e.g., "11000000.10101000.00000000.00000001").
 * - Decimal IP address format (e.g., "192.168.0.1").
 * - Decimal subnet mask format (e.g., "255.255.255.0").
 */
object RegexConstants {
    /**
     * Regular expression to validate IPv4 addresses in binary format.
     *
     * The pattern ensures the IP address consists of four 8-bit binary octets, separated by dots.
     * Example valid IP: "11000000.10101000.00000000.00000001"
     */
    val binaryIpRegex = Regex("^[01]{8}(\\.[01]{8}){3}$")

    /**
     * Regular expression to validate IPv4 addresses in decimal format.
     *
     * The pattern ensures the IP address consists of four octets (each between 0 and 255),
     * separated by dots, in the valid decimal format for IPv4 addresses.
     * Example valid IP: "192.168.0.1"
     */
    val decimalIpRegex = Regex(
        "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$"
    )

    /**
     * Regular expression to validate IPv4 subnet masks in decimal format.
     *
     * The pattern ensures the subnet mask is in the correct format, where each octet
     * is one of the values from the common subnet masks (e.g., 255, 254, 252, etc.).
     * Example valid mask: "255.255.255.0"
     */
    val decimalMaskRegex = Regex(
        "^(255|254|252|248|240|224|192|128|0)\\." +
                "(255|254|252|248|240|224|192|128|0)\\." +
                "(255|254|252|248|240|224|192|128|0)\\." +
                "(255|254|252|248|240|224|192|128|0)\$"
    )
}
package com.example.kotlinnetworkutils

import com.example.kotlinnetworkutils.Ipv4Validator.isBinaryIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isByteIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalMaskLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isPrefixLegal

/**
 * Utility object for converting IPv4 addresses and subnet masks between various formats.
 *
 * Provides methods to convert between:
 * - Binary string format
 * - Decimal string format
 * - Byte array format
 *
 * Typical use cases include transforming data for network calculations or compatibility with different systems.
 */
object Ipv4Converter {

    /**
     * Converts prefix length to decimal mask
     *
     * @param prefixLength length of prefix
     * @return mask in decimal format (e.g., "255.255.254.0")
     * @throws IllegalArgumentException if length of prefix exceeds 32 or lower then 0
     */
    @Throws(IllegalArgumentException::class)
    fun  convertPrefixToDecimalMask(prefixLength: Int): String{

        isPrefixLegal(prefixLength)

        val mask = IntArray(4)
        val fullMask = (0xFFFFFFFF shl (32 - prefixLength)) and 0xFFFFFFFF

        mask[0] = ((fullMask shr 24) and 0xFF).toInt()
        mask[1] = ((fullMask shr 16) and 0xFF).toInt()
        mask[2] = ((fullMask shr 8) and 0xFF).toInt()
        mask[3] = (fullMask and 0xFF).toInt()

        return mask.joinToString(".")
    }

    /**
     * Converts decimal mask to prefix length
     *
     * @param decimalMask mask in decimal format (e.g., "255.255.254.0")
     * @return length of prefix
     * @throws IllegalArgumentException if subnet mask is invalid subnet mask
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalMaskToPrefix(decimalMask: String): Int {

        isDecimalMaskLegal(decimalMask)

        val binaryString = convertDecimalIpToBinaryIp(decimalMask).replace(".", "")

        return binaryString.count { it == '1' }
    }

    /**
     * Converts prefix length to binary mask
     *
     * @param prefixLength length of prefix
     * @return mask in binary format (e.g., "11111111.11111000.00000000.00000000")
     * @throws IllegalArgumentException if length of prefix exceeds 32 or lower then 0
     */
    @Throws(IllegalArgumentException::class)
    fun convertPrefixToBinaryMask(prefixLength: Int): String {

        isPrefixLegal(prefixLength)

        val binaryMask = "1".repeat(prefixLength) + "0".repeat(32 - prefixLength)

        return binaryMask.chunked(8).joinToString(".")
    }

    /**
     * Converts binary mask to prefix length
     *
     * @param binaryMask mask in binary format (e.g., "11111111.11111000.00000000.00000000")
     * @return length of prefix
     * @throws IllegalArgumentException if subnet mask is invalid subnet mask
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryMaskToPrefix(binaryMask: String): Int {

        isBinaryIpLegal(binaryMask)

        val binaryString = binaryMask.replace(".", "")

        return binaryString.indexOf('0').takeIf { it >= 0 } ?: 32
    }

    /**
     * Converts binary IP address to decimal address
     *
     * @param binaryIp IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @return IP address in decimal format (e.g., "192.168.0.5")
     * @throws IllegalArgumentException if IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryIpToDecimalIp(binaryIp: String): String {

        isBinaryIpLegal(binaryIp)

        val binaryBytes = binaryIp.split('.')
        val decimalBytes = binaryBytes.map { it.toInt(2) }

        return decimalBytes.joinToString(".")
    }

    /**
     * Converts decimal IP address to binary ip address
     *
     * @param decimalIp IP address in decimal format (e.g., "192.168.0.5")
     * @return IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @throws IllegalArgumentException if IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalIpToBinaryIp(decimalIp: String): String {

        isDecimalIpLegal(decimalIp)

        val decimalBytes = decimalIp.split('.')
        val binaryBytes = decimalBytes.map { it.toInt().toString(2).padStart(8, '0') }

        return binaryBytes.joinToString(".")
    }

    /**
     * Converts binary IP address to a byte array.
     *
     * @param binaryIp IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @return ByteArray representing the IP address
     * @throws IllegalArgumentException if the IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryIpToByteIp(binaryIp: String): ByteArray {

        isBinaryIpLegal(binaryIp)

        val binaryBytes = binaryIp.split('.')

        return binaryBytes.map { it.toInt(2).toByte() }.toByteArray()
    }

    /**
     * Converts a byte array representing an IP address to a binary IP address.
     *
     * @param byteIp ByteArray representing the IP address
     * @return IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertByteIpToBinaryIp(byteIp: ByteArray): String {

        isByteIpLegal(byteIp)

        return byteIp.joinToString(".") {
            it.toInt().and(0xFF).toString(2).padStart(8, '0')
        }
    }

    /**
     * Converts binary IP address to a byte array.
     *
     * @param decimalIp IP address in decimal format (e.g., "192.168.0.5")
     * @return ByteArray representing the IP address
     * @throws IllegalArgumentException if the IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalIpToByteIp(decimalIp: String): ByteArray {

        isDecimalIpLegal(decimalIp)

        return convertBinaryIpToByteIp(convertDecimalIpToBinaryIp(decimalIp))
    }

    /**
     * Converts a byte array representing an IP address to a decimal IP address.
     *
     * @param byteIp ByteArray representing the IP address
     * @return IP address in decimal format (e.g., "192.168.0.5")
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertByteIpToDecimalIp(byteIp: ByteArray): String {

        isByteIpLegal(byteIp)

        return convertBinaryIpToDecimalIp(convertByteIpToBinaryIp(byteIp))
    }

    /**
     * Converts a byte array representing an IP address to an integer value.
     *
     * @param byteIp ByteArray representing the IP address (size must be 4)
     * @return integer value representing the IP address
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertByteIpToIntValue(byteIp: ByteArray): Int {

        isByteIpLegal(byteIp)

        var result = 0
        for (b in byteIp) {
            result = result shl 8 or (b.toInt() and 0xFF)
        }
        return result
    }

    /**
     * Converts decimal IP address to an integer value.
     *
     * @param decimalIp IP address in decimal format (e.g., "192.168.0.5")
     * @return integer value representing the IP address
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalIpToIntValue(decimalIp: String): Int {

        isDecimalIpLegal(decimalIp)

        return convertByteIpToIntValue(convertDecimalIpToByteIp(decimalIp))
    }

    /**
     * Converts a binary IP address to an integer value.
     *
     * @param binaryIp IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @return integer value representing the IP address
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryIpToIntValue(binaryIp: String): Int {

        isBinaryIpLegal(binaryIp)

        return convertByteIpToIntValue(convertBinaryIpToByteIp(binaryIp))
    }
}

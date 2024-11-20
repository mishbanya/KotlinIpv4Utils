package com.example.kotlinnetworkutils

import com.example.kotlinnetworkutils.Ipv4Comparer.isBinaryIpInBetweenOtherTwo
import com.example.kotlinnetworkutils.Ipv4Comparer.isDecimalIpInBetweenOtherTwo
import com.example.kotlinnetworkutils.Ipv4Converter.convertBinaryIpToDecimalIp
import com.example.kotlinnetworkutils.Ipv4Converter.convertByteIpToDecimalIp
import com.example.kotlinnetworkutils.Ipv4Converter.convertDecimalIpToBinaryIp
import com.example.kotlinnetworkutils.Ipv4Validator.isBinaryIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalIpLegal
import com.example.kotlinnetworkutils.Ipv4Validator.isDecimalMaskLegal

/**
 * Represents an IPv4 subnet, defined by an IP address and a subnet mask in decimal format.
 *
 * @property decimalIp the IP address in decimal format (e.g., "192.168.1.1").
 * @property decimalMask the subnet mask in decimal format (e.g., "255.255.255.0").
 */
class Ipv4Subnet(
    val decimalIp: DecimalIp,
    val decimalMask: DecimalMask
) {
    /**
     * Secondary constructor for creating an IPv4 subnet from binary representations of the IP address and subnet mask.
     *
     * @param binaryIp the IP address in binary string format (e.g., "11000000.10101000.00000000.00000001").
     * @param binaryMask the subnet mask in binary string format (e.g., "11111111.11111111.11111111.00000000").
     * @throws IllegalArgumentException if the binary IP or mask is invalid.
     */
    constructor(
        binaryIp: BinaryIp,
        binaryMask: BinaryMask
    ) : this(
        decimalIp = DecimalIp(convertBinaryIpToDecimalIp(binaryIp.binaryIpAddress)),
        decimalMask = DecimalMask(convertBinaryIpToDecimalIp(binaryMask.binaryMaskAddress))
    )

    /**
     * Secondary constructor for creating an IPv4 subnet from byte array representations of the IP address and subnet mask.
     *
     * @param byteIp the IP address as a byte array (expected size: 4 bytes).
     * @param byteMask the subnet mask as a byte array (expected size: 4 bytes).
     * @throws IllegalArgumentException if the byte array size is invalid or if conversion fails.
     */
    constructor(
        byteIp: ByteIp,
        byteMask: ByteMask
    ) : this(
        decimalIp = DecimalIp(convertByteIpToDecimalIp(byteIp.byteIpAddress)),
        decimalMask = DecimalMask(convertByteIpToDecimalIp(byteMask.byteMaskAddress))
    )

    /**
     * The minimal decimal IP address within the subnet range.
     *
     * Computed using the subnet's decimal IP address and subnet mask.
     * @throws IllegalArgumentException if IP or mask are invalid.
     */
    val minimalDecimalBorder: String =
        getDecimalIpBordersFromDecimalIpAndMask(decimalIp.decimalIpAddress, decimalIp.decimalIpAddress).first

    /**
     * The maximal decimal IP address within the subnet range.
     *
     * Computed using the subnet's decimal IP address and subnet mask.
     * @throws IllegalArgumentException if IP or mask are invalid.
     */
    val maximalDecimalBorder: String =
        getDecimalIpBordersFromDecimalIpAndMask(decimalIp.decimalIpAddress, decimalIp.decimalIpAddress).second

    /**
     * Checks if a given decimal IP address is contained within the subnet.
     *
     * @param IP the decimal IP address to check, represented as a `DecimalIp` object.
     * @throws IllegalArgumentException if the IP is invalid.
     */
    fun subnetContains(IP: DecimalIp) {
        isDecimalIpInSubnet(IP.decimalIpAddress, decimalIp.decimalIpAddress, decimalMask.decimalMaskAddress)
    }

    /**
     * Checks if a given binary IP address is contained within the subnet.
     *
     * @param IP the binary IP address to check, represented as a `BinaryIp` object.
     * @throws IllegalArgumentException if the IP is invalid.
     */
    fun subnetContains(IP: BinaryIp) {
        isBinaryIpInSubnet(
            IP.binaryIpAddress,
            convertDecimalIpToBinaryIp(decimalIp.decimalIpAddress),
            convertDecimalIpToBinaryIp(decimalMask.decimalMaskAddress)
        )
    }


    companion object {
        /**
         * Converts binary subnet components to two binary IP addresses, which are borders of given subnet
         *
         * @param binaryIp IP address in format binary format (e.g., "11000000.10101000.00000000.00000001")
         * @param binaryMask mask in binary format (e.g., "11111111.11111000.00000000.00000000")
         * @return pair of IP addresses in binary format (e.g., "11000000.10101000.00000000.00000001") ;
         * First pair member is a minimal IP address usable in given subnet,
         * second pair member is maximal.
         * @throws IllegalArgumentException if either one of subnet components is invalid
         */
        @Throws(IllegalArgumentException::class)
        fun getBinaryIpBordersFromBinaryIpAndMask(
            binaryIp: String,
            binaryMask: String
        ): Pair<String, String> {

            isBinaryIpLegal(binaryIp)
            isBinaryIpLegal(binaryMask)

            val ipBits = binaryIp.replace(".", "").toCharArray()
            val maskBits = binaryMask.replace(".", "").toCharArray()
            val prefixLength = maskBits.count { it == '1' }

            val minIpBits = ipBits.take(prefixLength) + List(31 - prefixLength) { '0' } + '1'
            val maxIpBits = ipBits.take(prefixLength) + List(31 - prefixLength) { '1' } + '0'

            val minIp = minIpBits.chunked(8).joinToString(".") { it.joinToString("") }
            val maxIp = maxIpBits.chunked(8).joinToString(".") { it.joinToString("") }

            return Pair(minIp, maxIp)
        }

        /**
         * Converts decimal subnet components to two binary IP addresses, which are borders of given subnet
         *
         * @param decimalIp IP address in decimal format (e.g., "192.168.0.5")
         * @param decimalMask mask in decimal format (e.g., "255.255.254.0")
         * @return pair of IP addresses in binary format (e.g., "11000000.10101000.00000000.00000001") ;
         * First pair member is a minimal IP address usable in given subnet,
         * second pair member is maximal.
         * @throws IllegalArgumentException if either one of subnet components is invalid
         */
        @Throws(IllegalArgumentException::class)
        fun getBinaryIpBordersFromDecimalIpAndMask(
            decimalIp: String,
            decimalMask: String
        ): Pair<String, String> {

            isDecimalIpLegal(decimalIp)
            isDecimalMaskLegal(decimalMask)

            return getBinaryIpBordersFromBinaryIpAndMask(
                convertDecimalIpToBinaryIp(decimalIp),
                convertDecimalIpToBinaryIp(decimalMask)
            )
        }

        /**
         * Converts binary subnet components to two decimal IP addresses, which are borders of given subnet
         *
         * @param binaryIp IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
         * @param binaryMask mask in binary format (e.g., "11111111.11111000.00000000.00000000")
         * @return pair of IP addresses in decimal format (e.g., "192.168.0.5") ;
         * First pair member is a minimal IP address usable in given subnet,
         * second pair member is maximal.
         * @throws IllegalArgumentException if either one of subnet components is invalid
         */
        @Throws(IllegalArgumentException::class)
        fun getDecimalIpBordersFromBinaryIpAndMask(
            binaryIp: String,
            binaryMask: String
        ): Pair<String, String> {

            isBinaryIpLegal(binaryIp)
            isBinaryIpLegal(binaryMask)

            val binaryBorders = getBinaryIpBordersFromBinaryIpAndMask(binaryIp, binaryMask)
            return Pair(
                convertBinaryIpToDecimalIp(binaryBorders.first),
                convertBinaryIpToDecimalIp(binaryBorders.second)
            )
        }

        /**
         * Converts decimal subnet components to two decimal IP addresses, which are borders of given subnet
         *
         * @param decimalIp IP address in decimal format (e.g., "192.168.0.5")
         * @param decimalMask mask in decimal format (e.g., "255.255.254.0")
         * @return pair of IP addresses in decimal format (e.g., "192.168.0.5") ;
         * First pair member is a minimal IP address usable in given subnet,
         * second pair member is maximal.
         * @throws IllegalArgumentException if either one of subnet components is invalid
         */
        @Throws(IllegalArgumentException::class)
        fun getDecimalIpBordersFromDecimalIpAndMask(
            decimalIp: String,
            decimalMask: String
        ): Pair<String, String> {

            isDecimalIpLegal(decimalIp)
            isDecimalMaskLegal(decimalMask)

            val binaryBorders = getBinaryIpBordersFromBinaryIpAndMask(
                convertDecimalIpToBinaryIp(decimalIp),
                convertDecimalIpToBinaryIp(decimalMask)
            )
            return Pair(
                convertBinaryIpToDecimalIp(binaryBorders.first),
                convertBinaryIpToDecimalIp(binaryBorders.second)
            )
        }

        /**
         * Checks if the given decimal IP address is in the range of the subnet.
         *
         * @param decimalIp the IP address to check in decimal format (e.g., "192.168.0.5")
         * @param subnetDecimalIp the subnet's IP address in decimal format
         * @param subnetDecimalMask the subnet mask in decimal format
         * @return `true` if the decimal IP is within the subnet, `false` otherwise
         * @throws IllegalArgumentException if any of the IP addresses or mask are invalid
         */
        fun isDecimalIpInSubnet(
            decimalIp: String,
            subnetDecimalIp: String,
            subnetDecimalMask: String
        ): Boolean {

            isDecimalIpLegal(decimalIp)
            isDecimalIpLegal(subnetDecimalIp)
            isDecimalMaskLegal(subnetDecimalMask)

            val borders =
                getDecimalIpBordersFromDecimalIpAndMask(subnetDecimalIp, subnetDecimalMask)
            return isDecimalIpInBetweenOtherTwo(decimalIp, borders.first, borders.second)
        }

        /**
         * Checks if the given binary IP address is in the range of the subnet.
         *
         * @param binaryIp the IP address to check in binary format (e.g., "11000000.10101000.00000000.00000001")
         * @param subnetBinaryIp the subnet's IP address in binary format
         * @param subnetBinaryMask the subnet mask in binary format
         * @return `true` if the binary IP is within the subnet, `false` otherwise
         * @throws IllegalArgumentException if any of the IP addresses or mask are invalid
         */
        fun isBinaryIpInSubnet(
            binaryIp: String,
            subnetBinaryIp: String,
            subnetBinaryMask: String
        ): Boolean {

            isBinaryIpLegal(binaryIp)
            isBinaryIpLegal(subnetBinaryIp)
            isBinaryIpLegal(subnetBinaryMask)

            val borders = getBinaryIpBordersFromBinaryIpAndMask(subnetBinaryIp, subnetBinaryMask)
            return isBinaryIpInBetweenOtherTwo(binaryIp, borders.first, borders.second)
        }

    }
}
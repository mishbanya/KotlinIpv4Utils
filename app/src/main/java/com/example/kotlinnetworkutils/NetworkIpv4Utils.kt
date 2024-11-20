package com.example.kotlinnetworkutils

object IPv4Utils {
    private val binaryMaskAndIpRegex = Regex("^[01]{8}(\\.[01]{8}){3}$")
    private val decimalIpRegex = Regex(
        "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$"
    )
    private val decimalMaskRegex = Regex(
        "^(255|254|252|248|240|224|192|128|0)\\." +
                "(255|254|252|248|240|224|192|128|0)\\." +
                "(255|254|252|248|240|224|192|128|0)\\." +
                "(255|254|252|248|240|224|192|128|0)\$"
    )
    private fun isPrefixLegal(prefixLength: Int):Boolean{
        return prefixLength > 32 || prefixLength < 0
    }

    /**
     * Converts prefix length to decimal mask
     * @param prefixLength length of prefix
     * @return decimal mask in format x.x.x.x
     * @throws IllegalArgumentException if length of prefix exceeds 32 or lower then 0
     */
    @Throws(IllegalArgumentException::class)
    fun  convertPrefixToDecimalMask(prefixLength: Int): String{
        if (isPrefixLegal(prefixLength)) {
            throw IllegalArgumentException("Invalid prefix length: $prefixLength")
        }
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
     * @param decimalMask decimal mask in format x.x.x.x
     * @return length of prefix
     * @throws IllegalArgumentException if subnet mask is invalid subnet mask
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalMaskToPrefix(decimalMask: String): Int {
        val binaryString = convertDecimalIpToBinaryIp(decimalMask).replace(".", "")

        return binaryString.count { it == '1' }
    }

    /**
     * Converts prefix length to binary mask
     * @param prefixLength length of prefix
     * @return binary mask in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @throws IllegalArgumentException if length of prefix exceeds 32 or lower then 0
     */
    @Throws(IllegalArgumentException::class)
    fun convertPrefixToBinaryMask(prefixLength: Int): String {
        if (isPrefixLegal(prefixLength)) {
            throw IllegalArgumentException("Invalid prefix length: $prefixLength")
        }
        val binaryMask = "1".repeat(prefixLength) + "0".repeat(32 - prefixLength)

        return binaryMask.chunked(8).joinToString(".")
    }

    /**
     * Converts binary mask to prefix length
     * @param binaryMask binary mask in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @return length of prefix
     * @throws IllegalArgumentException if subnet mask is invalid subnet mask
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryMaskToPrefix(binaryMask: String): Int {
        if (!binaryMask.matches(binaryMaskAndIpRegex)) {
            throw IllegalArgumentException("Invalid subnet mask: $binaryMask")
        }
        val binaryString = binaryMask.replace(".", "")

        return binaryString.indexOf('0').takeIf { it >= 0 } ?: 32
    }

    /**
     * Converts binary IP address to decimal address
     * @param binaryIp binary IP address in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @return decimal IP address in format x.x.x.x
     * @throws IllegalArgumentException if IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryIpToDecimalIp(binaryIp: String): String {
        if (!binaryIp.matches(binaryMaskAndIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $binaryIp")
        }
        val binaryBytes = binaryIp.split('.')
        val decimalBytes = binaryBytes.map { it.toInt(2) }

        return decimalBytes.joinToString(".")
    }

    /**
     * Converts decimal IP address to binary ip address
     * @param decimalIp decimal IP address in format x.x.x.x
     * @return binary IP address in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @throws IllegalArgumentException if IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalIpToBinaryIp(decimalIp: String): String {
        if (!decimalIp.matches(decimalIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $decimalIp")
        }
        val decimalBytes = decimalIp.split('.')
        val binaryBytes = decimalBytes.map { it.toInt().toString(2).padStart(8, '0') }

        return binaryBytes.joinToString(".")
    }

    /**
     * Converts binary IP address to a byte array.
     * @param binaryIp binary IP address in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @return ByteArray representing the IP address
     * @throws IllegalArgumentException if the IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryIpToByteIp(binaryIp: String): ByteArray {
        if (!binaryIp.matches(binaryMaskAndIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $binaryIp")
        }
        val binaryBytes = binaryIp.split('.')

        return binaryBytes.map { it.toInt(2).toByte() }.toByteArray()
    }

    /**
     * Converts a byte array representing an IP address to a binary IP address.
     * @param byteIp ByteArray representing the IP address
     * @return binary IP address in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertByteIpToBinaryIp(byteIp: ByteArray): String {
        if (byteIp.size != 4) {
            throw IllegalArgumentException("Invalid byte array size: ${byteIp.size}. Expected size is 4.")
        }

        return byteIp.joinToString(".") {
            it.toInt().and(0xFF).toString(2).padStart(8, '0')
        }
    }

    /**
     * Converts binary IP address to a byte array.
     * @param decimalIp decimal IP address in format x.x.x.x
     * @return ByteArray representing the IP address
     * @throws IllegalArgumentException if the IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalIpToByteIp(decimalIp: String): ByteArray {
        if (!decimalIp.matches(decimalIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $decimalIp")
        }
        return convertBinaryIpToByteIp(convertDecimalIpToBinaryIp(decimalIp))
    }

    /**
     * Converts a byte array representing an IP address to a decimal IP address.
     * @param byteIp ByteArray representing the IP address
     * @return decimal IP address in format x.x.x.x
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertByteIpToDecimalIp(byteIp: ByteArray): String {
        if (byteIp.size != 4) {
            throw IllegalArgumentException("Invalid byte array size: ${byteIp.size}. Expected size is 4.")
        }

        return convertBinaryIpToDecimalIp(convertByteIpToBinaryIp(byteIp))
    }

    /**
     * Converts a byte array representing an IP address to an integer value.
     * @param byteIp ByteArray representing the IP address (size must be 4)
     * @return integer value representing the IP address
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertByteIpToIntValue(byteIp: ByteArray): Int {
        if (byteIp.size != 4) {
            throw IllegalArgumentException("Invalid byte array size: ${byteIp.size}. Expected size is 4.")
        }
        var result = 0
        for (b in byteIp) {
            result = result shl 8 or (b.toInt() and 0xFF)
        }
        return result
    }

    /**
     * Converts decimal IP address to an integer value.
     * @param decimalIp decimal IP address in format x.x.x.x
     * @return integer value representing the IP address
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertDecimalIpToIntValue(decimalIp: String): Int {
        if (!decimalIp.matches(decimalIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $decimalIp")
        }

        return convertByteIpToIntValue(convertDecimalIpToByteIp(decimalIp))
    }

    /**
     * Converts a binary IP address to an integer value.
     * @param binaryIp binary IP address in format xxxx.xxxx.xxxx.xxxx
     * @return integer value representing the IP address
     * @throws IllegalArgumentException if the byte array size is not 4
     */
    @Throws(IllegalArgumentException::class)
    fun convertBinaryIpToIntValue(binaryIp: String): Int {
        if (!binaryIp.matches(binaryMaskAndIpRegex)) {
            throw IllegalArgumentException("Invalid IP address: $binaryIp")
        }

        return convertByteIpToIntValue(convertBinaryIpToByteIp(binaryIp))
    }


    /**
     * Converts binary subnet components to two binary IP addresses, which are borders of given subnet
     * @param binaryIp binary IP address in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @param binaryMask binary mask in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @return pair of binary IP addresses in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx ;
     * First pair member is a minimal IP address usable in given subnet,
     * second pair member is maximal.
     * @throws IllegalArgumentException if either one of subnet components is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun getBinaryIpBordersFromBinaryIpAndMask(binaryIp: String, binaryMask: String): Pair<String, String> {
        if (!(binaryIp.matches(binaryMaskAndIpRegex) && binaryMask.matches(binaryMaskAndIpRegex))) {
            throw IllegalArgumentException("Invalid binary IP address: $binaryIp or mask: $binaryMask")
        }
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
     * @param decimalIp decimal IP address in format x.x.x.x
     * @param decimalMask decimal mask in format x.x.x.x
     * @return pair of binary IP addresses in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx ;
     * First pair member is a minimal IP address usable in given subnet,
     * second pair member is maximal.
     * @throws IllegalArgumentException if either one of subnet components is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun getBinaryIpBordersFromDecimalIpAndMask(decimalIp: String, decimalMask: String): Pair<String, String> {
        if (!(decimalIp.matches(decimalIpRegex) && decimalMask.matches(decimalMaskRegex))) {
            throw IllegalArgumentException("Invalid binary IP address: $decimalIp or mask: $decimalMask")
        }

        return getBinaryIpBordersFromBinaryIpAndMask(convertDecimalIpToBinaryIp(decimalIp), convertDecimalIpToBinaryIp(decimalMask))
    }

    /**
     * Converts binary subnet components to two decimal IP addresses, which are borders of given subnet
     * @param binaryIp binary IP address in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @param binaryMask binary mask in format xxxxxxxx.xxxxxxxx.xxxxxxxx.xxxxxxxx
     * @return pair of decimal IP addresses in format x.x.x.x ;
     * First pair member is a minimal IP address usable in given subnet,
     * second pair member is maximal.
     * @throws IllegalArgumentException if either one of subnet components is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun getDecimalIpBordersFromBinaryIpAndMask(binaryIp: String, binaryMask: String): Pair<String, String> {
        if (!(binaryIp.matches(binaryMaskAndIpRegex) && binaryMask.matches(binaryMaskAndIpRegex))) {
            throw IllegalArgumentException("Invalid binary IP address: $binaryIp or mask: $binaryMask")
        }
        val binaryBorders = getBinaryIpBordersFromBinaryIpAndMask(binaryIp,binaryMask)
        return Pair(convertBinaryIpToDecimalIp(binaryBorders.first), convertBinaryIpToDecimalIp(binaryBorders.second))
    }

    /**
     * Converts decimal subnet components to two decimal IP addresses, which are borders of given subnet
     * @param decimalIp decimal IP address in format x.x.x.x
     * @param decimalMask decimal mask in format x.x.x.x
     * @return pair of decimal IP addresses in format x.x.x.x ;
     * First pair member is a minimal IP address usable in given subnet,
     * second pair member is maximal.
     * @throws IllegalArgumentException if either one of subnet components is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun getDecimalIpBordersFromDecimalIpAndMask(decimalIp: String, decimalMask: String): Pair<String, String> {
        if (!(decimalIp.matches(decimalIpRegex) && decimalMask.matches(decimalMaskRegex))) {
            throw IllegalArgumentException("Invalid binary IP address: $decimalIp or mask: $decimalMask")
        }
        val binaryBorders = getBinaryIpBordersFromBinaryIpAndMask(convertDecimalIpToBinaryIp(decimalIp), convertDecimalIpToBinaryIp(decimalMask))
        return Pair(convertBinaryIpToDecimalIp(binaryBorders.first), convertBinaryIpToDecimalIp(binaryBorders.second))
    }

    /**
     * Compares two IP addresses represented as byte arrays.
     * @param firstByteIp the first IP address as a byte array of size 4
     * @param secondByteIp the second IP address as a byte array of size 4
     * @return `true` if the first IP address is greater than the second, `false` otherwise
     * @throws IllegalArgumentException if either IP address is not exactly 4 bytes
     */
    @Throws(IllegalArgumentException::class)
    fun compareByteIps(firstByteIp: ByteArray, secondByteIp: ByteArray): Boolean {
        if (!(firstByteIp.size == 4 && secondByteIp.size == 4)) {
            throw IllegalArgumentException("Both IPs must be exactly 4 bytes")
        }
        val firstValue = convertByteIpToIntValue(firstByteIp).toUInt()
        val secondValue = convertByteIpToIntValue(secondByteIp).toUInt()

        return firstValue > secondValue
    }

    /**
     * Compares two decimal IP addresses represented as strings.
     * @param firstDecimalIp the first IP address in decimal format (e.g., "192.168.0.1")
     * @param secondDecimalIp the second IP address in decimal format (e.g., "192.168.1.1")
     * @return `true` if the first IP address is greater than the second, `false` otherwise
     * @throws IllegalArgumentException if either IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun compareDecimalIps(firstDecimalIp: String, secondDecimalIp: String): Boolean {
        if (!(firstDecimalIp.matches(decimalIpRegex) && secondDecimalIp.matches(decimalIpRegex))) {
            throw IllegalArgumentException("One of IP addresses is invalid: $firstDecimalIp or $secondDecimalIp")
        }
        val firstValue = convertDecimalIpToIntValue(firstDecimalIp).toUInt()
        val secondValue = convertDecimalIpToIntValue(secondDecimalIp).toUInt()

        return firstValue > secondValue
    }

    /**
     * Compares two binary IP addresses represented as strings.
     * @param firstBinaryIp the first IP address in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @param secondBinaryIp the second IP address in binary format (e.g., "11000000.10101000.00000001.00000001")
     * @return `true` if the first IP address is greater than the second, `false` otherwise
     * @throws IllegalArgumentException if either IP address is invalid
     */
    @Throws(IllegalArgumentException::class)
    fun compareBinaryIps(firstBinaryIp: String, secondBinaryIp: String): Boolean {
        if (!(firstBinaryIp.matches(binaryMaskAndIpRegex) && secondBinaryIp.matches(binaryMaskAndIpRegex))) {
            throw IllegalArgumentException("One of IP addresses is invalid: $firstBinaryIp or $secondBinaryIp")
        }
        val firstValue = convertBinaryIpToIntValue(firstBinaryIp).toUInt()
        val secondValue = convertBinaryIpToIntValue(secondBinaryIp).toUInt()

        return firstValue > secondValue
    }

    /**
     * Checks if a byte IP address is between two other byte IP addresses.
     * @param byteIp the IP address to check, in byte array format
     * @param startIp the starting IP address, in byte array format
     * @param endIp the ending IP address, in byte array format
     * @return `true` if the `ip` is between `startIp` and `endIp` (inclusive), `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses are invalid
     */
    @Throws(IllegalArgumentException::class)
    fun isByteIpInBetweenOtherTwo(byteIp: ByteArray, startIp: ByteArray, endIp: ByteArray): Boolean {
        if (!(byteIp.size == 4 && startIp.size == 4 && endIp.size == 4)) {
            throw IllegalArgumentException("All IPs must be exactly 4 bytes")
        }
        val ipValue = convertByteIpToIntValue(byteIp).toUInt()
        val startValue = convertByteIpToIntValue(startIp).toUInt()
        val endValue = convertByteIpToIntValue(endIp).toUInt()

        return ipValue in startValue..endValue
    }

    /**
     * Checks if a binary IP address is between two other binary IP addresses.
     * @param binaryIp the IP address to check, in binary string format (e.g., "11000000.10101000.00000000.00000001")
     * @param startIp the starting IP address, in binary string format
     * @param endIp the ending IP address, in binary string format
     * @return `true` if the `ip` is between `startIp` and `endIp` (inclusive), `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses are invalid
     */
    @Throws(IllegalArgumentException::class)
    fun isBinaryIpInBetweenOtherTwo(binaryIp: String, startIp: String, endIp: String): Boolean {
        if (!(binaryIp.matches(binaryMaskAndIpRegex) && startIp.matches(binaryMaskAndIpRegex) && endIp.matches(binaryMaskAndIpRegex))) {
            throw IllegalArgumentException("All IPs must be valid binary IPs")
        }
        val ipValue = convertBinaryIpToIntValue(binaryIp).toUInt()
        val startValue = convertBinaryIpToIntValue(startIp).toUInt()
        val endValue = convertBinaryIpToIntValue(endIp).toUInt()

        return ipValue in startValue..endValue
    }

    /**
     * Checks if a decimal IP address is between two other decimal IP addresses.
     * @param decimalIp the IP address to check, in decimal string format (e.g., "192.168.0.1")
     * @param startIp the starting IP address, in decimal string format
     * @param endIp the ending IP address, in decimal string format
     * @return `true` if the `ip` is between `startIp` and `endIp` (inclusive), `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses are invalid
     */
    @Throws(IllegalArgumentException::class)
    fun isDecimalIpInBetweenOtherTwo(decimalIp: String, startIp: String, endIp: String): Boolean {
        if (!(decimalIp.matches(decimalIpRegex) && startIp.matches(decimalIpRegex) && endIp.matches(decimalIpRegex))) {
            throw IllegalArgumentException("All IPs must be valid decimal IPs")
        }
        val ipValue = convertDecimalIpToIntValue(decimalIp).toUInt()
        val startValue = convertDecimalIpToIntValue(startIp).toUInt()
        val endValue = convertDecimalIpToIntValue(endIp).toUInt()

        return ipValue in startValue..endValue
    }


    /**
     * Checks if the given decimal IP address is in the range of the subnet.
     * @param decimalIp the IP address to check in decimal format (e.g., "192.168.0.5")
     * @param subnetDecimalIp the subnet's IP address in decimal format
     * @param subnetDecimalMask the subnet mask in decimal format
     * @return `true` if the decimal IP is within the subnet, `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses or mask are invalid
     */
    fun isDecimalIpInSubnet(decimalIp: String, subnetDecimalIp: String, subnetDecimalMask: String): Boolean {
        if (!(decimalIp.matches(decimalIpRegex) && subnetDecimalIp.matches(decimalIpRegex) && subnetDecimalMask.matches(decimalMaskRegex))) {
            throw IllegalArgumentException("All IPs must be valid decimal IPs")
        }

        val borders = getDecimalIpBordersFromDecimalIpAndMask(subnetDecimalIp, subnetDecimalMask)
        return isDecimalIpInBetweenOtherTwo(decimalIp, borders.first, borders.second)
    }

    /**
     * Checks if the given binary IP address is in the range of the subnet.
     * @param binaryIp the IP address to check in binary format (e.g., "11000000.10101000.00000000.00000001")
     * @param subnetBinaryIp the subnet's IP address in binary format
     * @param subnetBinaryMask the subnet mask in binary format
     * @return `true` if the binary IP is within the subnet, `false` otherwise
     * @throws IllegalArgumentException if any of the IP addresses or mask are invalid
     */
    fun isBinaryIpInSubnet(binaryIp: String, subnetBinaryIp: String, subnetBinaryMask: String): Boolean {
        if (!(binaryIp.matches(binaryMaskAndIpRegex) && subnetBinaryIp.matches(binaryMaskAndIpRegex) && subnetBinaryMask.matches(binaryMaskAndIpRegex))) {
            throw IllegalArgumentException("All IPs must be valid binary IPs")
        }

        val borders = getBinaryIpBordersFromBinaryIpAndMask(subnetBinaryIp, subnetBinaryMask)
        return isBinaryIpInBetweenOtherTwo(binaryIp, borders.first, borders.second)
    }
}

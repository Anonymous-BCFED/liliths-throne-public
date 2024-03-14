package com.lilithsthrone.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class CryptoUtils {
    public static byte[] Blake2b(int size, byte[] data) {
        final ove.crypto.digest.Blake2b d = ove.crypto.digest.Blake2b.Digest.newInstance(size);
        d.update(data, 0, data.length);
        return d.digest();
    }

    public static BigInteger Bytes2BILE(byte[] b) {
        var bi = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).array();
        return new BigInteger(1, bi);
    }

    static char[] ALPHABET36 = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789").toCharArray();

    public static String BigInt2Base36(BigInteger i) {
        BigInteger r;
        StringBuilder buf = new StringBuilder();
        BigInteger THIRTY_SIX = BigInteger.valueOf(ALPHABET36.length);
        while (i.compareTo(BigInteger.ZERO) > 0) {
            r = i.remainder(THIRTY_SIX);
            i = i.divide(THIRTY_SIX);
            buf.insert(0, ALPHABET36[r.intValue()]);
        }
        return buf.toString();
    }

    public static long bytesToLong(final byte[] b) {
        long result = 0;
        int i = b.length - 1;
        while (i >= 0) {
            result <<= 8;
            result |= (b[i] & 0xFF);
            --i;
        }
        return result;
    }

    public static String longToBase36(long v) {
        StringBuilder o = new StringBuilder();
        while (v > 0) {
            o.insert(0, ALPHABET36[(int) (v % 36)]);
            v = v / 36L;
        }
        return o.toString();
    }


    public static String CID2SID(String cid) {
        // 5 B -> 40 b
        byte[] hash = Blake2b(5, cid.getBytes(StandardCharsets.UTF_8));
        //System.out.println(hash.toString());
        //StringBuilder b = new StringBuilder(BigInt2Base36(Bytes2BILE(hash)));
        StringBuilder b = new StringBuilder(longToBase36(bytesToLong(hash)));
        while (b.length() < 8)
            b.insert(0, ALPHABET36[0]);
        String buf = b.toString();
        return String.format("%s-%s", buf.substring(0, 4), buf.substring(4, 8));
    }
}

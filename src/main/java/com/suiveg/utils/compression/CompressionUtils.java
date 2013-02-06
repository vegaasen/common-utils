package com.suiveg.utils.compression;

import com.suiveg.utils.abs.AbstractUtil;
import com.suiveg.utils.string.StringUtils;

import java.io.*;
import java.util.zip.*;

/**
 * @author <a href="vegard.aasen@telenor.com">Vegard Aasen</a>
 */
public final class CompressionUtils extends AbstractUtil {

    private static final int DEFAULT_DEFLATE_SIZE = 1024;

    private CompressionUtils() {}

    private static void copyAll(final InputStream in, final OutputStream out) {
        try {
            int c;
            while ((c = in.read()) > 0) {
                out.write(c);
            }
        } catch (final IOException e) {
            //??
        }
    }

    public static byte[] compressWithDeflater(final String s) {
        try {
            final ByteArrayOutputStream outBuff = new ByteArrayOutputStream();
            final DeflaterOutputStream out = new DeflaterOutputStream(outBuff, new Deflater(Deflater.BEST_COMPRESSION, true));
            final ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes("UTF-8"));
            try {
                copyAll(in, out);
            } finally {
                out.close();
            }
            return outBuff.toByteArray();
        } catch (final IOException e) {
            //?
        }
        return null;
    }

    public static String decompressWithInflater(final byte[] compressedData) {
        final InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(compressedData), new Inflater(true));
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            copyAll(in, out);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toString();
    }

    public static byte[] deflate(final String in) {
        if(StringUtils.isNotBlank(in)) {
            final byte[] bb = in.getBytes();

            byte[] out = new byte[DEFAULT_DEFLATE_SIZE];
            Deflater comp = new Deflater();
            comp.setInput(bb);
            comp.finish();
            comp.deflate(out);
            comp.end();
            return out;
        }
        throw new IllegalArgumentException("");
    }

    public static  String inflate(final byte[] in) throws UnsupportedEncodingException {
        if(in!=null && in.length > 0) {
            Inflater dec = new Inflater();
            try {
                dec.setInput(in, 0, in.length);
                byte[] result = new byte[100];
                dec.inflate(result);
                dec.end();
                return new String(result, 0, result.length, "UTF-8");
            } catch (DataFormatException e) {
                //??
            }
        }
        throw new IllegalArgumentException("");
    }

    public static OutputStream addToZip(final String in) {
        if(StringUtils.isNotBlank(in)) {
            OutputStream os = new ByteArrayOutputStream();
            ZipOutputStream zipO = new ZipOutputStream(os);
            try {
                ZipEntry entry = new ZipEntry("");
                zipO.putNextEntry(entry);
                zipO.write(in.getBytes(), 0, in.getBytes().length);
                zipO.close();
                return os;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("");
    }

}

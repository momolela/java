package com.momolela.serialize.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import com.momolela.serialize.SerializeException;
import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

/**
 * @author sunzj
 */
public class CompressStreamFactory {

    public static InputStream buildInputStream(InputStream is, byte type) {
        try {
            switch (type) {
                case 1:
                    return new GZIPInputStream(is);
                case 2:
                    return new BZip2CompressorInputStream(is);
                case 3:
                    return new InflaterInputStream(is);
                case 4:
                    return new SnappyInputStream(is);
                case 5:
                    return new LZ4BlockInputStream(is);
                default:
                    return is;
            }
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        }
    }

    public static OutputStream buildOutputStream(OutputStream os, byte type) {
        try {
            switch (type) {
                case 1:
                    return new GZIPOutputStream(os);
                case 2:
                    return new BZip2CompressorOutputStream(os);
                case 3:
                    return new DeflaterOutputStream(os);
                case 4:
                    return new SnappyOutputStream(os);
                case 5:
                    return new LZ4BlockOutputStream(os);
                default:
                    return os;
            }
        } catch (IOException ioe) {
            throw new SerializeException(SerializeException.CODE_IO_FAILED, ioe);
        }
    }
}
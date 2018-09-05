package com.test.otp;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public abstract class Bytes {
	
	private static final byte[] EMPTY_BYTES = new byte[0];
	
	private Bytes() {
		// do nothing
	}
	
	public static int getInt(byte[] bytes, int off) {
        return ((bytes[off] & 0xFF) << 24) |
        		((bytes[off + 1] & 0xFF) << 16) |
        		((bytes[off + 2] & 0xFF) << 8) |
        		(bytes[off + 3]  & 0xFF);
	}
	
	public static void putLong(byte[] bytes, int off, long v) {
		bytes[off] = (byte) ((v >> 56) & 0xFF);
		bytes[off + 1] = (byte) ((v >> 48) & 0xFF);
		bytes[off + 2] = (byte) ((v >> 40) & 0xFF);
		bytes[off + 3] = (byte) ((v >> 32) & 0xFF);
		bytes[off + 4] = (byte) ((v >> 24) & 0xFF);
		bytes[off + 5] = (byte) ((v >> 16) & 0xFF);
		bytes[off + 6] = (byte) ((v >>  8) & 0xFF);
		bytes[off + 7] = (byte) (v & 0xFF);
	}
	
	public static char[] asChars(byte[] bytes, Charset charset) {
		final CharBuffer buf = charset.decode(ByteBuffer.wrap(bytes));
		
		final char[] chars = new char[buf.remaining()];
		buf.get(chars);
		
		if (buf.hasArray()) {
			Arrays.fill(buf.array(), (char) 0);
		}
		
		return chars;
	}
	
	public static byte[] asBytes(char[] chars, Charset charset) {
		if (chars == null) {
			return null;
		}
		
		if (chars.length == 0) {
			return EMPTY_BYTES;
		}
		
		final ByteBuffer buf = charset.encode(CharBuffer.wrap(chars));
		
		if (buf.remaining() == 0) {
			return EMPTY_BYTES;
		}
		
		final byte[] bytes = new byte[buf.remaining()];
		buf.get(bytes);
		
		if (buf.hasArray()) {
			Arrays.fill(buf.array(), (byte) 0);
		}
		
		return bytes;
	}
	
	public static byte[] asBytes(long v) {
		return new byte[] {
			(byte) ((v >> 56) & 0xFF),
			(byte) ((v >> 48) & 0xFF),
			(byte) ((v >> 40) & 0xFF),
			(byte) ((v >> 32) & 0xFF),
			(byte) ((v >> 24) & 0xFF),
			(byte) ((v >> 16) & 0xFF),
			(byte) ((v >> 8) & 0xFF),
			(byte) (v & 0xFF)
		};
	}
	
	public static long asLong(byte[] bytes) {
		return (((long) bytes[0] << 56) +
				((long) (bytes[1] & 255) << 48) +
				((long) (bytes[2] & 255) << 40) +
				((long) (bytes[3] & 255) << 32) +
				((long) (bytes[4] & 255) << 24) +
				((bytes[5] & 255) << 16) +
				((bytes[6] & 255) <<  8) +
				((bytes[7] & 255) <<  0));
	}
	
	public static byte[] join(byte[]... bytes) {
		int total = 0;
		
		for (int i = 0, n = bytes.length; i < n; ++i) {
			total += bytes[i].length;
		}
		
		final byte[] out = new byte[total];
		int len;
		
		for (int i = 0, j = 0, n = bytes.length; i < n; ++i) {
			len = bytes[i].length;
			
			System.arraycopy(bytes[i], 0, out, j, len);
			
			j += len;
		}
		
		return out;
	}
}

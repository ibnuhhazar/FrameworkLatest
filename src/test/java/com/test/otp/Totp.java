package com.test.otp;

import java.time.OffsetDateTime;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.util.DigestFactory;

import  com.test.otp.Bytes;

public class Totp {

	public static final int DEFAULT_OTP_LENGTH = 6;
	
	public static final int MAX_OTP_LENGTH = 9;
	
	public static final int DEFAULT_ADJACENT_INTERVALS = 3;
	
	public static final int DEFAULT_INTERVAL = 30;
	
	protected static final int[] DIGITS_POWER  = {
		1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000
	};
	
	protected static final String ZERO_PADS = "000000000";
	
	private static final Totp DEFAULT_INSTANCE = new Totp();
	
	private long startTime;
	
	private long interval;
	
	private int window;
	
	protected int otpLength;
	
	public static Totp createDefault() {
		return DEFAULT_INSTANCE;
	}
	
	public static Totp custom() {
		return new Totp();
	}
	
	public Totp() {
		this.startTime = 0L;
		
		this.interval = DEFAULT_INTERVAL;
		
		this.window = DEFAULT_ADJACENT_INTERVALS;
		
		this.otpLength = DEFAULT_OTP_LENGTH;
	}
	
	public Totp interval(long interval) {
		if (interval <= 0) {
			throw new IllegalArgumentException("interval must be positive: " + interval);
		}
		
		this.interval = interval;
		return this;
	}

	public Totp window(int window) {
		if (window <= 0) {
			throw new IllegalArgumentException("window time must be positive: " + window);
		}
		
		this.window = window;
		return this;
	}

	public Totp otpLength(int otpLength) {
		if ((otpLength <= 0) || (otpLength > MAX_OTP_LENGTH)) {
			throw new IllegalArgumentException("otp length must be between 1 and " + MAX_OTP_LENGTH);
		}
		
		this.otpLength = otpLength;
		return this;
	}
	
	public String generate(byte[] key, OffsetDateTime now, byte[] challenge) {
		return generate(newMac(key), getValueAtTime(now), challenge);
	}
	
	public boolean matches(byte[] key, OffsetDateTime now, byte[] challenge,
			CharSequence code) {
		
		return matches(key, now, challenge, window, window, code);
	}
	
	protected boolean matches(byte[] key, OffsetDateTime now, byte[] challenge,
			int deltaPast, int deltaFuture, CharSequence code) {
		
		return matches(newMac(key), getValueAtTime(now), challenge, deltaPast,
				deltaFuture, code);
	}
	
	protected HMac newMac(byte[] key) {
		final Digest digest = DigestFactory.createSHA1();
		if (digest == null) {
			return null;
		}
		
		final HMac mac = new HMac(digest);
		
		mac.init(new KeyParameter(key));
		
		return mac;
	}
	
	protected long getValueAtTime(OffsetDateTime now) {
		if (now == null) {
			throw new IllegalArgumentException("Time is required");
		}
		
		return getValueAtTime(now.toEpochSecond());
	}
	
	protected long getValueAtTime(long time) {
		if (time < 0) {
			throw new IllegalArgumentException(
					"Time must be zero or positive: " + time);
		}
		
		final long elapsed = time - startTime;
		if (elapsed >= 0) {
			return elapsed / interval;
		}
		
		return (elapsed - (interval - 1)) / interval;
	}
	
	protected String generate(HMac mac, long v, byte[] challenge) {
		final byte[] bytes;
		
		if ((challenge == null) || (challenge.length == 0)) {
			bytes = new byte[8];
		} else {
			bytes = new byte[8 + challenge.length];
			System.arraycopy(challenge, 0, bytes, 8, challenge.length);
		}
		
		Bytes.putLong(bytes, 0, v);
		return generate(mac, bytes);
	}
	
	protected String generate(HMac mac, byte[] v) {
		mac.update(v, 0, v.length);
		
		final byte[] hash = new byte[mac.getMacSize()];
		mac.doFinal(hash, 0);
		
		return hashToCode(hash, hash[hash.length - 1] & 0x0F);
	}
	
	protected String hashToCode(byte[] hash, int off) {
		final int intval = Bytes.getInt(hash, off) & 0x7FFFFFFF;
		
		return zeropad(Integer.toString(intval % DIGITS_POWER[otpLength]));
	}
	
	protected boolean matches(HMac mac, long v, byte[] challenge, int deltaPast,
			int deltaFuture, CharSequence code) {
		
		if (code == null) {
			return false;
		}
		if (code.length() != otpLength) {
			return false;
		}

		boolean matched = false;
		
		final byte[] bytes;
		
		if ((challenge == null) || (challenge.length == 0)) {
			bytes = new byte[8];
		} else {
			bytes = new byte[8 + challenge.length];
			System.arraycopy(challenge, 0, bytes, 8, challenge.length);
		}
		
		// pass 1
		if (matches(mac, bytes, v, code)) {
			return true;
		}
		
		// pass 2
		for (int i = 1; i <= deltaPast; ++i) {
			if (matches(mac, bytes, v - i, code)) {
				return true;
			}
		}
		
		// pass 3
		for (int i = 1; i <= deltaFuture; ++i) {
			if (matches(mac, bytes, v + i, code)) {
				return true;
			}
		}
		
		return matched;
	}
	
	private boolean matches(HMac mac, byte[] bytes, long v, CharSequence code) {
		Bytes.putLong(bytes, 0, v);
		
		return generate(mac, bytes).equals(code);
	}
	
	protected String zeropad(String value) {
		final int len = value.length();
		
		if (len == 0) {
			return ZERO_PADS;
		}
		if (len == otpLength) {
			return value;
		}
		if (len > otpLength) {
			return value.substring(0, otpLength);
		}
		
		return ZERO_PADS.substring(MAX_OTP_LENGTH - otpLength + len)
				.concat(value);
	}
}

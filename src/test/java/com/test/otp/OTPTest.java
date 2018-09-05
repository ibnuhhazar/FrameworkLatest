package com.test.otp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.apache.commons.codec.binary.Base32;
import org.junit.Ignore;
import org.junit.Test;

public class OTPTest {

	private byte[] secret;
	
	private byte[] secret2;
	
	private Totp totp;
	
	public String otp;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OTPTest otpt = new OTPTest();
		otpt.init();
		otpt.testA();
	}

	//@Before
	public void init() {
		secret = new Base32().decode("7777777777777777");
		
		secret2 = new Base32().decode("CJQJ5 NLDW RRYP HMNN".replace(" ", ""));
		
		totp = Totp.custom();
	}
	
	//@Test
	public void testA() {
		init();
		setOtp(totp.generate(secret2, OffsetDateTime.now(), null));
		//otp = totp.generate(secret2, OffsetDateTime.now(), null);
		System.out.println("Ini OTP : " + getOtp());
	}
	
	@Test
	public void testGenerateWithoutChallenge() {
		final OffsetDateTime now = OffsetDateTime.now();
		
		final String code = totp.generate(secret, now, null);
		
		assertTrue(totp.matches(secret, now, null, code));
		assertTrue(totp.matches(secret, now.plusMinutes(1), null, code));
		assertTrue(totp.matches(secret, now.minusMinutes(1), null, code));
		
		assertFalse(totp.matches(secret, now.plusMinutes(30), null, code));
		assertFalse(totp.matches(secret, now.minusMinutes(30), null, code));
		
		assertFalse(code, totp.matches(secret, now, null,
				"X".concat(code.substring(1))));
		assertFalse(totp.matches(secret, now, "aa".getBytes(StandardCharsets.UTF_8),
				code));
		
		final OffsetDateTime epoch = Instant.ofEpochMilli(0).atOffset(ZoneOffset.UTC);
		
		assertEquals("724477", totp.generate(secret, epoch, null));
	}
	
	@Test
	public void testGenerateWithChallenge() {
		final OffsetDateTime now = OffsetDateTime.now();
		final byte[] challenge = "sauhweyt".getBytes(StandardCharsets.UTF_8);
		
		final String code = totp.generate(secret, now, challenge);
		
		assertTrue(totp.matches(secret, now, challenge, code));
		assertTrue(totp.matches(secret, now.plusMinutes(1), challenge, code));
		assertTrue(totp.matches(secret, now.minusMinutes(1), challenge, code));
		
		assertFalse(totp.matches(secret, now.plusMinutes(30), challenge, code));
		assertFalse(totp.matches(secret, now.minusMinutes(30), challenge, code));
		
		assertFalse(code, totp.matches(secret, now, challenge,
				"X".concat(code.substring(1))));
		assertFalse(totp.matches(secret, now, null, code.substring(1)));
		assertFalse(totp.matches(secret, now, "aa".getBytes(StandardCharsets.UTF_8),
				code));
	}
	
	@Test
	@Ignore
	public void perfTestGenerateWithoutChallenge() {
		final OffsetDateTime now = OffsetDateTime.now();
		
		final int n = 1000000;
		final long start = System.nanoTime();
		
		for (int i = n; i > 0; --i) {
			totp.generate(secret, now, null);
		}
		
		final long end = System.nanoTime();
		
		System.out.println(n * 1000 / Duration.ofNanos(end - start).toMillis());
	}
	
	@Test
	@Ignore
	public void perfTestMatchesWithoutChallenge() {
		final OffsetDateTime now = OffsetDateTime.now();
		
		final String code = totp.generate(secret, now, null);
		
		final OffsetDateTime next = now.plusMinutes(0);
		final int n = 1000000;
		
		final long start = System.nanoTime();
		
		for (int i = n; i > 0; --i) {
			totp.matches(secret, next, null, code);
		}
		
		final long end = System.nanoTime();
		
		System.out.println(n * 1000 / Duration.ofNanos(end - start).toMillis());
	}
	
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

}

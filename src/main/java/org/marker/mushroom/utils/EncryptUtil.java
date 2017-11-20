package org.marker.mushroom.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * java支持的加密解密 <br>
 * 单向加密：MD5、SHA1 <br>
 * 双向加密：DES、AES <br>
 * 注意：本工具类不使用Base64转字符串，而是直接将byte[]转为16进制字符串
 * 
 * @author linin
 *
 */
public class EncryptUtil implements EncryptUtilApi
{

	public static final String KEY = "hansap_pasnah";

	public static void main(final String... arg)
	{
		final String res = "tt-123456";
		final String key = "hansap_pasnah";
		String mw = "密文，临时用的";
		/*
		 * System.out.println("--MD5--"); System.out.println(EncryptUtil.getInstance().MD5(res));
		 * System.out.println(EncryptUtil.getInstance().MD5(res,key)); System.out.println("--SHA1--");
		 * System.out.println(EncryptUtil.getInstance().SHA1(res));
		 * System.out.println(EncryptUtil.getInstance().SHA1(res,key)); System.out.println("--DES--"); mw =
		 * EncryptUtil.getInstance().DESencode(res,key); System.out.println(mw);
		 * System.out.println(EncryptUtil.getInstance().DESdecode(mw, key));
		 */
		System.out.println("--AES--");
		mw = EncryptUtil.getInstance().AESencode(res, key);
		mw = "446FB4D5584306231AB23D0D3A927922";
		System.out.println(mw);
		System.out.println(EncryptUtil.getInstance().AESdecode(mw, key));

		System.out.println("--异或加密--");
		mw = EncryptUtil.getInstance().XORencode(res, key);
		System.out.println(mw);
		System.out.println(EncryptUtil.getInstance().XORdecode(mw, key));
		final int i = 12345;
		final int ii = EncryptUtil.getInstance().XOR(i, key);
		final int iii = EncryptUtil.getInstance().XOR(ii, key);
		System.out.println(String.format(i + "异或一次：%s；异或两次：%s", ii, iii));
	}

	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA1";
	public static final String HmacMD5 = "HmacMD5";
	public static final String HmacSHA1 = "HmacSHA1";
	public static final String DES = "DES";
	public static final String AES = "AES";

	/** 编码格式；默认null为GBK */
	public String charset = null;
	/** DES */
	public int keysizeDES = 0;
	/** AES */
	public int keysizeAES = 128;
	public static EncryptUtil me;

	private EncryptUtil()
	{
		//单例
	}

	public static EncryptUtil getInstance()
	{
		if (me == null)
		{
			me = new EncryptUtil();
		}
		return me;
	}

	/** 使用MessageDigest进行单向加密（无密码） */
	private String messageDigest(final String res, final String algorithm)
	{
		try
		{
			final MessageDigest md = MessageDigest.getInstance(algorithm);
			final byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
			return base64(md.digest(resBytes));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/** 使用KeyGenerator进行单向/双向加密（可设密码） */
	private String keyGeneratorMac(final String res, final String algorithm, final String key)
	{
		try
		{
			SecretKey sk = null;
			if (key == null)
			{
				final KeyGenerator kg = KeyGenerator.getInstance(algorithm);
				sk = kg.generateKey();
			}
			else
			{
				final byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
				sk = new SecretKeySpec(keyBytes, algorithm);
			}
			final Mac mac = Mac.getInstance(algorithm);
			mac.init(sk);
			final byte[] result = mac.doFinal(res.getBytes());
			return base64(result);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/** 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错 */
	private String keyGeneratorES(final String res, final String algorithm, final String key, final int keysize,
			final boolean isEncode)
	{
		try
		{
			final KeyGenerator kg = KeyGenerator.getInstance(algorithm);
			//解决linux下解密报错问题 javax.crypto.BadPaddingException: Given final block not properly padded
			final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(charset == null ? key.getBytes() : key.getBytes(charset));

			if (keysize == 0)
			{
				final byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
				kg.init(random);
			}
			else if (key == null)
			{
				kg.init(keysize);
			}
			else
			{
				final byte[] keyBytes = charset == null ? key.getBytes() : key.getBytes(charset);
				kg.init(keysize, random);
			}
			final SecretKey sk = kg.generateKey();
			final SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
			final Cipher cipher = Cipher.getInstance(algorithm);
			if (isEncode)
			{
				cipher.init(Cipher.ENCRYPT_MODE, sks);
				final byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
				return parseByte2HexStr(cipher.doFinal(resBytes));
			}
			else
			{
				cipher.init(Cipher.DECRYPT_MODE, sks);
				return new String(cipher.doFinal(parseHexStr2Byte(res)));
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private String base64(final byte[] res)
	{
		return Base64.encode(res);
	}

	/** 将二进制转换成16进制 */
	public static String parseByte2HexStr(final byte[] buf)
	{
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/** 将16进制转换为二进制 */
	public static byte[] parseHexStr2Byte(final String hexStr)
	{
		if (hexStr.length() < 1)
			return null;
		final byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			final int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			final int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	@Override
	public String MD5(final String res)
	{
		return messageDigest(res, MD5);
	}

	@Override
	public String MD5(final String res, final String key)
	{
		return keyGeneratorMac(res, HmacMD5, key);
	}

	@Override
	public String SHA1(final String res)
	{
		return messageDigest(res, SHA1);
	}

	@Override
	public String SHA1(final String res, final String key)
	{
		return keyGeneratorMac(res, HmacSHA1, key);
	}

	@Override
	public String DESencode(final String res, final String key)
	{
		return keyGeneratorES(res, DES, key, keysizeDES, true);
	}

	@Override
	public String DESdecode(final String res, final String key)
	{
		return keyGeneratorES(res, DES, key, keysizeDES, false);
	}

	@Override
	public String AESencode(final String res, final String key)
	{
		return keyGeneratorES(res, AES, key, keysizeAES, true);
	}

	@Override
	public String AESdecode(final String res, final String key)
	{
		return keyGeneratorES(res, AES, key, keysizeAES, false);
	}

	@Override
	public String XORencode(final String res, final String key)
	{
		final byte[] bs = res.getBytes();
		for (int i = 0; i < bs.length; i++)
		{
			bs[i] = (byte) ((bs[i]) ^ key.hashCode());
		}
		return parseByte2HexStr(bs);
	}

	@Override
	public String XORdecode(final String res, final String key)
	{
		final byte[] bs = parseHexStr2Byte(res);
		for (int i = 0; i < bs.length; i++)
		{
			bs[i] = (byte) ((bs[i]) ^ key.hashCode());
		}
		return new String(bs);
	}

	@Override
	public int XOR(final int res, final String key)
	{
		return res ^ key.hashCode();
	}

}

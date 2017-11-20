package org.marker.mushroom.utils;

import java.util.UUID;


/**
 * Created by dongbin on 2017/4/7.
 */
public class GenerateUUID
{

	public GenerateUUID()
	{

	}

	public static String getUUID()
	{
		final UUID uuid = UUID.randomUUID();
		final String str = uuid.toString();
		// 去掉"-"符号
		final String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	//获得指定数量的UUID
	public static String[] getUUID(final int number)
	{
		if (number < 1)
		{
			return null;
		}
		final String[] ss = new String[number];
		for (int i = 0; i < number; i++)
		{
			ss[i] = getUUID();
		}
		return ss;
	}
}

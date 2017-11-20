package org.marker.mushroom.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 日期、时间工具类
 * 
 * @author marker
 * @date 2014-08-02
 */
public class DateUtils
{


	/**
	 * 获取当前剩余时间秒数
	 * 
	 * @return
	 */
	public static int getCurentDayRemainingTime()
	{
		final Calendar c = Calendar.getInstance();
		final Date d = c.getTime();

		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		final Date d2 = c.getTime();
		return (int) ((d2.getTime() - d.getTime()) / 60 / 60 / 1000);
	}

	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

	private static final Object object = new Object();

	/**
	 * 获取SimpleDateFormat
	 *
	 * @param pattern
	 *           日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *            异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(final String pattern) throws RuntimeException
	{
		SimpleDateFormat dateFormat = threadLocal.get();
		synchronized (object)
		{
			if (dateFormat == null)
			{
				dateFormat = new SimpleDateFormat(pattern);
				dateFormat.setLenient(false);
				threadLocal.set(dateFormat);
			}
		}

		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 *
	 * @param date
	 *           日期
	 * @param dateType
	 *           日期格式
	 * @return 数值
	 */
	private static int getInteger(final Date date, final int dateType)
	{
		int num = 0;
		final Calendar calendar = Calendar.getInstance();
		if (date != null)
		{
			calendar.setTime(date);
			num = calendar.get(dateType);
		}
		return num;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *           日期字符串
	 * @param dateType
	 *           类型
	 * @param amount
	 *           数值
	 * @return 计算后日期字符串
	 */
	private static String addInteger(final String date, final int dateType, final int amount)
	{
		String dateString = null;
		final DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null)
		{
			Date myDate = stringToDate(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = dateToString(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 *
	 * @param date
	 *           日期
	 * @param dateType
	 *           类型
	 * @param amount
	 *           数值
	 * @return 计算后日期
	 */
	private static Date addInteger(final Date date, final int dateType, final int amount)
	{
		Date myDate = null;
		if (date != null)
		{
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 获取精确的日期
	 *
	 * @param timestamps
	 *           时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(final List<Long> timestamps)
	{
		Date date = null;
		long timestamp = 0;
		final Map<Long, long[]> map = new HashMap<Long, long[]>();
		final List<Long> absoluteValues = new ArrayList<Long>();

		if (timestamps != null && timestamps.size() > 0)
		{
			if (timestamps.size() > 1)
			{
				for (int i = 0; i < timestamps.size(); i++)
				{
					for (int j = i + 1; j < timestamps.size(); j++)
					{
						final long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
						absoluteValues.add(absoluteValue);
						final long[] timestampTmp =
						{ timestamps.get(i), timestamps.get(j) };
						map.put(absoluteValue, timestampTmp);
					}
				}

				// 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
				// 因此不能将minAbsoluteValue取默认值0
				long minAbsoluteValue = -1;
				if (!absoluteValues.isEmpty())
				{
					minAbsoluteValue = absoluteValues.get(0);
					for (int i = 1; i < absoluteValues.size(); i++)
					{
						if (minAbsoluteValue > absoluteValues.get(i))
						{
							minAbsoluteValue = absoluteValues.get(i);
						}
					}
				}

				if (minAbsoluteValue != -1)
				{
					final long[] timestampsLastTmp = map.get(minAbsoluteValue);

					final long dateOne = timestampsLastTmp[0];
					final long dateTwo = timestampsLastTmp[1];
					if (absoluteValues.size() > 1)
					{
						timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
					}
				}
			}
			else
			{
				timestamp = timestamps.get(0);
			}
		}

		if (timestamp != 0)
		{
			date = new Date(timestamp);
		}
		return date;
	}

	/**
	 * 判断字符串是否为日期字符串
	 *
	 * @param date
	 *           日期字符串
	 * @return true or false
	 */
	public static boolean isDate(final String date)
	{
		boolean isDate = false;
		if (date != null)
		{
			if (getDateStyle(date) != null)
			{
				isDate = true;
			}
		}
		return isDate;
	}

	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(final String date)
	{
		DateStyle dateStyle = null;
		final Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		final List<Long> timestamps = new ArrayList<Long>();
		for (final DateStyle style : DateStyle.values())
		{
			if (style.isShowOnly())
			{
				continue;
			}
			Date dateTmp = null;
			if (date != null)
			{
				try
				{
					final ParsePosition pos = new ParsePosition(0);
					dateTmp = getDateFormat(style.getValue()).parse(date, pos);
					if (pos.getIndex() != date.length())
					{
						dateTmp = null;
					}
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
			if (dateTmp != null)
			{
				timestamps.add(dateTmp.getTime());
				map.put(dateTmp.getTime(), style);
			}
		}
		final Date accurateDate = getAccurateDate(timestamps);
		if (accurateDate != null)
		{
			dateStyle = map.get(accurateDate.getTime());
		}
		return dateStyle;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 日期
	 */
	public static Date stringToDate(final String date)
	{
		final DateStyle dateStyle = getDateStyle(date);
		return stringToDate(date, dateStyle);
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @param pattern
	 *           日期格式
	 * @return 日期
	 */
	public static Date stringToDate(final String date, final String pattern)
	{
		Date myDate = null;
		if (date != null)
		{
			try
			{
				myDate = getDateFormat(pattern).parse(date);
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}
		return myDate;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @param dateStyle
	 *           日期风格
	 * @return 日期
	 */
	public static Date stringToDate(final String date, final DateStyle dateStyle)
	{
		Date myDate = null;
		if (dateStyle != null)
		{
			myDate = stringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param pattern
	 *           日期格式
	 * @return 日期字符串
	 */
	public static String dateToString(final Date date, final String pattern)
	{
		String dateString = null;
		if (date != null)
		{
			try
			{
				dateString = getDateFormat(pattern).format(date);
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}
		return dateString;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param dateStyle
	 *           日期风格
	 * @return 日期字符串
	 */
	public static String dateToString(final Date date, final DateStyle dateStyle)
	{
		String dateString = null;
		if (dateStyle != null)
		{
			dateString = dateToString(date, dateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *           旧日期字符串
	 * @param newPattern
	 *           新日期格式
	 * @return 新日期字符串
	 */
	public static String stringToString(final String date, final String newPattern)
	{
		final DateStyle oldDateStyle = getDateStyle(date);
		return stringToString(date, oldDateStyle, newPattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *           旧日期字符串
	 * @param newDateStyle
	 *           新日期风格
	 * @return 新日期字符串
	 */
	public static String stringToString(final String date, final DateStyle newDateStyle)
	{
		final DateStyle oldDateStyle = getDateStyle(date);
		return stringToString(date, oldDateStyle, newDateStyle);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *           旧日期字符串
	 * @param olddPattern
	 *           旧日期格式
	 * @param newPattern
	 *           新日期格式
	 * @return 新日期字符串
	 */
	public static String stringToString(final String date, final String olddPattern, final String newPattern)
	{
		return dateToString(stringToDate(date, olddPattern), newPattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *           旧日期字符串
	 * @param olddDteStyle
	 *           旧日期风格
	 * @param newParttern
	 *           新日期格式
	 * @return 新日期字符串
	 */
	public static String stringToString(final String date, final DateStyle olddDteStyle, final String newParttern)
	{
		String dateString = null;
		if (olddDteStyle != null)
		{
			dateString = stringToString(date, olddDteStyle.getValue(), newParttern);
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *           旧日期字符串
	 * @param olddPattern
	 *           旧日期格式
	 * @param newDateStyle
	 *           新日期风格
	 * @return 新日期字符串
	 */
	public static String stringToString(final String date, final String olddPattern, final DateStyle newDateStyle)
	{
		String dateString = null;
		if (newDateStyle != null)
		{
			dateString = stringToString(date, olddPattern, newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 *
	 * @param date
	 *           旧日期字符串
	 * @param olddDteStyle
	 *           旧日期风格
	 * @param newDateStyle
	 *           新日期风格
	 * @return 新日期字符串
	 */
	public static String stringToString(final String date, final DateStyle olddDteStyle, final DateStyle newDateStyle)
	{
		String dateString = null;
		if (olddDteStyle != null && newDateStyle != null)
		{
			dateString = stringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param yearAmount
	 *           增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(final String date, final int yearAmount)
	{
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param yearAmount
	 *           增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(final Date date, final int yearAmount)
	{
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param monthAmount
	 *           增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static String addMonth(final String date, final int monthAmount)
	{
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param monthAmount
	 *           增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(final Date date, final int monthAmount)
	{
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @param dayAmount
	 *           增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(final String date, final int dayAmount)
	{
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param dayAmount
	 *           增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDay(final Date date, final int dayAmount)
	{
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @param hourAmount
	 *           增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(final String date, final int hourAmount)
	{
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param hourAmount
	 *           增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(final Date date, final int hourAmount)
	{
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @param minuteAmount
	 *           增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(final String date, final int minuteAmount)
	{
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param minuteAmount
	 *           增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(final Date date, final int minuteAmount)
	{
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @param secondAmount
	 *           增加数量。可为负数
	 * @return 增加秒钟后的日期字符串
	 */
	public static String addSecond(final String date, final int secondAmount)
	{
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @param secondAmount
	 *           增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(final Date date, final int secondAmount)
	{
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *           日期字符串
	 * @return 年份
	 */
	public static int getYear(final String date)
	{
		return getYear(stringToDate(date));
	}

	/**
	 * 获取日期的年份。失败返回0。
	 *
	 * @param date
	 *           日期
	 * @return 年份
	 */
	public static int getYear(final Date date)
	{
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *           日期字符串
	 * @return 月份
	 */
	public static int getMonth(final String date)
	{
		return getMonth(stringToDate(date));
	}

	/**
	 * 获取日期的月份。失败返回0。
	 *
	 * @param date
	 *           日期
	 * @return 月份
	 */
	public static int getMonth(final Date date)
	{
		return getInteger(date, Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *           日期字符串
	 * @return 天
	 */
	public static int getDay(final String date)
	{
		return getDay(stringToDate(date));
	}

	/**
	 * 获取日期的天数。失败返回0。
	 *
	 * @param date
	 *           日期
	 * @return 天
	 */
	public static int getDay(final Date date)
	{
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *           日期字符串
	 * @return 小时
	 */
	public static int getHour(final String date)
	{
		return getHour(stringToDate(date));
	}

	/**
	 * 获取日期的小时。失败返回0。
	 *
	 * @param date
	 *           日期
	 * @return 小时
	 */
	public static int getHour(final Date date)
	{
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *           日期字符串
	 * @return 分钟
	 */
	public static int getMinute(final String date)
	{
		return getMinute(stringToDate(date));
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 *
	 * @param date
	 *           日期
	 * @return 分钟
	 */
	public static int getMinute(final Date date)
	{
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *           日期字符串
	 * @return 秒钟
	 */
	public static int getSecond(final String date)
	{
		return getSecond(stringToDate(date));
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 *
	 * @param date
	 *           日期
	 * @return 秒钟
	 */
	public static int getSecond(final Date date)
	{
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 日期
	 */
	public static String getDate(final String date)
	{
		return stringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @return 日期
	 */
	public static String getDate(final Date date)
	{
		return dateToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 时间
	 */
	public static String getTime(final String date)
	{
		return stringToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @return 时间
	 */
	public static String getTime(final Date date)
	{
		return dateToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认yyyy-MM-dd HH:mm格式。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 时间
	 */
	public static String getFullMinute(final String date)
	{
		return stringToString(date, DateStyle.YYYY_MM_DD_HH_MM);
	}

	/**
	 * 获取日期的时间。默认yyyy-MM-dd HH:mm格式。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @return 时间
	 */
	public static String getFullMinute(final Date date)
	{
		return dateToString(date, DateStyle.YYYY_MM_DD_HH_MM);
	}

	/**
	 * 获取日期的时间。默认yyyy-MM-dd HH格式。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 时间
	 */
	public static String getFullHour(final String date)
	{
		return stringToString(date, DateStyle.YYYY_MM_DD_HH);
	}

	/**
	 * 获取日期的时间。默认yyyy-MM-dd HH格式。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @return 时间
	 */
	public static String getFullHour(final Date date)
	{
		return dateToString(date, DateStyle.YYYY_MM_DD_HH);
	}

	/**
	 * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 日期
	 */
	public static String getFullDay(final String date)
	{
		return stringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @return 日期
	 */
	public static String getFullDay(final Date date)
	{
		return dateToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date
	 *           日期字符串
	 * @return 星期
	 */
	public static Week getWeek(final String date)
	{
		Week week = null;
		final DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null)
		{
			final Date myDate = stringToDate(date, dateStyle);
			week = getWeek(myDate);
		}
		return week;
	}

	/**
	 * 获取日期的星期。失败返回null。
	 *
	 * @param date
	 *           日期
	 * @return 星期
	 */
	public static Week getWeek(final Date date)
	{
		Week week = null;
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		final int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (weekNumber)
		{
			case 0:
				week = Week.SUNDAY;
				break;
			case 1:
				week = Week.MONDAY;
				break;
			case 2:
				week = Week.TUESDAY;
				break;
			case 3:
				week = Week.WEDNESDAY;
				break;
			case 4:
				week = Week.THURSDAY;
				break;
			case 5:
				week = Week.FRIDAY;
				break;
			case 6:
				week = Week.SATURDAY;
				break;
		}
		return week;
	}

	/**
	 * 获取两个日期相差的天数
	 *
	 * @param date
	 *           日期字符串
	 * @param otherDate
	 *           另一个日期字符串
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(final String date, final String otherDate)
	{
		return getIntervalDays(stringToDate(date), stringToDate(otherDate));
	}

	/**
	 * @param date
	 *           日期
	 * @param otherDate
	 *           另一个日期
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(final Date date, final Date otherDate)
	{
		int num = -1;
		final Date dateTmp = DateUtils.stringToDate(DateUtils.getFullDay(date), DateStyle.YYYY_MM_DD);
		final Date otherDateTmp = DateUtils.stringToDate(DateUtils.getFullDay(otherDate), DateStyle.YYYY_MM_DD);
		if (dateTmp != null && otherDateTmp != null)
		{
			final long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (24 * 60 * 60 * 1000));
		}
		return num;
	}

	/**
	 * @param date
	 *           日期
	 * @param otherDate
	 *           另一个日期
	 * @return 相差分钟。如果失败则返回-1
	 */
	public static int getIntervalHour(final Date date, final Date otherDate)
	{
		int num = -1;
		final Date dateTmp = DateUtils.stringToDate(DateUtils.getFullHour(date), DateStyle.YYYY_MM_DD_HH);
		final Date otherDateTmp = DateUtils.stringToDate(DateUtils.getFullHour(otherDate), DateStyle.YYYY_MM_DD_HH);
		if (dateTmp != null && otherDateTmp != null)
		{
			final long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (1000 * 60 * 60));
		}
		return num;
	}

	/**
	 * @param date
	 *           日期
	 * @param otherDate
	 *           另一个日期
	 * @return 相差分钟。如果失败则返回-1
	 */
	public static int getIntervalMinute(final Date date, final Date otherDate)
	{
		int num = -1;
		final Date dateTmp = DateUtils.stringToDate(DateUtils.getFullMinute(date), DateStyle.YYYY_MM_DD_HH_MM);
		final Date otherDateTmp = DateUtils.stringToDate(DateUtils.getFullMinute(otherDate), DateStyle.YYYY_MM_DD_HH_MM);
		if (dateTmp != null && otherDateTmp != null)
		{
			final long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (1000 * 60));
		}
		return num;
	}

	public static String dateFormat(final Date date)
	{
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(date);
	}

	/**
	 *
	 * @param date
	 * @return 根据年月，返回月份有多少天
	 */
	public static int getDaysOfMonth(final Date date)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 *
	 * @param date
	 * @return 根据年月，返回月份有多少天
	 */
	public static int getDaysOfMonth(final String date)
	{
		return getDaysOfMonth(stringToDate(date));
	}

}

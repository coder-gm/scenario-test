package cn.com.common.utils.date;

import cn.com.common.handle.exception.BusinessException;
import cn.com.common.model.enums.DateFormatEnum;
import cn.com.common.model.response.FinalValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: cloud-terrace
 * @description: 日期时间工具类
 * @author: gm.Zhang
 * @create: 2020-06-25 00:56
 **/
@Slf4j
public class DateTimeUtils {


    private static Date getDateAdd(int days) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        return c.getTime();
    }

    /**
     * 获取多少天日期
     *
     * @param days 最近几天日期
     * @return
     */
    public static List<String> getDaysBetwwen(int days) {
        List<String> dayss = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(getDateAdd(days));
        Long startTIme = start.getTimeInMillis();
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        Long endTime = end.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24L;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(df.format(d));
            dayss.add(df.format(d));
            time += oneDay;
        }
        return dayss;
    }


    /**
     * 获取当前时间的时间戳
     *
     * @param timeType 0:秒 1:毫秒
     * @return
     */
    public static long getCurrentTimestamp(String timeType) {
        if (FinalValue.ZERO.equals(timeType)) {
            return Instant.now().getEpochSecond();
        } else if (FinalValue.ONE.equals(timeType)) {
            return Instant.now().toEpochMilli();
        }
        return Long.parseLong(null);
    }

    /**
     * 获取当前日期时间
     *
     * @param format 自定义的格式类型
     * @return
     */
    public static String getCurrentDateTime(DateFormatEnum format) {
        return DateTimeFormatter.ofPattern(format.getFormat()).format(LocalDateTime.now());
    }

    /**
     * 获取当前日期时间
     * <p>
     * Date 格式
     *
     * @param timeType 0:年月日 ,1:时分秒 ,2:年月日时分秒
     * @return Object 需要强制类型转换
     */
    public static Object getCurrentDateTime(Integer timeType) {
        if (FinalValue.ZERO.equals(timeType)) {
            String currentDateTimeStr = DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT_DAY.getFormat()).format(LocalDate.now());
            return LocalDate.parse(currentDateTimeStr, DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT_DAY.getFormat()));
        } else if (FinalValue.ONE.equals(timeType)) {
            String currentDateTimeStr = DateTimeFormatter.ofPattern(DateFormatEnum.HOUR_MINUTE_SECONDS.getFormat()).format(LocalTime.now());
            return LocalTime.parse(currentDateTimeStr, DateTimeFormatter.ofPattern(DateFormatEnum.HOUR_MINUTE_SECONDS.getFormat()));
        } else if (FinalValue.TWO.equals(timeType)) {
            String currentDateTimeStr = DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT.getFormat()).format(LocalDateTime.now());
            return LocalDateTime.parse(currentDateTimeStr, DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT.getFormat()));
        }
        throw new BusinessException("请输入获取的日期格式!!");
    }


    /**
     * String 时间类型转为Date时间类型
     * <p>
     * Date 格式
     *
     * @param timeType    0:年月日时分秒 ,1:时分秒 ,2:年月日
     * @param dateTimeStr 时间字符串
     * @return Object 需要强制类型转换
     */
    public static Object getDateTimeStrDate(Integer timeType, String dateTimeStr) {
        if (FinalValue.ZERO.equals(timeType)) {
            //String时间格式转为Date时间格式
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT.getFormat()));
        } else if (FinalValue.ONE.equals(timeType)) {
            LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT.getFormat()));
            StringBuffer sb = new StringBuffer();
            sb.append(ldt.getHour()).append(":").append(ldt.getMinute()).append(":").append(ldt.getSecond());
            //String时间格式转为Date时间格式
            return LocalTime.parse(sb, DateTimeFormatter.ofPattern(DateFormatEnum.HOUR_MINUTE_SECONDS.getFormat()));
        } else if (FinalValue.TWO.equals(timeType)) {
            LocalDateTime ldt = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT.getFormat()));
            StringBuffer sb = new StringBuffer();
            if (ldt.getMonthValue() <= FinalValue.NINE) {
                sb.append(ldt.getYear()).append("-0").append(ldt.getMonthValue()).append("-").append(ldt.getDayOfMonth());
            } else {
                sb.append(ldt.getYear()).append("-").append(ldt.getMonthValue()).append("-").append(ldt.getDayOfMonth());
            }
            //String时间格式转为Date时间格式
            return LocalDate.parse(sb, DateTimeFormatter.ofPattern(DateFormatEnum.FORMAT_DAY.getFormat()));
        }
        throw new BusinessException("请输入获取的日期格式!!");
    }


    /**
     * Date 格式转为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        //方法获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(date.toInstant(), zoneId);
    }

    /**
     * LocalDateTime 格式转为Date
     *
     * @param ldt
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime ldt) {
        //方法获取系统默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = ldt.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 时间戳转换 LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime timestampToLocalDateTIme(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    /**
     * 将LocalDateTime转为时间戳
     *
     * @param ldt   日期时间
     * @param level 0:毫秒  1:秒
     * @return
     */
    public static long localDateTImeToTimestamp(LocalDateTime ldt, Integer level) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = ldt.atZone(zone).toInstant();
        if (FinalValue.ZERO.equals(level)) {
            return instant.toEpochMilli();
        } else if (FinalValue.ONE.equals(level)) {
            return instant.toEpochMilli();
        }
        return Long.parseLong(null);
    }


    /**
     * LocalDateTime 转换成自己想要的格式
     *
     * @param ldt
     * @param format
     * @return
     */
    public static String localDateTimeFormat(LocalDateTime ldt, DateFormatEnum format) {
        return DateTimeFormatter.ofPattern(format.getFormat()).format(ldt);
    }

    public static void main(String[] args) {
        LocalDateTime dateTimeStrDate = (LocalDateTime) DateTimeUtils.getDateTimeStrDate(0, "2020-08-17 07:51:54");
        System.out.println("===============>:{}" + DateTimeUtils.localDateTimeFormat(dateTimeStrDate, DateFormatEnum.FORMAT_DAY));
    }

    /**
     * 获取特殊日期
     */
    public static class GetSpecialDate {
        /**
         * 获取下一个工作日
         *
         * @param localDateTime
         * @return
         */
        public static LocalDateTime nextWorkday(LocalDateTime localDateTime) {
            return localDateTime.with((time) -> {
                LocalDateTime ldt = (LocalDateTime) time;
                DayOfWeek dayOfWeek = ldt.getDayOfWeek();
                if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                    return ldt.plusDays(3);
                } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                    return ldt.plusDays(2);
                } else {
                    return ldt.plusDays(1);
                }
            });
        }

        /**
         * @param ldt
         * @param level
         * @return
         */
        public static LocalDateTime getDay(LocalDateTime ldt, Integer level) {
            if (FinalValue.ZERO.equals(level)) {
                //当前月的第一天
                return ldt.with(TemporalAdjusters.firstDayOfMonth());
            } else if (FinalValue.ONE.equals(level)) {
                //当月的最后一天
                return ldt.with(TemporalAdjusters.lastDayOfMonth());
            } else if (FinalValue.TWO.equals(level)) {
                //下一个月的第一天
                return ldt.with(TemporalAdjusters.firstDayOfNextMonth());
            } else if (FinalValue.THREE.equals(level)) {
                //下一年的第一天
                return ldt.with(TemporalAdjusters.firstDayOfNextYear());
            } else if (FinalValue.FOUR.equals(level)) {
                //当年的第一天
                return ldt.with(TemporalAdjusters.firstDayOfYear());
            }
            throw new BusinessException("请输入格式类型!!");
        }


        /**
         * 获取下一个周几日期
         * <p>
         * 注意:下个星期五的日期  如果今天是星期五，则返回下(上)周五的日期
         *
         * @param ldt
         * @param dayOfWeek
         * @param level
         * @return
         */
        public static LocalDateTime nextOrPrevious(LocalDateTime ldt, DayOfWeek dayOfWeek, Integer level) {
            if (FinalValue.ZERO.equals(level)) {
                //获取下一个周几的日期
                return ldt.with(TemporalAdjusters.next(dayOfWeek));
            } else if (FinalValue.ONE.equals(level)) {
                //获取上一个周几的日期
                return ldt.with(TemporalAdjusters.previous(dayOfWeek));
            }
            throw new BusinessException("请输入格式类型!!");
        }


        /**
         * 获取下一个周几日期
         * <p>
         * 注意:(往后包括当天)最近星期五的日期  如果今天是星期五，则返回今天日期
         *
         * @param ldt
         * @param dayOfWeek
         * @return
         */
        public static LocalDateTime nextOrSameAndPreviousOrSame(LocalDateTime ldt, DayOfWeek dayOfWeek, Integer level) {
            if (FinalValue.ZERO.equals(level)) {
                //获取下一个周几的日期(包括当日)
                return ldt.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            } else if (FinalValue.ONE.equals(level)) {
                //获取上一个周几的日期(包括当日)
                return ldt.with(TemporalAdjusters.previousOrSame(dayOfWeek));
            }
            throw new BusinessException("请输入格式类型!!");
        }

        /**
         * 查询当前月份在第几周 ,星期几的日期
         *
         * @param ldt       时间
         * @param ordinal   周数(可为负数,负数就是上个月的)
         * @param dayOfWeek 周几
         * @return
         */
        public static LocalDateTime dayOfWeekInMonth(LocalDateTime ldt, Integer ordinal, DayOfWeek dayOfWeek) {
            return ldt.with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek));
        }

    }

    /**
     * 比较时间
     * <p>
     * 两个时间差
     */
    public static class DateTimeBetween {


        /**
         * 比较两个时间是否相等
         * <p>
         * yyyy-MM-dd hh:mm:ss
         * <p>
         * date1.compareTo(date2) == 0相同，
         * -1 date1<date2
         * 1 data1>date2
         *
         * @param time1
         * @param time2
         * @return
         * @throws ParseException
         */
        public static Integer compare(String time1, String time2, String formatStr) {
            //如果想比较日期则写成"yyyy-MM-dd"就可以了
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            //将字符串形式的时间转化为Date类型的时间
            Date a = null;
            Date b = null;
            try {
                a = sdf.parse(time1);
                b = sdf.parse(time2);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (a.compareTo(b) == 0) {
                return 0;
            } else if (a.compareTo(b) == -1) {
                return -1;
            } else if (a.compareTo(b) == 1) {
                return 1;
            }

            return -9999;
        }


        /**
         * 时间戳比较
         * <p>
         * 间隔秒:duration.getSeconds()
         * 间隔纳秒 duration.getNano()
         * 间隔分钟: duration.toMinutes()
         * 间隔天: duration.toDays()
         * 间隔小时: duration.toHours()
         *
         * @param instant0 时间戳 1
         * @param instant1 时间戳 2
         * @return
         */
        public static Duration timestampBetween(Instant instant0, Instant instant1) {
            return Duration.between(instant0, instant1);
        }

        /**
         * 两个LocalTime时间比较
         * <p>
         * 间隔秒: duration.getSeconds();
         * 间隔纳秒: duration.getNano();
         * 间隔纳秒: duration.toNanos();
         * 间隔分钟: duration.toMinutes();
         * 间隔天: duration.toDays();
         * 间隔小时: duration.toHours();
         *
         * @param localDateTime0
         * @param localDateTime1
         * @return
         */
        public static Duration localDateTimeBetween(LocalDateTime localDateTime0, LocalDateTime localDateTime1) {
            return Duration.between(localDateTime0, localDateTime1);
        }


        /**
         * 两个LocalTime时间比较
         * <p>
         * 间隔秒: duration.getSeconds();
         * 间隔纳秒: duration.getNano();
         * 间隔纳秒: duration.toNanos();
         * 间隔分钟: duration.toMinutes();
         * 间隔天: duration.toDays();
         * 间隔小时: duration.toHours();
         *
         * @param localTime0
         * @param localTime1
         * @return
         */
        public static Duration localTimeBetween(LocalTime localTime0, LocalTime localTime1) {
            return Duration.between(localTime0, localTime1);
        }


        /**
         * 间隔年: period.getYears();
         * 间隔月: period.getMonths();
         * 间隔日: period.getDays();
         * 间隔秒: period.getChronology();
         * 间隔秒: period.getUnits();
         *
         * @param localDate0
         * @param localDate1
         * @return
         */
        public static Period localDateBetween(LocalDate localDate0, LocalDate localDate1) {
            return Period.between(localDate0, localDate1);
        }

    }


    /**
     * 时间加减
     */
    public static class DateTimeAddSubtract {

        /**
         * 当前时间 加法
         *
         * @param level 0:年 1:月 2:日 4:小时 5:周 6:分钟 7:秒 8:纳秒
         * @param num   数目
         * @return
         */
        public static LocalDateTime addCurrentDateTime(Integer level, long num) {
            LocalDateTime localDateTime = LocalDateTime.now();
            if (FinalValue.ZERO.equals(level)) {
                return localDateTime.plusYears(num);
            } else if (FinalValue.ONE.equals(level)) {
                return localDateTime.plusMonths(num);
            } else if (FinalValue.TWO.equals(level)) {
                return localDateTime.plusDays(num);
            } else if (FinalValue.THREE.equals(level)) {
                return localDateTime.plusHours(num);
            } else if (FinalValue.FOUR.equals(level)) {
                return localDateTime.plusWeeks(num);
            } else if (FinalValue.SIX.equals(level)) {
                return localDateTime.plusMinutes(num);
            } else if (FinalValue.SEVEN.equals(level)) {
                return localDateTime.plusSeconds(num);
            } else if (FinalValue.EIGHT.equals(level)) {
                return localDateTime.plusNanos(num);
            }
            throw new BusinessException("请输入格式类型!!");
        }

        /**
         * 当前时间 减法
         *
         * @param level 0:年 1:月 2:日 4:小时 5:周 6:分钟 7:秒 8:纳秒
         * @param num   数目
         * @return
         */
        public static LocalDateTime subtractCurrentDateTime(Integer level, long num) {
            LocalDateTime localDateTime = LocalDateTime.now();
            if (FinalValue.ZERO.equals(level)) {
                return localDateTime.minusYears(num);
            } else if (FinalValue.ONE.equals(level)) {
                return localDateTime.minusMonths(num);
            } else if (FinalValue.TWO.equals(level)) {
                return localDateTime.minusDays(num);
            } else if (FinalValue.THREE.equals(level)) {
                return localDateTime.minusHours(num);
            } else if (FinalValue.FOUR.equals(level)) {
                return localDateTime.minusWeeks(num);
            } else if (FinalValue.SIX.equals(level)) {
                return localDateTime.minusMinutes(num);
            } else if (FinalValue.SEVEN.equals(level)) {
                return localDateTime.minusSeconds(num);
            } else if (FinalValue.EIGHT.equals(level)) {
                return localDateTime.minusNanos(num);
            }
            throw new BusinessException("请输入格式类型!!");
        }


        /**
         * 当前时间 加法
         *
         * @param level 0:年 1:月 2:日 4:小时 5:周 6:分钟 7:秒 8:纳秒
         * @param num   数目
         * @return
         */
        public static LocalDateTime addCustomDateTime(Integer level, long num, DateTimeModel dateTimeModel) {
            LocalDateTime localDateTime = LocalDateTime.of(dateTimeModel.getYear(),
                    dateTimeModel.getMonth(),
                    dateTimeModel.getDay(),
                    dateTimeModel.getHour(),
                    dateTimeModel.getMinute(),
                    dateTimeModel.getSecond(),
                    dateTimeModel.getNanos());
            if (FinalValue.ZERO.equals(level)) {
                return localDateTime.plusYears(num);
            } else if (FinalValue.ONE.equals(level)) {
                return localDateTime.plusMonths(num);
            } else if (FinalValue.TWO.equals(level)) {
                return localDateTime.plusDays(num);
            } else if (FinalValue.THREE.equals(level)) {
                return localDateTime.plusHours(num);
            } else if (FinalValue.FOUR.equals(level)) {
                return localDateTime.plusWeeks(num);
            } else if (FinalValue.SIX.equals(level)) {
                return localDateTime.plusMinutes(num);
            } else if (FinalValue.SEVEN.equals(level)) {
                return localDateTime.plusSeconds(num);
            } else if (FinalValue.EIGHT.equals(level)) {
                return localDateTime.plusNanos(num);
            }
            throw new BusinessException("请输入格式类型!!");
        }

        /**
         * 当前时间 减法
         *
         * @param level 0:年 1:月 2:日 4:小时 5:周 6:分钟 7:秒 8:纳秒
         * @param num   数目
         * @return
         */
        public static LocalDateTime subtractCustomDateTime(Integer level, long num, DateTimeModel dateTimeModel) {
            LocalDateTime localDateTime = LocalDateTime.of(dateTimeModel.getYear(),
                    dateTimeModel.getMonth(),
                    dateTimeModel.getDay(),
                    dateTimeModel.getHour(),
                    dateTimeModel.getMinute(),
                    dateTimeModel.getSecond(),
                    dateTimeModel.getNanos());
            if (FinalValue.ZERO.equals(level)) {
                return localDateTime.minusYears(num);
            } else if (FinalValue.ONE.equals(level)) {
                return localDateTime.minusMonths(num);
            } else if (FinalValue.TWO.equals(level)) {
                return localDateTime.minusDays(num);
            } else if (FinalValue.THREE.equals(level)) {
                return localDateTime.minusHours(num);
            } else if (FinalValue.FOUR.equals(level)) {
                return localDateTime.minusWeeks(num);
            } else if (FinalValue.SIX.equals(level)) {
                return localDateTime.minusMinutes(num);
            } else if (FinalValue.SEVEN.equals(level)) {
                return localDateTime.minusSeconds(num);
            } else if (FinalValue.EIGHT.equals(level)) {
                return localDateTime.minusNanos(num);
            }
            throw new BusinessException("请输入格式类型!!");
        }


    }


    @Test
    public void test() {
        //获得一个时间格式的字符串
        String dateStr = "2016/12/31 12:22:22";
        //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            Date date = sf.parse(dateStr);



            //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
            SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
            //打印Date
            System.out.println(sf1.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}

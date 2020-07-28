package com.jingchu.jdk8.datetime;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @description: 新时间日期格式测试使用
 * @author: JingChu
 * @createtime :2020-07-28 16:05:48
 **/
public class MyDateTimeTest {

    /**
     * LocalDateTime测试
     */
    @Test
    public void test() {
        // 获取当前系统时间
        LocalDateTime localDateTime1 = LocalDateTime.now();
        System.out.println("当前系统时间:\t" + localDateTime1);

        // 指定日期时间
        LocalDateTime localDateTime2 = LocalDateTime.of(2019, 7, 23, 9, 30, 0);
        System.out.println("指定日期时间:\t" + localDateTime2);

        //操作
        LocalDateTime localDateTime3 = localDateTime1
                // 加三年
                .plusYears(3)
                // 减三个月
                .minusMonths(3)
                //减3小时
                .minusHours(3);
        System.out.println("系统时间加3年，减3月，减3小时后的日期时间：\t" + localDateTime3);

        /**
         * 分别获取年/月/日/时/分秒
         */
        System.out.println(localDateTime1.getYear() + "年");
        System.out.println(localDateTime1.getMonthValue() + "月");
        System.out.println(localDateTime1.getDayOfMonth() + "日");
        System.out.println(localDateTime1.getHour() + "时");
        System.out.println(localDateTime1.getMinute() + "分");
        System.out.println(localDateTime1.getSecond() + "秒");

        LocalDateTime localDateTime4 = LocalDateTime.now();
        System.out.println("改变之前的日期时间：\t" + localDateTime4);
        //只改变日期时间的一部分信息，这里时改变日
        LocalDateTime localDateTime5 = localDateTime4.withDayOfMonth(10);
        System.out.println("将日改变成10之后的日期时间：\t" + localDateTime5);


    }

    /**
     * Instant测试
     */
    @Test
    public void test1() {
        //获取当前日期时间戳，默认获取UTC时区，
        Instant instant = Instant.now();
        System.out.println("当前日期时间戳：\t" + instant);

        //中国上海时区与UTC时区差8个时区,通过计算偏移量计算当前时间
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println("计算了偏移量之后的日期时间戳：\t" + offsetDateTime);
        //输出毫秒值
        System.out.println("毫秒：\t" + instant.toEpochMilli());
        //输出秒
        System.out.println("秒：\t" + instant.getEpochSecond());

        //对时间戳计算,从系统时间加1小时
        Instant instant1 = Instant.ofEpochSecond(3600);
        System.out.println("系统时间加1小时后:\t" + instant1);

    }

    /**
     * Duration:用于计算两个“时间”间隔
     * Period:用于计算两个“日期”间隔
     */
    @Test
    public void test2() {
        //Duration
        Instant instant = Instant.ofEpochSecond(1800);
        Instant instant1 = Instant.ofEpochSecond(3600);
        Duration duration = Duration.between(instant, instant1);
        //秒，纳秒都是get，其他的都是to
        System.out.println("两个时间戳的时间差(分钟):\t" + duration.toMinutes());

        //Period
        LocalDate localDate = LocalDate.of(2019, 6, 23);
        LocalDate localDate1 = LocalDate.now();//2020-7-28
        Period period = Period.between(localDate, localDate1);
        System.out.println("两个时间戳的日期差:\t" + period);
        System.out.println("两个时间戳的日期差(月份):\t" + period.getMonths());


    }

    /**
     * - TemporalAdjuster : 时间校正器。有时我们可能需要获取例如：将日期调整到“下个周日”等操作。
     * - TemporalAdjusters : 该类通过静态方法提供了大量的常用TemporalAdjuster 的实现。
     */
    @Test
    public void test3() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("当前时间日期:\t" + localDateTime);

        LocalDateTime localDateTime1 = localDateTime.with(TemporalAdjusters.lastDayOfYear());
        System.out.println("今年的最后一天:\t" + localDateTime1);
        LocalDateTime localDateTime2 = localDateTime.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("当前日期的下个月第一天：\t" + localDateTime2);
        //今天是周二，所以下一个周三就是明天
        LocalDateTime localDateTime3 = localDateTime.with(DayOfWeek.WEDNESDAY);
        System.out.println("当前日期的下一个周三:\t" + localDateTime3);


        //自定义：计算当前日期的5天后
        LocalDateTime localDateTime4 = localDateTime.with((l) -> {
            LocalDateTime localDateTime5 = (LocalDateTime) l;
            DayOfWeek dayOfWeek = localDateTime5.getDayOfWeek();
            return localDateTime5.plusDays(5);
        });
        System.out.println("自定义计算当前日期的5天后：\t" + localDateTime4);
    }

    /**
     * DateTimeFormatter日期格式化
     */
    @Test
    public void test4() {
        //系统提供的
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("格式化之前:\t" + localDateTime);
        System.out.println("格式化之后:\t" + dateTimeFormatter.format(localDateTime));
        //自定义
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String date = dateTimeFormatter1.format(localDateTime);
        System.out.println("自定义格式化日期 ：\t" + date);
        LocalDateTime localDateTime1 =  LocalDateTime.parse(date,dateTimeFormatter1);
        System.out.println("格式转回日期：\t" + localDateTime1);

    }

    /**
     * 时区处理
     */
    @Test
    public void test5(){
        //输出所有时区
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
        System.out.println(set.size());

        //指定时区(指定上海时区)
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println("上海时区，相当于本地时间：\t"+localDateTime);
        //指定美国纽约时区
        LocalDateTime localDateTime1 = LocalDateTime.now(ZoneId.of("America/New_York"));
        System.out.println("纽约时间：\t"+localDateTime1);
        LocalDateTime localDateTime2 = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime2.atZone(ZoneId.of("America/New_York"));
        System.out.println("带时区格式纽约时间:\t"+zonedDateTime);
        ZonedDateTime zonedDateTime1 = localDateTime2.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println("带时区格式上海时间:\t"+zonedDateTime1);
    }
}

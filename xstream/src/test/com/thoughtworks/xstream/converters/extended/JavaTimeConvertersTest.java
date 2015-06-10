package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.acceptance.AbstractAcceptanceTest;

import java.time.*;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;

public class JavaTimeConvertersTest extends AbstractAcceptanceTest {

    public void testConvertingEpochInstantDoesSoAccordingToISO8601(){
        Instant instant = Instant.EPOCH;

        String expected = "1970-01-01T00:00:00Z";
        String actual = JavaTimeConverters.InstantConverter.toString(instant);

        assertEquals(expected, actual);
    }

    public void testHandlesKnownJavaTimeFormats(){

        assertBothWays(Instant.parse("2015-06-03T13:21:58Z"), "<instant>2015-06-03T13:21:58Z</instant>");
        assertBothWays(LocalTime.parse("13:21:58"), "<local-time>13:21:58</local-time>");
        assertBothWays(LocalDateTime.parse("2015-06-03T13:21:58"), "<local-date-time>2015-06-03T13:21:58</local-date-time>");
        assertBothWays(LocalDate.parse("2015-06-03"), "<local-date>2015-06-03</local-date>");
        assertBothWays(OffsetDateTime.parse("2015-06-03T13:21:58Z"), "<offset-date-time>2015-06-03T13:21:58Z</offset-date-time>");
        assertBothWays(OffsetTime.parse("13:21:58Z"), "<offset-time>13:21:58Z</offset-time>");
        assertBothWays(Year.parse("2015"), "<year>2015</year>");
        assertBothWays(YearMonth.parse("2015-06"), "<year-month>2015-06</year-month>");
        assertBothWays(ZonedDateTime.parse("2015-06-03T13:21:58-07:00[America/Los_Angeles]"), "<zoned-date-time>2015-06-03T13:21:58-07:00[America/Los_Angeles]</zoned-date-time>");
        assertBothWays(Duration.parse("PT75H32M32.6S"), "<jtduration>PT75H32M32.6S</jtduration>");
        assertBothWays(Period.parse("P3Y11M2D"), "<period>P3Y11M2D</period>");

        //note: http://stackoverflow.com/questions/27120984/get-month-name-of-java-time-chrono-hijrahdate-instance
        //also note the default toString behaviour is to give us back things much more in line with the actual calendars
        // http://islamicreliefcanada.org/islamic-hijri-calendar/?gclid=CjwKEAjwwN-rBRD-oMzT6aO_wGwSJABwEIkJY62EPWNC8SJ3oG1asiIMcit3oZiLtVlCe5_e8ZsrchoChXXw_wcB
        //making this work by using a western value as a middle man is icky.
        //Id like our own formatters using unicode characters. TODO.
        HijrahDate hijrahDate = DateTimeFormatter.ISO_DATE.parse("2015-06-03", HijrahDate::from);
        assertBothWays(hijrahDate,        "<hijrah-date>2015-06-03</hijrah-date>");
        assertBothWays(DateTimeFormatter.ISO_DATE.parse("2015-06-03", JapaneseDate::from),      "<japanese-date>2015-06-03</japanese-date>");
        assertBothWays(DateTimeFormatter.ISO_DATE.parse("2015-06-03", MinguoDate::from),        "<minguo-date>2015-06-03</minguo-date>");
        assertBothWays(DateTimeFormatter.ISO_DATE.parse("2015-06-03", ThaiBuddhistDate::from),  "<thai-date>2015-06-03</thai-date>");
    }


}
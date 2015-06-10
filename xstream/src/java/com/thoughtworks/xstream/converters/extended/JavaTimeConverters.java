package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.time.*;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Created by Geoff on 2015-06-10.
 */
public class JavaTimeConverters {

    static Iterable<JavaTimeConverter> values(){
        return Arrays.<JavaTimeConverter>asList(
                InstantConverter,
                LocalTimeConverter,
                LocalDateTimeConverter,
                LocalDateConverter,
                HijrahDateConverter,
                JapaneseDateConverter,
                MinguoDateConverter,
                ThaiBuddhistDateConverter,
                OffsetDateTimeConverter,
                OffsetTimeConverter,
                YearConverter,
                YearMonthConverter,
                ZonedDateTimeConverter,
                DurationConverter,
                PeriodConverter
        );
    }

    /**
     * invoked dynamically on the {@link XStream#setupConverters()}
     */
    @SuppressWarnings("UnusedDeclaration")
    public static void registerAll(XStream instance){
        for(JavaTimeConverter converter : values()){
            instance.registerConverter(converter, XStream.PRIORITY_NORMAL);
        }
    }

    public static final JavaTimeConverter InstantConverter           = new InstantConverter();
    public static final JavaTimeConverter LocalTimeConverter         = new LocalTimeConverter();
    public static final JavaTimeConverter LocalDateTimeConverter     = new LocalDateTimeConverter();
    public static final JavaTimeConverter LocalDateConverter         = new LocalDateConverter();
    public static final JavaTimeConverter HijrahDateConverter        = new HijrahDateConverter();
    public static final JavaTimeConverter JapaneseDateConverter      = new JapaneseDateConverter();
    public static final JavaTimeConverter MinguoDateConverter        = new MinguoDateConverter();
    public static final JavaTimeConverter ThaiBuddhistDateConverter  = new ThaiBuddhistDateConverter();
    public static final JavaTimeConverter OffsetDateTimeConverter    = new OffsetDateTimeConverter();
    public static final JavaTimeConverter OffsetTimeConverter        = new OffsetTimeConverter();
    public static final JavaTimeConverter YearConverter              = new YearConverter();
    public static final JavaTimeConverter YearMonthConverter         = new YearMonthConverter();
    public static final JavaTimeConverter ZonedDateTimeConverter     = new ZonedDateTimeConverter();
    public static final JavaTimeConverter DurationConverter          = new DurationConverter();
    public static final JavaTimeConverter PeriodConverter            = new PeriodConverter();

    public static class InstantConverter            extends JavaTimeConverter{ private InstantConverter(){          super(Instant.class);}          public Object fromString(String str) { return Instant.parse(str);                                   }}
    public static class LocalTimeConverter          extends JavaTimeConverter{ private LocalTimeConverter(){        super(LocalTime.class);}        public Object fromString(String str) { return LocalTime.parse(str);                                 }}
    public static class LocalDateTimeConverter      extends JavaTimeConverter{ private LocalDateTimeConverter(){    super(LocalDateTime.class);}    public Object fromString(String str) { return LocalDateTime.parse(str);                             }}
    public static class LocalDateConverter          extends JavaTimeConverter{ private LocalDateConverter(){        super(LocalDate.class);}        public Object fromString(String str) { return LocalDate.parse(str);                                 }}

    public static class HijrahDateConverter         extends JavaTimeConverter{ private HijrahDateConverter(){       super(HijrahDate.class);}       public Object fromString(String str) { return ISODateFormatter.parse(str, HijrahDate::from);        } public String toString(Object obj){ return ((HijrahDate)obj).format(ISODateFormatter); }}
    public static class JapaneseDateConverter       extends JavaTimeConverter{ private JapaneseDateConverter(){     super(JapaneseDate.class);}     public Object fromString(String str) { return ISODateFormatter.parse(str, JapaneseDate::from);      } public String toString(Object obj){ return ((JapaneseDate)obj).format(ISODateFormatter); }}
    public static class MinguoDateConverter         extends JavaTimeConverter{ private MinguoDateConverter(){       super(MinguoDate.class);}       public Object fromString(String str) { return ISODateFormatter.parse(str, MinguoDate::from);        } public String toString(Object obj){ return ((MinguoDate)obj).format(ISODateFormatter); }}
    public static class ThaiBuddhistDateConverter   extends JavaTimeConverter{ private ThaiBuddhistDateConverter(){ super(ThaiBuddhistDate.class);} public Object fromString(String str) { return ISODateFormatter.parse(str, ThaiBuddhistDate::from);  } public String toString(Object obj){ return ((ThaiBuddhistDate)obj).format(ISODateFormatter); }}

    public static class OffsetDateTimeConverter     extends JavaTimeConverter{ private OffsetDateTimeConverter(){   super(OffsetDateTime.class);}   public Object fromString(String str) { return OffsetDateTime.parse(str);                            }}
    public static class OffsetTimeConverter         extends JavaTimeConverter{ private OffsetTimeConverter(){       super(OffsetTime.class);}       public Object fromString(String str) { return OffsetTime.parse(str);                                }}
    public static class YearConverter               extends JavaTimeConverter{ private YearConverter(){             super(Year.class);}             public Object fromString(String str) { return Year.parse(str);                                      }}
    public static class YearMonthConverter          extends JavaTimeConverter{ private YearMonthConverter(){        super(YearMonth.class);}        public Object fromString(String str) { return YearMonth.parse(str);                                 }}
    public static class ZonedDateTimeConverter      extends JavaTimeConverter{ private ZonedDateTimeConverter(){    super(ZonedDateTime.class);}    public Object fromString(String str) { return ZonedDateTime.parse(str);                             }}

    public static class DurationConverter           extends JavaTimeConverter{ private DurationConverter(){         super(Duration.class);}         public Object fromString(String str) { return Duration.parse(str);                                  }}
    public static class PeriodConverter             extends JavaTimeConverter{ private PeriodConverter(){           super(Period.class);}           public Object fromString(String str) { return Period.parse(str);                                    }}

    static abstract class JavaTimeConverter extends AbstractSingleValueConverter {

        protected static final DateTimeFormatter ISODateFormatter = DateTimeFormatter.ISO_DATE;

        private final Class typeToConvert;

        protected JavaTimeConverter(Class typeToConvert){
            this.typeToConvert = typeToConvert;
        }

        @Override
        public boolean canConvert(Class type) {
            return type != null && type.equals(typeToConvert);
        }

        @Override
        public String toString(Object obj) {
            return obj.toString();
        }
    }
}

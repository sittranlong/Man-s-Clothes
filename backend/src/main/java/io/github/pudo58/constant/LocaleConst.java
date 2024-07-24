package io.github.pudo58.constant;

import java.util.List;
import java.util.Locale;

public class LocaleConst {
    public static final Locale VI = new Locale("vi", "");
    public static final Locale ZH = Locale.CHINESE;
    public static final Locale EN = Locale.ENGLISH;

    public static final Locale DEFAULT_LOCALE = VI;

    public static final List<Locale> SUPPORTED_LOCALES = List.of(VI, ZH, EN);

    public static Locale getLocale(Locale locale) {
        if (locale == null) {
            return VI;
        }
        for (Locale supportedLocale : SUPPORTED_LOCALES) {
            if (supportedLocale.equals(locale)) {
                return supportedLocale;
            }
        }
        return VI;
    }
}

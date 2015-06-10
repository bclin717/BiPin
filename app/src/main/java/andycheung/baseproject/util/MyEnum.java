package andycheung.baseproject.util;

import java.util.Locale;

/**
 * Created by Andy Cheung on 4/5/15.
 */
public class MyEnum {


    public enum MyLanguage {
        TC(0, Locale.TRADITIONAL_CHINESE,
                Locale.CHINESE), //
        SC(1, Locale.SIMPLIFIED_CHINESE,
                Locale.CHINA), //
        EN(2, Locale.ENGLISH) //
        ;
        public final int id;
        public final Locale[] local;

        private MyLanguage(int languageId,
                           Locale... local) {
            this.id = languageId;

            this.local = local;
        }
    }

}

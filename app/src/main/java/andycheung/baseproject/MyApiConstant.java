package andycheung.baseproject;

public class MyApiConstant {

    public enum DomainType {

        // OC
        CLIENT_SIT(CLIENT_SIT_BASE_DOMAIN),

        // OC
        CLIENT_UAT(CLIENT_UAT_BASE_DOMAIN),

        // GT
        OWN_UAT(OWN_UAT_BASE_DOMAIN),
        //
        ;

        public final String baseDomain;

        private DomainType(String baseDomain) {
            this.baseDomain = baseDomain;
        }

    }

    private final static String OWN_UAT_BASE_DOMAIN ="";

    private final static String CLIENT_UAT_BASE_DOMAIN ="";

    private final static String CLIENT_SIT_BASE_DOMAIN = "";

    public static DomainType BUILD_DOMAIN =
            (BuildConfig.productFlavor.equals("own_uat")) ? DomainType.OWN_UAT : (BuildConfig.productFlavor.equals("client_sit")) ? DomainType.CLIENT_SIT : DomainType.CLIENT_UAT;
}

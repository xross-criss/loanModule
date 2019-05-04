package pl.kkwiatkowski.loan.mappers;

import java.util.HashMap;
import java.util.Map;

public class CreditHistoryCategoryMapper {

    public static final Map<String, String> HISTROY_CATEGORIES;
    static {
        HISTROY_CATEGORIES = new HashMap<>();
        HISTROY_CATEGORIES.put("1", "HIPOTECZNA");
        HISTROY_CATEGORIES.put("2", "WYNAJMOWANA");
        HISTROY_CATEGORIES.put("3", "ZWYKLA");
    }

}

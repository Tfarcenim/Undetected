package tfar.undetected;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class UndetectedConfig {
    public static final UndetectedConfig CONFIG;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        final Pair<UndetectedConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(UndetectedConfig::new);
        SERVER_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    public final ModConfigSpec.BooleanValue DIE;
    public final ModConfigSpec.BooleanValue DISPLAY_COUNT;


    public UndetectedConfig(ModConfigSpec.Builder builder) {
        builder.push("general");
        DIE = builder.define("die",true);
        DISPLAY_COUNT = builder.define("display_count",true);
        builder.pop();
    }

}

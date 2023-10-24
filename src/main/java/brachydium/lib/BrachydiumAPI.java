package brachydium.lib;

import brachydium.lib.unification.material.Material;
import brachydium.lib.util.BrachydiumRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BrachydiumAPI {

    public static boolean debug = true;
    public static final Logger LOG = LogManager.getFormatterLogger("brachydium");
    public static final MaterialRegistry MATERIALS = new MaterialRegistry();

    public static class MaterialRegistry extends BrachydiumRegistry<String, Material> {

        private MaterialRegistry() {
        }

        @Override
        public String getKey(Material value) {
            return value.toString();
        }

        @Override
        public int getId(Material value) {
            return value.getId();
        }
    }
}

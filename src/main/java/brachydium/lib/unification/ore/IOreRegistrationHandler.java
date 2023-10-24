package brachydium.lib.unification.ore;

@FunctionalInterface
public interface IOreRegistrationHandler {

    void processMaterial(OrePrefix orePrefix, Material material);

}

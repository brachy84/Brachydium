package brachy84.testmod;

import brachy84.brachydium.api.BrachydiumInitializer;
import brachy84.testmod.recipes.TestRecipes;

public class BrachydiumPlugin implements BrachydiumInitializer {

    @Override
    public String getModName() {
        return "TestMod";
    }

    @Override
    public String getModId() {
        return "testmod";
    }

    @Override
    public void registerRecipes() {
        TestRecipes.init();
    }

    @Override
    public void registerMaterials() {
    }

    @Override
    public void registerGeneral() {
        MTBlockEntities.init();
    }
}

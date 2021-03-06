package brachy84.brachydium.api.blockEntity;

import brachy84.brachydium.api.blockEntity.trait.AbstractRecipeLogic;
import brachy84.brachydium.api.handlers.storage.FluidInventory;
import brachy84.brachydium.api.handlers.storage.IFluidHandler;
import brachy84.brachydium.api.handlers.storage.IItemHandler;
import brachy84.brachydium.api.handlers.storage.ItemInventory;
import brachy84.brachydium.api.recipe.RecipeTable;
import org.jetbrains.annotations.NotNull;

public abstract class WorkableTileEntity extends TileEntity {

    private final AbstractRecipeLogic workable;

    public WorkableTileEntity(RecipeTable<?> recipeTable) {
        this.workable = createWorkable(recipeTable);
    }

    @NotNull
    protected abstract AbstractRecipeLogic createWorkable(RecipeTable<?> recipeTable);

    public AbstractRecipeLogic getWorkable() {
        return workable;
    }

    public RecipeTable<?> getRecipeTable() {
        return workable.recipeTable;
    }

    @Override
    public boolean isActive() {
        return workable.isActive();
    }

    @Override
    public @NotNull IFluidHandler createInputFluidHandler() {
        return FluidInventory.importInventory(getRecipeTable().getMaxFluidInputs(), 64 * 81000);
    }

    @Override
    public @NotNull IFluidHandler createOutputFluidHandler() {
        return FluidInventory.exportInventory(getRecipeTable().getMaxFluidOutputs(), 64 * 81000);
    }

    @Override
    public @NotNull IItemHandler createInputItemHandler() {
        return ItemInventory.importInventory(getRecipeTable().getMaxInputs());
    }

    @Override
    public @NotNull IItemHandler createOutputItemHandler() {
        return ItemInventory.exportInventory(getRecipeTable().getMaxOutputs());
    }
}

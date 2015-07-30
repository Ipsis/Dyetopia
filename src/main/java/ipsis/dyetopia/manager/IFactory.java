package ipsis.dyetopia.manager;

public interface IFactory {

        boolean isOutputValid(IFactoryRecipe recipe);
        boolean isEnergyAvailable(int amount);

        void consumeInputs(IFactoryRecipe recipe);
        void createOutputs(IFactoryRecipe recipe);

        void consumeEnergy(int amount);

        int getEnergyTick();

        IFactoryRecipe getRecipe();

        void updateRunning(boolean running);
}

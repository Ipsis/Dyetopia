package com.ipsis.dyetopia.manager;

public interface IFactory {

        public boolean isOutputValid(IFactoryRecipe recipe);
        public boolean isEnergyAvailable(int amount);

        public void consumeInputs(IFactoryRecipe recipe);
        public void createOutputs(IFactoryRecipe recipe);

        public void consumeEnergy(int amount);

        public int getEnergyTick();

        public IFactoryRecipe getRecipe();

        public void updateRunning(boolean running);
}

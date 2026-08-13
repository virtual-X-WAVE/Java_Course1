package com.homework.first;

public final class ImmutableMachine {
    private final String modelName;
    private final Characteristics characteristics;

    public ImmutableMachine(String modelName, Characteristics characteristics){
        this.modelName = modelName;
        this.characteristics = new Characteristics(characteristics.getMaxSpeed());
    }

    public String getModelName(){
        return modelName;
    }

    /*
        В иммутабельных классах не должно быть сеттеров.
        Модификатор доступа 'final' решает проблему создания сеттеров внутри класса,
        "поднимая(raise)" ошибку при попытке создания сеттера для переменной
     */

    /*
        Этот метод содерджит ошибку, т.к. 'modelName' имеет доступ 'final'
        public void setModelName(String modelName){
            this.modelName = modelName;
        }
    */

    public Characteristics getCharacteristics(){
        return new Characteristics(characteristics.getMaxSpeed());
    }
}
package com.homework.first;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        /*
            Если можно изменить поле в иммутабельном классе, то класс НЕ иммутабельный.
            В данной реализации переменную получится изменить ТОЛЬКО при помощи ClassLoader'а.
         */
        String[] prints =  {
                "machine 'modelName' value = ",
                "\ncharacteristics 'max speed' value in machine = ",
                "characteristics 'max speed' value declared in 'static void main()' = ",
                "characteristics 'max speed' value in machine = ",
                "exportedCharacteristics 'max speed' value declared in static main() = ",
                "characteristics 'max speed' value in machine = "
        };

        // Создание объектов "характеристики" и "машина"; вывод в консоль
        Characteristics characteristics = new Characteristics(50);
        ImmutableMachine machine = new ImmutableMachine("Жигули", characteristics);
        System.out.println(prints[0] + machine.getModelName() + prints[1] + machine.getCharacteristics().getMaxSpeed());

        // Попытка изменить значение в объекте характеристик внутри static void Main(); вывод в консоль
        System.out.println("\nTrying to change 'max speed' to 100 in characteristics declared in static main()...");
        characteristics.setMaxSpeed(100);
        System.out.println(prints[2] + characteristics.getMaxSpeed() + prints[3] + machine.getCharacteristics().getMaxSpeed());

        // Попытка изменить значение через получение объекта "характеристики" из объекта "машина"; вывод в консоль
        System.out.println("\nTrying to change 'max speed' to 200 with extracting characteristics from machine...");
        Characteristics exportedCharacteristics = machine.getCharacteristics();
        exportedCharacteristics.setMaxSpeed(200);
        System.out.println(prints[4] + exportedCharacteristics.getMaxSpeed() + prints[5] + machine.getCharacteristics().getMaxSpeed());

        // Попытка изменить значение через ClassLoader; вывод в консоль; обработка исключений
        System.out.println("\nTrying to change 'max speed' to 500 in characteristics declared in static main()...");
        try {
            сhangeMaxSpeedWithClassLoader(machine);
        } catch (NoSuchFieldException e) {
            System.out.println("Can't find that field!\n\n" + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.print("Changing this field is illegal!\n\n" + e.getMessage());
        } finally {
            System.out.println("characteristics 'max speed' value in machine = " + machine.getCharacteristics().getMaxSpeed());
        }
    }

    static void сhangeMaxSpeedWithClassLoader(ImmutableMachine immutableMachineObject) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends ImmutableMachine> immutableMachineClass = immutableMachineObject.getClass();

        Characteristics newCharacteristics = new Characteristics(500);
        System.out.println("newCharacteristics 'max speed' value declared in void ChangeMaxSpeedWithClassLoader = "
                + newCharacteristics.getMaxSpeed());

        Field characteristicsField = immutableMachineClass.getDeclaredField("characteristics");

        characteristicsField.setAccessible(true);
        characteristicsField.set(immutableMachineObject, newCharacteristics);
        characteristicsField.setAccessible(false);
    }
}
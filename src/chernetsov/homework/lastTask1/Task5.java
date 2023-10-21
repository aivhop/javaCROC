package chernetsov.homework.lastTask1;

public class Task5 {
    public static void main(String[] args) {
        Fridge fridge = new Fridge(3, 10_000,
                47, 110, 210, 60, Appliance.Color.BLACK, "Standard fridge");

        FridgeWithFreezer fridgeWithFreezer = new FridgeWithFreezer(2, 4, 15_000,
                61, 95, 230, 80, Appliance.Color.WHITE, "Standard fridge with freezer");

        FridgeImported fridgeImported = new FridgeImported(5, "Belarus", 20_000,
                72.5, 100, 200, 90, Appliance.Color.BLUE, "Some imported Fridge");

        FridgeImported fridgeImportedWithGuarantee = new FridgeImported(6, "Egypt", 12,
                35_000, 100, 120, 190, 110, Appliance.Color.WHITE,
                "Some imported Fridge with guarantee");

        FridgeWithFreezerImported frWithFrImp = new FridgeWithFreezerImported(3, 5,
                "USA", 50_000, 82, 100, 200, 120, Appliance.Color.WHITE,
                "Imported fridge with freezer");

        FridgeWithFreezerImported frWithFrImp2 = new FridgeWithFreezerImported(1, 2,
                "UK", 24, 100_000, 39, 45, 100, 50, Appliance.Color.RED,
                "Fridge with freezer with guarantee");

        Stove stove1 = new Stove(true, "glass ceramics", 2_000, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Stove.Burners.INDUCTION);

        StoveImported stove2 = new StoveImported(true, "glass", "UK",
                24, 5_000, 4, 30, 7, 40,
                Appliance.Color.WHITE, "Small imported stove", Stove.Burners.ELECTRIC);

        StoveWithOven stove3 = new StoveWithOven(100, 250, 34,
                "enameled steel", 20_000, 72, 60, 130, 80,
                Appliance.Color.WHITE, "Default stove with oven", Stove.Burners.ELECTRIC,
                Stove.Burners.ELECTRIC, Stove.Burners.INDUCTION, Stove.Burners.INDUCTION);

        StoveWithOvenImported stove4 = new StoveWithOvenImported(80, 300, 60,
                "glass ceramics", "USA", 170_000, 95, 105, 120, 80,
                Appliance.Color.BLUE, "Big imported stove", Stove.Burners.INDUCTION,
                Stove.Burners.INDUCTION, Stove.Burners.INDUCTION, Stove.Burners.INDUCTION);

        Appliance appliances[] = {
                fridge, fridgeWithFreezer, fridgeImported, fridgeImportedWithGuarantee, frWithFrImp,
                frWithFrImp2, stove1, stove2, stove3, stove4
        };
        for (Appliance ap : appliances) {
            System.out.println(ap);
            System.out.println();
        }

    }
}

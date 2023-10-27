package homework.chernetsov.correction.lastTask1;

import homework.chernetsov.correction.lastTask1.base.Appliance;
import homework.chernetsov.correction.lastTask1.fridge.Fridge;
import homework.chernetsov.correction.lastTask1.fridge.FridgeImported;
import homework.chernetsov.correction.lastTask1.fridge.FridgeWithFreezer;
import homework.chernetsov.correction.lastTask1.fridge.FridgeWithFreezerImported;
import homework.chernetsov.correction.lastTask1.stove.*;

public class Task5 {
    public static void main(String[] args) {
        Fridge fridge = new Fridge(3, 10_000,
                47, 110, 210, 60, Appliance.Color.BLACK, "Standard fridge");

        FridgeWithFreezer fridgeWithFreezer = new FridgeWithFreezer(2, 4, 15_000,
                61, 95, 230, 80, Appliance.Color.WHITE, "Standard fridge with freezer");

        FridgeImported fridgeImported = new FridgeImported("Belarus",5,  20_000,
                72.5, 100, 200, 90, Appliance.Color.BLUE, "Some imported Fridge");

        FridgeImported fridgeImportedWithGuarantee = new FridgeImported( "Egypt", 12, 6,
                35_000, 100, 120, 190, 110, Appliance.Color.WHITE,
                "Some imported Fridge with guarantee");

        FridgeWithFreezerImported frWithFrImp = new FridgeWithFreezerImported("USA",3,
                5, 50_000, 82, 100, 200, 120, Appliance.Color.WHITE,
                "Imported fridge with freezer");

        FridgeWithFreezerImported frWithFrImp2 = new FridgeWithFreezerImported("UK", 24,
                1, 2, 100_000, 39, 45, 100,
                50, Appliance.Color.RED, "Fridge with freezer with guarantee");

        Stove stove1 = new Stove(true, "glass ceramics", 2_000, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Burner.Burners.INDUCTION);

        StoveImported stove2 = new StoveImported("UK", 24, true, "glass",
                5_000, 4, 30, 7, 40,
                Appliance.Color.WHITE, "Small imported stove", Burner.Burners.ELECTRIC);

        StoveWithOven stove3 = new StoveWithOven(100, 250, 34,
                "enameled steel", 20_000, 72, 60, 130, 80,
                Appliance.Color.WHITE, "Default stove with oven", Burner.Burners.ELECTRIC,
                Burner.Burners.ELECTRIC, Burner.Burners.INDUCTION, Burner.Burners.INDUCTION);

        StoveWithOvenImported stove4 = new StoveWithOvenImported("USA",80, 300, 60,
                "glass ceramics", 170_000, 95, 105, 120, 80,
                Appliance.Color.BLUE, "Big imported stove", Burner.Burners.INDUCTION,
                Burner.Burners.INDUCTION, Burner.Burners.INDUCTION, Burner.Burners.INDUCTION);

        Appliance[] appliances = {
                fridge, fridgeWithFreezer, fridgeImported, fridgeImportedWithGuarantee, frWithFrImp,
                frWithFrImp2, stove1, stove2, stove3, stove4
        };
        for (Appliance ap : appliances) {
            System.out.println(ap);
            System.out.println();
        }
        

    }
}

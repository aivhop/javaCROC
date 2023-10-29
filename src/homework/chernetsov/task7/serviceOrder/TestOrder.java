package homework.chernetsov.task7.serviceOrder;

import homework.chernetsov.correction.lastTask1.base.Appliance;
import homework.chernetsov.correction.lastTask1.fridge.Fridge;
import homework.chernetsov.correction.lastTask1.fridge.FridgeImported;
import homework.chernetsov.correction.lastTask1.fridge.FridgeWithFreezerImported;
import homework.chernetsov.correction.lastTask1.stove.*;
import homework.chernetsov.task7.Order;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestOrder {
    public static void testNotification() {
        Appliance fridge = new Fridge(3, 10_000.001,
                47, 110, 210, 60, Appliance.Color.BLACK, "Standard fridge");
        Appliance stove = new Stove(true, "glass ceramics", 2_000.004, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Burner.Burners.INDUCTION, Burner.Burners.ELECTRIC);
        Order order = new Order("Chernetsov Alexander Mikhailovich", "89822939299", ZonedDateTime.now(), fridge, stove);
        order.setCollectingDateTime(order.getCreatingDateTime().plusDays(1));
        System.out.println(order.getNotification());
    }

    public static void testIsExpired() {
        FridgeImported fridgeImportedWithGuarantee = new FridgeImported("Egypt", 12, 6,
                35_000, 100, 120, 190, 110, Appliance.Color.WHITE,
                "Some imported Fridge with guarantee");

        FridgeWithFreezerImported frWithFrImp = new FridgeWithFreezerImported("USA", 3,
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
        Order order1 = new Order("Chernetsov Alexander", "89822939299",
                ZonedDateTime.of(2007, 6, 6, 6, 6, 6, 6,
                        ZoneId.of("Europe/Moscow")), fridgeImportedWithGuarantee);
        Order order2 = new Order("Ivanov Ivan", "8800553535", ZonedDateTime.now().minusDays(30), frWithFrImp);
        Order order3 = new Order("Smirnov Anton", "89008007001", ZonedDateTime.now().minusDays(20), frWithFrImp2);
        Order order4 = new Order("Plotnikov Artem", "83002001002", ZonedDateTime.now().minusDays(10), stove1);
        Order order5 = new Order("Chesnokov Dmitry", "89111120102", ZonedDateTime.now().minusDays(16), stove2);

        order1.collect(ZonedDateTime.of(2007, 6, 9, 6, 6, 6, 6,
                ZoneId.of("Europe/Moscow")));
        order2.collect(ZonedDateTime.now().minusDays(25));
        order3.collect(ZonedDateTime.now().minusDays(18));
        order4.collect();
        order5.collect(ZonedDateTime.now().minusDays(4));
        Order[] orders = new Order[]{order1, order2, order3, order4, order5};
        for (Order ord : orders) {
            System.out.print(ord.getStatus() + " ");
            System.out.print("isExpired: " + ord.isExpired() + " ");
            System.out.println(ord.getStatus());
        }
    }
}

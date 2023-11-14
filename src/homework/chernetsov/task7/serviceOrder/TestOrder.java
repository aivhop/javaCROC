package homework.chernetsov.task7.serviceOrder;

import homework.chernetsov.task5.base.Appliance;
import homework.chernetsov.task5.fridge.Fridge;
import homework.chernetsov.task5.fridge.FridgeImported;
import homework.chernetsov.task5.fridge.FridgeWithFreezerImported;
import homework.chernetsov.task5.stove.*;
import homework.chernetsov.task7.Order;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TestOrder {
    public static void testNotification() {
        Appliance fridge = new Fridge(3, 10_000.001,
                47, 110, 210, 60, Appliance.Color.BLACK, "Standard fridge");
        Appliance stove = new Stove(true, "glass ceramics", 2_000.004, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Burner.Burners.INDUCTION, Burner.Burners.ELECTRIC);
        ArrayList<Appliance> array = new ArrayList<>();
        array.add(fridge);
        array.add(stove);
        Order order = new Order("Chernetsov Alexander Mikhailovich", "89822939299", ZonedDateTime.now(), array);

        System.out.println("Сообщение до сборки заказа: ");
        System.out.println(order.getNotification());
        System.out.println("-------------------------\n");

        System.out.println("Сообщение после сборки заказа: ");
        order.collect();
        System.out.println(order.getNotification());
        System.out.println("-------------------------\n");

        System.out.println("Сообщение после получения заказа: ");
        order.give();
        System.out.println(order.getNotification());
        System.out.println("-------------------------\n");




        Order orderExp = new Order("Chernetsov Alexander", "89822939299", ZonedDateTime.now().minusDays(30),array);
        orderExp.collect(ZonedDateTime.now().minusDays(29));
        System.out.println("Сообщение после истечении срока хранения заказа: ");
        System.out.println(orderExp.getNotification());
        System.out.println("-------------------------\n");




        //test getter
        Stove stove1 = new Stove(true, "glass ceramics", 2_000, 1.7, 31,
                9, 39, Appliance.Color.BLACK, "Cheep stove", Burner.Burners.INDUCTION);
        order.getProducts().clear();
        System.out.println(order.getNotification());
        System.out.println("-------------------------\n");





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
        ArrayList<Appliance> ar = new ArrayList<>();
        ar.add(fridgeImportedWithGuarantee);
        ArrayList<Appliance> ar1 = new ArrayList<>();
        ar1.add(frWithFrImp);
        ArrayList<Appliance> ar2 = new ArrayList<>();
        ar2.add(frWithFrImp2);
        ArrayList<Appliance> ar3 = new ArrayList<>();
        ar3.add(stove1);
        ArrayList<Appliance> ar4 = new ArrayList<>();
        ar4.add(stove2);
        Order order1 = new Order("Chernetsov Alexander", "89822939299",
                ZonedDateTime.of(2007, 6, 6, 6, 6, 6, 6,
                        ZoneId.of("Europe/Moscow")), ar);
        Order order2 = new Order("Ivanov Ivan", "8800553535", ZonedDateTime.now().minusDays(30), ar1);
        Order order3 = new Order("Smirnov Anton", "89008007001", ZonedDateTime.now().minusDays(20), ar2);
        Order order4 = new Order("Plotnikov Artem", "83002001002", ZonedDateTime.now().minusDays(10), ar3);
        Order order5 = new Order("Chesnokov Dmitry", "89111120102", ZonedDateTime.now().minusDays(16), ar4);

        order1.collect(ZonedDateTime.of(2007, 6, 9, 6, 6, 6, 6,
                ZoneId.of("Europe/Moscow")));
        order2.collect(ZonedDateTime.now().minusDays(25));
        order3.collect(ZonedDateTime.now().minusDays(18));
        order4.collect();
        order5.collect(ZonedDateTime.now().minusDays(4));
        Order[] orders = new Order[]{order1, order2, order3, order4, order5};
        for (Order ord : orders) {
            System.out.print(ord.getStatus() + " ");
            System.out.print("isExpired: " + ord.isExpiredAndUpdate() + " ");
            System.out.println(ord.getStatus());
        }
    }
}

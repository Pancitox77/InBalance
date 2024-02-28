package main;

import util.InventoryCalculator;
import util.InventorySystem;

public class Test {
    public static void main(String[] args) {
        InventoryCalculator calc = new InventoryCalculator(100, "100:200");
        calc.setInventorySystem(InventorySystem.FIFO);
        calc.calculateEntry(20, 70f);
        calc.calculateOutput(50);

        /*
         * Inv: 120 -> 100:$200y 20:$70
         *      $20.000 total
         * 
         * FIFO: 100-50 a $200 c/u.
         *      Quedan: 50:$200 y 20:$70.
         * 
         * Venta: 50 a $200 c/u
         *      CMV $10.000
        */

        System.out.println("Unit: " + calc.getTotalPrice());
        System.out.println("CMV: $" +calc.getStockQuantity());
    }
}

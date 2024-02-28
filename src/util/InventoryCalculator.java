package util;

import java.util.ArrayList;

import javafx.util.Pair;

public class InventoryCalculator {
    private int stockQuantity;
    private String unitPrice;
    private float totalStockPrice;
    private float cost; // CMV

    private InventorySystem inventorySystem = InventorySystem.PPP;
    private ArrayList<Pair<Integer, Float>> unitPriceArray;

    public InventoryCalculator(int initialStockQuantity, String initialUnitPrice) {
        this.stockQuantity = initialStockQuantity;

        this.unitPriceArray = parseStringToArr(initialUnitPrice);
        for (Pair<Integer, Float> i : unitPriceArray) {
            this.totalStockPrice += i.getKey() * i.getValue();
        }
    }

    /* Entradas y salidas */

    /**
     * Llamado al hacer una compra. Calcula el stock, precio unitario y precio total
     * 
     * @param quantity       Unidades de mercadería que ingresan.
     * @param entryUnitPrice Precio unitario de la mercadería ingresada. Este valor
     *                       es al coste de compra, no al coste al que lo estás
     *                       vendiendo.
     */
    public void calculateEntry(int quantity, float entryUnitPrice) {
        this.stockQuantity += quantity;
        this.totalStockPrice += (quantity * entryUnitPrice); // Precio total

        unitPriceArray.add(new Pair<>(quantity, entryUnitPrice));
        this.unitPrice = parseArrToString();
    }

    /**
     * Llamado al hacer una venta. Calcula el stock restante, precio unitario
     * actualizado
     * y precio total. Según el sistema de inventario usado, el CMV (costo de
     * mercadería vendida)
     * variará. El CMV se guarda una vez llamada está función.
     * Los datos se actualizan después de hacer la venta.
     * 
     * @param quantity Unidades vendidas
     */
    public void calculateOutput(int quantity) {
        this.stockQuantity -= quantity;

        if (inventorySystem == InventorySystem.LIFO)
            caseLIFOSystem(quantity);
        else if (inventorySystem == InventorySystem.FIFO)
            caseFIFOSystem(quantity);
        else
            casePPPSystem(quantity);

        this.unitPrice = parseArrToString();
        this.totalStockPrice = 0;
        for (Pair<Integer, Float> i : unitPriceArray) {
            this.totalStockPrice += (i.getKey() * i.getValue());
        }
    }

    /* Funciones para convertir arr/str str/arr */

    /**
     * Convierte una cadena de texto a array. Cada '|' representa una relación
     * distinta.
     * La cadena de texto tiene el formato: cantidad:precio|cantidad2:precio2|...
     * 
     * @param str Cadena de texto a convertir
     * @return Array con las cantidad-precio separados
     */
    private ArrayList<Pair<Integer, Float>> parseStringToArr(String str) {
        String[] prices = str.split("\\|");
        ArrayList<Pair<Integer, Float>> array = new ArrayList<>(prices.length);

        for (String item : prices) {
            String[] parts = item.split("\\:");

            int quantity = Integer.parseInt(parts[0]);
            float price = Float.parseFloat(parts[1]);

            Pair<Integer, Float> pair = new Pair<>(quantity, price);
            array.add(pair);
        }
        return array;
    }

    /**
     * Convierte un array de cantidad-precio a un string con el formato '
     * cantidad:precio|... '
     * 
     * @return El string en crudo
     */
    private String parseArrToString() {
        StringBuilder parsed = new StringBuilder();
        for (Pair<Integer, Float> item : this.unitPriceArray) {
            String itemStr = item.getKey() + ":" + item.getValue();
            parsed.append(itemStr);

            if (!item.equals(unitPriceArray.get(unitPriceArray.size() - 1))) {
                // Si no es el último item
                parsed.append("|");
            }
        }
        return parsed.toString();
    }

    /* Funciones de sistemas de inventario */

    /**
     * Calcula una venta usando el sistema LIFO/UEPS. "Último en entrar, primero en
     * salir".
     * Además calcula el CMV.
     * 
     * @param quantity Unidades vendidas
     */
    private void caseLIFOSystem(int quantity) {
        int remainingQuantity = quantity;
        float initialCost = calculateCMV(unitPriceArray);

        for (int i = unitPriceArray.size() - 1; i >= 0; i--) {
            Pair<Integer, Float> item = unitPriceArray.get(i);

            if (item.getKey() > remainingQuantity) {
                int remaining = item.getKey() - remainingQuantity;
                Pair<Integer, Float> newItem = new Pair<>(remaining, item.getValue());
                unitPriceArray.set(i, newItem);
                break;

            } else {
                remainingQuantity -= item.getKey();
                unitPriceArray.remove(i);
            }
        }
        this.cost = initialCost - calculateCMV(unitPriceArray);
    }

    /**
     * Calcula una venta usando el sistema FIFO/PEPS. "Primero en entrar, primero en
     * salir".
     * Además calcula el CMV.
     * 
     * @param quantity Unidades vendidas
     */
    private void caseFIFOSystem(int quantity) {
        int remainingQuantity = quantity;
        float totalCMV = 0;
        int currentIndex = 0;

        ArrayList<Integer> itemsToRemove = new ArrayList<>(unitPriceArray.size());

        while (remainingQuantity > 0) {
            Pair<Integer, Float> currentItem = unitPriceArray.get(currentIndex);
            int availableQuantity = currentItem.getKey();
            float currentPrice = currentItem.getValue();

            if (availableQuantity > remainingQuantity) {
                currentItem = new Pair<>(remainingQuantity, currentPrice);
                unitPriceArray.set(currentIndex, currentItem);
                totalCMV += remainingQuantity * currentPrice;
                remainingQuantity = 0;
            } else {
                totalCMV += availableQuantity * currentPrice;
                remainingQuantity -= availableQuantity;
                currentIndex++;
                itemsToRemove.add(currentIndex);
            }
        }

        itemsToRemove.forEach(index -> unitPriceArray.remove((int) index));
        this.cost = totalCMV;
    }

    /**
     * Calcula una venta usando el sistema PPP. Precio Promedio Ponderado.
     * Además calcula el CMV.
     * 
     * @param quantity Unidades vendidas
     */
    private void casePPPSystem(int quantity) {
        // Calculo del promedio
        int totalUnits = 0;
        float totalPrice = 0;
        for (Pair<Integer, Float> i : unitPriceArray) {
            totalUnits += i.getKey();
            totalPrice += i.getKey() * i.getValue();
        }
        totalUnits = totalUnits == 0 ? 1 : totalUnits;
        float averageUnitPrice = totalPrice / totalUnits;

        // CMV
        this.cost = averageUnitPrice * quantity;
        this.unitPriceArray.clear();
        this.unitPriceArray.add(new Pair<>(this.stockQuantity, averageUnitPrice));
    }

    private float calculateCMV(ArrayList<Pair<Integer, Float>> unitPriceArray) {
        float totalCMV = 0;

        for (Pair<Integer, Float> item : unitPriceArray) {
            totalCMV += item.getKey() * item.getValue();
        }

        return totalCMV;
    }

    /* Getters */

    public int getStockQuantity() {
        return this.stockQuantity;
    }

    public String getUnitPrice() {
        return this.unitPrice;
    }

    public float getTotalPrice() {
        return this.totalStockPrice;
    }

    public float getCost() {

        return this.cost;
    }

    public InventorySystem getInventorySystem() {
        return this.inventorySystem;
    }

    public void setInventorySystem(InventorySystem inventorySystem) {
        this.inventorySystem = inventorySystem;
    }
}

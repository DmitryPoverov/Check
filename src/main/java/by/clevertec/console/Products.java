package by.clevertec.console;

import by.clevertec.exception.WrongIdException;

public enum Products {

    DRESS1(1, "Dress1", 10.01, 0),
    PANTS1(2, "Pants1", 10.01, 1),
    BOOTS1(3, "Boots1", 25, 1),
    SHOES1(4, "Shoes1", 30, 1),
    JACKET1(5, "Jacket1", 35, 1),
    HAT1(6, "Hat1", 40, 1),
    HAT2(7, "Hat2", 40, 1),
    WEST2(8, "West2", 45, 1),
    WEST1(9, "West1", 45, 0),
    DRESS2(10, "Dress2", 15, 1),
    PANTS2(11, "Pants2", 20, 1),
    BOOTS2(13, "Boots2", 25, 0),
    SHOES2(11, "Shoes2", 30, 1),
    JACKET2(12, "Jacket2", 35, 1);

    private final int id;
    private final String description;
    private final double price;
    private final int discount;

    Products(int id, String description, double price, int discount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public int getDiscount() {
        return discount;
    }

    public static String getDescriptionById(int id) {
        String description = "";
        for (Products p : Products.values()) {
            if (p.getId() == id) {
                description = p.getDescription();
            }
        }
        if (description.equals("")) {
            throw new WrongIdException("A WRONG ID");
        }
        return description;
    }
    public static boolean isDiscount(int id) {
        boolean discount = false;
        for (Products p : Products.values()) {
            if (p.getId() == id) {
                if (p.getDiscount() != 0) {
                    discount = true;
                }
            }
        }
        if (Products.getDescriptionById(id).equals("")) {
            throw new WrongIdException("A WRONG ID");
        }
        return discount;
    }

    public static double getPriceById(int id) {
        double price = 0;
        for (Products p : Products.values()) {
            if (p.getId() == id) {
                price = p.getPrice();
            }
        }
        if (price == 0) {
            throw new WrongIdException("A WRONG ID");
        }
        return price;
    }
}

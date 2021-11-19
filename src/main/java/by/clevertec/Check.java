package by.clevertec;

import by.clevertec.exception.WrongIdException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Check {

    private String discountCard;
    private List<ParamMapper> paramMappersList = new ArrayList<>();

    public void setParamMappersList(List<ParamMapper> paramMappersList) {
    this.paramMappersList = paramMappersList;
}
    public void setDiscountCard(String discountCard) {
        this.discountCard = discountCard;
    }

    public Check(String[] args) {
        parseParams(args);
    }

    private void parseParams(String[] appArgs) {
        List<String> tempList = new ArrayList<>();
        String tempCard = "";
        for (String appArg : appArgs) {
            String temp1 = appArg.replace(",", "");
            char[] c = temp1.toCharArray();
            if ((c[0] != 0) && (c[0] >= 48 && c[0] <= 57)) {
                tempList.add(temp1);
            } else if ((c[0] != 0) && (c[0] == 'c')) {
                tempCard = appArg.replace("card-", "");
            } else {
                System.out.println("!!! It seems like you entered a wrong format discount card!!!");
            }
        }
        this.setDiscountCard(tempCard);
        this.setParamMappersList(this.setParamMapper(tempList));

    }

    private List<ParamMapper> setParamMapper(List<String> params) {
        List<ParamMapper> paramMappers = new ArrayList<>();
        for (String line : params) {
            ParamMapper mapper = new ParamMapper();
            var split = line.split("-");
            for (int i=0; i<line.length(); i++) {
                if(i==0) {
                    mapper.setId(Integer.parseInt(split[i]));
                } else if(i==1) {
                    mapper.setQuantity(Integer.parseInt(split[i]));
                }
            }
            paramMappers.add(mapper);
        }
        return paramMappers;
    }

    public void print() {
        int id;
        double price;
        int quantity;
        String description;
        int discountProductsCounter = 0;
        double fiveProductDiscount;
        double fiveProductsTotalDiscount=0;
        double discountCardDiscount = discountCard.equals("")? 0 : 0.15;
        double total;
        double totalDiscount;
        double totalPrice = 0;
        double finalPrice;

        for (ParamMapper pM : paramMappersList) {
            id = pM.getId();
            quantity = pM.getQuantity();

            try {
                if(Products.isDiscount(id)) {
                    discountProductsCounter+=quantity;
                }
            } catch (WrongIdException e) {
                System.out.println("!!! It seems like id=" + id + " is wrong !!!");
            }
        }

        System.out.println("-----------------------------------");
        System.out.println("         CASH RECEIPT");
        System.out.println("          Supermarket\n");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("              " + formatter.format(new Date()));
        System.out.println("-----------------------------------");
        System.out.println("QTY   DESCRIPTION    PRICE   TOTAL");

        for (ParamMapper pM : paramMappersList) {
            fiveProductDiscount = 0;
            id = pM.getId();

            try {
                description = Products.getDescriptionById(pM.getId());
                price = Products.getPriceById(pM.getId());
                quantity = pM.getQuantity();
                if(discountProductsCounter>5){
                    fiveProductDiscount=0.2;
                }
                if (Products.isDiscount(id)) {
                    double fiveProductsCurrentDiscount = fiveProductDiscount*price*quantity;
                    fiveProductsTotalDiscount += fiveProductsCurrentDiscount;
                    total = price*quantity-fiveProductsCurrentDiscount;
                } else {
                    total = price*quantity;
                }
                totalPrice+=total;

                String format = String.format("%2d     %-12s %6.2f %7.2f", quantity, description, price, total);
                System.out.println(format);
            } catch (WrongIdException ignored) {}
        }
        totalDiscount=totalPrice*discountCardDiscount;
        finalPrice = totalPrice - totalDiscount;

        System.out.println("-----------------------------------");
        System.out.println("Discount card No:" + discountCard);
        System.out.printf("Discount card discount%12.2f\n", totalDiscount);
        System.out.printf("SALE discount%21.2f\n", fiveProductsTotalDiscount);
        System.out.printf("Total discount%20.2f\n", fiveProductsTotalDiscount+totalDiscount);
        System.out.printf("TOTAL %28.2f\n", finalPrice);
        System.out.println("-----------------------------------");
    }
}

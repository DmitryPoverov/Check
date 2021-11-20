package by.clevertec.console;

import by.clevertec.exception.WrongIdException;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Check {

    private String discountCard;
    private List<ParamMapper> paramMappersList = new ArrayList<>();

    public Check(String[] args) {
        parseParams(args);
    }

    public Check(String path) {
        String fileContent = convertPathStringToTextString(path);
        String[] args = convertStringToArray(fileContent);
        parseParams(args);
    }

    private void setParamMappersList(List<ParamMapper> paramMappersList) {
        this.paramMappersList = paramMappersList;
    }
    private void setDiscountCard(String discountCard) {
        this.discountCard = discountCard;
    }

    private String[] convertStringToArray(String text) {
        return text.split(", ");
    }

    @SneakyThrows
    private String convertPathStringToTextString(String path) {
        File file = Path.of("src", "main", "resources", path+".txt").toFile();
        StringBuilder collect = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            collect.append(reader.lines()
                    .collect(Collectors.joining("")));
        }
        return collect.toString();
    }

    @SneakyThrows
    public void printToFile() {
        File file = Path.of("src","main", "resources", "checkToFile.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            List<String> stringList = createList();
            for (String s : stringList) {
                writer.write(s);
                writer.newLine();
            }
        }
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

    public void printToConsole() {
        List<String> stringList = createList();
        for (String s : stringList) {
            System.out.println(s);
        }
    }

    public List<String> createList() {
        int id;
        double price;
        int quantity;
        String description;
        int discountProductsCounter = 0;
        double fiveProductDiscount;
        double fiveProductsTotalDiscount=0;
        double discountCardDiscount = discountCard==null? 0 : 0.15;
        double total;
        double totalDiscount;
        double totalPrice = 0;
        double finalPrice;
        List <String> stringsToPrint = new ArrayList<>();

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

        stringsToPrint.add("--------------------------------------");
        stringsToPrint.add("            CASH RECEIPT");
        stringsToPrint.add("            Supermarket\n");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stringsToPrint.add("                  " + formatter.format(new Date()));
        stringsToPrint.add("--------------------------------------");
        stringsToPrint.add("QTY\tDESCRIPTION\t       \tPRICE   TOTAL");

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

                stringsToPrint.add(String.format("%2d  %-17s %7.2f  %6.2f", quantity, description, price, total));
            } catch (WrongIdException ignored) {}
        }
        totalDiscount=totalPrice*discountCardDiscount;
        finalPrice = totalPrice - totalDiscount;

        stringsToPrint.add("--------------------------------------");
        stringsToPrint.add("Discount card No:" + discountCard);
        stringsToPrint.add(String.format("Discount card discount %14.2f", totalDiscount));
        stringsToPrint.add(String.format("SALE discount %23.2f", fiveProductsTotalDiscount));
        stringsToPrint.add(String.format("Total discount %22.2f", fiveProductsTotalDiscount+totalDiscount));
        stringsToPrint.add(String.format("TOTAL %31.2f", finalPrice));
        stringsToPrint.add("--------------------------------------");
        return stringsToPrint;
    }
}

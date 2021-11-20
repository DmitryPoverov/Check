package by.clevertec.console;

public class CheckRunner {

    public static void main(String[] args) {

        //3-1, 5-3, 3-4, 7-2, 12-3, card-006

//        Check myCheck = new Check(args);

        Check myCheck = new Check(args[0]);

        //myCheck.printToConsole();

        myCheck.printToFile();
    }
}

package Labs.lab2.main;

import Labs.lab2.abiturient.Abiturient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Abiturient> abiturients = createArrayOfAbiturients();

        Scanner scanner = new Scanner(System.in);
        int check;
        boolean isEnd = true;

        while (isEnd) {
            System.out.println("""
                    Choose an action (write a number)
                    1 - print all abiturients with the specified name
                    2 - print all abiturients whose average score is higher than the specified one
                    3 - print a given number n of abiturients with the highest average score
                    4 - print all abiturients
                    5 - terminate the program""");
            check = scanner.nextInt();
            scanner.nextLine();
            switch (check) {
                case 1 -> printListOfAbiturientsWithSpecifiedName(abiturients);
                case 2 -> printAbiturientsWithScoreHigherGiven(abiturients);
                case 3 -> printNAbiturientsWithTheHigherScore(abiturients);
                case 4 -> printResultListAsTable(abiturients, "All Abiturients");
                case 5 -> isEnd = false;
                default -> System.out.println("You entered the wrong number, please try again");
            }
        }

        scanner.close();
    }

    public static void printListOfAbiturientsWithSpecifiedName(ArrayList<Abiturient> list){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name by which all abiturients will be printed:\n\t\t");
        String name = sc.nextLine();

        printResultListAsTable(list.stream()
                .filter(x -> x.getName().equals(name))
                .toList(),
        "All abiturients with name - " + name);
    }
    public static void printAbiturientsWithScoreHigherGiven(ArrayList<Abiturient> list){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the score by which all abiturients that have score higher than the one will be printed:\n\t\t");
        double minScore = sc.nextDouble();
        sc.nextLine();

        printResultListAsTable(list.stream()
                .filter(e -> e.getAverageScore() > minScore)
                .toList(),
        "All abiturients that have score higher than - " + minScore);
    }
    public static void printNAbiturientsWithTheHigherScore(ArrayList<Abiturient> list){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of abiturients with the highest score to be printed:\n\t\t");
        int numberToPrint = sc.nextInt();
        sc.nextLine();

        printResultListAsTable(list.stream()
                .sorted((x, y) -> Double.compare(y.getAverageScore(), x.getAverageScore()))
                .limit(numberToPrint)
                .toList(),
        numberToPrint + " abiturients with the highest score");
    }

    public static void printResultListAsTable(List<Abiturient> list, String title) {
        System.out.println("\n\t\t" + title);
        if (list.size() == 0) System.out.println("\tThere are no such abiturients");
        else {
            System.out.println("\t|  Id  |    Last name    |     Name     |    Patronymic    |  Phone number  |  Av.  score  |");
            System.out.println("\t" + "-".repeat(92));
        }
        for (Abiturient x : list) {
            System.out.printf("\t| %4d | %-15s | %-12s | %-16s | %-14s |   %-8.2f   |\n",
                    x.getId(), x.getLastName(), x.getName(), x.getPatronymic(),
                    x.getPhoneNumber(), x.getAverageScore());
        }
    }

    public static ArrayList<Abiturient> createArrayOfAbiturients() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Abiturient> abiturients = new ArrayList<>();

        System.out.print("""
                Enter the data about abiturients following instructions.
                To stop input - in field 'id' enter number less than 0
                """);

        for (int i = 1;; i++) {
            System.out.println("\nEnter information about " + i + "-nth abiturient:");
            System.out.print("\tEnter id (<0 - stop input):\t");
            int id = scanner.nextInt();
            scanner.nextLine();

            if (id < 0) break;

            System.out.print("\tEnter surname:\t\t\t\t");
            String lastName = scanner.nextLine();
            System.out.print("\tEnter name:\t\t\t\t\t");
            String name = scanner.nextLine();
            System.out.print("\tEnter patronymic:\t\t\t");
            String patronymic = scanner.nextLine();
            System.out.print("\tEnter phone number:\t\t\t");
            String phoneNumber = scanner.nextLine();
            System.out.print("\tEnter average score:\t\t");
            double averageScore = scanner.nextDouble();

            abiturients.add(new Abiturient(id,lastName,name,patronymic,phoneNumber,averageScore));
        }

        return abiturients;
    }
}

/*
1
Марич
Олег
Сергійович
123456789
58,9
2
Зінкевич
Василь
Іванович
123456789
88,9
3
Івасюк
Володимир
Михайлович
123456789
96,2
4
Українка
Леся
Петрівна
123456789
86,4
5
Франко
Іван
Якович
123456789
71,5
6
Дутківський
Левко
Тарасович
123456789
68,7
7
Яремчук
Назарій
Назарович
123456789
90,4
8
Стус
Василь
Семенович
123456789
93,8
9
Миколайчук
Іван
Васильович
123456789
98,1
10
Чорновіл
В'ячеслав
Максимович
123456789
99
-1

 */

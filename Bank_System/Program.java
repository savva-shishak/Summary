package Bank_System;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String password = "123456";

        // создаётся объект представляющий банк
        Bank bank = new Bank("New bank", 100);

        // создаются 2 пользователя Шерлок и Ватсон
        User user = new User("Шерлок", "Холмс");
        User user2 = new User("Ватсон", "Доктор");

        /** 
         * Они рагистрируются в этом банке через метод createAccount,
         * передавая ссылки на их объекты, пароль и кодовое слово, 
         * и получаем id или номера их карт.
         */
        int idSherlock = bank.createAccount(user2, password, "codeWorld");
        int idVatson = bank.createAccount(user, password, "codeWorld");

        /**
         * Затем через метод setPlusBalans начисляются деньги на их балансы, 
         * передавая полученные номера карт и суммы зачисления.
         */
        bank.setPlusBalans(idSherlock, 400);
        bank.setPlusBalans(idVatson, 200);
        
        /**
         * Получаем их текущие балансы с помощью метода getBalans, передавая номера карт и пароли от них
         */
        System.out.println("Баланс карты " + idSherlock + ": " + bank.getBalans(idSherlock, password));
        System.out.println("Баланс карты " + idVatson + ": " + bank.getBalans(idVatson, password));

        /**
         * Взаимодействие с пользователем,
         * 
         * Просим пользователя ввести пароль
         */
        System.out.println("Укажите пароль:");

        String EnterPassword = in.nextLine();

        /**
         * И сумму перевода
         */
        System.out.println("Укажите сумму перевода:");

        double money = in.nextDouble();

        /**
         * Переводим деньги с карты Шерлока на карту Ватсона, 
         * передав в метод transfer сумму перевода, номеркарты Ватсона, 
         * номер карты Шерлока и пароль, который ввёл пользователь как пароль от карты Шерлока
         */
        Enum[] status = bank.transfer(money, idVatson, idSherlock, EnterPassword);

        /**
         * Вывод результата
         */
        for (Enum en : status) {
            System.out.println(en);
        }

        /**
         * Опять проверяем их балансы
         */
        System.out.println("Баланс карты " + idSherlock + ": " + bank.getBalans(idSherlock, password));
        System.out.println("Баланс карты " + idVatson + ": " + bank.getBalans(idVatson, password));

        /**
         * В комментариях файла Bank.java подробно описаны все методы и как они работают 
         * В этом проекте практически все переменные имеют область видимость default для того, 
         * чтобы с ними можно было взаимодействовать только в приделах пакета 
         * или через класс Bank
         */
    }
}
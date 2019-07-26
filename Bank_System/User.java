package Bank_System;

/**
 * User
 */
public final class User {

    static int count = 0;
    String name, surname;

    /**
     * через поле passport проверяется уникальность пользователей 
     */
    int passport;

    User(String n, String s){
        name = n;
        surname = s;
        passport = count;

        count++;
    }
}
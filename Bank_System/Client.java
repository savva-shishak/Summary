package Bank_System;

/**
 * Client
 */
public final class Client {

    int id, count = 0;
    User user;
    double balans = 0;
    boolean block = false;
    String password, passwordRev, codeWorld;
    
    /**
     * 
     * @param user - пользователь, к которову привязан счёт
     * @param id - номер карты
     * @param password - пароль
     */
    Client(User user, int id, String password, String codeWorld){
        this.user = user;
        this.password = password;
        this.codeWorld = codeWorld;

        StringBuffer text = new StringBuffer(password);
        text.reverse();

        this.passwordRev = new String(text);
    }
}
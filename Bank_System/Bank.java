package Bank_System;

/**
 * По большому счёту, этот класс является просто контейнером, 
 * в котором хранятся ссылки на объекты Client, 
 * и через который можно взаимодействовать с ними
 */
public final class Bank {

    String name;

    Client[] clients;
    int count = 0;

    /**
     * Конструктор
     * 
     * При создании банка указывается его имя с количество вмещаемых клиентов
     * 
     * @param name - Имя банка
     * @param num - Количество клиентов, которые он может в себя вместить
     */
    Bank(String name, int num){
        this.name = name;
        this.clients = new Client[num];
    }

    /**
     * Создание (открытие) нового счёта
     * 
     * Проверяет на существование пользователя с таким же паспартом,
     * если есть то возвращает -1,
     * если нет, то создаёт нового пользователя и возвращает его id
     * 
     * @param user - Пользователь, которого надо зарегистрировать
     * @param password - Пароль
     * @param codeWord
     * @return номер счёта
     */
    public int createAccount(User user, String password, String codeWold){
        for (Client client : clients) {
            if (client != null && client.user.passport == user.passport) return -1;    
        }

        clients[count] = new Client(user, count, password, codeWold);

        return count++;
    }

    /**
     * Проверка клиента
     * 
     * Если клиента нет в бд, возвращает Not found,
     * Если не верно введён пароль инкрементирует переменную count и возвращает Error password
     * Если клиент заблокирован возвращает Error block
     * Если клиент временно заморожен, Error ice
     * А так же устанавливает количество попыток ввода не правильного пароля
     * 
     * @param id - номер счёта
     * @param password - пароль
     * @return статус
     */
    private Enum checkClient(int id, String password){
        // клиент не найден
        if (clients[id] == null)
            return Status.NotFound;

        // клиент ввёл не правильный пароль
        if (!clients[id].password.equals(password)) {
            clients[id].count++; // счётчик попыток ввода

            return Status.ErrPass;
        }

        // клиент ввёл пароль задом на перёд
        if (clients[id].passwordRev.equals(password)) 
            return Status.Warning;

        // клиент заблокирован
        if (clients[id].block) 
            return Status.ErrBlock;

        // клиент уже существует
        if (id == -1) 
            return Status.AlReal;
        
        // проблем нет
        clients[id].count = 0;
        return Status.Ok;
    }

    /**
     * Существование клиента
     * 
     * Этот метод определяет клиента только на существование в бд 
     * и статус блокировки клиента, т.е. пароль не проверяется.
     * Нужен для переводов между счетами
     * 
     * @param id
     * @return статус
     */
    private Enum checkClient(int id){
        if (id == -1) return Status.AlReal;
        if (clients[id] == null) return Status.NotFound;
        if (clients[id].block) return Status.ErrBlock;

        return Status.Ok;
    }

    /**
     * Снятие со счёта
     * 
     * @param id - Номер счёта
     * @param password - Пароль
     * @param money - Количество счисления
     * @return статус
     */
    public Enum setMinusBalans(int id, String password, double money){
        Enum check = checkClient(id);
        if (check != Status.Ok) return check;
        if (clients[id].balans < money) return Status.SmallMoney;

        clients[id].balans -= money;

        return Status.Ok;
    }

    /**
     * Пополнение счёта
     * 
     * Нам не надо знать пароль счёта того, 
     * кому мы хотим его пополнить. 
     * Поэтоиу здесь используется только id и количество денег
     *                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
     * @param id - Номер счёта
     * @param money - Количество перевода
     * @return статус
     */
    public Enum setPlusBalans(int id, double money) {
        Enum check = checkClient(id);
        if (check != Status.Ok) return check;

        clients[id].balans += money;
        
        return Status.Ok;
    }

    /**
     * Получение баланса
     * 
     * @param id - Номер счёта
     * @param password - Пароль
     * @return статус
     */
    public Object getBalans(int id, String password) {
        Enum check = checkClient(id, password);

        if (check != Status.Ok) return check;

        return clients[id].balans;
    }

    /**
     * Перевод денег от одного клиента, к другому
     * 
     * Здесь Создаётся массив Enum с 4-я элементами (этапами):
     * 1 - Status.Ok, если отправитель найден и он не заблокирован + пароль
     * 2 - Status.Ok, если получатель найден он не заблокирован
     * 3 - Status.Ok. если у получателя достаточно денег
     * 4 - Status.Ok, перевод
     * 
     * 4-й этап происходит только тогда, когда остальные 3 этапа равны Status.Ok
     * 
     * иначе при первой же ошибке метод прерывается, возвращается не полный массив, 
     * у которого последний, из имеющихся, элемент указывает на этап с ошибкой,
     * и у всех клиентов деньги остаются не тронутыми
     * 
     * @param money - сумма перевода
     * @param idTake - номер карты получателя
     * @param id - номер карты отправителя
     * @param password - пароль отправителя
     * @return - статус ответа
     */
    public Enum[] transfer(double money, int idTake, int id, String password) {
        Enum[] process = new Enum[4];

        // 1-й этап 
        // существование отправителя
        process[0] = this.checkClient(id, password);

        if (process[0] != Status.Ok)
            return process;


        // 2-й этап
        // существование получателя
        process[1] = this.checkClient(idTake);
        
        if (process[1] != Status.Ok) {
            return process;
        }

        // 3-й этап
        // возможность перевода
        if (clients[id].balans < money){
            process[2] = Status.SmallMoney;
            return process;
        } 

        process[2] = Status.Ok;


        // 4-й этап
        // перевод
        clients[id].balans -= money; // отправитель
        clients[idTake].balans += money; // получатель

        process[3] = Status.Ok;

        return process;
    }

    /**
     * Блокировка карты
     * 
     * Осуществляется через кодовое слово и номер счёта
     * 
     * @param id - номер счёта
     * @param codeWorld - кодовое слово
     * @return статус
     */
    public Enum BlokedAccount(int id, String codeWold) {
        Enum check = checkClient(id);
        if (check != Status.ErrBlock) return check;
        if (!clients[id].codeWord.equals(codeWord)) return Status.ErrCode;

        clients[id].block = true;
        return Status.Ok;
    }

    /**
     * Разблокировка карты
     * 
     * Осуществляется через кодовое слово и номер счёта
     * 
     * @param id - номер счёта
     * @param codeWorld - кодовое слово
     * @return
     */
    public Enum CloseBlock(int id, String codeWord) {
        Enum check = checkClient(id);
        if (check != Status.ErrBlock) return check;
        if (clients[id].codeWord != codeWorld) return Status.ErrCode;

        clients[id].block = false;
        return Status.Ok;
    }

    /**
     * Получение имени клиента.
     * 
     * Полезно во время переводов
     * 
     * @param id - номер счёта клиента
     * @return строка
     */
    public String getName(int id) {
        if (checkClient(id) != Status.Ok) return null; // если клиент не существует в бд

        return clients[id].user.name;
    }
}

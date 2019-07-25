package Bank_System;

/**
 * Перечисления необходимы при работе между классами и методами.
 * В них переопределены методы toString чтобы выводить на консоль уже понятные для людей ответы программы
 */
public enum Status {
    /**
     * карта временно заблокированна (замороженна)
     */
    ErrIce {
        public String statusDescript = "Карта временно заблокированна (замороженна)";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * карта заблокированна
     */
    ErrBlock {
        public String statusDescript = "Карта заблокированна";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * неправильный пароль
     */
    ErrPass {
        public String statusDescript = "Неправильный пароль";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * не правильное кодовое слово
     */
    ErrCode {
        public String statusDescript = "Не правильное кодовое слово";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * клиент не найден
     */
    NotFound {
        public String statusDescript = "Клиент не найден";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * уже существует (already real)
     */
    AlReal {
        public String statusDescript = "Уже существует";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * особый случай
     */
    Warning {
        public String statusDescript = "Особый случай";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * не достаточно денег
     */
    SmallMoney {
        public String statusDescript = "Не достаточно денег на счёте";

        @Override
        public String toString() {
            return statusDescript;
        }
    },

    /**
     * успешно
     */
    Ok {
        public String statusDescript = "Успешно";

        @Override
        public String toString() {
            return statusDescript;
        }
    }
}
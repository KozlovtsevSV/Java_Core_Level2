package ru.geekbrains;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        String inputStr = "Этот пальчик хочет спать,\n" +
                "Этот пальчик прыг в кровать,\n" +
                "Этот пальчик уж вздремнул,\n" +
                "Этот пальчик уж уснул.\n" +
                "Этот крепко, крепко спит,\n" +
                "И тебе он спать велит.";

        String tempStr = inputStr.replace(",", "");
        tempStr = tempStr.replace(".", "");
        tempStr = tempStr.replace("\n", " ");
        tempStr = tempStr.toLowerCase();
        String[] arrayStr = tempStr.split(" ");

        System.out.println("\n------ЗАДАНИЕ 1------");

        HashMap<String, Integer> strHasp = new HashMap<>();
        for (int i = 0; i < arrayStr.length; i++) {
           if (strHasp.containsKey(arrayStr[i])){
               strHasp.put(arrayStr[i], strHasp.get(arrayStr[i]).intValue() + 1);
           }
           else{
               strHasp.put(arrayStr[i], 1);
           }
        }
        System.out.println(inputStr);
        System.out.println("Список используемых слов и количество их вхожнений в тексте: ");
        System.out.println(strHasp);


        System.out.println("\n------ЗАДАНИЕ 2------");

        PhoneBook myPhoneBook = new PhoneBook("My Phone Book");

        myPhoneBook.insertItemPhoneBook("Иванов","8(923)542-56-54","exsampl_1@geekbrains.ru");
        myPhoneBook.insertItemPhoneBook("Петров","8(999)545-12-15","exsampl_2@geekbrains.ru");
        myPhoneBook.insertItemPhoneBook("Петров","8(913)555-45-55","exsampl_3@geekbrains.ru");
        myPhoneBook.insertItemPhoneBook("Иванов","8(999)483-12-60","exsampl_4@geekbrains.ru");
        myPhoneBook.insertItemPhoneBook("Петров","8(999)654-15-18","exsampl_5@geekbrains.ru");

        // пытаемся вывести данные всей телефонной книги на экран
        System.out.println("Попытка получить данне из класса не используя get методов: " + myPhoneBook);

        System.out.println("Поиск в телефонной книге: "+ myPhoneBook.namePhoneBook);

        String surName = "Петров";

        System.out.println("Результаты поиска e-mail по фамилии " + surName + " " + myPhoneBook.find_E_Mail(surName));

        surName = "Иванов";
        System.out.println("Результаты поиска телефона по фамилии " + surName + " " + myPhoneBook.find_phone(surName));

    }
}


// 1. Создать массив с набором слов (20-30 слов, должны встречаться повторяющиеся):
// - Найти список слов, из которых состоит текст (дубликаты не считать);
// - Посчитать сколько раз встречается каждое слово (использовать HashMap);

// 2. Написать простой класс PhoneBook(внутри использовать HashMap):
// - В качестве ключа использовать фамилию
// - В каждой записи всего два поля: phone, e-mail
// - Отдельный метод для поиска номера телефона по фамилии (ввели фамилию, получили ArrayList телефонов),
//  и отдельный метод для поиска e-mail по фамилии. Следует учесть, что под одной фамилией может быть несколько записей.
// Итого должно получиться 3 класса Main, PhoneBook, Person.

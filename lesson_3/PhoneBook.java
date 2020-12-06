package ru.geekbrains;

import java.util.*;

public class PhoneBook {
    public String namePhoneBook;
    // по одному ключу может быть несколько персон поэтому list из персон
    private HashMap <String, ArrayList<Person>> listPhoneBook = new HashMap <>();

    PhoneBook(String namePhoneBook){
        this.namePhoneBook = namePhoneBook;
     }

    @Override
    public String toString() {
        // закрываем возможность получить данные адресной книги из вне
        return "не отдам пользуйтесть get";

    }

    public void insertItemPhoneBook(String surName, String phone, String eMail){

        ArrayList<Person> tempListPerson= new ArrayList();
        if(listPhoneBook.get(surName) != null){
            tempListPerson = listPhoneBook.get(surName);
            tempListPerson.add(new Person(surName, phone, eMail));
        }
        else{
            tempListPerson.add(new Person(surName, phone, eMail));
        }
        listPhoneBook.put(surName, tempListPerson);
    }

    public ArrayList find_E_Mail(String surName){
        ArrayList<String> result = new ArrayList<>();
        for (Person item : listPhoneBook.get(surName)) {
            result.add(item.get_eMail());
        }
        return result;
    }

    public ArrayList find_phone(String surName){
        ArrayList<String> result = new ArrayList<>();
        for (Person item : listPhoneBook.get(surName)) {
            result.add(item.getPhone());
        }
        return result;
    }

}

package com.example.lab4;

import java.util.ArrayList;

public class BookRepository {
    private static BookRepository ourInstance = new BookRepository();
    public static BookRepository getInstance() {
        return ourInstance;
    }
    private BookRepository(){}
    private final ArrayList<Book> mBooks = new ArrayList<Book>() {
        {
            add(new Book("Коллекционер", "Роман рассказывает историю безумного клерка Фредерика Клегга, попытавшегося добавить в свою коллекцию живого человека.", 1));
            add(new Book("Идиот", "26-летний князь Мышкин возвращается из Швейцарии, где провёл несколько лет, лечась от недуга. Размышление Достоевского о добре и красоте в мире наживы, безбожия, разгула эгоистических страстей не оставят вас равнодушными.", 2));
            add(new Book("Пролетая над гнездом кукушки", "Огромный индеец по кличке Вождь Бромден притворяется глухонемым в психиатрической больнице. Приход нового пациента постепенно меняет его жизнь и побуждает на совершение побега.", 3));
        }
    };
    public Book getBook(int id) {
        return mBooks.get(id);
    }
    public ArrayList<Book> getBooks() {
        return mBooks;
    }
}


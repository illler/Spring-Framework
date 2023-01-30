package org.crud.services;

import org.crud.models.Book;
import org.crud.models.Person;
import org.crud.repositories.BookRepository;
import org.crud.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll(int page, int itemsPerPage, boolean sortByYear) {
        return (sortByYear) ? bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent() :
                bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<String> allTitle() {
        List<Book> books = bookRepository.findAll();
        List<String> title = new ArrayList<>();
        books.forEach(book -> title.add(book.getTitle()));
        return title;
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional(readOnly = false)
    public boolean expired(int id) {
        List<Book> books = personRepository.findById(id).get().getItems();
        for (Book book : books) {
            LocalDate now = LocalDate.now();
            LocalDate createdAt = book.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long daysBetween = ChronoUnit.DAYS.between(createdAt, now);
            if (daysBetween > 10) {
                book.setColor(true);
                return true;
            } else {
                book.setColor(false);
                return false;
            }
        }
        return false;
    }

    @Transactional
    public Book show(int id) {
        Optional<Book> optionalPerson = bookRepository.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public boolean check(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        List<Person> personList = personRepository.findAll();
        for (Person person : personList) {
            if (person == optionalBook.get().getOwner()) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public List<Book> bookList(int id) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> books = new ArrayList<>();
        bookList.forEach(book -> {
            if (book.getOwner() != null) {
                if (book.getOwner().getPerson_id() == id) {
                    books.add(book);
                }
            }
        });
        return books;
    }

    @Transactional
    public Person bookBusy(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        List<Person> personList = personRepository.findAll();
        for (Person person : personList) {
            if (person == optionalBook.get().getOwner()) {
                return person;
            }
        }
        return null;
    }

    @Transactional
    public void resque(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        optionalBook.get().setOwner(null);
    }

    @Transactional
    public void append(int people, int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Optional<Person> optionalPerson = personRepository.findById(people);
        optionalBook.get().setOwner(optionalPerson.get());
        optionalBook.get().setCreatedAt(new Date());
    }

    @Transactional
    public List<Book> performSearch(String query) {
        List<Book> bookList = bookRepository.findAll();
        List<Book> stringList = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().contains(query)) {
                stringList.add(book);
            }
        }
        if (stringList.isEmpty()) {
            return null;
        } else return stringList;
    }
}

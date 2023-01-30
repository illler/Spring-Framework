package org.crud.controllers;

import org.crud.models.Book;
import org.crud.models.Person;
import org.crud.services.BookService;
import org.crud.services.PersonService;
import org.crud.util.BookValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/library/book")
public class BookController {

    private final BookService bookService;

    private final PersonService personService;
    private final BookValidator bookValidator;

    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }


    @GetMapping
    public String index(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        @RequestParam(value = "books_per_page", required = false, defaultValue = "999999999") int bookPerPage,
                        @RequestParam(value = "sort_by_year", required = false, defaultValue = "false")boolean sortByYear,
                        Model model){
        model.addAttribute("books", bookService.findAll(page, bookPerPage, sortByYear));
        return "book/index";
    }

    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("titles", bookService.allTitle());
        return "book/search";
    }

    @PostMapping("/search")
    public String resultSearch(@RequestParam("query") String query, Model model) {
        List<Book> results = bookService.performSearch(query);
        model.addAttribute("results", results);
        return "book/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("books", bookService.show(id));
        model.addAttribute("check", bookService.check(id));
        model.addAttribute("book_busy", bookService.bookBusy(id));
        model.addAttribute("people", personService.findAll());
        return "book/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("books") Book book){
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            return "book/new";
        }
        bookService.save(book);
        return "redirect:/library/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("books", bookService.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id){
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()){
            return "book/edit";
        }
        bookService.update(id, book);
        return "redirect:/library/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/library/book";
    }

    @PatchMapping("/{id}/del")
    public String resque_book(@PathVariable("id") int id){
        bookService.resque(id);
        return "redirect:/library/book/" + id;
    }
    @PatchMapping("/{id}/upd")
    public String chooseBook(@ModelAttribute("people") Person person, @PathVariable("id") int id,
                             @Valid int people){
        bookService.append(people, id);
        return "redirect:/library/book/" + id;
    }

}

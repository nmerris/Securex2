package byAJ.Securex.controllers;

import byAJ.Securex.models.Book;
import byAJ.Securex.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("/list")
    public String listBooks(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "listbooks";
    }
    @GetMapping("/add")
    public String addBook(Model model){
        model.addAttribute("book", new Book());
        return "bookform";
    }
    @PostMapping("/add")
    public String processBook(@ModelAttribute Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "bookform";
        }
        bookRepository.save(book);
        return "redirect:/books/list";
    }
    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable("id")int bookid, Model model){
        Book book = new Book();
        book = bookRepository.findOne(bookid);
        model.addAttribute("book", book);
        return "bookform";
    }
    @RequestMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id")int bookid){
        bookRepository.delete(bookid);
        return "redirect:/books/list";
    }
}

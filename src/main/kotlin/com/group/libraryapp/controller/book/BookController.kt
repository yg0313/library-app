package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.requst.BookLoanRequest
import com.group.libraryapp.dto.book.requst.BookRequest
import com.group.libraryapp.dto.book.requst.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.service.book.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val bookService: BookService
) {

    @PostMapping("/book")
    fun saveBook(@RequestBody request: BookRequest) {
        bookService.saveBook(request)
    }

    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: BookLoanRequest) {
        bookService.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest) {
        bookService.returnBook(request)
    }

    /**
     * 대여중인 책의 권수.
     */
    @GetMapping("/book/loan")
    fun countLoanedBook(): Long {
//        return bookService.countLoanedBook()
        return bookService.countLoanedBookByQuery()
    }

    /**
     * 분야별 책의 권수.
     */
    @GetMapping("/book/stat")
    fun getBookStatistics(): List<BookStatResponse>{
        return bookService.getBookStatistics()
    }
}
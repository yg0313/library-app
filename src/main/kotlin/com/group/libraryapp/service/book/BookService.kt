package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.requst.BookLoanRequest
import com.group.libraryapp.dto.book.requst.BookRequest
import com.group.libraryapp.dto.book.requst.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository
) {

    @Transactional
    fun saveBook(request: BookRequest){
        val newBook = Book(request.name, request.type)
        bookRepository.save(newBook)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest){
        val book = bookRepository.findByName(request.bookName) ?: fail()
        if(userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null){
            throw IllegalArgumentException("진작 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest){
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        return userLoanHistoryRepository.findAllByStatus(UserLoanStatus.LOANED).size
    }

    @Transactional(readOnly = true)
    fun countLoanedBookByQuery(): Long {
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED)
    }

    @Transactional(readOnly = true)
    fun getBookStatistics(): List<BookStatResponse> {
        return bookRepository.findAll() //List<Book>
            .groupBy {book -> book.type} // Map<BookType, List<Book>>
            .map { (type, books) -> BookStatResponse(type, books.size.toLong()) } // List<BookStatResponse>

//        val results = mutableListOf<BookStatResponse>()
//        val books = bookRepository.findAll()
//        for(book in books) {
//            //콜 체인으로 인해 가독성과 유지보수가 힘들어 질 수 있다.
//            val targetDto = results.firstOrNull {dto ->
//                book.type == dto.type
//            }?.plusOne()
//                ?: results.add(BookStatResponse(book.type, 1))
//        }
//        return results
    }

    @Transactional(readOnly = true)
    fun getBookStatisticsByQuery(): List<BookStatResponse> {
        return bookRepository.getStats()
    }
}
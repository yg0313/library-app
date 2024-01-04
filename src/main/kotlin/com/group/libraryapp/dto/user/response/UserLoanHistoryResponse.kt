package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus

data class UserLoanHistoryResponse(
    val name: String, //유저 이름
    val books: List<BookHistoryResponse>
)

data class BookHistoryResponse(
    val name: String,
    val isReturn: Boolean
)
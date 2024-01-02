package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book(
    val name: String,

    @Enumerated(EnumType.STRING) //Enum에 정의된 값이 순서가 아닌 명시된값(String)으로 DB에 저장됨
    val type: BookType, //분야

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    init {
        if (name.isBlank()){
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다.")
        }
    }

    companion object {
        fun fixture(
            name: String = "책 이름",
            type: BookType = BookType.COMPUTER,
            id: Long? = null
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id
            )
        }
    }
}
package com.netcracker.borodin;

public class SQLConstant {

    public static final String GET_AVERAGE_RATE = "select avg(m2m_user_book.rate) as avg_rate from m2m_user_book where book_id = ?1";

    public static final String UPDATE_AVERAGE_RATE = "UPDATE books SET average_rate = ?2 WHERE (`book_id` = ?1)";

    public static final String UPDATE_COMMENT = "UPDATE comments SET modify_date = ?1, text = ?2 WHERE (comment_id = ?3)";

    public static final String UPDATE_USER = "UPDATE users SET email = ?1, username = ?2, role = ?4, state = ?5 WHERE (user_id = ?3)";

    public static final String ADD_USER_BOOK = "INSERT INTO m2m_user_book (user_id, book_id, rate) VALUES (?1, ?2, ?3)";

    public static final String UPDATE_USER_BOOK = "UPDATE m2m_user_book SET rate = ?3 WHERE (user_id = ?1) and (book_id = ?2)";

    public static final String GET_RATE_USER_BOOK = "SELECT rate from m2m_user_book where book_id = ?1 and user_id = ?2";
}

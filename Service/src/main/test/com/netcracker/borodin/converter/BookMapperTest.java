package com.netcracker.borodin.converter;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class BookMapperTest {

    @Autowired private BookMapper bookMapperImpl;

    @Test
    public void toDTO(){
        BookDTO bookDTO = bookMapperImpl.bookToBookDTO(DataClass.goodBook);
        assertEquals(DataClass.goodBookDTO.hashCode(), bookDTO.hashCode());
    }

    @Test
    public void fromDTO(){
        Book book = bookMapperImpl.bookDTOToBook(DataClass.goodBookDTO);
        assertEquals(DataClass.goodBook.hashCode(), book.hashCode());
    }
}
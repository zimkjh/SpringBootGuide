package zerobase.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;
import zerobase.weather.repository.JdbcTemplateMemoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class JdbcTemplateMemoRepositoryTest {
    @Autowired
    JdbcTemplateMemoRepository jdbcTemplateMemoRepository;

    @Test
    void findAllMemoTest() {
        List<Memo> memoList = jdbcTemplateMemoRepository.findAll();
        System.out.println(memoList);
        assertNotNull(memoList);
    }

    @Test
    void insertMemoTest() {
        //given
        Memo newMemo = new Memo(1, "hi~ this is new memo~");

        //when
        jdbcTemplateMemoRepository.save(newMemo);

        //then
        List<Memo> memoList = jdbcTemplateMemoRepository.findAll();
        assertTrue(memoList.size() > 0);
    }

    @Test
    void findByIdTest() {
        //given
        Memo newMemo = new Memo(123, "test 123");
        jdbcTemplateMemoRepository.save(newMemo);

        //when
        Optional<Memo> result = jdbcTemplateMemoRepository.findById(123);

        //then
        assertTrue(result.isPresent());
        assertEquals("test 123", result.get().getText());
    }
}

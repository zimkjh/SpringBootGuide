package zerobase.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zerobase.weather.domain.Memo;
import zerobase.weather.repository.JpaMemoRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class JpaMemoRepositoryTest {
    @Autowired
    JpaMemoRepository jpaMemoRepository;

    @Test
    void findAllMemoTest() {
        List<Memo> memoList = jpaMemoRepository.findAll();
        System.out.println(memoList);
        assertNotNull(memoList);
    }

    @Test
    void insertMemoTest(){
        //given
        Memo newMemo = new Memo(1,"hi~ this is new memo~");

        //when
        jpaMemoRepository.save(newMemo);

        //then
        List<Memo> memoList = jpaMemoRepository.findAll();
        assertTrue(memoList.size() > 0);
    }

    @Test
    void findByIdTest() {
        //given
        Memo newMemo = new Memo(123, "test 123");
        Memo dbMemo = jpaMemoRepository.save(newMemo);
        System.out.println(dbMemo.getId());

        //when
        Optional<Memo> result = jpaMemoRepository.findById(dbMemo.getId());

        //then
        assertTrue(result.isPresent());
        assertEquals("test 123", result.get().getText());
    }
}

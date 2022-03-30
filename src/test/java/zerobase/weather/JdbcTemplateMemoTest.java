package zerobase.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;
import zerobase.weather.repository.JdbcTemplateMemoRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@Transactional
public class JdbcTemplateMemoTest {
    @Autowired
    JdbcTemplateMemoRepository jdbcTemplateMemoRepository;

    @Test
    void memo_테이블_조회하기() {
        List<Memo> memoList = jdbcTemplateMemoRepository.findAll();
        Integer memoCount = memoList.size();
        System.out.println(memoList);
        assertThat(memoCount, greaterThanOrEqualTo(0));
    }

    @Test
    void memo_테이블_데이터_넣기() {
        Memo newMemo = new Memo(3, "happy day~");
        assertThat(jdbcTemplateMemoRepository.save(newMemo), anything());
        List<Memo> memoList = jdbcTemplateMemoRepository.findAll();
        Integer memoCount = memoList.size();
        System.out.println(memoCount);
        assertThat(memoCount, greaterThan(0));
    }
}

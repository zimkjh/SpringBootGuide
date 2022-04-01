package zerobase.weather.repository;

import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository {
    Memo save(Memo memo);

    Optional<Memo> findById(Long id);

    Optional<Memo> findByText(String text);

    List<Memo> findAll();
}

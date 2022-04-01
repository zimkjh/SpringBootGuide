package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateMemoRepository implements MemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Memo save(Memo memo) {
        String sql = "insert into memo values(?,?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    @Override
    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        List<Memo> result = jdbcTemplate.query(sql, memoRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Memo> findAll() {
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    private RowMapper<Memo> memoRowMapper() {
        // ResultSet
        // {id=1, text='happy day~'}
        return (rs, count) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        );
    }
}

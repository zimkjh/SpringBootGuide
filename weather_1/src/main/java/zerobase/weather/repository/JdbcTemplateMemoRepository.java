package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("memo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("text", memo.getText());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        memo.setId(key.intValue());
        return memo;
    }

    @Override
    public Optional<Memo> findById(Long id) {
        List<Memo> result = jdbcTemplate.query("select * from memo where id = ?", memoRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Memo> findByText(String text) {
        return Optional.empty();
    }

    @Override
    public List<Memo> findAll() {
        return jdbcTemplate.query("select * from memo", memoRowMapper());
    }

    private RowMapper<Memo> memoRowMapper() {
        return (rs, rowNum) -> {
            Memo memo = new Memo();
            memo.setId(rs.getInt("id"));
            memo.setText(rs.getString("text"));
            return memo;
        };
    }
}

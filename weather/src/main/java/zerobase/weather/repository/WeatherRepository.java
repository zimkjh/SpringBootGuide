package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {
}

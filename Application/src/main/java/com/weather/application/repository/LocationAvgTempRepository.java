package com.weather.application.repository;



import com.weather.application.domain.LocationAvgTemp;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationAvgTempRepository extends JpaRepository<LocationAvgTemp,Long> {

  @Query(value = " SELECT L FROM  location_avg_temp L WHERE L.location = :location")
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<LocationAvgTemp> findByLocation(@Param("location")String location);

  @Transactional
  void deleteAllByLocation(String location);

}

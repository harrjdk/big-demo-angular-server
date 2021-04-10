package dev.hornetshell.bigdemoangular.repositories;

import dev.hornetshell.bigdemoangular.repositories.entities.SmartData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartDataRepository extends JpaRepository<SmartData, Integer> {

  /**
   * Find the record with the highest ID.
   *
   * <p>This is essentially a over-simplified "most recent".
   *
   * @return a smart data entry or empty.
   */
  Optional<SmartData> findTopByOrderByIdDesc();
}

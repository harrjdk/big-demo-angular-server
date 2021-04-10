package dev.hornetshell.bigdemoangular.services;

import dev.hornetshell.bigdemoangular.repositories.SmartDataRepository;
import dev.hornetshell.bigdemoangular.repositories.entities.SmartData;
import dev.hornetshell.bigdemoangular.types.SmartDataRecord;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Service for handleing smart data. */
@Service
public class SmartDataService {

  private final SmartDataRepository smartDataRepository;

  @Autowired
  public SmartDataService(SmartDataRepository smartDataRepository) {
    this.smartDataRepository = smartDataRepository;
  }

  /**
   * Persist smart data.
   *
   * @param record the event record of smart data.
   */
  @Transactional
  public void saveSmartData(SmartDataRecord record) {
    final SmartData smartData = new SmartData();
    smartData.setCapacityBytes(record.getCapacityBytes());
    smartData.setModel(record.getModel());
    smartData.setSerialNumber(record.getSerialNumber());

    smartDataRepository.save(smartData);
  }

  public Optional<SmartData> getRecentSmartData() {
    return smartDataRepository.findTopByOrderByIdDesc();
  }
}

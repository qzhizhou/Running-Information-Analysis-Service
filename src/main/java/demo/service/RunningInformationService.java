package demo.service;

import demo.domain.RunningInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RunningInformationService {
 // define service methods

    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList);

    public Page<RunningInformation> findByHeartRate(int heartRate, Pageable pageable);

    public Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable);

    public Page<RunningInformation> findAllByRunningId(String runningId, Pageable pageable);

    public Page<RunningInformation> findAll(Pageable pageable);

    public void deleteAll();

    public void deleteByRunningId(String runningId);

}

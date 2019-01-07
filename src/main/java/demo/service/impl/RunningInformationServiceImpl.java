package demo.service.impl;

import demo.domain.RunningInformation;
import demo.domain.RunningInformationRepository;
import demo.service.RunningInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningInformationServiceImpl implements RunningInformationService {
    private RunningInformationRepository runningInformationRepository;

    // constructor dependency injection
    @Autowired
    public RunningInformationServiceImpl(RunningInformationRepository runningInformationRepository){
        this.runningInformationRepository = runningInformationRepository;
    }

    @Override
    public List<RunningInformation> saveRunningInformation(List<RunningInformation> runningInformationList) {
        return this.runningInformationRepository.save(runningInformationList);
    }

    @Override
    public Page<RunningInformation> findByHeartRate(int heartRate, Pageable pageable) {

        return this.runningInformationRepository.findByHeartRate(heartRate, pageable);
    }

    @Override
    public Page<RunningInformation> findByHeartRateGreaterThan(int heartRate, Pageable pageable) {

        return this.runningInformationRepository.findByHeartRateGreaterThan(heartRate, pageable);
    }

    @Override
    public Page<RunningInformation> findAllByRunningId(String runningId, Pageable pageable) {
        return this.runningInformationRepository.findAllByRunningId(runningId, pageable);
    }

    @Override
    public Page<RunningInformation> findAll(Pageable pageable){
        return this.runningInformationRepository.findAll(pageable);
    }

    @Override
    public void deleteAll() {
        this.runningInformationRepository.deleteAll();
    }

    @Override
    public void deleteByRunningId(String runningId){
        this.runningInformationRepository.deleteByRunningId(runningId);
    }
}

package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface RunningInformationRepository extends JpaRepository<RunningInformation, Long> {
    //告诉JpaRepository 映射到 RunningInformation这个表

    Page<RunningInformation> findByHeartRateGreaterThan(
            @Param("heartRate") int heartRate,
            Pageable pageable
    );

    Page<RunningInformation> findByHeartRate(
            @Param("heartRate") int heartRate,
            Pageable pageable
    );

    Page<RunningInformation> findAllByRunningId(
            @Param("runningId") String runningId,
            Pageable pageable
    );

    void deleteByRunningId(@Param("runningId") String runningId);

}

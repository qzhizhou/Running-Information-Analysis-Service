package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "private")
public class RunningInformation {
    public enum HealthWarningLevel{
        LOW, NORMAL, HIGH;
    }

    @Id              //mySql key
    @GeneratedValue  //@id产生的方法
    private Long id;

    //Embed UserInfo
    @Embedded
    private final UserInfo userInfo;

    private  String runningId;

    private double latitude;
    private double longitude;

    private double runningDistance;
    private double totalRunningTime;

    private int heartRate;
    private HealthWarningLevel healthWarningLevel;

    private Date timeStamp = new Date();

    public RunningInformation(){
        this.userInfo = null;
    }

    public RunningInformation(String username, String address){
        this.userInfo = new UserInfo(username, address);
    }

    @JsonCreator
    public RunningInformation(
            @JsonProperty("runningId") String runningId,
            @JsonProperty("latitude") String latitude,
            @JsonProperty("longitude") String longitude,
            @JsonProperty("runningDistance") String runningDistance,
            @JsonProperty("totalRunningTime") String totalRunningTime,
            @JsonProperty("heartRate") String heartRate,
            @JsonProperty("timeStamp") String timeStamp,
            @JsonProperty("userInfo") UserInfo userInfo
    ){
        this.runningId = runningId;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.runningDistance = Double.parseDouble(runningDistance);
        this.totalRunningTime = Double.parseDouble(totalRunningTime);
        this.timeStamp = new Date();
        this.userInfo = userInfo;
        this.heartRate = getRandomHeartRate(60, 200);

        if(this.heartRate > 120){
            this.healthWarningLevel = HealthWarningLevel.HIGH;
        } else if(this.heartRate > 75){
            this.healthWarningLevel = HealthWarningLevel.NORMAL;
        } else if(this.heartRate >= 60){
            this.healthWarningLevel = HealthWarningLevel.LOW;
        } else{
            // Intentionally left blank
        }

        System.out.print(this.heartRate); //used for debug
    }

    public RunningInformation(final UserInfo userInfo){
        this.userInfo = userInfo;
    }

    public String getUsername(){
        return this.userInfo == null ? null : this.userInfo.getUsername();
    }

    public String getAddress(){
        return this.userInfo == null ? null : this.userInfo.getAddress();
    }



    private int getRandomHeartRate(int low, int high){
        Random random = new Random();
        return low + random.nextInt(high - low + 1);
    }








}

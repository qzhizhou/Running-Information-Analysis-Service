package demo.rest;

import demo.domain.RunningInformation;
import demo.service.impl.RunningInformationServiceImpl;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RunningInformationRestController {
    private final String kDefaultPage = "0";
    private final String kDefaultItemPerPage = "2";

    private RunningInformationServiceImpl runningInformationService;

    @Autowired
    public RunningInformationRestController(RunningInformationServiceImpl runningInformationService){
        this.runningInformationService = runningInformationService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)   //因为是created，所以需要改一下responseStatus
    public void upload(@RequestBody List<RunningInformation> runningInformation){
        this.runningInformationService.saveRunningInformation(runningInformation);
    }

    @RequestMapping(value = "/purge", method = RequestMethod.DELETE)
    public void purge(){
        this.runningInformationService.deleteAll();
    }

    @RequestMapping(value = "/purge/{runningId}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable String runningId){
        this.runningInformationService.deleteByRunningId(runningId);
    }

    @RequestMapping(value = "/heartRate/{heartRate}", method = RequestMethod.GET)
    public Page<RunningInformation> findByHeartRate(
            @PathVariable Integer heartRate,
            @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) Integer size
            ){
        return this.runningInformationService.findByHeartRate(heartRate, new PageRequest(page, size));
    }

    @RequestMapping(value = "/heartRateGreaterThan/{heartRate}", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> findByHeartRateGreaterThan(
            //Video: W2codelab2 0:30min
            @PathVariable Integer heartRate,
            @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) Integer size
    ) {
        Page<RunningInformation> rawResult = runningInformationService.findByHeartRateGreaterThan(
                heartRate,
                new PageRequest(page, size)
        );

        return getJsonWithSimpleInfoFromPages(rawResult);
    }

    @RequestMapping(value = "/runningInfo/{runningId}", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> findAllByRunningId(
            @PathVariable String runningId,
            @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) Integer size
    ){
        PageRequest request = new PageRequest(
                //page, size == null ? 2 : size, Sort.Direction.DESC, "healthWarnLevel"
                page,
                size
        );
        Page<RunningInformation> rawResult = runningInformationService.findAllByRunningId(
                runningId,
                request
        );
        return getJsonWithSimpleInfoFromPages(rawResult);
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.GET)
    public ResponseEntity<List<JSONObject>> findAll(
            @RequestParam(name = "page", required = false, defaultValue = kDefaultPage) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = kDefaultItemPerPage) Integer size
    ){
        Sort sort = new Sort(Sort.Direction.DESC, "healthWarningLevel");
        PageRequest request = new PageRequest(page, size, sort);
        Page<RunningInformation> rawResult = runningInformationService.findAll(request);
        return getJsonWithSimpleInfoFromPages(rawResult);
    }

    private ResponseEntity<List<JSONObject>> getJsonWithSimpleInfoFromPages(Page<RunningInformation> rawResult){
        List<RunningInformation> content = rawResult.getContent();
        List<JSONObject> results = new ArrayList<JSONObject>();
        for(RunningInformation item : content){
            JSONObject info = new JSONObject();
            info.put("runningId", item.getRunningId());
            info.put("totalRunningTime", item.getTotalRunningTime());
            info.put("heartRate", item.getHeartRate());
            info.put("userId", item.getId());
            info.put("username", item.getUserInfo().getUsername());
            info.put("userAddress", item.getUserInfo().getAddress());
            info.put("healthWarningLevel", item.getHealthWarningLevel());
            results.add(info);
        }
        // transform RunningInformation to customized Json Object
        return new ResponseEntity<List<JSONObject>>(results, HttpStatus.OK);
    }
}

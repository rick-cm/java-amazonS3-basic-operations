package rcm.project_aws1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rcm.project_aws1.service.StorageService;

import java.util.Optional;

@RestController
public class BucketController {
    private StorageService service;

    public BucketController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/bucket/{bucketName}")
    public ResponseEntity<?> getBucketContentList(@PathVariable("bucketName") String bucketName){
        Optional key = service.listBucketContent(bucketName);
        if (key.isEmpty()){
            return new ResponseEntity(key, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(key, HttpStatus.OK);
    }

    @GetMapping("/bucket")
    public ResponseEntity<?> listBuckets(){
        Optional key = service.listBuckets();
        if (key.isEmpty()){
            return new ResponseEntity(key, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(key, HttpStatus.OK);
    }
}

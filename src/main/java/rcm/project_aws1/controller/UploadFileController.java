package rcm.project_aws1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rcm.project_aws1.service.StorageService;

import java.util.Optional;

@RestController
public class UploadFileController {

    private StorageService service;

    public UploadFileController(StorageService service) {
        this.service = service;
    }

    @PostMapping(value = "/upload", produces = "application/json")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        Optional key = service.upload(file);
        if (key.isEmpty()){
            return new ResponseEntity(key, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(key, HttpStatus.OK);
    }
}

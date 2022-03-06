package rcm.project_aws1.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rcm.project_aws1.model.DownloadedResource;
import rcm.project_aws1.service.StorageService;

import java.util.Map;
import java.util.Optional;


@RestController
public class DownloadFileController {

    private StorageService service;

    public DownloadFileController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestBody Map<String, String> req) {
        Optional<DownloadedResource> optionalResource = service.download(req.get("id"));

        if(optionalResource.isEmpty()) return ResponseEntity.badRequest().body("Arquivo não encontrado");

        DownloadedResource downloadedResource = optionalResource.get();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
        + downloadedResource.getFileName())
          	.contentLength(downloadedResource.getContentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(downloadedResource.getInputStream()));
    }
}

package rcm.project_aws1.service;

import org.springframework.web.multipart.MultipartFile;
import rcm.project_aws1.model.DownloadedResource;

import java.util.List;
import java.util.Optional;

public interface StorageService {
    Optional<String> upload(MultipartFile multipartFile);

    Optional<DownloadedResource> download(String id);

    Optional<List<String>> listBuckets();

    Optional<List<String>> listBucketContent(String bucketName);
}

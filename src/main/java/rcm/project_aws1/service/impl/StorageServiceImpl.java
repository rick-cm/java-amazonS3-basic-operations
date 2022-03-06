package rcm.project_aws1.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import rcm.project_aws1.model.DownloadedResource;
import rcm.project_aws1.service.StorageService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StorageServiceImpl implements StorageService {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private AmazonS3 amazonS3;

    public StorageServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public Optional<String> upload(MultipartFile multipartFile) {
        try {
            File arquivoUpload = new File (multipartFile.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(arquivoUpload);
            outputStream.write(multipartFile.getBytes());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, arquivoUpload.getName(),arquivoUpload);
            return Optional.of(amazonS3.putObject(putObjectRequest).toString());
        }catch (IOException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<DownloadedResource> download(String key_name) {
        try{
            S3Object s3Object = amazonS3.getObject(bucketName, key_name);
            Long contentLength = s3Object.getObjectMetadata().getContentLength();
            return Optional.of(new DownloadedResource(key_name, key_name,contentLength,s3Object.getObjectContent()));
        }catch (AmazonS3Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<String>> listBuckets() {
        List<Bucket> buckets = amazonS3.listBuckets();
        if(buckets.isEmpty()) return Optional.empty();

        return Optional.of(buckets.stream().map(Bucket::getName).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<String>> listBucketContent(String bucketName) {
        boolean bucketExist = amazonS3.doesBucketExistV2(bucketName);
        if(!bucketExist) return Optional.empty();;

        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result;

        result = amazonS3.listObjectsV2(request);
        List<String> contentNameList = new ArrayList<>();
        for (S3ObjectSummary page : result.getObjectSummaries()) {
            contentNameList.add("CONTENT NAME: " + page.getKey() + " SIZE: " + page.getSize());
        }
        if (contentNameList.isEmpty()) return Optional.empty();
        return Optional.of(contentNameList);
    }
}

package rcm.project_aws1.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.s3.key}")
    private String key;

    @Value("${aws.s3.secret}")
    private String secret;

    @Bean
    public AmazonS3 AutheticationS3() {

        logger.info("Authenticating S3...");
        return AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true) //USADO PARA RESOLVER O java.net.UnknownHostException: mybucket.localhost
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl,region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(key, secret) ) )
                .build();
    }
}
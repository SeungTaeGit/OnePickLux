package com.onepicklux.global.common;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3UploaderService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            log.info("S3 이미지 업로드 및 압축 시작. 파일명: {}", fileName);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(inputStream)
                    .size(1080, 1080)
                    .outputQuality(0.8)
                    .toOutputStream(outputStream);

            byte[] compressedImageBytes = outputStream.toByteArray();
            InputStream compressedInputStream = new ByteArrayInputStream(compressedImageBytes);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            metadata.setContentLength(compressedImageBytes.length);

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, compressedInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            log.error("S3 이미지 압축 또는 업로드 중 에러가 발생했습니다.", e);
            throw new RuntimeException("S3 이미지 업로드에 실패했습니다.", e);
        }

        String imageUrl = amazonS3.getUrl(bucket, fileName).toString();
        log.info("S3 업로드 완료! URL: {}", imageUrl);

        return imageUrl;
    }

    public void deleteImage(String fileUrl) {
        if (fileUrl == null || fileUrl.trim().isEmpty()) {
            return;
        }

        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8.name());

            log.info("S3 이미지 삭제 시도: {}", decodedFileName);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, decodedFileName));
            log.info("S3 이미지 삭제 완료: {}", decodedFileName);

        } catch (Exception e) {
            log.error("S3 이미지 삭제 중 에러 발생. 파일 URL: {}", fileUrl, e);
        }
    }
}
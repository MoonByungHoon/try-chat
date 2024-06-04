package study.trychat.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import study.trychat.exception.custom.S3UploadException;
import study.trychat.message.ErrorMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

@Component
@RequiredArgsConstructor
public class S3ImgService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucketName}")
  private String bucketName;

  public List<String> upload(Map<String, MultipartFile> files) {
    List<String> fileAddress = new ArrayList<>();

    if (isEmptyFiles(files)) {
      return null;
    }

    files.forEach((key, file) -> {

      String fileType = determineFileType(file.getOriginalFilename());

      fileAddress.add(uploadFile(file, fileType));
    });

    return fileAddress;
  }

  private boolean isEmptyFiles(Map<String, MultipartFile> files) {
    if (files.isEmpty()) {
      return true;
    }
    return false;
  }

  public String upload(MultipartFile file) {
    validateFileNullCheck(file);

    String fileType = determineFileType(file.getOriginalFilename());

    return uploadFile(file, fileType);
  }

  private String uploadFile(MultipartFile file, String fileType) {
    return switch (fileType) {
      case "jpg", "jpeg", "png" -> uploadImg(file);
      default -> throw new S3UploadException(ErrorMessage.S3_FILE_NOT_FOUND);
    };
  }

  private String uploadImg(MultipartFile file) {
    try {
      return this.uploadImageToS3(file);
    } catch (IOException e) {
      throw new S3UploadException(ErrorMessage.S3_FILE_UPLOAD_FALSE);
    }
  }

  private String uploadImageToS3(MultipartFile file) throws IOException {
    String originalFilename = file.getOriginalFilename();
    String lastFilename = originalFilename.substring(originalFilename.lastIndexOf("."));

    String s3Filename = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

    InputStream is = file.getInputStream();
    byte[] bytes = IOUtils.toByteArray(is);

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("image/" + lastFilename);
    metadata.setContentLength(bytes.length);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    try {
      PutObjectRequest putObjectRequest =
              new PutObjectRequest(bucketName, s3Filename, byteArrayInputStream, metadata)
                      .withCannedAcl(CannedAccessControlList.PublicRead);
    } catch (Exception e) {
      throw new S3UploadException(ErrorMessage.S3_FILE_UPLOAD_FALSE);
    } finally {
      byteArrayInputStream.close();
      is.close();
    }

    return amazonS3.getUrl(bucketName, s3Filename).toString();
  }

  public void deleteFileFromS3(String fileAddress) {
    String ket = getKeyFromFileAddress(fileAddress);
  }

  private String getKeyFromFileAddress(String fileAddress) {
    try {
      URL url = new URL(fileAddress);
      String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");

      return decodingKey.substring(1);
    } catch (MalformedURLException | UnsupportedEncodingException e) {
      throw new S3UploadException(ErrorMessage.S3_FILE_DELETE_FALSE);
    }
  }


  private String determineFileType(String fileName) {
    int lastIndex = fileName.lastIndexOf(".");

    if (lastIndex == -1) {
      throw new S3UploadException(ErrorMessage.S3_FILE_NOT_FOUND);
    }

    return fileName.substring(lastIndex + 1).toLowerCase();
  }

  private void validateFileNullCheck(MultipartFile file) {
    if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
      throw new S3UploadException(ErrorMessage.S3_FILE_IS_NULL);
    }
  }
}

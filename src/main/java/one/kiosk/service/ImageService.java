package one.kiosk.service;

import lombok.RequiredArgsConstructor;
import one.kiosk.entity.Image;
import one.kiosk.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${spring.file.upload-dir}")  // 외부 설정에서 파일 저장 경로를 주입받음
    private String uploadDir;

    private final ImageRepository imageRepository;

    // 이미지 저장 메서드
    public String saveImage(MultipartFile file) throws IOException {
        String fileName = generateUniqueFileName(file);
        Path filePath = Paths.get(uploadDir, fileName);

        // 디렉토리가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 경로에 디렉토리가 없으면 생성
        }

        // 파일 시스템에 이미지 저장
        Files.copy(file.getInputStream(), filePath);

        // Image 엔티티 생성 및 저장
        Image image = new Image();
        image.setImg(filePath.toString()); // 이미지 경로를 저장

        return filePath.toString();
    }

    // 모든 이미지 조회 메서드
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    // 특정 이미지 조회 메서드 (ID로 검색)
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    // 이미지 삭제 메서드
    public boolean deleteImage(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            Path imagePath = Paths.get(image.getImg());
            try {
                Files.deleteIfExists(imagePath); // 파일 시스템에서 삭제
                imageRepository.delete(image);  // 데이터베이스에서 삭제
                return true;
            } catch (IOException e) {
                e.printStackTrace(); // 로그를 남기거나 예외 처리
            }
        }
        return false;
    }

//    public Image findImg(Long imgId) {
//        Optional<Image> findImg = imageRepository.findById(imgId);
//        if(findImg.isEmpty()) throw new CustomException("이미지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
//        return findImg.get();
//    }

    // 파일 이름에 고유한 UUID 추가
    private String generateUniqueFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }


}

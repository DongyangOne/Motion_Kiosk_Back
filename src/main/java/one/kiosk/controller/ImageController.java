package one.kiosk.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.kiosk.entity.Image;
import one.kiosk.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.mysql.cj.conf.PropertyKey.logger;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    // 이미지 업로드 처리
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("title") String title) {
        log.debug("파일 업로드 요청 받음 - 파일명: {}, 제목: {}", file.getOriginalFilename(), title);


        try {
            Image savedImage = imageService.saveImage(file, title);
            log.info("이미지 저장 성공 - 파일명: {}, 이미지 ID: {}", file.getOriginalFilename(), savedImage.getId());
            return new ResponseEntity<>(savedImage, HttpStatus.OK); // 성공적으로 저장된 이미지 반환
        } catch (IOException e) {
            log.error("이미지 업로드 실패 - 파일명: {}", file.getOriginalFilename(), e);
            return new ResponseEntity<>("이미지 업로드 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 모든 이미지 조회
    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK); // 모든 이미지 반환
    }

    // 특정 ID로 이미지 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        Optional<Image> image = imageService.findById(id);
        if (image.isPresent()) {
            return new ResponseEntity<>(image.get(), HttpStatus.OK); // 이미지 존재 시 반환
        } else {
            return new ResponseEntity<>("이미지를 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        }
    }

    // 이미지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {
        boolean deleted = imageService.deleteImage(id);
        if (deleted) {
            return new ResponseEntity<>("이미지 삭제 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("이미지 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

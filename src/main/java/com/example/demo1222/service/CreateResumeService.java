package com.example.demo1222.service;

import com.example.demo1222.Entity.Student;
import com.example.demo1222.Entity.StudentResume;
import com.example.demo1222.Exception.FailedToSaveFileException;
import com.example.demo1222.dto.aim.StudentDTO;
import com.example.demo1222.repositories.StudentRepository;
import com.example.demo1222.repositories.StudentResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CreateResumeService {
    private final StudentRepository studentRepository;
    private final StudentResumeRepository studentResumeRepository;

    public StudentDTO getStudent(Long userId){
        Student student = studentRepository.findByUserId(userId);
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setLastname(student.getLastname());
        return studentDTO;
    }

    public String uploadResume(MultipartFile file,Long userId){
        try {
            // Указываем путь для сохранения файла
            Path uploadPath = Paths.get("C:\\Users\\Виталий\\Desktop\\bazz");
            // Получаем имя файла
            String fileName = generateUniqueFileName(file.getOriginalFilename());

            // Полный путь к файлу на сервере
            Path filePath = uploadPath.resolve(fileName);
            // Сохраняем файл
            Files.copy(file.getInputStream(), filePath);

            StudentResume studentResume = new StudentResume();

            studentResume.setStudent(studentRepository.findByUserId(userId));
            studentResume.setName(fileName);
            studentResume.setPath("C:\\Users\\Виталий\\Desktop\\bazz");

            studentResumeRepository.save(studentResume);

            return filePath.toString();
        } catch (Exception e) {
            throw new FailedToSaveFileException("Failed to save file");
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String timestamp = currentTime.format(formatter);
        return timestamp + "_" + originalFileName;
    }
}

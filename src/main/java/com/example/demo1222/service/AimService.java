package com.example.demo1222.service;

import com.example.demo1222.Entity.*;
import com.example.demo1222.dto.aim.EditAim;
import com.example.demo1222.repositories.*;
import com.example.demo1222.service.dto.AimEdit;
import com.example.demo1222.service.dto.AimStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AimService {

    private final AimRepository aimRepository;
    private final StudentRepository studentRepository;

    private final AimSubjectStudentRepository aimSubjectStudentRepository;

    private final StudentAimStatusRepository studentAimStatusRepository;

    private final StatusRepository statusRepository;

    private final StudentAimNameInfoRepository studentAimNameInfoRepository;

    public HashMap<String, AimStatus> getAimList(Long userId){
        List<AimSubjectStudent> aimSubjectStudents = aimSubjectStudentRepository.findAllByStudent(studentRepository.findByUserId(userId));
        HashMap<String, AimStatus> aimList = new HashMap<>();
        HashSet<Long> uniqueStrings = new HashSet<>();
        for(AimSubjectStudent aimSubjectStudent:aimSubjectStudents){
            uniqueStrings.add(aimSubjectStudent.getAim().getId());
        }
        List<Long> listNum = new ArrayList<>(uniqueStrings);
        for (Long num : listNum) {
            List<AimSubjectStudent> aimSubjects = aimSubjectStudentRepository.findAllByAimId(num);
            List<String> subjectList = new ArrayList<>();
            for (AimSubjectStudent aimSubject : aimSubjects) {
                subjectList.add(aimSubject.getSubject().getName());
            }
            AimStatus aimStatus = new AimStatus();
            aimStatus.setValues(subjectList.toArray(new String[0]));

            AimEdit aimEdit = new AimEdit();
            aimEdit.setValues(subjectList.toArray(new String[0]));


            if (studentAimStatusRepository.findByStudentIdAndAimId(studentRepository.findByUserId(userId).getId(), aimSubjects.get(0).getAim().getId()).isEmpty()) {
                aimStatus.setStatus("none");
                aimEdit.setStatus("none");
            } else {
                aimStatus.setStatus(studentAimStatusRepository.findByStudentIdAndAimId(studentRepository.findByUserId(userId).getId(), aimSubjects.get(0).getAim().getId())
                        .orElseThrow(() -> new RuntimeException("Статус не найден")).getStatus().getName());
                aimEdit.setStatus(studentAimStatusRepository.findByStudentIdAndAimId(studentRepository.findByUserId(userId).getId(), aimSubjects.get(0).getAim().getId())
                        .orElseThrow(() -> new RuntimeException("Статус не найден")).getStatus().getName());
            }


//            aimList.put(aimSubjects.get(0).getAim().getName(),aimStatus);

            if(studentAimNameInfoRepository.findByStudentAndAim(studentRepository.findByUserId(userId),aimRepository.findById(num).orElseThrow(null))!=null){
                StudentAimNameInfo studentAimNameInfo = studentAimNameInfoRepository.findByStudentAndAim(studentRepository.findByUserId(userId),aimRepository.findById(num).orElseThrow(null));
                aimEdit.setAimId(studentAimNameInfo.getAim().getId());
                aimEdit.setDescription(studentAimNameInfo.getInfo());
                aimList.put(studentAimNameInfo.getName(),aimEdit);


            } else{

                aimList.put(aimSubjects.get(0).getAim().getName(),aimStatus);
            }


//            if(!(studentAimNameInfo.getInfo() == null)) {
//                aimStatus.setDescription(studentAimNameInfo.getInfo());
//            }
//            if(studentAimNameInfo.getAim().getId()!=null){
//                aimStatus.setAimId(studentAimNameInfo.getAim().getId());
//            }


//            aimList.put(aimSubjects.get(0).getAim().getName(), aimStatus);



        }

        return aimList;

    }

    public void chooseAim(Long userId,String aimName){
        Student student = studentRepository.findByUserId(userId);
        Aim aim = aimRepository.findByName(aimName);

        StudentAimNameInfo studentAimNameInfo = new StudentAimNameInfo();

        studentAimNameInfo.setName(aimName);
        studentAimNameInfo.setStudent(student);
        studentAimNameInfo.setInfo("");
        studentAimNameInfo.setAim(aim);

        studentAimNameInfoRepository.save(studentAimNameInfo);

        Status status = statusRepository.findById(1L).orElseThrow();
        StudentAimStatus studentAimStatus = new StudentAimStatus();
        studentAimStatus.setStudent(student);
        studentAimStatus.setAim(aim);
        studentAimStatus.setStatus(status);
        
        studentAimStatusRepository.save(studentAimStatus);

    }

    public void editAim(EditAim editAim){
        StudentAimNameInfo studentAimNameInfo = studentAimNameInfoRepository.findByStudentAndAim(studentRepository.findByUserId(editAim.getUserId()),aimRepository.findById(editAim.getAimId()).orElseThrow(null));

        studentAimNameInfo.setName(editAim.getAimName());
        studentAimNameInfo.setInfo(editAim.getAimDescription());

        studentAimNameInfoRepository.save(studentAimNameInfo);

    }


}

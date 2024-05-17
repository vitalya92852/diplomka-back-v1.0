package com.example.demo1222.service;

import com.example.demo1222.Entity.*;
import com.example.demo1222.Exception.RequestGradeExistException;
import com.example.demo1222.repositories.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    private final UserRoleRepository userRoleRepository;
    private final StudentRepository studentRepository;
    private final StatusRepository statusRepository;
    private final GroupSubjectTeacherRepository groupSubjectTeacherRepository;
    private final RequestGradeRepository requestGradeRepository;

    public Map<String,Integer> getSubjectAndAvgGrade(Long userId){
        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterId(userId,1L);
        Set<Long> uniqueSubjectId = new HashSet<>();
        for(Grade grade:gradeList) {
            uniqueSubjectId.add(grade.getSubject().getId());
        }
        Map<String,Integer> map = new HashMap<>();
        for(Long id:uniqueSubjectId){
            int avgGrade = 0;
            int count = 0;
            for(Grade grade:gradeList){
                if(id.equals(grade.getSubject().getId()) && grade.getTypeOfGrade().getId()==1){
                    avgGrade += grade.getGrade();
                    count++;
                }
            }
            avgGrade /= count;
            map.put(subjectRepository.findById(id).orElseThrow(null).getName(),avgGrade);
        }

        return map;
    }

    public int getAvgCombinedGradeFirstPage(Long userId,Long semesterId){
        List<UserRole> userRole = userRoleRepository.findAllByUserId(userId);
        for(UserRole haveRole:userRole){
            if(!haveRole.getRole().getName().equals("ROLE_USER")){
                return 0;
            }
        }
        Long subjectId = 0L;
        List<Long> subjectIdList = gradeRepository.findDistinctSubjectIdsByUserId(userId);
        for(Long grade:subjectIdList){
            subjectId = grade;
            break;
        }

        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndSubjectId(userId,1L,subjectId);
        int avgAcademGrade = 0;
        int countAcademGrade = 0;
        int avgPractiseGrade = 0;
        int countPractiseGrade = 0;

        for(Grade grade:gradeList){

            if(grade.getTypeOfGrade().getId()==1){
                avgAcademGrade +=grade.getGrade();
                countAcademGrade++;
            }
            if(grade.getTypeOfGrade().getId()==2){
                avgPractiseGrade+=grade.getGrade();
                countPractiseGrade++;
            }
        }

        avgAcademGrade/=countAcademGrade;
        avgPractiseGrade/=countPractiseGrade;
        return (avgAcademGrade+avgPractiseGrade)/2;
    }

    public int getAvgCombinedGrade(Long userId,Long semesterId,String subjectName){
        List<UserRole> userRole = userRoleRepository.findAllByUserId(userId);
        for(UserRole haveRole:userRole){
            if(!haveRole.getRole().getName().equals("ROLE_USER")){
                return 0;
            }
        }
        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndSubjectName(userId,1L,subjectName);
        int avgAcademGrade = 0;
        int countAcademGrade = 0;
        int avgPractiseGrade = 0;
        int countPractiseGrade = 0;

        for(Grade grade:gradeList){

            if(grade.getTypeOfGrade().getId()==1){
                avgAcademGrade +=grade.getGrade();
                countAcademGrade++;
            }
            if(grade.getTypeOfGrade().getId()==2){
                avgPractiseGrade+=grade.getGrade();
                countPractiseGrade++;
            }
        }

        int divide = 2;

        if(countAcademGrade<=0) {
            divide--;

        }
        if(avgAcademGrade>0){
            avgAcademGrade /= countAcademGrade;
        }
        if(countPractiseGrade<=0) {
            divide--;

        }

        if(avgPractiseGrade>0){
            avgPractiseGrade /= countPractiseGrade;
        }

        if(divide > 0) {
            return (avgAcademGrade + avgPractiseGrade) / divide;
        }
        return 0;
    }


    public int getRequestGradeFirstPage(Long userId,int requestNumber){

        Long subjectId = 0L;
        List<Long> subjectIdList = gradeRepository.findDistinctSubjectIdsByUserId(userId);
        for(Long grade:subjectIdList){
            subjectId = grade;
            break;
        }

        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(userId, 1L, 1L,subjectId);

        int count = 0;
        int total = 0;
        int finalGrade;
        for(Grade grade:gradeList){
            total+= grade.getGrade();
            count++;
        }

        finalGrade = (count+1)*requestNumber-total;
        if(finalGrade>100) {
            return 0;
        }

        return finalGrade;

    }

    public int getRequestGradeFirstPage(Long userId,int requestNumber,String currentSubject){

        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(userId,1L,1L,subjectRepository.findSubjectByName(currentSubject).getId());

        int count = 0;
        int total = 0;
        int finalGrade;
        for(Grade grade:gradeList){
            total+= grade.getGrade();
            count++;
        }

        finalGrade = (count+1)*requestNumber-total;
        if(finalGrade>100) {
            return 0;
        }

        return finalGrade;

    }

    public void postRequestGrade(Long userId,int grade,String subjectName){

        RequestGrade requestGrade = new RequestGrade();
        Student student = studentRepository.findByUserId(userId);
        Subject subject = subjectRepository.findSubjectByName(subjectName);
        GroupSubjectTeacher groupSubjectTeacher = groupSubjectTeacherRepository.findByGroupIdAndSubjectId(student.getGroup().getId(),subject.getId());
        requestGrade.setGrade(grade);
        requestGrade.setStudent(student);
        requestGrade.setStatus(statusRepository.findById(1L).orElseThrow(null));
        requestGrade.setGroupSubjectTeacher(groupSubjectTeacher);
        RequestGrade tempRequestGrade = requestGradeRepository.findByStudentAndGradeAndGroupSubjectTeacher(student,grade,groupSubjectTeacher).orElse(null);
        if(tempRequestGrade == null) {
            requestGradeRepository.save(requestGrade);
        } else{
            throw new RequestGradeExistException("Вы уже отправляли подобный запрос");
        }


    }

}

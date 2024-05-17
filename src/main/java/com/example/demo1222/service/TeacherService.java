package com.example.demo1222.service;

import com.example.demo1222.Entity.*;
import com.example.demo1222.dto.StudentResponse;
import com.example.demo1222.dto.teacher.StudentRequestResponse;
import com.example.demo1222.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final SubjectRepository subjectRepository;
    private final GroupSubjectTeacherRepository groupSubjectTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final RequestGradeRepository requestGradeRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public HashSet<String> getSubjects(Long userId){
        Teacher teacher = teacherRepository.findByUserId(userId);
        List<GroupSubjectTeacher> groupSubjectTeachers = groupSubjectTeacherRepository.findAllByTeacherId(teacher.getId());
        return groupSubjectTeachers.stream()
                .map(groupSubjectTeachersObj -> groupSubjectTeachersObj.getSubject().getName())
                .collect(Collectors.toCollection(HashSet::new));
    }

    public List<StudentResponse> getStudents(Long userId,String subjectName){
        Teacher teacher = teacherRepository.findByUserId(userId);
        List<GroupSubjectTeacher> groupSubjectTeachers = groupSubjectTeacherRepository.findAllByTeacherIdAndSubjectName(teacher.getId(),subjectName);
        List<Long> groupsId = groupSubjectTeachers.stream().map(groupSubjectTeacher -> groupSubjectTeacher.getGroup().getId()).toList();
        List<Student> studentList = new ArrayList<>();
        for(Long groupId:groupsId){
            List<Student> tempStudentsList = studentRepository.findAllByGroupId(groupId);
            studentList.addAll(tempStudentsList);
        }

        List<StudentResponse> studentResponses = new ArrayList<>();

        for(Student student:studentList){
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(student.getId());
            studentResponse.setName(student.getName());
            studentResponse.setLastname(student.getLastname());
            studentResponse.setSurname(student.getSurname());
            studentResponse.setCourse(student.getCourse());
            studentResponse.setGroup(student.getGroup().getName());

            int avgGrade = 0;
            int count = 0;
            List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(userRepository.findById(student.getUser().getId()).orElseThrow(null).getId(),1L,1L,subjectRepository.findSubjectByName(subjectName).getId());
            for(Grade grade:gradeList){
                avgGrade+=grade.getGrade();
                count++;
            }
            if(count!=0) {
                studentResponse.setStatusGrade(avgGrade / count);
            } else {
                studentResponse.setStatusGrade(0);
            }
            studentResponses.add(studentResponse);

        }
        return studentResponses;
    }


    public HashSet<String> getRequestGradeSubjects(Long userId){
        Teacher teacher = teacherRepository.findByUserId(userId);
        List<RequestGrade> requestGrades = requestGradeRepository.findAllByTeacherIdaAndStatusId(teacher.getId(),1L);
        HashSet<String> hashSet = new HashSet<>();
        for(RequestGrade requestGrade:requestGrades){
            hashSet.add(requestGrade.getGroupSubjectTeacher().getSubject().getName());
        }
        return hashSet;
    }

    public List<StudentRequestResponse> getStudentsRequest(Long teacherId,String subjectName){
        Teacher teacher = teacherRepository.findByUserId(teacherId);
        List<GroupSubjectTeacher> groupSubjectTeachers = groupSubjectTeacherRepository.findAllByTeacherIdAndSubjectName(teacher.getId(),subjectName);
        List<Long> groupsId = groupSubjectTeachers.stream().map(groupSubjectTeacher -> groupSubjectTeacher.getGroup().getId()).toList();
        List<Student> studentList = new ArrayList<>();
        for(Long groupId:groupsId){
            List<Student> tempStudentsList = studentRepository.findAllByGroupId(groupId);
            studentList.addAll(tempStudentsList);
        }

        List<StudentRequestResponse> studentRequestResponses = new ArrayList<>();

        for(int i = 0;i<studentList.size();i++){
            StudentRequestResponse studentRequestResponse = new StudentRequestResponse();
            studentRequestResponse.setId(studentList.get(i).getId());
            studentRequestResponse.setName(studentList.get(i).getName());
            studentRequestResponse.setLastname(studentList.get(i).getLastname());
            studentRequestResponse.setSurname(studentList.get(i).getSurname());
            studentRequestResponse.setCourse(studentList.get(i).getCourse());
            studentRequestResponse.setGroup(studentList.get(i).getGroup().getName());

            int avgGrade = 0;
            int count = 0;
            List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(studentList.get(i).getId(),1L,1L,subjectRepository.findSubjectByName(subjectName).getId());
            for(Grade grade:gradeList){
                avgGrade+=grade.getGrade();
                count++;
            }
            if(count!=0) {
                studentRequestResponse.setStatusGrade(avgGrade / count);
            } else {
                studentRequestResponse.setStatusGrade(0);
            }

            if(requestGradeRepository.findByStudentAndGroupSubjectTeacherAndStatusId(studentList.get(i),groupSubjectTeachers.get(i),1L) == null){
                return null;
            }
            if(requestGradeRepository.findByStudentAndGroupSubjectTeacherAndStatusId(studentList.get(i), groupSubjectTeachers.get(i),1L).getStatus().getName() == null){
                return null;
            }

            studentRequestResponse.setRequestGrade(requestGradeRepository.findByStudentAndGroupSubjectTeacherAndStatusId(studentList.get(i),groupSubjectTeachers.get(i),1L).getGrade());
            studentRequestResponse.setStatusName(requestGradeRepository.findByStudentAndGroupSubjectTeacherAndStatusId(studentList.get(i), groupSubjectTeachers.get(i),1L).getStatus().getName());

            studentRequestResponses.add(studentRequestResponse);
        }
        return studentRequestResponses;
    }

    public void rejectRequest(Long userId,Long studentId,int requestGrade,String subjectName){
        Teacher teacher = teacherRepository.findByUserId(userId);
        RequestGrade requestGrades = requestGradeRepository.findByTeacherIdAndStudentIdAndGrade(teacher.getId(),studentId,requestGrade,subjectName);
        Status status = statusRepository.findById(3L).orElseThrow(null);
        requestGrades.setStatus(status);
        requestGradeRepository.save(requestGrades);

    }

    public void acceptRequest(Long userId,Long studentId,int requestGrade,String subjectName){
        Teacher teacher = teacherRepository.findByUserId(userId);
        Student student = studentRepository.findById(studentId).orElseThrow(null);
        RequestGrade requestGrades = requestGradeRepository.findByTeacherIdAndStudentIdAndGrade(teacher.getId(),studentId,requestGrade,subjectName);
        Status status = statusRepository.findById(2L).orElseThrow(null);
        requestGrades.setStatus(status);
        requestGradeRepository.save(requestGrades);
        Grade grade = gradeRepository.findByUserIdAndSemesterIdAndSubjectNameAndTypeOfGradeIdAndMaxWeek(student.getUser().getId(),1L,subjectName,1L);
        Grade newGrade = new Grade();
        newGrade.setGrade(requestGrades.getGrade());
        newGrade.setUser(grade.getUser());
        newGrade.setTypeOfGrade(grade.getTypeOfGrade());
        newGrade.setWeek(grade.getWeek());
        newGrade.setSemester(grade.getSemester());
        newGrade.setSubject(grade.getSubject());
        gradeRepository.save(newGrade);

    }



}

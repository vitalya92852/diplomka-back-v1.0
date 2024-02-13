package com.example.demo1222.service;

import com.example.demo1222.Entity.Grade;
import com.example.demo1222.Entity.Role;
import com.example.demo1222.Entity.Subject;
import com.example.demo1222.Entity.User;
import com.example.demo1222.dto.UserResponse;
import com.example.demo1222.dto.userData.SemesterResponse;
import com.example.demo1222.dto.userData.WeekResponse;
import com.example.demo1222.repositories.GradeRepository;
import com.example.demo1222.repositories.RoleRepository;
import com.example.demo1222.repositories.SubjectRepository;
import com.example.demo1222.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    @Override
    @Transactional // Транзакция или что то типо того хз
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден",username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public UserResponse getUserResponse(String token,String username){
        UserResponse userResponse = new UserResponse();
        User user = userRepository.findByUsername(username).orElseThrow(null);
        userResponse.setId(user.getId());
        userResponse.setToken(token);
        userResponse.setName(user.getName());
        return userResponse;
    }

    public SemesterResponse getGoalUserData(long userId,Long typeOfGradeId) {
        SemesterResponse semester = new SemesterResponse();
        semester.setSemesterCount(1);
        Long subjectId = 0L;
        List<Long> subjectIdList = gradeRepository.findDistinctSubjectIdsByUserId(userId);
        for(Long grade:subjectIdList){
            subjectId = grade;
            break;
        }
        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(userId, 1L, typeOfGradeId,subjectId);

        List<WeekResponse> weekResponseList = new ArrayList<>();
        Set<Integer> processedWeeks = new HashSet<>();
        for (Grade grade1 : gradeList) {
            if (processedWeeks.contains(grade1.getWeek())) {
                continue;
            }
            WeekResponse weekResponse = new WeekResponse();
            List<Integer> gradeNumList = new ArrayList<>();
            for (Grade grade2 : gradeList) {
                if (grade1.getWeek() == grade2.getWeek()) {
                    gradeNumList.add(grade2.getGrade());
                }
            }
            weekResponse.setGrade(Arrays.stream(gradeNumList.toArray(new Integer[0])).mapToInt(Integer::intValue).toArray());
            weekResponse.setWeekCount(grade1.getWeek());
            weekResponseList.add(weekResponse);

            processedWeeks.add(grade1.getWeek());
        }
        semester.setWeek(weekResponseList.toArray(new WeekResponse[0]));

        return semester;

    }

    public SemesterResponse changeSubject(long userId,Long typeOfGradeId,String subjectName) {
        SemesterResponse semester = new SemesterResponse();
        semester.setSemesterCount(1);
        Long subjectId = subjectRepository.findSubjectByName(subjectName).getId();

        List<Grade> gradeList = gradeRepository.findAllByUserIdAndSemesterIdAndTypeOfGradeIdAndSubjectId(userId, 1L, typeOfGradeId,subjectId);

        List<WeekResponse> weekResponseList = new ArrayList<>();
        Set<Integer> processedWeeks = new HashSet<>();
        for (Grade grade1 : gradeList) {
            if (processedWeeks.contains(grade1.getWeek())) {
                continue;
            }
            WeekResponse weekResponse = new WeekResponse();
            List<Integer> gradeNumList = new ArrayList<>();
            for (Grade grade2 : gradeList) {
                if (grade1.getWeek() == grade2.getWeek()) {
                    gradeNumList.add(grade2.getGrade());
                }
            }
            weekResponse.setGrade(Arrays.stream(gradeNumList.toArray(new Integer[0])).mapToInt(Integer::intValue).toArray());
            weekResponse.setWeekCount(grade1.getWeek());
            weekResponseList.add(weekResponse);

            processedWeeks.add(grade1.getWeek());
        }
        semester.setWeek(weekResponseList.toArray(new WeekResponse[0]));

        return semester;

    }

    public List<String> getUserSubjects(Long userId) {
        List<Long> gradeList = gradeRepository.findDistinctSubjectIdsByUserId(userId);
        List<String> subjectList = new ArrayList<>();
        for (Long gradeId : gradeList) {
            subjectList.add(subjectRepository.findById(gradeId).orElseThrow(null).getName());
        }
        return subjectList;
    }






}

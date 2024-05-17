package com.example.demo1222.service;

import com.example.demo1222.Entity.*;
import com.example.demo1222.dto.RegistrationUserDto;
import com.example.demo1222.dto.UserResponse;
import com.example.demo1222.dto.userData.SemesterResponse;
import com.example.demo1222.dto.userData.WeekResponse;
import com.example.demo1222.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    private final UserRoleRepository userRoleRepository;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    @Override
    @Transactional // Транзакция или что то типо того хз
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден",username)
        ));
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(user.getId());
        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public UserResponse getUserResponse(String token,String username){
        UserResponse userResponse = new UserResponse();
        User user = userRepository.findByUsername(username).orElseThrow(null);
        List<UserRole> userRole = userRoleRepository.findAllByUserId(user.getId());
        List<Role> roles = userRole.stream().map(UserRole::getRole).toList();
        List<String> rolesName = roles.stream().map(Role::getName).toList();
        userResponse.setId(user.getId());
        userResponse.setToken(token);
        if(studentRepository.findByUserId(user.getId())!=null) {
            userResponse.setName(studentRepository.findByUserId(user.getId()).getName());
        } else if(teacherRepository.findByUserId(user.getId())!=null){
            userResponse.setName(teacherRepository.findByUserId(user.getId()).getName());
        }
        userResponse.setRole(rolesName.toArray(new String[0]));
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

    public void createNewUser(RegistrationUserDto registrationUserDto){

        User user = new User();
        user.setUsername(registrationUserDto.getLogin());
        user.setPassword(new BCryptPasswordEncoder().encode(registrationUserDto.getPassword()));

        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(roleRepository.findById(1L).orElseThrow(null));
        userRoleRepository.save(userRole);

        Student student = new Student();
        student.setName(registrationUserDto.getName());
        student.setLastname(registrationUserDto.getLastname());
        student.setSurname(registrationUserDto.getSurname());
        student.setGroup(groupRepository.findByName(registrationUserDto.getGroup()));
        student.setCourse(registrationUserDto.getCourse());
        student.setUser(user);
        studentRepository.save(student);





    }

    public boolean hasEmptyFields(RegistrationUserDto registrationUserDto) {
        return
                registrationUserDto.getPassword() == null
                || registrationUserDto.getConfirmPassword() ==null
                || registrationUserDto.getLogin() == null
                || registrationUserDto.getName() ==null
                || registrationUserDto.getLastname() ==null
                || registrationUserDto.getSurname() ==null
                || registrationUserDto.getGroup() ==null
                || registrationUserDto.getCourse() >0;
    }






}

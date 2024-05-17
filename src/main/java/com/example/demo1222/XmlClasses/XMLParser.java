package com.example.demo1222.XmlClasses;

import com.example.demo1222.Entity.*;
import com.example.demo1222.XmlClasses.StudentXML.*;
import com.example.demo1222.XmlClasses.TeacherXML.AssignmentXML;
import com.example.demo1222.XmlClasses.TeacherXML.TeacherXML;
import com.example.demo1222.XmlClasses.TeacherXML.TeachersXML;
import com.example.demo1222.dto.userData.SemesterResponse;
import com.example.demo1222.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XMLParser {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;
    private final TypeOfGradeRepository typeOfGradeRepository;
    private final TeacherRepository teacherRepository;
    private final GroupSubjectTeacherRepository groupSubjectTeacherRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    public void parseStudentData(){
        try {
            File file = new File("C:\\Users\\Виталий\\IdeaProjects\\demo1222\\xml\\student.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(StudentsXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StudentsXML studentsXML = (StudentsXML) jaxbUnmarshaller.unmarshal(file);

            // Вывод информации о студентах
            List<StudentXML> students = studentsXML.getStudents();
            for (StudentXML student : students) {
                String[] fullName = student.getFullName().split("\\s+");

                User user = new User();
                user.setUsername(fullName[0]+"_"+fullName[1]);
                user.setPassword(new BCryptPasswordEncoder().encode("123"));

                Student studentData = new Student();
                studentData.setName(fullName[0]);
                studentData.setLastname(fullName[1]);
                studentData.setSurname(fullName[2]);
                studentData.setUser(user);
                Group group = new Group();
                if(groupRepository.findByName(student.getGroup())!=null){
                    group = groupRepository.findByName(student.getGroup());
                } else{
                    group.setName(student.getGroup());
                    groupRepository.save(group);
                }
                studentData.setGroup(group);
                studentData.setCourse(student.getCourse());

                userRepository.save(user);
                studentRepository.save(studentData);

                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(roleRepository.findById(1L).orElseThrow(null));
                userRoleRepository.save(userRole);


                List<SemesterXML> semesters = student.getSemesters();
                for (SemesterXML semester : semesters) {
                    List<WeekXML> weeks = semester.getWeeks();
                    for (WeekXML week : weeks) {
                       Grade grade = new Grade();
                       grade.setWeek(week.getNumber());
                       grade.setUser(user);
                       grade.setTypeOfGrade(typeOfGradeRepository.findById(1L).orElseThrow(null));


                        List<GradeXML> grades = week.getGrades();
                        for(GradeXML gradeXML:grades){
                            grade.setGrade(gradeXML.getGrade());
                            grade.setSubject(subjectRepository.findSubjectByName(gradeXML.getSubject()));
                            grade.setSemester(semesterRepository.findByCount(semester.getNumber()));
                            gradeRepository.save(grade);
                        }
                    }
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void parseTeacherData(){
        try {
            File file = new File("C:\\Users\\Виталий\\IdeaProjects\\demo1222\\xml\\teacher.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(TeachersXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TeachersXML teachersXML = (TeachersXML) jaxbUnmarshaller.unmarshal(file);

            for(TeacherXML teacherXML:teachersXML.getTeacher()) {
                String[] fullName = teacherXML.getFullname().split("\\s+");

                User user = new User();
                user.setUsername(fullName[0]+"_"+fullName[1]);
                user.setPassword(new BCryptPasswordEncoder().encode("123"));
                Teacher teacher = new Teacher();
                teacher.setName(fullName[0]);
                teacher.setLastname(fullName[1]);
                teacher.setSurname(fullName[2]);
                teacher.setUser(user);
                userRepository.save(user);
                teacherRepository.save(teacher);

                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(roleRepository.findById(3L).orElseThrow(null));
                userRoleRepository.save(userRole);

                for (AssignmentXML assignment : teacherXML.getTeachingAssignmentsXML().getAssignmentXML()) {
                    GroupSubjectTeacher groupSubjectTeacher = new GroupSubjectTeacher();
                    groupSubjectTeacher.setTeacher(teacher);
                    groupSubjectTeacher.setSubject(subjectRepository.findSubjectByName(assignment.getSubject()));
                    groupSubjectTeacher.setGroup(groupRepository.findByName(assignment.getGroup()));
                    if(groupSubjectTeacher.getSubject()==null || groupSubjectTeacher.getGroup()==null){
                        continue;
                    }
                    groupSubjectTeacherRepository.save(groupSubjectTeacher);
                }
            }
        } catch (JAXBException e){
            e.printStackTrace();
        }
    }
}

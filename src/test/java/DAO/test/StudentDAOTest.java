package  DAO.test;

import com.muyundefeng.spring.dao.StudentDAO;
import com.muyundefeng.spring.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xukangming on 16-5-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Mybatis.groovy")
public class StudentDAOTest {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    StudentDAO studentDAO;
    @Test
    public void testSelect(){
        String id = "1123";
        Student student = studentDAO.selectStudentByPrimaryKey(id);
        System.out.println(student.getName());
    }

}

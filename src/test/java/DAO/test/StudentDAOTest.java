package  DAO.test;

import com.muyundefeng.spring.dao.BlogDAO;
import com.muyundefeng.spring.entity.Student;
import com.muyundefeng.spring.mapper.StudentMapper;
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
    StudentMapper studentMapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    BlogDAO blogDAO;

    @Test
    public void testSelect(){
        Student student = studentMapper.selectByPrimaryKey(1234);
        System.out.println(student.getClass().getName());
    }
    @Test
    public void testBlogDAO(){
        //Student student = blogDAO.selectAllBlogs();
        System.out.println(blogDAO.selectAllBlogs());
    }
}

package  com.example.admin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertiesTest {
    @Value("$path.python_file_path")
    public String path;

    @Test
    public void test_file_path(){
        Assert.assertEquals(path, "C:/代码相关/实验室/测试用文件夹/测试工程/admin/src/main/java/com/example/admin/algorithms/python");
    }

    public static void main(String[] args) {
        System.out.println("test");
    }

}
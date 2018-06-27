package tao.core.repositorymapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
public class ItemRepositoryMapperTest {

    @Autowired
    private ItemRepositoryMapper itemRepositoryMapper;

    @Test
    public void should_return_data() {
        //itemRepositoryMapper.getAll("h","city");
    }
}
package tao.usecase.nomenclature.core.repositorymapper;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@MybatisTest
@Ignore
public class ItemRepositoryMapperTest {

    @Autowired
    private ItemRepositoryMapper itemRepositoryMapper;

    /**
     * SELECT id, countryShortName, countryCodeLen2, countryCodeLen3, countryNumCode, countryLanguage, language, countryContinentId
     * FROM t_pays
     * WHERE status IN ( '0', '1' ) and lang IN ( 'FR', 'EN' )
     * order by  ASC
     * limit 0, 50
     */
    @Test
    public void should_return_data() {
        final String selectedFields = "id, countryShortName, countryCodeLen2, countryCodeLen3, countryNumCode, countryLanguage, language, countryContinentId";
        final String tableName = "t_pays";
        final String whereClauses = "status IN ( '0', '1' ) and lang IN ( 'FR', 'EN' )";
        final String orderByField = "code";
        final String orderByDirection = "ASC";
        final String limitClause = "limit 0, 50";

        List<Map<String, Object>> result = itemRepositoryMapper.getAll(selectedFields, tableName, whereClauses, orderByField, orderByDirection, limitClause);

        assertThat(result).isNotEmpty();
    }

    @Test
    public void should_return_count_value_greaterthan_zero() {
        final String tableName = "t_pays";
        Integer result = itemRepositoryMapper.count(tableName);

        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void should_return_count_value_greaterthan_zero_with_sql_injection() {
        final String tableName = "t_pays; delete from t_pays;--";
        Integer result1 = itemRepositoryMapper.count(tableName);
        Integer result2 = itemRepositoryMapper.count(tableName);

        assertThat(result1).isEqualTo(result2);
    }

    @Test
    public void should_return_data_when_findbyid() {
        final String selectedFields = "id, countryShortName, countryCodeLen2, countryCodeLen3, countryNumCode, countryLanguage, language, countryContinentId";
        final String tableName = "t_pays";
        final String whereClauses = "status IN ( '0', '1' ) and lang IN ( 'FR', 'EN' )";

        Map<String, Object> result = itemRepositoryMapper.findById(selectedFields, tableName, whereClauses, "id", "1");

        assertThat(result).isNotNull();
    }
}
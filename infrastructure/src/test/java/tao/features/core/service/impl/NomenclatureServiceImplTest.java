package tao.features.core.service.impl;


import org.junit.Before;
import org.mockito.Mock;
import tao.features.core.repositorymapper.ItemRepositoryMapper;
import tao.features.core.service.SqlHelper;

public class NomenclatureServiceImplTest {

    private NomenclatureServiceImpl nomenclatureService;

    @Mock
    ItemRepositoryMapper itemRepositoryMapper;

    SqlHelper sqlHelper;

    @Before
    public void setUp() throws Exception {
        sqlHelper = new SqlHelperImpl();
        nomenclatureService = new NomenclatureServiceImpl(itemRepositoryMapper, sqlHelper);
    }
}
package tao.usecase.nomenclature.core.service.impl;


import org.junit.Before;
import org.mockito.Mock;
import tao.usecase.nomenclature.core.repositorymapper.ItemRepositoryMapper;
import tao.usecase.nomenclature.core.service.SqlHelper;

public class NomenclatureRepositoryImplTest {

    private NomenclatureRepositoryImpl nomenclatureService;

    @Mock
    ItemRepositoryMapper itemRepositoryMapper;

    SqlHelper sqlHelper;

    @Before
    public void setUp() throws Exception {
        sqlHelper = new SqlHelperImpl();
        nomenclatureService = new NomenclatureRepositoryImpl(itemRepositoryMapper, sqlHelper);
    }
}
package tao.usecase.nomenclature.format.adapter;

import java.util.List;

public interface ResponseContentTypeFactory {
    ContentTypeProducer create(List<String> headers);
}

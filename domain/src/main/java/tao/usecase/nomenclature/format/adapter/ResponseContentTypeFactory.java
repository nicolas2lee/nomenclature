package tao.usecase.nomenclature.format.adapter;

import java.util.List;

public interface ResponseContentTypeFactory {
    ContentTypeFactory create(List<String> headers);
}

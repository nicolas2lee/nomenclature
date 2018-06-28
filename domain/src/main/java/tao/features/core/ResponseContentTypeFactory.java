package tao.features.core;

import tao.features.format.adapter.ContentTypeFactory;

import java.util.List;

public interface ResponseContentTypeFactory {
    ContentTypeFactory create(List<String> headers);
}

package io.agrest.runtime.encoder;

import io.agrest.ResourceEntity;
import io.agrest.encoder.Encoder;
import io.agrest.encoder.EntityMetadataEncoder;
import io.agrest.encoder.PropertyMetadataEncoder;
import io.agrest.encoder.ResourceEncoder;
import io.agrest.runtime.semantics.IRelationshipMapper;
import org.apache.cayenne.di.Inject;

import java.util.Map;

public class EncoderService implements IEncoderService {

    protected IAttributeEncoderFactory attributeEncoderFactory;
    protected IRelationshipMapper relationshipMapper;
    protected IStringConverterFactory stringConverterFactory;
    protected Map<String, PropertyMetadataEncoder> propertyMetadataEncoders;

    public EncoderService(
            @Inject IAttributeEncoderFactory attributeEncoderFactory,
            @Inject IStringConverterFactory stringConverterFactory,
            @Inject IRelationshipMapper relationshipMapper,
            @Inject Map<String, PropertyMetadataEncoder> propertyMetadataEncoders) {

        this.attributeEncoderFactory = attributeEncoderFactory;
        this.relationshipMapper = relationshipMapper;
        this.stringConverterFactory = stringConverterFactory;
        this.propertyMetadataEncoders = propertyMetadataEncoders;
    }

    @Override
    public <T> Encoder metadataEncoder(ResourceEntity<T> entity) {
        return new ResourceEncoder<>(entity, entity.getApplicationBase(), entityMetadataEncoder(entity));
    }

    @Override
    public <T> Encoder dataEncoder(ResourceEntity<T> entity) {
        return dataEncoderFactory().encoder(entity);
    }

    protected <T> DataEncoderFactory dataEncoderFactory() {
        return new DataEncoderFactory(attributeEncoderFactory, stringConverterFactory, relationshipMapper);
    }

    protected Encoder entityMetadataEncoder(ResourceEntity<?> resourceEntity) {
        return new EntityMetadataEncoder(resourceEntity, propertyMetadataEncoders);
    }
}

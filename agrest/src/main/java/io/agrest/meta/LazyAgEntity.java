package io.agrest.meta;

import io.agrest.property.IdReader;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @since 2.0
 */
public class LazyAgEntity<T> extends BaseLazyAgEntity<T, AgEntity<T>> implements AgEntity<T> {

    private Class<T> type;

    public LazyAgEntity(Class<T> type, Supplier<AgEntity<T>> delegateSupplier) {
        super(delegateSupplier);
        this.type = type;
    }

    @Override
    public String getName() {
        return getDelegate().getName();
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public IdReader getIdReader() {
        return getDelegate().getIdReader();
    }

    @Override
    public Collection<AgAttribute> getIds() {
        return getDelegate().getIds();
    }

    @Override
    public AgAttribute getIdAttribute(String name) {
        return getDelegate().getIdAttribute(name);
    }

    @Override
    public Collection<AgAttribute> getAttributes() {
        return getDelegate().getAttributes();
    }

    @Override
    public AgAttribute getAttribute(String name) {
        return getDelegate().getAttribute(name);
    }

    @Override
    public Collection<AgRelationship> getRelationships() {
        return getDelegate().getRelationships();
    }

    @Override
    public AgRelationship getRelationship(String name) {
        return getDelegate().getRelationship(name);
    }
}

package io.github.pudo58.base.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public abstract class AbstractService<T> implements io.github.pudo58.base.service.core.Service<T> {
    @Autowired
    @SuppressWarnings("all")
    protected JpaRepository<T, UUID> repo;

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public T save(T t) {
        return repo.save(t);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public T update(UUID uuid, T t) {
        T old = repo.findById(uuid).orElse(null);
        if (old == null) {
            return null;
        }
        return repo.save(t);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void delete(T t) {
        repo.delete(t);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void deleteById(UUID uuid) {
        repo.deleteById(uuid);
    }

    @Override
    public T findById(UUID uuid) {
        return repo.findById(uuid).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repo.findAll();
    }
}

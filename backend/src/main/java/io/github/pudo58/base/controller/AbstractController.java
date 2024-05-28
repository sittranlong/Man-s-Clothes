package io.github.pudo58.base.controller;

import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.base.service.core.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class AbstractController<T> extends AbstractService<T> implements Service<T> {
    @Autowired
    private Service<T> service;
    @PostMapping("/save")
    public T save(@RequestBody T t) {
        return service.save(t);
    }

    @PutMapping("/update/{id}")
    public T update(@PathVariable UUID id, @RequestBody T t) {
        return service.update(id, t);
    }

    @DeleteMapping("/delete")
    public void delete(T t) {
        service.delete(t);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public T findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/findAll")
    public List<T> findAll() {
        return service.findAll();
    }

}

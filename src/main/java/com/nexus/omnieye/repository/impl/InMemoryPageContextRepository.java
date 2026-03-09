package com.nexus.omnieye.repository.impl;

import com.nexus.omnieye.core.OmniContext;
import com.nexus.omnieye.repository.PageContextRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

@Repository
public class InMemoryPageContextRepository implements PageContextRepository {
    private final ConcurrentMap<String, OmniContext> contexts = new ConcurrentHashMap<>();

    @Override
    public Optional<OmniContext> findByUrl(String url) {
        return Optional.ofNullable(contexts.get(url));
    }

    @Override
    public OmniContext getOrCreate(String url, Supplier<OmniContext> supplier) {
        return contexts.computeIfAbsent(url, key -> supplier.get());
    }
}

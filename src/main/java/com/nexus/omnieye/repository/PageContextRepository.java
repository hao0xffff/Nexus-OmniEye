package com.nexus.omnieye.repository;

import com.nexus.omnieye.core.OmniContext;

import java.util.Optional;
import java.util.function.Supplier;

public interface PageContextRepository {
    Optional<OmniContext> findByUrl(String url);

    OmniContext getOrCreate(String url, Supplier<OmniContext> supplier);
}

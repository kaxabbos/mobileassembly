package com.mobileassembly.repo;

import com.mobileassembly.models.Actions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoActions extends JpaRepository<Actions, Long> {
}

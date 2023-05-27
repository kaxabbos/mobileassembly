package com.mobileassembly.repo;

import com.mobileassembly.models.Devices;
import com.mobileassembly.models.enums.DeviceType;
import com.mobileassembly.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoDevices extends JpaRepository<Devices, Long> {

    List<Devices> findByServesId(long id);

    List<Devices> findByStatus(Status status);

    List<Devices> findByDeviceType(DeviceType type);

    List<Devices> findByStatusAndDeviceType(Status status, DeviceType type);
}

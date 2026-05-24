package proseccovan.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import proseccovan.backend.controller.packages.dto.PackageDto;
import proseccovan.backend.persistence.bookingpackage.PackageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;

    public List<PackageDto> getPackages() {
        return packageRepository.findAll().stream()
                .map(p -> {
                    PackageDto dto = new PackageDto();
                    dto.setPackageId(p.getId());
                    dto.setPackageName(p.getName());
                    dto.setPackageDescription(p.getDescription());
                    return dto;
                })
                .toList();
    }
}

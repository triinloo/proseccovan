package proseccovan.backend.controller.packages;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proseccovan.backend.controller.packages.dto.PackageDto;
import proseccovan.backend.service.PackageService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @Operation(
            summary = "Paketite nimekiri. Tagastab packageId, packageName, packageDescription",
            description = "Tagastab kõik paketid andmebaasist. Autentimine nõutud.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/packages")
    public List<PackageDto> getPackages() {
        return packageService.getPackages();
    }
}

package proseccovan.backend.controller.packages.dto;

import lombok.Data;

@Data
public class PackageDto {
    private Integer packageId;
    private String packageName;
    private String packageDescription;
}

package com.ahad.helper;

import com.ahad.dto.imports.CompanySearchDTO;

public class FallbackResponse {

    // ✅ Public static so it can be used anywhere
    public static CompanySearchDTO defaultCompanyDTO() {
        return CompanySearchDTO.builder()
                .id(null) // optional, ya UUID.randomUUID()
                .name("UNKNOWN")
                .headline("Not available")
                .location("UNKNOWN")
                .build();
    }
}

package com.genesisnest.gdpt;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import com.genesisnest.gdpt.app.GnDeliveryPilotTestApplication;

class ModulithStructureTest {

    @Test
    void verifyModularStructure() {
        ApplicationModules modules = ApplicationModules.of(GnDeliveryPilotTestApplication.class);
        modules.verify(); // 모듈 간 순환 참조나 잘못된 접근이 있으면 실패
    }
}

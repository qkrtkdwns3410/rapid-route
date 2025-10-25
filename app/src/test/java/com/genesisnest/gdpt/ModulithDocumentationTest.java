package com.genesisnest.gdpt;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.core.DependencyDepth;
import org.springframework.modulith.docs.Documenter;
import com.genesisnest.gdpt.app.GnDeliveryPilotTestApplication;

class ModulithDocumentationTest {

    @Test
    void createModuleDocumentation() {
        ApplicationModules modules = ApplicationModules.of(GnDeliveryPilotTestApplication.class);

        new Documenter(modules)
                .writeModulesAsPlantUml(
                        Documenter.DiagramOptions.defaults()
                                .withDependencyDepth(DependencyDepth.ALL)
                                .withElementsWithoutRelationships(
                                        Documenter.DiagramOptions.ElementsWithoutRelationships.VISIBLE)
                                .withStyle(Documenter.DiagramOptions.DiagramStyle.UML))
                .writeIndividualModulesAsPlantUml();
    }
}

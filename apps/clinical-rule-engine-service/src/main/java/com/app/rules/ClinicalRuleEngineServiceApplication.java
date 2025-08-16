package com.app.rules;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ClinicalRuleEngineServiceApplication implements QuarkusApplication {

    public static void main(String[] args) {
        Quarkus.run(ClinicalRuleEngineServiceApplication.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        Quarkus.waitForExit();
        return 0;
    }
}

package com.apress.chapter6.jce.providers.bouncycastle.signature;

import java.security.Security;

/**
 * List supported curve names by JCE.
 * Although this example demonstrates EC curves supported by JCE, we thought it'd be better to place it
 * under the bouncycastle package, as many EC curves require an external JCE provider to work.
 */
public class JceSupportedECCurves {

    public static void main(String[] args) {
        System.out.println(Security.getProviders("AlgorithmParameters.EC")[0]
                .getService("AlgorithmParameters", "EC").getAttribute("SupportedCurves"));
    }
}

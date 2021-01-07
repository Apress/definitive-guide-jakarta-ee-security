package org.eclipse.microprofile.test.jwt;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
@DeclareRoles({"ViewBalance", "Debtor", "Creditor", "Debtor2", "Whale"})
@Path("/")
@DenyAll
public class MySecureWallet {
    private double whaleLimit = 1000;
    private BigDecimal usdBalance = new BigDecimal("100000.0000");
    private BigDecimal bitcoinXrate = new BigDecimal("4538.0000");
    private BigDecimal ethereumXrate = new BigDecimal("328.0000");

    @Inject
    private JsonWebToken jwt;

    @Inject
    @Claim("warningLimit")
    private Instance<Optional<JsonNumber>> warningLimitInstance;

    @GET
    @Path("/balance")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ViewBalance", "Debtor", "Creditor"})
    public JsonObject getBalance() {
        return generateBalanceInfo();
    }

    @GET
    @Path("/debit")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Debtor", "Debtor2"})
    public Response debit(@QueryParam("amount") String amount,
                          @Context SecurityContext securityContext) {
        Double dAmount = Double.valueOf(amount);
        if (dAmount > whaleLimit) {
            if (securityContext.isUserInRole("Whale")) {
                // Validate the spending limit from the token claim
                JsonNumber spendingLimit = jwt.getClaim("spendingLimit");
                if (spendingLimit == null || spendingLimit.doubleValue() < dAmount) {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        usdBalance = usdBalance.subtract(new BigDecimal(amount));
        return Response.ok(generateBalanceInfo()).build();
    }

    @GET
    @Path("/credit")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Creditor")
    public JsonObject credit(@QueryParam("amount") String amount) {
        usdBalance = usdBalance.add(new BigDecimal(amount));
        return generateBalanceInfo();
    }

    private JsonObject generateBalanceInfo() {
        BigDecimal balanceInBitcoins = usdBalance.divide(bitcoinXrate, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal balanceInEthereum = usdBalance.divide(ethereumXrate, BigDecimal.ROUND_HALF_EVEN);
        JsonObjectBuilder result = Json.createObjectBuilder()
                .add("usd", usdBalance)
                .add("bitcoin", balanceInBitcoins)
                .add("ethereum", balanceInEthereum);

        Optional<JsonNumber> warningLimit = warningLimitInstance.get();
        if (warningLimit.isPresent()) {
            if (warningLimit.get().doubleValue() > usdBalance.doubleValue()) {
                String warningMsg = String.format("balance is below warning limit: %s", warningLimit.get());
                result.add("warning", warningMsg);
            }
        }

        return result.build();
    }

}

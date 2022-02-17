package com.arkmikhjava.openapi.tinkoff.client.reports;

import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.IOException;
import java.util.List;

public class AllFigiStockReport extends CommonReport {

    public AllFigiStockReport(List<MarketInstrument> instruments) throws IOException {
        super();
        this.setFileName("allFigis.csv")
                .setReportObjects(instruments)
                .setFields(new String[]{"figi"});
    }

}
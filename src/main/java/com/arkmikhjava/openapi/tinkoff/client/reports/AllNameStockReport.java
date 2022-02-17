package com.arkmikhjava.openapi.tinkoff.client.reports;

import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.IOException;
import java.util.List;

public class AllNameStockReport extends CommonReport {

    public AllNameStockReport(List<MarketInstrument> instruments) throws IOException {
        super();
        this.setFileName("allNames.csv")
                .setReportObjects(instruments)
                .setFields(new String[]{"name"});
    }

}
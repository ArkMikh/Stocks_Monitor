package com.arkmikhjava.monitor.client.reports;

import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.IOException;
import java.util.List;

public class AllNameStockReportForm extends ReportForm {

    public AllNameStockReportForm(List<MarketInstrument> instruments) throws IOException {
        super();
        this.setFileName("allNames.csv")
                .setReportObjects(instruments)
                .setFields(new String[]{"name"});
    }

}
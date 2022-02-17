package com.arkmikhjava.monitor.client.reports;

import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.IOException;
import java.util.List;

public class AllFigiStockReportForm extends ReportForm {

    public AllFigiStockReportForm(List<MarketInstrument> instruments) throws IOException {
        super();
        this.setFileName("allFigis.csv")
                .setReportObjects(instruments)
                .setFields(new String[]{"figi"});
    }

}
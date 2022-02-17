package com.arkmikhjava.monitor.client.reports;

import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.IOException;
import java.util.List;

public class AllStocksReportForm extends ReportForm {

    public AllStocksReportForm(List<MarketInstrument> instruments) throws IOException {
        super();

        this.setFileName("allStocks.csv")
                .setReportObjects(instruments)
                .setReportName("Акции")
                .setFields(new String[]{"figi", "name", "ticker", "currency"})
                .setHeaders(new String[]{"figi", "наименование", "тикер",  "валюта"});


    }

}

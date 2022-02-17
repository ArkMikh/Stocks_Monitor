package com.arkmikhjava.monitor.client;

import com.arkmikhjava.monitor.client.config.ApiConnector;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/*
Класс получения информации
Получаем инструменты из MarketInstrumentList: акции, фьючерсы, валюты
 */
public class ContextProvider {

    private ApiConnector apiConnector;

    public ContextProvider(ApiConnector apiConnector) {
        this.apiConnector = apiConnector;
    }



    public MarketInstrumentList getStocks() throws Exception {
        return getOpenApi().getMarketContext().getMarketStocks().join();
    }

    public MarketInstrumentList getBonds() throws Exception {
        return getOpenApi().getMarketContext().getMarketBonds().join();
    }

    public MarketInstrumentList getEtfs() throws Exception {
        return getOpenApi().getMarketContext().getMarketEtfs().join();
    }

    public MarketInstrumentList getCurrencies() throws Exception {
        return getOpenApi().getMarketContext().getMarketCurrencies().join();
    }

    public MarketInstrumentList getPrice() throws Exception{
        return getOpenApi().getMarketContext().getMarketStocks().join();
    }

    public List<Candles> candleOptional(String s, int days) throws Exception{
        Optional<Candles> candles=
                getOpenApi().getMarketContext().getMarketCandles(s,
                OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now().minusDays(days)), ZoneOffset.UTC),
                OffsetDateTime.of(LocalDateTime.from(LocalDateTime.now()), ZoneOffset.UTC),
                CandleResolution.HOUR).join();
        Stream<Candles> stringStream = candles.stream();
        List<Candles> candlesList=stringStream.collect(Collectors.toList());;
        return candlesList;
    }

    private OpenApi getOpenApi () throws Exception {
        return apiConnector.getOpenApi();
    }

}
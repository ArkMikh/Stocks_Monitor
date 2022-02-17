package com.arkmikhjava.openapi.tinkoff.client;

import com.arkmikhjava.openapi.tinkoff.client.config.ApiConnector;
import com.arkmikhjava.openapi.tinkoff.client.config.Parameters;
import com.arkmikhjava.openapi.tinkoff.client.reports.*;

import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetReports {

    public static void main(String[] args) throws Exception {

        //Создаем подключение используя токен в режиме sandBox
        Scanner tokenScanner=new Scanner(new File("token.txt)"));
        String token=tokenScanner.nextLine();
        Parameters parameters = new Parameters(token, true);
        //Создаем апиконнектор который будет использоваться для подключения с заданными параметрами
        ApiConnector apiConnector = new ApiConnector(parameters);
        //Создаем класс с помощью которого будет получать информацию по акциям
        ContextProvider contextProvider = new ContextProvider(apiConnector);

        //Коллекции для списка акций, имен и figi
        List<CommonReport> reportStocks = new ArrayList<>();
        List<CommonReport> reportNames = new ArrayList<>();
        List<CommonReport> reportFigis = new ArrayList<>();

        //Получение отчетов по акциям, имени и figi, и их экспорт в файл
        reportStocks.add(new AllStocksReport(contextProvider.getStocks().getInstruments()));
        reportStocks.forEach(CommonReport<MarketInstrument>::doCommonExport);

        reportNames.add(new AllNameStockReport(contextProvider.getStocks().getInstruments()));
        reportNames.forEach(CommonReport<MarketInstrument>::doSingleExport);

        reportFigis.add(new AllFigiStockReport(contextProvider.getStocks().getInstruments()));
        reportFigis.forEach(CommonReport<MarketInstrument>::doSingleExport);

        //Формируем отчет по всем свечам
        AllCandlesReport candleReport=new AllCandlesReport();
        String allPrices="OLAP\\allPrices.csv";
        String allFigis="OLAP\\allFigis.csv";
        candleReport.createCandleReport(apiConnector, 4, allFigis, allPrices);

        //Формируем финальный отчет из кубов
        FinalReport finalReport=new FinalReport();
        String allStocks="OLAP\\allStocks.csv";
        String report="report.csv";
        finalReport.DoReportFromCubes(allPrices, allStocks, report);
    }
}
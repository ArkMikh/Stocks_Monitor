package com.arkmikhjava.monitor.client;

import com.arkmikhjava.monitor.client.config.Parameters;
import com.arkmikhjava.monitor.client.reports.*;
import com.arkmikhjava.monitor.client.config.ApiConnector;

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
        List<ReportForm> reportStocks = new ArrayList<>();
        List<ReportForm> reportNames = new ArrayList<>();
        List<ReportForm> reportFigis = new ArrayList<>();

        //Получение отчетов по акциям, имени и figi, и их экспорт в файл
        reportStocks.add(new AllStocksReportForm(contextProvider.getStocks().getInstruments()));
        reportStocks.forEach(ReportForm<MarketInstrument>::doCommonExport);

        reportNames.add(new AllNameStockReportForm(contextProvider.getStocks().getInstruments()));
        reportNames.forEach(ReportForm<MarketInstrument>::doSingleExport);

        reportFigis.add(new AllFigiStockReportForm(contextProvider.getStocks().getInstruments()));
        reportFigis.forEach(ReportForm<MarketInstrument>::doSingleExport);

        //Формируем отчет по всем свечам
        AllCandlesReport candleReport=new AllCandlesReport();
        String allPrices="OLAP\\allPrices.csv";
        String allFigis="OLAP\\allFigis.csv";
        candleReport.createCandleReport(apiConnector, 4, allFigis, allPrices);

        //Формируем финальный отчет из собранных данных
        FinalReport finalReport=new FinalReport();
        String allStocks="OLAP\\allStocks.csv";
        String report="report.csv";
        finalReport.DoReportFromCubes(allPrices, allStocks, report);
    }
}
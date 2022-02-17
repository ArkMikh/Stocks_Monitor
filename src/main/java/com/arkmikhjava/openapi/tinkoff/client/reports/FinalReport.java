package com.arkmikhjava.openapi.tinkoff.client.reports;

import com.arkmikhjava.openapi.tinkoff.client.tools.IoTools;
import com.arkmikhjava.openapi.tinkoff.client.tools.RegEXPatterns;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FinalReport {

    //Метод будет принимать 3 параметра: два источника и одну цель
    public void DoReportFromCubes(String sourcePrice, String sourceStocks, String target) throws IOException {

        //Создай файл target если его нет
        Files.deleteIfExists(Paths.get(target));
        IoTools.createFileIfNotExists(target);
        FileWriter writer=new FileWriter(new File(target));

        //Сканнеры для двух источников
        Scanner scannerStocks = new Scanner(new File(sourceStocks));
        Scanner scannerPrice = new Scanner(new File(sourcePrice));

        //Пропусти две строчки в sourceStocks
        scannerStocks.nextLine();
        scannerStocks.nextLine();
        writer.write(
                "Название компании;Тикер;Валюта котировки;Дата и время котировки;Цена последней сделки(текущая котировка)\n");

        //Пока мы не дошли до пустой линии в файле allStocks
        while (scannerStocks.hasNext()) {
            //Линия файла allStocks
            String stringStocks = scannerStocks.nextLine();
            //Линия файла allPrices
            String stringPrice = scannerPrice.nextLine();
            String stringTarget = "";
            //Пока не нашли одинаковые FIGI в двух файлах - пропускай линию в файле allPrices
            while (!(stringStocks.substring(0, 12).equals(stringPrice.substring(0, 12)))) {
                scannerPrice.nextLine();
            }
            //Запиши в stringTarget когда вышли из верхнего цикла
            stringTarget = stringStocks.substring(13) +";"+ stringPrice.substring(13);
            writer.write(stringTarget+"\n");
            writer.flush();
        }
        writer.close();
    }
}


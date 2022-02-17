package com.arkmikhjava.openapi.tinkoff.client.reports;

import com.arkmikhjava.openapi.tinkoff.client.ContextProvider;
import com.arkmikhjava.openapi.tinkoff.client.config.ApiConnector;
import com.arkmikhjava.openapi.tinkoff.client.config.Parameters;
import com.arkmikhjava.openapi.tinkoff.client.tools.IoTools;
import com.arkmikhjava.openapi.tinkoff.client.tools.RegEXPatterns;
import com.google.gson.Gson;
import ru.tinkoff.invest.openapi.model.rest.CandleResolution;
import ru.tinkoff.invest.openapi.model.rest.Candles;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Класс, формурующий свечи согласно figi в \\reports\\figi
public class AllCandlesReport {

    //Создаем подключение используя токен в режиме sandBox
    Parameters parameters = new Parameters("t.z5j1l-exsnK66435VFJdWDh3ssFe1-vQhVEiiURMzuLCwOQc7OmChK2hC4HF3p1FRCDBpTUoxW1sFoYP8h4NGA", true);
    //Создаем апиконнектор который будет использоваться для подключения с заданными параметрами
    ApiConnector apiConnector = new ApiConnector(parameters);
    //Создаем класс с помощью которого будет получать информацию по акциям
    ContextProvider contextProvider = new ContextProvider(apiConnector);
    //Созданием перечисления с regEX
    RegEXPatterns regEXPatterns;


    public void createCandleReport(ApiConnector apiConnector, int days, String source, String target) throws Exception {

        //Указываем путь откуда будет происходить чтение
        Scanner scanner = new Scanner(new File(source));

        //Указываем путь куда будет происходить запись
        Files.deleteIfExists(Paths.get(target));
        IoTools.createFileIfNotExists(target);
        FileWriter writer = new FileWriter(target);

        //Для перевода JSON в String
        Gson gson = new Gson();
        int i=1;

        //Пока в файле остались строчки
        while (scanner.hasNextLine()) {


            System.out.println("Идет получение данных: "+i+" из 1902");
            //Указываем таймаут, чтобы сервер не отключил соединение из-за количества запросов к нему
            Thread.sleep(75);
            String s = scanner.nextLine();
            //Запрос информации об акции по имени из файла
            List<Candles> candlesList=contextProvider.candleOptional(s, days);
            //Запиши каждую Свечу в файл
            for (Candles candle : candlesList) {
                String jsonString = gson.toJson(candle) + "\n";
                if(jsonString.length()>55) {
                    Matcher matcherClosedPrice = regEXPatterns.CLOSED_PRICE.matches(jsonString);
                    Matcher matcherDateYear = regEXPatterns.DATE_YEAR.matches(jsonString);
                    Matcher matcherDateMonth = regEXPatterns.DATE_MONTH.matches(jsonString);
                    Matcher matcherDateDay = regEXPatterns.DATE_DAY.matches(jsonString);
                    Matcher matcherTimeHour = regEXPatterns.TIME_HOUR.matches(jsonString);
                    Matcher matcherTimeMinute = regEXPatterns.TIME_MINUTE.matches(jsonString);

                    if (matcherClosedPrice.find() &&
                            matcherDateDay.find() && matcherDateMonth.find() &&
                            matcherDateYear.find() && matcherTimeHour.find() &&
                            matcherTimeMinute.find()) {
                        //Запиши в файл элементы
                        writer.write(
                                jsonString.substring(9, 21) + ";" +
                                        jsonString.substring(matcherDateDay.start(1), matcherDateDay.end(1)) + "." +
                                        jsonString.substring(matcherDateMonth.start(1), matcherDateMonth.end(1)) + "." +
                                        jsonString.substring(matcherDateYear.start(1), matcherDateYear.end(1)) + " " +
                                        jsonString.substring(matcherTimeHour.start(1), matcherTimeHour.end(1)) + ":" +
                                        jsonString.substring(matcherTimeMinute.start(1), matcherTimeMinute.end(1)) + "0;" +
                                        jsonString.substring(matcherClosedPrice.start(1), matcherClosedPrice.end(1)) + "\n"
                        );
                        writer.flush();
                    }
                }else{
                    writer.write(jsonString.substring(9,21)+";"+"No Data\n");
                }
            }
            i++;
        }
        writer.close();
        System.out.println("Формируем отчет из полученных данных...");
    }

}

## Stocks_Monitor
Мониторинг акций.  
Программа с помощью Tinkoff API получает список акций и информацию по ним.  
После чего формирует отчет в .csv формате в виде:
| Название компании | Тикер         | Валюта котировки  | Дата и время котировки  | Текущая котировка  | 
| ----------------- |:-------------:|:-----------------:|:-----------------------:| -----------------: |
| Ubiquiti Inc      | UI            |   USD             |   16.2.2022 19:00       |   13.8             |
| Cerner            | CERN          |   USD             |   16.2.2022 15:00       |   42.93            |
| Fix Price Group   | FIXP          |   RUB             |   14.2.2022 04:00       |   467.5            |
  
## Установка  
  Проект работает "из коробки", всё что нужно сделать — поместить token Tinkoff Invest API в файл token.txt
  Получить token Tinkoff Invest API можно по ссылке: https://www.tinkoff.ru/invest/settings/api/ (аутентификация обязательна)
## Принцип работы  
  1. Прописываем токен в параметре класса Parameters
  2. С помощью класса ApiConnector и передачи ему параметров из класса Parameters осуществляются все следующие подключения
  3. Все методы по вызову отчетов находятся в классе ContextProvider
  4. Используя форму отчетов - класс Report Form, формируются отчеты по:  
  --*Всем акциям
  --*Всем именам FIGI
  5. Используя класс AllCandlesReport и отчет FIGI формируем отчеты по ценам и дата\время котировки
  6. Сравнивания FIGI в отчете по всем акциям allStocks и allPrices формируем финальный отчет
  

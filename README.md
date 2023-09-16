# HardTime русификация

![image](https://github.com/softbie/hardtime-russification/assets/39442071/b0fd78b2-dede-430f-ab77-bdbb094be2fd)

Русификация игры [HardTime](http://www.mdickie.com/prev_hardtime.htm) от [MDickie](http://www.mdickie.com/)

Игра разработана на движке Blitz3D с языком программирования BlitzBasic

Перевод игры выполняется с помощью написанного мною плагина, представленный в виде функции `translate`

## Компиляция и окружение
Для того, что бы внести вклад в перевод или просто есть необходимость в компиляции игры нужно правильно настроить окружение, следуя этой инструкции:
1. Склонировать репозиторий
2. Поместить в папку с репозиторием ассеты игры (скачать игру на оф. сайте и переместить все папки - Characters, Data, и т.д)
3. Скачать и установить Blitz3D
4. (Опционально) Создать новую переменную среды `blitzpath` для компиляции из командной строки. `blitzpath` должен содержать путь до установленного Blitz3D (прим. `C:\Program Files\Blitz3D`). Так же, для удобства, вынести путь до директории с компилятором в переменную `PATH` (прим. `C:\Program Files\Blitz3D\bin`). Что бы проверить правильность настройки окружения для компиляции выполните команду `blitzcc -h`, должна будет отобразиться справка по компилятору
5. Стартовой точкой игры является файл `Gameplay.bb`. Пример компиляции в режиме отладки: `blitzcc -d Gameplay.bb`

## Общие сведения
Из за специфики языка BlitzBasic перевод выполнен в виде двумероного массива, содержащего строки с переводом вида:

```
array$(index, 0) = "Hello, #NAME#"
array$(index, 1) = "Привет, #NAME#"
```

Где `index` - номер переводимой строки, `0` - строка на английском, `1` - строка на русском

Сам перевод осуществляется непосредственно в исходных файлах, для перевода нужно подключить файл `Translate.bb` и использовать функцию `translate`.

```
;Пример работы

Include "translate_plugin/Translate.bb"

Print translate("Hello, #NAME#", "#NAME#", "Вася")

;result: Привет, Вася
```

Функция имеет три аргумента, первый - обязательный, содержит ключ для поиска строк, второй и третий - необязательны и используются для подстановки по подстроке.

## Специфика русификации

Если заглянуть в файлы, которые содержат строки с переводом, в самом GitHub можно увидеть, что кириллица отображается в неправильной кодировке, из за чего редактировать и вносить вклад в "онлайн" режиме становится невозможным. К сожалению, это особенность движка или самой игры. HardTime не умеет работать с UTF кодировкой, если использовать ее, то кракозябры будут уже в самой игре. Поэтому файлы с переводами сохранены в `Windows-1251`.

Ввиду того, что этот ЯП не поддерживает ассоциативные и динамические массивы необходимо заранее объявлять длину массива. Это может быть очень не удобным, потому, что строки с переводами разбиты по файлам, из за этого добавление или удаление строк приведет к полному переписыванию всех индексов в остальных файлах.

## Вспомогательный инструмент

Что бы избежать проблемы, описанной выше, я написал отдельную вспомогательную программу с использованием `flutter`, которая хранит все строки с переводом в `sqlite` базе данных и позволяет автоматически экспортировать строки в нужном формате.

![image](https://github.com/softbie/hardtime-russification/assets/39442071/582c6d17-59dc-4065-930f-05b4f3b64d63)

Чуть позже выложу эту программу и базу данных к ней, за актуальностью базы можно следить [здесь](https://github.com/softbie/hardtime-russification/releases)


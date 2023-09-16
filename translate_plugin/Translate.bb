;========================= Плагин перевода текста ==================================
;== Версия:             0.0.3                                                     ==
;== Дата обновления:    13.09.2023                                                ==
;== Автор:              13Hz                                                      ==
;== Почта:              async13hhz@gmail.com                                      ==
;===================================================================================
;
; Добавлена возможность подстановки значений по ключам, пример:
; Фраза I have 10 apples, если число 10 - динамическое значение, можно добавить в файлы с переводом так:
; messages$(X, X) = "I have #COUNT# apples"
; Теперь, что бы заменить ключ, нужно передать два параметра в функцию, что ищем и на что заменяем:
; translate("I have #COUNT# apples", "#COUNT#", str 10)
;
; Структура массива с текстом:
;
; messages$(X, 0) = ТЕКСТ ДЛЯ ПЕРЕВОДА
; messages$(X, 1) = ПЕРЕВОД

Include "translate_plugin/Main.bb"

; Функция для перевода строк
; title - строка, по которой производится поиск (в данном случае - оригинальная строка)
; needle - искомое выражение для замены
; replacement - заменяемая строка
; Возвращает переведенную или оригинальную строку
Function translate$(title$, needle$ = "", replacement$ = "")
	For i = 0 To messagesCount
		If title$ = messages$(i, 0) Then
		    If Len(needle$) > 0 and Len(replacement$) > 0 Then
		        Return Replace$(messages$(i, 1), needle$, replacement$)
            Else
                Return messages$(i, 1)
            EndIf
        EndIf
	Next
	Return title$
End Function
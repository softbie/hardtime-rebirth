;========================= Плагин перевода текста ==================================
;== Версия:             0.0.2                                                     ==
;== Дата обновления:    04.08.2022                                                ==
;== Автор:              13Hz                                                      ==
;== Почта:              async13hhz@gmail.com                                      ==
;===================================================================================
;
; Для корректного использования плагина, заполнить файл Messages.bb
; Структура массива с текстом:
;
; messages$(X, 0) = ТЕКСТ ДЛЯ ПЕРЕВОДА
; messages$(X, 1) = ПЕРЕВОД

Global messagesCount = 805

Include "translate_plugin/lang/Messages.bb"

Function translate$(title$)
	For i = 0 To messagesCount
		If title$ = messages$(i, 0) Then Return messages$(i, 1)
	Next
	Return title$
End Function
;========================= Плагин перевода текста ==================================
;== Автор:              13Hz                                                      ==
;== Почта:              async13hhz@gmail.com                                      ==
;===================================================================================

Include "translate_plugin/Main.bb"

; Функция замены подстрок, всего 2 ключа для замены в тексте #FIRST# и #SECOND#
; string - исходная строка
; first - строка для замены ключа #FIRST#
; second - строка для замены ключа #SECOND#
; Возвращает строку с произведенными заменами, можно указать как два аргумента так и только один
Function replacement$(string$, firstReplace$ = "", secondReplace$ = "", thirdReplace$ = "")
    If Len(firstReplace$) > 0 Then
        string$ = Replace$(string$, "#FIRST#", firstReplace$)
    EndIf
    If Len(secondReplace$) > 0 Then
        string$ = Replace$(string$, "#SECOND#", secondReplace$)
    EndIf
    If Len(thirdReplace$) > 0 Then
        string$ = Replace$(string$, "#THIRD#", thirdReplace$)
    EndIf
    Return string$
End Function

; Функция для перевода строк
; title - строка, по которой производится поиск (в данном случае - оригинальная строка)
; first - строка для подстановки в ключ #FIRST#
; second - строка для подстановки в ключ #SECOND#
; Возвращает переведенную или оригинальную строку
Function translate$(title$, firstReplace$ = "", secondReplace$ = "", thirdReplace$ = "")
	For i = 0 To messagesCount
		If title$ = messages$(i, 0) Then Return replacement$(messages$(i, optLanguage), firstReplace$, secondReplace$, thirdReplace$)
	Next
    Return replacement$(title$, firstReplace$, secondReplace$, thirdReplace$)
End Function
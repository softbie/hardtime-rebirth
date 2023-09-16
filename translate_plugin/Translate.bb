;========================= Плагин перевода текста ==================================
;== Автор:              13Hz                                                      ==
;== Почта:              async13hhz@gmail.com                                      ==
;===================================================================================

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
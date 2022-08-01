Include "translate_plugin/lang/en/Text.bb"
Include "translate_plugin/lang/ru/Text.bb"

Function translate$(title$)
	For i = 0 To 188
		If title$ = messagesEn$(i) Then Return messagesRu$(i)
	Next
	Return title$
End Function
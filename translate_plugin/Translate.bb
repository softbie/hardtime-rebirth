Global messagesCount = 560

Include "translate_plugin/lang/Messages.bb"

Function translate$(title$)
	For i = 0 To messagesCount
		If title$ = messages$(i, 0) Then Return messages$(i, 1)
	Next
	Return title$
End Function
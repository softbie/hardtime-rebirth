;//////////////////////////////////////////////////////////////////////////////
;-------------------------- HARD TIME: MENU SCREENS ---------------------------
;//////////////////////////////////////////////////////////////////////////////

;//////////////////////////////////////////////////////////////////////////////
;------------------------------ 1. MAIN MENU ----------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function MainMenu()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
foc=1 : oldfoc=foc
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames
	
	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0
	
	;PORTAL
    gotim=gotim+1
	If gotim>20 And keytim=0
	 ;leave
	 If KeyDown(1) Then go=-1
	 ;proceed
	 If KeyDown(28) Or ButtonPressed()
	  If foc=4 Then go=-1 Else go=1
	 EndIf
	EndIf
	
	;CONFIGURATION 
	If gotim>20 And keytim=0
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=4
	 If foc>4 Then foc=1
	EndIf     
	
 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawMainLogo(rX#(400),rY#(300))
 DrawImage gMDickie,rX#(400),rY#(515)
 ;options
 DrawOption(1,rX#(400),rY#(75),translate("PLAY"),"")
 DrawOption(2,rX#(400),rY#(130),translate("OPTIONS"),"")
 DrawOption(3,rX#(400),rY#(185),translate("CREDITS"),"")
 DrawOption(4,rX#(400),rY#(415),translate("<<< EXIT <<<"),"")

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
If go=1
 If foc=1 Then screen=5
 If foc=2 Then screen=2
 If foc=3 Then gamEnded=0 : screen=6
EndIf
If go=-1 Then screen=7
End Function

;//////////////////////////////////////////////////////////////////////////////
;------------------------------- 2. OPTIONS -----------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function Options()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
foc=9 : oldfoc=foc
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames

	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0

	;PORTAL
    gotim=gotim+1
	If gotim>20 And keytim=0
	 ;leave
	 If KeyDown(1) Then go=-1
	 ;proceed
	 If KeyDown(28) Or ButtonPressed()
	  If foc<6 Then foc=9 : keytim=10
	  If foc=>7 And foc=<8 Then go=1
	  If foc=9 And keytim=0 Then go=-1
	 EndIf
	EndIf

	;CONFIGURATION
	If gotim>20 And keytim=0
	 ;highlight options
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=9
	 If foc>9 Then foc=1
	 ;browse left
	 If KeyDown(203) Or JoyXDir()=-1
	  If foc=1 Then optRes=optRes-1 : PlaySound sMenuBrowse : keytim=6
	  If foc=2 Then optPopulation=optPopulation-5 : PlaySound sMenuBrowse : keytim=6
	  If foc=3 Then optFog=optFog-1 : PlaySound sMenuBrowse : keytim=6
	  If foc=4 Then optShadows=optShadows-1 : PlaySound sMenuBrowse : keytim=6
	  If foc=5 Then optFX=optFX-1 : PlaySound sMenuBrowse : keytim=6
	  If foc=6 Then optGore=optGore-1 : PlaySound sMenuBrowse : keytim=6
	 EndIf
	 ;browse right
	 If KeyDown(205) Or JoyXDir()=1
	  If foc=1 Then optRes=optRes+1 : PlaySound sMenuBrowse : keytim=6
	  If foc=2 Then optPopulation=optPopulation+5 : PlaySound sMenuBrowse : keytim=6
	  If foc=3 Then optFog=optFog+1 : PlaySound sMenuBrowse : keytim=6
	  If foc=4 Then optShadows=optShadows+1 : PlaySound sMenuBrowse : keytim=6
	  If foc=5 Then optFX=optFX+1 : PlaySound sMenuBrowse : keytim=6
	  If foc=6 Then optGore=optGore+1 : PlaySound sMenuBrowse : keytim=6
	 EndIf
	EndIf
	;check limits
	If optRes<1 Then optRes=1
	If optRes>5 Then optRes=5
    If optPopulation<40 Then optPopulation=40
	If optPopulation>100 Then optPopulation=100
    If optFog<0 Then optFog=1
	If optFog>1 Then optFog=0
	If optShadows<0 Then optShadows=0
	If optShadows>2 Then optShadows=2
	If optFX<0 Then optFX=0
	If optFX>2 Then optFX=2
	If optGore<0 Then optGore=0
	If optGore>3 Then optGore=3

 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawImage gLogo(3),rX#(400),rY#(50)
 ;options
 SetFont font(1)
 x=400 : y=120 : spacer=53
 DrawOption(1,rX#(400),rY#(y),translate("Resolution"),textResX$(optRes)+" x "+textResY$(optRes)) : y=y+spacer
 DrawOption(2,rX#(400),rY#(y),translate("Population"),translate("#FIRST# characters", str optPopulation)) : y=y+(spacer+5)
 DrawOption(3,rX#(400),rY#(y),translate("Fog Effect"),textOnOff$(optFog)) : y=y+spacer
 DrawOption(4,rX#(400),rY#(y),translate("Shadows"),textShadows$(optShadows)) : y=y+spacer
 DrawOption(5,rX#(400),rY#(y),translate("Particle FX"),textFX$(optFX)) : y=y+spacer
 DrawOption(6,rX#(400),rY#(y),translate("Gore"),textGore$(optGore)) : y=y+(spacer+5)
 DrawOption(7,rX#(400),rY#(y),translate("REDEFINE KEYS"),"") : y=y+spacer
 DrawOption(8,rX#(400),rY#(y),translate("REDEFINE GAMEPAD"),"") : y=y+(spacer+5)
 DrawOption(9,rX#(400),rY#(y),translate("<<< BACK <<<"),"")

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
If go=1
 If foc=7 Then screen=3
 If foc=8 Then screen=4
 If foc=9 Then screen=1
EndIf
If go=-1 Then screen=1
End Function

;//////////////////////////////////////////////////////////////////////////////
;---------------------------- 3. REDEFINE KEYS --------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function RedefineKeys()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
foc=6 : oldfoc=foc : screenCall=0
go=0 : gotim=0 : keytim=20
While go=0

 If screenCall=0 Then Cls

 frames=WaitTimer(timer)
 For framer=1 To frames

	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0

	;PORTAL
    gotim=gotim+1
	If gotim>20 And keytim=0 And screenCall=0
	 ;leave
	 If KeyDown(1) Then go=-1
	 ;proceed
	 If KeyDown(28) Or ButtonPressed() Or MouseDown(1)
	  If foc=6 Then go=-1
	 EndIf
	EndIf

	;CONFIGURATION
	If gotim>20 And keytim=0 And screenCall=0
	 ;highlight options
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=6
	 If foc>6 Then foc=1
	 ;activate
	 If KeyDown(28) Or ButtonPressed()
	  ;enter new command
	  If foc=<4
	   PlaySound sMenuBrowse : keytim=20
	   callX=MouseX() : callY=MouseY()
	   screenCall=foc
	  EndIf
	  ;restore defaults
	  If foc=5
	   PlaySound sTrash : keytim=20
	   keyAttack=30 : keyThrow=31
	   keyDefend=44 : keyPickUp=45
	  EndIf
	 EndIf
	EndIf

	;INPUT DELAY
    If screenCall>0 And keytim=0
     If screenCall=1 Then keyAttack=AssignKey(keyAttack)
     If screenCall=2 Then keyDefend=AssignKey(keyDefend)
     If screenCall=3 Then keyThrow=AssignKey(keyThrow)
     If screenCall=4 Then keyPickUp=AssignKey(keyPickUp)
    EndIf

 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawImage gLogo(3),rX#(400),rY#(50)
 ;lock mouse
 If screenCall>0 Then MoveMouse callX,callY
 ;options
 SetFont font(1)
 y=120
 DrawOption(1,rX#(400),rY#(y),translate("Attack / Shoot"),Key$(keyAttack))
 DrawOption(2,rX#(400),rY#(y+60),translate("Defend / Run"),Key$(keyDefend))
 DrawOption(3,rX#(400),rY#(y+120),translate("Throw / Grab"),Key$(keyThrow))
 DrawOption(4,rX#(400),rY#(y+180),translate("Pick Up / Drop"),Key$(keyPickUp))
 DrawOption(5,rX#(400),rY#(y+250),translate("RESTORE DEFAULTS"),"")
 DrawOption(6,rX#(400),rY#(y+320),translate("<<< BACK <<<"),"")
 ;new overlay
 If screenCall=1 Then DrawOption(1,rX#(400),rY#(y),translate("Attack / Shoot"),translate("Press New Key"))
 If screenCall=2 Then DrawOption(2,rX#(400),rY#(y+60),translate("Defend / Run"),translate("Press New Key"))
 If screenCall=3 Then DrawOption(3,rX#(400),rY#(y+120),translate("Throw / Grab"),translate("Press New Key"))
 If screenCall=4 Then DrawOption(4,rX#(400),rY#(y+180),translate("Pick Up / Drop"),translate("Press New Key"))

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
screen=2
End Function

;//////////////////////////////////////////////////////////////////////////////
;--------------------------- 4. REDEFINE GAMEPAD ------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function RedefineGamepad()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
foc=6 : oldfoc=foc : screenCall=0
go=0 : gotim=0 : keytim=20
While go=0

 If screenCall=0 Then Cls

 frames=WaitTimer(timer)
 For framer=1 To frames

	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0

	;PORTAL
    gotim=gotim+1
	If gotim>20 And keytim=0 And screenCall=0
	 ;leave
	 If KeyDown(1) Then go=-1
	 ;proceed
	 If KeyDown(28) Or ButtonPressed() Or MouseDown(1)
	  If foc=6 Then go=-1
	 EndIf
	EndIf

	;CONFIGURATION
	If gotim>20 And keytim=0 And screenCall=0
	 ;highlight options
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=6
	 If foc>6 Then foc=1
	 ;activate
	 If KeyDown(28) Or ButtonPressed()
	  ;enter new command
	  If foc=<4
	   PlaySound sMenuBrowse : keytim=20
	   callX=MouseX() : callY=MouseY()
	   screenCall=foc
	  EndIf
	  ;restore defaults
	  If foc=5
	   PlaySound sTrash : keytim=20
	   buttAttack=1 : buttThrow=2
	   buttDefend=3 : buttPickUp=4
	  EndIf
	 EndIf
	EndIf

	;INPUT DELAY
    If screenCall>0 And keytim=0
     If screenCall=1 Then buttAttack=AssignButton(buttAttack)
     If screenCall=2 Then buttDefend=AssignButton(buttDefend)
     If screenCall=3 Then buttThrow=AssignButton(buttThrow)
     If screenCall=4 Then buttPickUp=AssignButton(buttPickUp)
    EndIf

 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawImage gLogo(3),rX#(400),rY#(50)
 ;lock mouse
 If screenCall>0 Then MoveMouse callX,callY
 ;options
 SetFont font(1)
 y=120
 DrawOption(1,rX#(400),rY#(y),translate("Attack / Shoot"),buttAttack)
 DrawOption(2,rX#(400),rY#(y+60),translate("Defend / Run"),buttDefend)
 DrawOption(3,rX#(400),rY#(y+120),translate("Throw / Grab"),buttThrow)
 DrawOption(4,rX#(400),rY#(y+180),translate("Pick Up / Drop"),buttPickUp)
 DrawOption(5,rX#(400),rY#(y+250),translate("RESTORE DEFAULTS"),"")
 DrawOption(6,rX#(400),rY#(y+320),translate("<<< BACK <<<"),"")
 ;new overlay
 If screenCall=1 Then DrawOption(1,rX#(400),rY#(y),translate("Attack / Shoot"),translate("Press New Button"))
 If screenCall=2 Then DrawOption(2,rX#(400),rY#(y+60),translate("Defend / Run"),translate("Press New Button"))
 If screenCall=3 Then DrawOption(3,rX#(400),rY#(y+120),translate("Throw / Grab"),translate("Press New Button"))
 If screenCall=4 Then DrawOption(4,rX#(400),rY#(y+180),translate("Pick Up / Drop"),translate("Press New Button"))

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
screen=2
End Function

;//////////////////////////////////////////////////////////////////////////////
;----------------------------- 5. SLOT SELECT --------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function SlotSelect()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
If slot=>1 And slot=<3 Then foc=slot Else foc=1
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames

	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0

	;PORTAL
    gotim=gotim+1
	If gotim>20 And keytim=0
	 ;leave
	 If KeyDown(1) Then go=-1
	 ;proceed
	 If KeyDown(28) Or ButtonPressed()
	  If foc=4 Then go=-1 Else go=1
	 EndIf
	EndIf

	;CONFIGURATION
	If gotim>20 And keytim=0
	 ;highlight slot
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=4
	 If foc>4 Then foc=1
	 ;reset
	 If foc=>1 And foc=<3
	  If KeyDown(14) Or KeyDown(210) Then gamName$(foc)="" : PlaySound sTrash : keytim=10
	  If KeyDown(18) And gamName$(foc)<>"" Then PlaySound sComputer : go=2
	  If KeyDown(207) And gamName$(foc)<>"" Then PlaySound sDoor(1) : go=3
	 EndIf
	EndIf

 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawMainLogo(rX#(400),rY#(300))
 DrawImage gMDickie,rX#(400),rY#(515)
 ;game preview
 If foc=>1 And foc=<3
  If gamName$(foc)<>"" And gamPhoto(foc)>0
   DrawImage gamPhoto(foc),rX#(400)-160,rY#(20+(55*foc))
   Color 0,0,0 : Rect rX#(400)-235,rY#(20+(55*foc))-50,150,100,0
  EndIf
 EndIf
 ;options
 y=75
 For count=1 To 3
  If gamName$(count)="" Then DrawOption(count,rX#(400),rY#(y),translate("NEW GAME"),"")
  If gamName$(count)<>"" Then DrawOption(count,rX#(400),rY#(y),gamName$(count),"")
  y=y+55
 Next
 DrawOption(4,rX#(400),rY#(415),translate("<<< BACK <<<"),"")

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
If go=1
 slot=foc : oldLocation=0
 If gamName$(foc)=""
  Loader(translate("Please Wait"),translate("Generating Game"))
  GenerateGame()
  gamChar(0)=gamChar(slot)
  gamPointLimit=170
  screen=51
 Else
  Loader(translate("Please Wait"),translate("Restoring Game"))
  LoadProgress()
  LoadChars()
  LoadPhotos()
  LoadItems()
  camX#=0 : camY#=30 : camZ#=0
  If charY#(gamChar(slot))=>100 Then camY#=130
  If gamLocation(slot)=2 Then camX#=350 : camZ#=350
  screen=50
 EndIf
EndIf
If go=>2
 slot=foc
 Loader(translate("Please Wait"),translate("Restoring Game"))
 LoadProgress()
 LoadChars()
 LoadPhotos()
 LoadItems()
 If go=2 Then screen=8
 If go=3 Then gamEnded=0 : screen=53
EndIf
If go=-1 Then screen=1
End Function

;//////////////////////////////////////////////////////////////////////////////
;------------------------------ 8. EDIT SELECT --------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function EditSelect()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
foc=1 : oldfoc=foc
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames

	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0

	;PORTAL
    gotim=gotim+1
	If gotim>20 And keytim=0
	 ;leave
	 If KeyDown(1) Then go=-1
	 ;proceed
	 If KeyDown(28) Or ButtonPressed()
	  If foc=2 Then go=-1 Else go=1
	 EndIf
	EndIf

	;CONFIGURATION
	If gotim>20 And keytim=0
	 ;highlight option
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=2
	 If foc>2 Then foc=1
	 ;browse characters
	 If foc=1
	  If KeyDown(203) Or JoyXDir()=-1 Then gamChar(0)=gamChar(0)-1 : PlaySound sMenuBrowse : keytim=5
	  If KeyDown(205) Or JoyXDir()=1 Then gamChar(0)=gamChar(0)+1 : PlaySound sMenuBrowse : keytim=5
	 EndIf
	EndIf
	;limits
	If gamChar(0)<1 Then gamChar(0)=no_chars
	If gamChar(0)>no_chars Then gamChar(0)=1

 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawMainLogo(rX#(400),rY#(300))
 DrawImage gMDickie,rX#(400),rY#(515)
 ;options
 If charSnapped(gamChar(0))>0 And charPhoto(gamChar(0))>0
  DrawImage charPhoto(gamChar(0)),rX#(400),rY#(185)-80
 Else
  DrawImage gPhoto,rX#(400),rY#(185)-80
 EndIf
 DrawOption(1,rX#(400),rY#(185),translate("Character"),gamChar(0)+". "+charName$(gamChar(0)))
 DrawOption(2,rX#(400),rY#(415),translate("<<< BACK <<<"),"")

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
If go=1 Then gamPointLimit=999 : screen=51
If go=-1 Then screen=5
End Function

;////////////////////////////////////////////////////////////////
;---------------------- RELATED FUNCTIONS -----------------------
;////////////////////////////////////////////////////////////////

;DRAW MAIN LOGO
Function DrawMainLogo(x#,y#)
 ;logo halves
 DrawImage gLogo(1),x#,y#
 DrawImage gLogo(2),x#,y#
 ;version ID
 SetFont font(1)
 Outline(translate("Version #FIRST#", "1." + version),x#+310,y#+60,200,200,200,200,200,200)
 Outline(translate("Translate #FIRST#", "1.0.2"),x#+310,y#+80,200,200,200,200,200,200)
End Function

;DRAW MENU ITEM
Function DrawOption(box,x#,y#,scriptA$,scriptB$)
 ;assess highlight
 highlight=0
 If foc=box Or box=-1 Then highlight=1
 ;draw solo box
 If scriptB$=""
  If highlight=1 Then DrawImage gMenu(1),x#,y#
  If highlight=0 Then DrawImage gMenu(2),x#,y#
  SqueezeFont(scriptA$,185,3)
  r=135 : g=135 : b=135
  If highlight=1 Then r=255 : g=255 : b=255
  Outline(scriptA$,x#,y#,0,0,0,r,g,b)
 EndIf
 ;draw twin box
 If scriptB$<>""
  If highlight=1 Then DrawImage gMenu(3),x#,y#
  If highlight=0 Then DrawImage gMenu(4),x#,y#
  SqueezeFont(scriptA$,185,3)
  r=135 : g=135 : b=135
  If highlight=1 Then r=255 : g=255 : b=255
  Outline(scriptA$,x#-100,y#,0,0,0,r,g,b)
  SqueezeFont(scriptB$,185,3)
  Outline(scriptB$,x#+100,y#,0,0,0,r,g,b)
 EndIf
End Function

;SQUEEZE FONT INTO BOX
Function SqueezeFont(script$,width,start)
 f=start
 SetFont font(f)
 While f>0 And StringWidth(script$)>width
  f=f-1
  SetFont font(f)
 Wend
End Function

;ASSIGN KEY PROCESS
Function AssignKey(current)
 value=0
 While value=0
  For v=0 To 255
   If KeyDown(v)=1 And keytim=0
    If v<>0 And v<>1 And v<>28 And v<>25 Then value=v : screenCall=0 : PlaySound sMenuSelect : gotim=0 : keytim=20
   EndIf
  Next
  If KeyDown(1) And keytim=0 Then value=current : screenCall=0 : PlaySound sMenuBack : go=0 : gotim=0 : keytim=30
 Wend
 Return value
End Function

;ASSIGN BUTTON PROCESS
Function AssignButton(current)
 value=28
 While value=28
  For v=1 To 12
   If JoyDown(v)=1 And keytim=0 Then value=v : screenCall=0 : PlaySound sMenuSelect : gotim=0 : keytim=20
  Next
  If KeyDown(1) And keytim=0 Then value=current : screenCall=0 : PlaySound sMenuBack : go=0 : gotim=0 : keytim=30
 Wend
 Return value
End Function

;STANDARD LOADING DISPLAY
Function Loader(scriptA$,scriptB$)
 Cls
  ;logos
  TileImage gTile
  DrawMainLogo(rX#(400),rY#(300))
  DrawImage gMDickie,rX#(400),rY#(515)
  ;message
  DrawOption(-1,rX#(400),rY#(415),scriptA$,scriptB$)
  ;map reference
  If screen=50 Or screen=52 Then DisplayMap(400,125)
 Flip
End Function

;QUICK LOADING DISPLAY
Function QuickLoader(x#,y#,scriptA$,scriptB$)
 SetFont font(1)
 DrawOption(-1,x#,y#,scriptA$,scriptB$)
 Flip
End Function

;DISPLAY MAP
Function DisplayMap(x,y)
 DrawImage gMap,rX#(x),rY#(y)
 If screen=50
  If gamLocation(slot)=1 Then DrawImage gMarker,rX#(x)-21,rY#(y)-46
  If gamLocation(slot)=2 Then DrawImage gMarker,rX#(x)+22,rY#(y)-72
  If gamLocation(slot)=3 Then DrawImage gMarker,rX#(x)+47,rY#(y)-15
  If gamLocation(slot)=4
   If oldLocation=10 Or (oldLocation=4 And charLocation(gamChar(slot))=10)
    DrawImage gMarker,rX#(x)+55,rY#(y)+30
   Else
    DrawImage gMarker,rX#(x)+47,rY#(y)+26
   EndIf
  EndIf
  If gamLocation(slot)=5 Then DrawImage gMarker,rX#(x)+20,rY#(y)+53
  If gamLocation(slot)=6
   If oldLocation=11 Or (oldLocation=6 And charLocation(gamChar(slot))=11)
    DrawImage gMarker,rX#(x)-21,rY#(y)+67
   Else
    DrawImage gMarker,rX#(x)-22,rY#(y)+54
   EndIf
  EndIf
  If gamLocation(slot)=7 Then DrawImage gMarker,rX#(x)-50,rY#(y)+23
  If gamLocation(slot)=8 Then DrawImage gMarker,rX#(x)-48,rY#(y)-21
  If gamLocation(slot)=9
   If oldLocation=0 Then DrawImage gMarker,rX#(x),rY#(y)+5
   If oldLocation=1 Or (oldLocation=9 And charLocation(gamChar(slot))=1) Then DrawImage gMarker,rX#(x)-21,rY#(y)-31
   If oldLocation=2 Or (oldLocation=9 And charLocation(gamChar(slot))=2) Then DrawImage gMarker,rX#(x)+21,rY#(y)-31
   If oldLocation=3 Or (oldLocation=9 And charLocation(gamChar(slot))=3) Then DrawImage gMarker,rX#(x)+37,rY#(y)-16
   If oldLocation=4 Or (oldLocation=9 And charLocation(gamChar(slot))=4) Then DrawImage gMarker,rX#(x)+37,rY#(y)+25
   If oldLocation=5 Or (oldLocation=9 And charLocation(gamChar(slot))=5) Then DrawImage gMarker,rX#(x)+20,rY#(y)+40
   If oldLocation=6 Or (oldLocation=9 And charLocation(gamChar(slot))=6) Then DrawImage gMarker,rX#(x)-22,rY#(y)+40
   If oldLocation=7 Or (oldLocation=9 And charLocation(gamChar(slot))=7) Then DrawImage gMarker,rX#(x)-39,rY#(y)+24
   If oldLocation=8 Or (oldLocation=9 And charLocation(gamChar(slot))=8) Then DrawImage gMarker,rX#(x)-40,rY#(y)-21
  EndIf
  If gamLocation(slot)=10 Then DrawImage gMarker,rX#(x)+55,rY#(y)+44
  If gamLocation(slot)=11 Then DrawImage gMarker,rX#(x)-21,rY#(y)+78
 EndIf
End Function

;CHANGE RESOLUTION
Function ChangeResolution(resolution,task) ;0=pre-game, 1=during game
 ;assess preferences
 width=Int(textResX$(resolution))
 height=Int(textResY$(resolution))
 If GfxMode3DExists(width,height,16)=0 Then width=800 : height=600 : optRes=2
 ;make transition?
 If width<>GraphicsWidth() Or height<>GraphicsHeight()
  If task>0 Then Loader(translate("Please Wait"),translate("Adjusting Resolution"))
  Graphics3D width,height,16,2
  If task>0 ;restore media
   LoadImages()
   Loader(translate("Please Wait"),translate("Restoring Media"))
   LoadPhotos()
   LoadTextures()
   LoadWeaponData()
  EndIf
 EndIf
End Function

;GET SCREENSHOT
Function Screenshot()   
 ;obtain image
 PlaySound sCamera
 screenshot=CreateImage(GraphicsWidth(),GraphicsHeight())
 GrabImage screenshot,GraphicsWidth()/2,GraphicsHeight()/2
 ;title & save
 temp=MilliSecs()/10
 namer$="Screenshot - "+temp+".bmp"
 SaveImage(screenshot,"Photo Album/" + namer$)
End Function
;//////////////////////////////////////////////////////////////////////////////
;----------------------------- HARD TIME: EDITOR ------------------------------
;//////////////////////////////////////////////////////////////////////////////

;-------------------------------------------------------------------
;///////////////////// 51. EDIT CHARACTER //////////////////////////
;-------------------------------------------------------------------
Function Editor()
;loading
Loader(translate("Please Wait"),"Loading Editor")
;prison setting
world=LoadAnimMesh("World/Block/Block.3ds")
;camera
cam=CreateCamera()
CameraViewport cam,0,0,GraphicsWidth(),GraphicsHeight()
PositionEntity cam,-232,36,-89
If GraphicsWidth()>1024 Then PositionEntity cam,-228,36,-84
RotateEntity cam,3,119,0
;atmosphere
AmbientLight 200,190,170
light(1)=CreateLight(3)
PositionEntity light(1),-145,100,-70
RotateEntity light(1),90,0,0
LightRange light(1),500
LightConeAngles light(1),0,135
LightColor light(1),200,180,160
;load model
cyc=1 : char=gamChar(0)
pChar(cyc)=char
pX#(cyc)=-257 : pY#(cyc)=11.5 : pZ#(cyc)=-124 
ReloadModel(cyc)
;shadows
For limb=1 To 40
 pShadow(cyc,limb)=0
 If limb=30 Or (optShadows=2 And (limb=1 Or (limb=>4 And limb=<6) Or (limb=>17 And limb=<19) Or limb=32 Or limb=33 Or limb=35 Or limb=36))
  pShadow(cyc,limb)=LoadSprite("World/Sprites/Shadow.png",2)
  ScaleSprite pShadow(cyc,limb),13,13
  If limb<>30 Then ScaleSprite pShadow(cyc,limb),10,10
  If limb=6 Or limb=19 Or limb=33 Or limb=36 Then ScaleSprite pShadow(cyc,limb),8,8
  RotateEntity pShadow(cyc,limb),90,0,0
  SpriteViewMode pShadow(cyc,limb),2
  EntityAlpha pShadow(cyc,limb),0.1
  If optShadows=2 And (limb=30 Or limb=6 Or limb=19) Then EntityAlpha pShadow(cyc,limb),0.2
  If optShadows=1 Then EntityAlpha pShadow(cyc,limb),0.5
  If optShadows=0 Then EntityAlpha pShadow(cyc,limb),0  
  EntityColor pShadow(cyc,limb),10,10,10
 EndIf
Next 
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
foc=8 : oldfoc=foc : page=1
go=0 : gotim=0 : keytim=20
While go=0
	
 Cls
 screenCall=0
 frames=WaitTimer(timer)
 For framer=1 To frames
	
	;counters
	keytim=keytim-1
	If keytim<1 Then keytim=0
	
	;PORTAL 
	gotim=gotim+1
	If gotim>40 And keytim=0
	 ;quit
	 If KeyDown(1) Then go=-1
	 ;activations
	 If KeyDown(28) Or ButtonPressed()
	  ;next page
	  If foc=7 And keytim=0
	   PlaySound sMenuGo : keytim=10
	   page=page+1 : foc=7 : gotim=0
	   If page>2 Then page=1
	  EndIf
	  ;leave
	  If foc=8 And keytim=0 Then go=1
	 EndIf
	EndIf
	
	;CONFIGURATION
	If gotim>20 And keytim=0
	 ;browse up
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 ;browse down
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
    EndIf
    ;limits
    If foc<1 Then foc=1
	If foc>8 Then foc=8
	
	;ASSESS POINTS
	gamPoints=gamPointLimit
	gamPoints=gamPoints-charStrength(char)
	gamPoints=gamPoints-charAgility(char)
	gamPoints=gamPoints-charIntelligence(char)      
	If gamPoints<0 Then gamPoints=0 
	
	;1. PROFILE
	If page=1 And foc=>2 And foc=<6 And keytim=0
	 ;search left
	 If KeyDown(203) Or JoyXDir()=-1
	  If foc=2 Then charHeight(char)=charHeight(char)-1 : PlaySound sMenuBrowse : keytim=4
      If foc=3 Then charStrength(char)=charStrength(char)-1 : PlaySound sMenuBrowse : keytim=3
      If foc=4 Then charAgility(char)=charAgility(char)-1 : PlaySound sMenuBrowse : keytim=3
      If foc=5 Then charIntelligence(char)=charIntelligence(char)-1 : PlaySound sMenuBrowse : keytim=3
      If foc=6 Then charCrime(char)=charCrime(char)-1 : PlaySound sMenuBrowse : keytim=5
	 EndIf
	 ;search right
	 If KeyDown(205) Or JoyXDir()=1
	  If foc=2 Then charHeight(char)=charHeight(char)+1 : PlaySound sMenuBrowse : keytim=4
      If foc=3 And gamPoints>0 Then charStrength(char)=charStrength(char)+1 : PlaySound sMenuBrowse : keytim=3
      If foc=4 And gamPoints>0 Then charAgility(char)=charAgility(char)+1 : PlaySound sMenuBrowse : keytim=3
      If foc=5 And gamPoints>0 Then charIntelligence(char)=charIntelligence(char)+1 : PlaySound sMenuBrowse : keytim=3
      If foc=6 Then charCrime(char)=charCrime(char)+1 : PlaySound sMenuBrowse : keytim=5
	 EndIf 
	EndIf
	;limits
	If charHeight(char)<5 Then charHeight(char)=5
    If charHeight(char)>24 Then charHeight(char)=24
	If charStrength(char)<30 Then charStrength(char)=30
    If charStrength(char)>99 Then charStrength(char)=99
    If charAgility(char)<30 Then charAgility(char)=30
    If charAgility(char)>99 Then charAgility(char)=99
    If charIntelligence(char)<30 Then charIntelligence(char)=30
    If charIntelligence(char)>99 Then charIntelligence(char)=99
    If charCrime(char)<1 Then charCrime(char)=1
    If charCrime(char)>15 Then charCrime(char)=15
    If gamPointLimit<999 
     charReputation(char)=50+(charCrime(char)*2)
     charSentence(char)=30+(charCrime(char)*2)
    EndIf
	
	;2. APPEARANCE
	oldHairStyle=charHairStyle(char)
    oldHair=charHair(char)
    oldFace=charFace(char)
    oldSpecs=charSpecs(char)
	oldModel=charModel(char)
	oldCostume=charCostume(char)
	If page=2 And foc=>1 And foc=<6 And keytim=0
	 ;search left
	 If KeyDown(203) Or JoyXDir()=-1
	  If foc=1 Then charHairStyle(char)=charHairStyle(char)-1 : PlaySound sMenuBrowse : keytim=5
      If foc=2 Then charHair(char)=charHair(char)-1 : PlaySound sMenuBrowse : keytim=6
      If foc=3 Then charFace(char)=charFace(char)-1 : PlaySound sMenuBrowse : keytim=4
      If foc=4 Then charSpecs(char)=charSpecs(char)-1 : PlaySound sMenuBrowse : keytim=6
	  If foc=5 Then charModel(char)=charModel(char)-1 : PlaySound sMenuBrowse : keytim=6
	  If foc=6 Then charCostume(char)=charCostume(char)-1 : PlaySound sMenuBrowse : keytim=6
	 EndIf
	 ;search right
	 If KeyDown(205) Or JoyXDir()=1
	  If foc=1 Then charHairStyle(char)=charHairStyle(char)+1 : PlaySound sMenuBrowse : keytim=5
      If foc=2 Then charHair(char)=charHair(char)+1 : PlaySound sMenuBrowse : keytim=6
      If foc=3 Then charFace(char)=charFace(char)+1 : PlaySound sMenuBrowse : keytim=4
      If foc=4 Then charSpecs(char)=charSpecs(char)+1 : PlaySound sMenuBrowse : keytim=6
	  If foc=5 Then charModel(char)=charModel(char)+1 : PlaySound sMenuBrowse : keytim=6
	  If foc=6 Then charCostume(char)=charCostume(char)+1 : PlaySound sMenuBrowse : keytim=6   
	 EndIf 
	 ;refresh model
	 If KeyDown(28) Or ButtonPressed()
	  If foc=5 Then screenCall=1 : PlaySound sMenuGo : keytim=10
	 EndIf 
	EndIf  
	;limits
	If charHairStyle(char)<0 Then charHairStyle(char)=no_hairstyles
	If charHairStyle(char)>no_hairstyles Then charHairStyle(char)=0 
	If charHair(char)<1 Then charHair(char)=no_hairs
	If charHair(char)>no_hairs Then charHair(char)=1	
	If charFace(char)<1 Then charFace(char)=no_faces
	If charFace(char)>no_faces Then charFace(char)=1 
	If charSpecs(char)<0 Then charSpecs(char)=no_specs
	If charSpecs(char)>no_specs Then charSpecs(char)=0	  
	If charModel(char)<1 Then charModel(char)=no_models
	If charModel(char)>no_models Then charModel(char)=1
	If charCostume(char)<0 Then charCostume(char)=no_costumes
	If charCostume(char)>no_costumes Then charCostume(char)=0 	 
	 
	;UPDATE MODEL
	;costume
	If oldHairStyle<>charHairStyle(char) Or oldHair<>charHair(char) Or oldFace<>charFace(char) Or oldSpecs<>charSpecs(char) Or oldModel<>charModel(char) Or oldCostume<>charCostume(char)
	 ApplyCostume(cyc)
	EndIf
	;scale
	scaler#=charHeight(char)*0.0025
	ScaleEntity p(cyc),0.35+scaler#,0.35+scaler#,0.35+scaler#
	;shadows
	For limb=1 To 40
     If pShadow(cyc,limb)>0
      RotateEntity pShadow(cyc,limb),90,EntityYaw(pLimb(cyc,limb),1),0
      PositionEntity pShadow(cyc,limb),EntityX(pLimb(cyc,limb),1),pY#(cyc)+0.4,EntityZ(pLimb(cyc,limb),1)
     EndIf
    Next  
	
    ;CAMERA
    ;change position
    ;If KeyDown(36) Then camZ#=camZ#-1
    ;If KeyDown(38) Then camZ#=camZ#+1
    ;If KeyDown(23) Then camX#=camX#-1
    ;If KeyDown(37) Then camX#=camX#+1
    ;If KeyDown(35) Then camY#=camY#-1
    ;If KeyDown(21) Then camY#=camY#+1
    ;PositionEntity cam,camX#,camY#,camZ#
    ;change angles
    ;If KeyDown(203) Then camYA#=camYA#-1
    ;If KeyDown(205) Then camYA#=camYA#+1
    ;If KeyDown(200) Then camXA#=camXA#-1
    ;If KeyDown(208) Then camXA#=camXA#+1
    ;camXA#=CleanAngle#(camXA#)
    ;camYA#=CleanAngle#(camYA#)
    ;RotateEntity cam,camXA#,camYA#,camZA#

 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 DrawImage gLogo(3),rX#(570),rY#(65)
 ;PROFILE DISPLAY
 If page=1
  ;main options
  x=570 : y=145
  DrawOption(1,rX#(x),rY#(y),"Name",charName$(char))
  DrawOption(2,rX#(x),rY#(y+55),"Height",GetHeight$(charHeight(char)))
  DrawOption(3,rX#(x),rY#(y+115),"Strength",charStrength(char))
  DrawOption(4,rX#(x),rY#(y+170),"Agility",charAgility(char))
  DrawOption(5,rX#(x),rY#(y+230),"Intelligence",charIntelligence(char))
  DrawOption(6,rX#(x),rY#(y+285),"Crime",charCrime(char)+". "+textCrime$(charCrime(char)))
  DrawOption(7,rX#(x),rY#(y+345),">>> APPEARANCE >>>","")
  DrawOption(8,rX#(x),rY#(y+400),"<<< SAVE & EXIT <<<","")
  ;enter name
  If (KeyDown(14) Or ButtonPressed()) And foc=1 And gotim>40 And keytim=0
   PlaySound sMenuBrowse : keytim=20 : FlushKeys()
   DrawImage gMenu(1),rX#(x),rY#(y)
   DrawOption(1,rX#(x),rY#(y),"Name","   ")
   Flip
   Locate rX#(x)+15,rY#(y)-10 : Color 255,255,255
   SetFont font(3) 
   oldName$=charName$(char)
   charName$(char)=Left$(Input$(""),20) 
   If charName$(char)="" Then charName$(char)=oldNamee$
  Else
   SetFont font(2)
   If foc=1 Then Outline("(Press BACKSPACE to change)",rX#(x),rY#(y)+18,0,0,0,255,200,150)
  EndIf
  ;point reminder
  If foc=>3 And foc=<5 And gamPointLimit<999
   SetFont font(2)
   If foc=3 Then showY=y+115
   If foc=4 Then showY=y+170
   If foc=5 Then showY=y+230
   If foc=6 Then showY=y+285
   If gamPoints>0 Then Outline("("+gamPoints+" Points Remaining)",rX#(x),rY#(showY)+18,0,0,0,255,200,150)
   If gamPoints=0 Then Outline("(No Points Remaining!)",rX#(x),rY#(showY)+18,0,0,0,200,100,100)
  EndIf
 EndIf
 ;APPEARANCE DISPLAY
 If page=2
  ;main options
  x=570 : y=145
  DrawOption(1,rX#(x),rY#(y),"Hair Style",textHair$(charHairStyle(char)))
  DrawOption(2,rX#(x),rY#(y+55),"Hair Colour",charHair(char)+"/"+no_hairs)
  DrawOption(3,rX#(x),rY#(y+115),"Face",charFace(char)+"/"+no_faces)
  DrawOption(4,rX#(x),rY#(y+170),"Eyewear",textSpecs$(charSpecs(char)))
  DrawOption(5,rX#(x),rY#(y+230),"Build",textModel$(charModel(char)))
  DrawOption(6,rX#(x),rY#(y+285),"Outfit",textCostume$(charCostume(char)))
  DrawOption(7,rX#(x),rY#(y+345),">>> PROFILE >>>","")
  DrawOption(8,rX#(x),rY#(y+400),"<<< SAVE & EXIT <<<","")
  ;advice
  SetFont font(2)
  If foc=5 Then Outline("(Press ENTER to apply)",rX#(x),rY#(y+230)+18,0,0,0,255,200,150)
 EndIf
 ;loading call
 If screenCall=1
  QuickLoader(rX#(400),rY#(300),"Please Wait","Reloading Character")
  FreeEntity p(cyc)
  ReloadModel(cyc)
 EndIf
 ;diagnostics
 ;SetFont fontNumber
 ;Outline("camX: "+camX#,rX#(50),rY#(50),0,0,0,255,200,150)
 ;Outline("camY: "+camY#,rX#(50),rY#(65),0,0,0,255,200,150)
 ;Outline("camZ: "+camZ#,rX#(50),rY#(80),0,0,0,255,200,150) 
 ;Outline("camXA: "+camXA#,rX#(50),rY#(150),0,0,0,155,200,250)
 ;Outline("camYA: "+camYA#,rX#(50),rY#(165),0,0,0,155,200,250)
 ;Outline("camZA: "+camZA#,rX#(50),rY#(180),0,0,0,155,200,250) 

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
If go=>1 Then PlaySound sMenuGo Else PlaySound sMenuBack
;take photo
QuickLoader(rX#(400),rY#(300),"Please Wait","Saving Character")
charPhoto(char)=CreateImage(rX#(300),rY#(200))
GrabImage charPhoto(char),rX#(210),rY#(220-charHeight(char))
ResizeImage charPhoto(char),150,100
SaveImage(charPhoto(char),"Data/Slot0"+slot+"/Photos/Photo"+Dig$(char,100)+".bmp")
MaskImage charPhoto(char),255,0,255
charSnapped(char)=1
SaveChars()
;free entities
FreeTimer timer
FreeEntity world
FreeEntity cam
FreeEntity light(1)
FreeEntity p(cyc)
For limb=1 To 40
 If pShadow(cyc,limb)>0
  FreeEntity pShadow(cyc,limb)
 EndIf
Next
;proceed
screen=8
If gamPointLimit<999
 For v=1 To no_chars
  If charRole(v)=1 And charLocation(v)=9 Then promoAccuser=v
 Next
 screen=52
EndIf
End Function 

;-----------------------------------------------------------------
;////////////////////// RELATED FUNCTIONS ////////////////////////
;-----------------------------------------------------------------

;INITIALISE CREATION MODEL
Function ReloadModel(cyc)
 ;sequences
 p(cyc)=LoadAnimMesh("Characters/Models/Model"+Dig$(charModel(pChar(cyc)),10)+".3ds")
 pSeq(cyc,604)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard04.3ds")
 pSeq(cyc,1)=ExtractAnimSeq(p(cyc),770,850,pSeq(cyc,604))
 ;position
 Animate p(cyc),1,0.1,pSeq(cyc,1),0
 PositionEntity p(cyc),pX#(cyc),pY#(cyc),pZ#(cyc) 
 RotateEntity p(cyc),0,345,0
 ;load appearance
 ApplyCostume(cyc)
 EntityTexture FindChild(p(cyc),"Head"),tEyes(1),0,3
 ;hide weapons by default
 HideEntity FindChild(p(cyc),"Phone") 
 HideEntity FindChild(p(cyc),"Barbell") 
 For v=1 To weapList
  HideEntity FindChild(p(cyc),weapFile$(v))
 Next
End Function

;IDENTIFY LIMBS
Function GetLimbs(cyc)
 ;reset entries
 For count=1 To 40
  pLimb(cyc,count)=0 
 Next
 ;upper body
 pLimb(cyc,1)=FindChild(p(cyc),"Head")
 pLimb(cyc,2)=FindChild(p(cyc),"Neck")
 If BaggyTop(charCostume(pChar(cyc)))
  pLimb(cyc,3)=FindChild(p(cyc),"Body_Baggy") 
 Else
  pLimb(cyc,3)=FindChild(p(cyc),"Body")
 EndIf
 ;left arm
 pLimb(cyc,4)=FindChild(p(cyc),"L_Bicep")
 pLimb(cyc,5)=FindChild(p(cyc),"L_Arm")
 pLimb(cyc,6)=FindChild(p(cyc),"L_Palm")
 pLimb(cyc,7)=FindChild(p(cyc),"L_Thumb01")
 pLimb(cyc,8)=FindChild(p(cyc),"L_Thumb02")
 For count=1 To 8
  pLimb(cyc,8+count)=FindChild(p(cyc),"L_Finger0"+count)
 Next
 ;right arm
 pLimb(cyc,17)=FindChild(p(cyc),"R_Bicep")
 pLimb(cyc,18)=FindChild(p(cyc),"R_Arm")
 pLimb(cyc,19)=FindChild(p(cyc),"R_Palm")
 pLimb(cyc,20)=FindChild(p(cyc),"R_Thumb01")
 pLimb(cyc,21)=FindChild(p(cyc),"R_Thumb02")
 For count=1 To 8
  pLimb(cyc,21+count)=FindChild(p(cyc),"R_Finger0"+count)
 Next
 ;lower body
 pLimb(cyc,30)=FindChild(p(cyc),"Hips")
 pLimb(cyc,31)=FindChild(p(cyc),"L_Thigh")
 pLimb(cyc,32)=FindChild(p(cyc),"L_Leg")
 pLimb(cyc,33)=FindChild(p(cyc),"L_Foot")
 pLimb(cyc,34)=FindChild(p(cyc),"R_Thigh") 
 pLimb(cyc,35)=FindChild(p(cyc),"R_Leg")
 pLimb(cyc,36)=FindChild(p(cyc),"R_Foot")
 ;additional 
 pLimb(cyc,37)=FindChild(p(cyc),"L_Ear")
 pLimb(cyc,38)=FindChild(p(cyc),"R_Ear")
End Function

;MAJOR LIMB?
Function MajorLimb(limb)
 major=0
 If limb=>4 And limb=<6 Then major=1
 If limb=>17 And limb=<19 Then major=1
 If limb=>30 And limb=<36 Then major=1
 Return major
End Function

;HAND INTACT?
Function HandIntact(cyc,limb) ;left=4, right=17
 value=1
 If pScar(cyc,limb)=>5 Or pScar(cyc,limb+1)=>5 Or pScar(cyc,limb+2)=>5 Then value=0
 Return value
End Function

;DESCRIBE LIMB
Function DescribeLimb$(char)
 injury$="a limb"
 ;ears
 If charScar(char,37)=>5 Or charScar(char,38)=>5 Then injury$="an ear"
 ;fingers
 For count=1 To 8
  If charScar(char,8+count)=>5 Or charScar(char,21+count)=>5 Then injury$="a finger"
 Next
 ;thumbs
 If charScar(char,7)=>5 Or charScar(char,8)=>5 Or charScar(char,20)=>5 Or charScar(char,21)=>5 Then injury$="a thumb"
 ;hands
 If charScar(char,6)=>5 Or charScar(char,19)=>5 Then injury$="a hand"
 ;feet
 If charScar(char,33)=>5 Or charScar(char,36)=>5 Then injury$="a foot"
 ;arms
 If charScar(char,4)=>5 Or charScar(char,5)=>5 Or charScar(char,17)=>5 Or charScar(char,18)=>5 Then injury$="an arm"
 ;legs
 If charScar(char,31)=>5 Or charScar(char,32)=>5 Or charScar(char,34)=>5 Or charScar(char,35)=>5 Then injury$="a leg"
 Return injury
End Function

;DETERMINE RACE
Function GetRace(char) ;0=white, 1=asian, 2=black
 value=0
 If charFace(char)=>21 And charFace(char)=<40 Then value=1
 If charFace(char)=>41 And charFace(char)=<60 Then value=2
 Return value
End Function

;BAGGY COSTUME?
Function BaggyTop(costume) 
 baggy=0
 If costume=2 Or costume=4 Or costume=6 Or costume=8 Then baggy=1
 Return baggy
End Function

;APPLY COSTUME
Function ApplyCostume(cyc)
 GetLimbs(cyc)
 ApplyHairstyle(cyc)
 ApplyEyewear(cyc) 
 ApplyAccessories(cyc)
 ApplyClothing(cyc) 
End Function

;REMOVE ALL HAIR
Function RemoveHair(cyc)
 For limb=1 To 15
  If FindChild(p(cyc),hairFile$(limb))>0
   EntityAlpha FindChild(p(cyc),hairFile$(limb)),0
  EndIf
 Next
End Function

;APPLY HAIRSTYLE
Function ApplyHairstyle(cyc)
 ;hide all by default
 char=pChar(cyc)
 RemoveHair(cyc)
 ;determine style
 showA=0 : showB=0
 If charHairStyle(char)=2 Then hairerA$="Hair_Bald" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=3 Then hairerA$="Hair_Thin" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=4 Then hairerA$="Hair_Short" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=5 Then hairerA$="Hair_Raise" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=6 Then hairerA$="Hair_Quiff" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=7 Then hairerA$="Hair_Mop" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=8 Then hairerA$="Hair_Thick" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=9 Then hairerA$="Hair_Full" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=10 Then hairerA$="Hair_Curl" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=11 Then hairerA$="Hair_Afro" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=12 Then hairerA$="Hair_Spike" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=13 Then hairerA$="Hair_Punk" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=14 Then hairerA$="Hair_Rolls" : hairerB$="" : showA=1 : showB=0
 If charHairStyle(char)=15 Then hairerA$="Hair_Bald" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=16 Then hairerA$="Hair_Thin" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=17 Then hairerA$="Hair_Short" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=18 Then hairerA$="Hair_Raise" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=19 Then hairerA$="Hair_Quiff" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=20 Then hairerA$="Hair_Mop" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=21 Then hairerA$="Hair_Thick" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=22 Then hairerA$="Hair_Curl" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=23 Then hairerA$="Hair_Punk" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=24 Then hairerA$="Hair_Rolls" : hairerB$="Hair_Pony" : showA=1 : showB=1
 If charHairStyle(char)=25 Then hairerA$="Hair_Bald" : hairerB$="Hair_Long" : showA=1 : showB=1
 If charHairStyle(char)=26 Then hairerA$="Hair_Thin" : hairerB$="Hair_Long" : showA=1 : showB=1
 If charHairStyle(char)=27 Then hairerA$="Hair_Short" : hairerB$="Hair_Long" : showA=1 : showB=1
 If charHairStyle(char)=28 Then hairerA$="Hair_Raise" : hairerB$="Hair_Long" : showA=1 : showB=1
 If charHairStyle(char)=29 Then hairerA$="Hair_Quiff" : hairerB$="Hair_Long" : showA=1 : showB=1
 If charHairStyle(char)=30 Then hairerA$="Hair_Mop" : hairerB$="Hair_Long" : showA=1 : showB=1
 If charHairStyle(char)=31 Then hairerA$="Hair_Thick" : hairerB$="Hair_Long" : showA=1 : showB=1
 ;tuck hair under hat
 If charAccessory(char)=2 Or charAccessory(char)=7
  If charHairStyle(char)=>2 Then hairerA$="Hair_Bald"
 EndIf
 ;tuck mop into headband
 If charAccessory(char)=5
  If charHairStyle(char)=7 Or charHairStyle(char)=20 Or charHairStyle(char)=30 Then hairerA$="Hair_Short"
 EndIf
 ;compose hair
 If charHairStyle(char)>1
  randy=Rnd(1,3)
  If showA=1
   EntityAlpha FindChild(p(cyc),hairerA$),1
   EntityTexture FindChild(p(cyc),hairerA$),tHair(charHair(pChar(cyc)));,0,1
  EndIf
  If showB=1
   EntityAlpha FindChild(p(cyc),hairerB$),1 
   EntityTexture FindChild(p(cyc),hairerB$),tHair(charHair(pChar(cyc)));,0,1
  EndIf
 EndIf
 ;add shaved layer
 If charHairStyle(char)=1 Or charHairStyle(char)=14 Or charHairStyle(char)=24
  EntityTexture FindChild(p(cyc),"Head"),tShaved,0,2
 Else
  EntityTexture FindChild(p(cyc),"Head"),tMouth(0),0,2
 EndIf
End Function

;APPLY EYEWEAR
Function ApplyEyewear(cyc)
 ;hide by default
 char=pChar(cyc)
 HideEntity FindChild(p(cyc),"Specs")
 HideEntity FindChild(p(cyc),"Lens01")
 HideEntity FindChild(p(cyc),"Lens02")
 If charSpecs(char)>0
  ;compose specs
  ShowEntity FindChild(p(cyc),"Specs")
  EntityShininess FindChild(p(cyc),"Specs"),0.5
  For count=1 To 2
   ShowEntity FindChild(p(cyc),"Lens0"+count)
   EntityColor FindChild(p(cyc),"Lens0"+count),255,255,255
   EntityAlpha FindChild(p(cyc),"Lens0"+count),0.35
   EntityShininess FindChild(p(cyc),"Lens0"+count),1
  Next
  ;golden frame
  If charSpecs(char)=1 
   EntityTexture FindChild(p(cyc),"Specs"),tSpecs(1)
  EndIf
  ;silver frame
  If charSpecs(char)=2 
   EntityTexture FindChild(p(cyc),"Specs"),tSpecs(2)
  EndIf
  ;black frame
  If charSpecs(char)=3 
   EntityTexture FindChild(p(cyc),"Specs"),tSpecs(3)
  EndIf
  ;shades
  If charSpecs(char)=4 
   EntityTexture FindChild(p(cyc),"Specs"),tSpecs(3)
   For count=1 To 2
    EntityColor FindChild(p(cyc),"Lens0"+count),0,0,0
    EntityAlpha FindChild(p(cyc),"Lens0"+count),0.75
   Next
  EndIf
 EndIf
End Function

;APPLY ACCESSORIES
Function ApplyAccessories(cyc)
 ;hide by default
 char=pChar(cyc)
 HideEntity FindChild(p(cyc),"Turban")
 HideEntity FindChild(p(cyc),"Bling")
 HideEntity FindChild(p(cyc),"Tie")
 HideEntity FindChild(p(cyc),"BandA")
 HideEntity FindChild(p(cyc),"BandB")
 HideEntity FindChild(p(cyc),"Armband") 
 HideEntity FindChild(p(cyc),"Cap")
 ;turban
 If charAccessory(char)=2 Then ShowEntity FindChild(p(cyc),"Turban")
 ;bling
 If charAccessory(char)=3
  EntityShininess FindChild(p(cyc),"Bling"),1.0 
  ShowEntity FindChild(p(cyc),"Bling")
 EndIf
 ;tie
 If charAccessory(char)=4 Then ShowEntity FindChild(p(cyc),"Tie")
 ;headband
 If charAccessory(char)=5
  ShowEntity FindChild(p(cyc),"BandA")
  If charHairStyle(char)=<1 Or charHairStyle(char)=13 Or charHairStyle(char)=14 Or charHairStyle(char)=23 Or charHairStyle(char)=24 
   ShowEntity FindChild(p(cyc),"BandB")
  EndIf
 EndIf
 ;armband
 If charAccessory(char)=6 Then ShowEntity FindChild(p(cyc),"Armband")
 ;cap
 If charAccessory(char)=7 Then ShowEntity FindChild(p(cyc),"Cap")
End Function

;APPLY CLOTHING
Function ApplyClothing(cyc)
 ;head
 char=pChar(cyc)
 For limb=1 To 2
  EntityTexture pLimb(cyc,1),tFace(charFace(char)),0,1
  EntityTexture pLimb(cyc,2),tFace(charFace(char)),0,1
 Next
 ;ears
 For limb=37 To 38
  EntityTexture pLimb(cyc,limb),tFace(charFace(char)),0,1
  EntityTexture pLimb(cyc,limb),tEars,0,3
 Next
 ;body
 limb=3
 EntityTexture pLimb(cyc,limb),tMouth(0),0,2
 EntityTexture pLimb(cyc,limb),tMouth(0),0,3
 EntityTexture pLimb(cyc,limb),tMouth(0),0,4
 If charRole(char)=0 
  If charCostume(char)=0 
   If (charModel(char)=<2 And charStrength(char)<70) Or charModel(char)=>4 Then body=10 Else body=1
   EntityTexture pLimb(cyc,limb),tBody(body),0,1
   If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tBodyShade(GetRace(char)),0,2
   If charGang(char)>0 Then EntityTexture pLimb(cyc,limb),tTattooBody(charGang(char)),0,3
  EndIf
  If charCostume(char)=>1 And charCostume(char)=<2
   EntityTexture pLimb(cyc,limb),tBody(2),0,1
   If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tBodyShade(2+GetRace(char)),0,2
   If charGang(char)>0 Then EntityTexture pLimb(cyc,limb),tTattooVest(charGang(char)),0,3
  EndIf
  If charCostume(char)=>3 And charCostume(char)=<4 Then EntityTexture pLimb(cyc,limb),tBody(3),0,1
  If charCostume(char)=>5 Then EntityTexture pLimb(cyc,limb),tBody(4+charBlock(char)),0,1
  If charCostume(char)=>3
   EntityTexture pLimb(cyc,limb),tBlock(charBlock(char)),0,3
   EntityTexture pLimb(cyc,limb),tCell(charCell(char)),0,4
  EndIf
 EndIf
 If charRole(char)=1 Then EntityTexture pLimb(cyc,limb),tBody(4),0,1
 If charRole(char)=>2 Then EntityTexture pLimb(cyc,limb),tBody(9),0,1
 If BaggyTop(charCostume(pChar(cyc)))
  EntityAlpha FindChild(p(cyc),"Body_Baggy"),1
  EntityAlpha FindChild(p(cyc),"Body"),0
 Else
  EntityAlpha FindChild(p(cyc),"Body"),1
  EntityAlpha FindChild(p(cyc),"Body_Baggy"),0
 EndIf 
 ;arms
 For limb=4 To 29
  EntityTexture pLimb(cyc,limb),tMouth(0),0,2
  EntityTexture pLimb(cyc,limb),tMouth(0),0,3
  If charRole(char)=0
   If charCostume(char)=<2 
    EntityTexture pLimb(cyc,limb),tArm(1),0,1
    If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tArmShade(GetRace(char)),0,2
    If charGang(char)>0 Then EntityTexture pLimb(cyc,limb),tTattooArm(charGang(char)),0,3
   EndIf
   If charCostume(char)=>3 And charCostume(char)=<4
    EntityTexture pLimb(cyc,limb),tArm(2),0,1
    If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tArmShade(2+GetRace(char)),0,2
    If charGang(char)>0 Then EntityTexture pLimb(cyc,limb),tTattooTee(charGang(char)),0,3
   EndIf
   If charCostume(char)=>5 And charCostume(char)=<6
    EntityTexture pLimb(cyc,limb),tArm(3+charBlock(char)),0,1
    If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tArmShade(2+GetRace(char)),0,2
    If charGang(char)>0 Then EntityTexture pLimb(cyc,limb),tTattooTee(charGang(char)),0,3
   EndIf
   If charCostume(char)=>7 And charCostume(char)=<8
    EntityTexture pLimb(cyc,limb),tArm(7+charBlock(char)),0,1
    If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tArmShade(4+GetRace(char)),0,2
    If charGang(char)>0 Then EntityTexture pLimb(cyc,limb),tTattooSleeve(charGang(char)),0,3 
   EndIf
  EndIf
  If charRole(char)=1
   EntityTexture pLimb(cyc,limb),tArm(3),0,1
   If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tArmShade(2+GetRace(char)),0,2
  EndIf
  If charRole(char)=>2
   EntityTexture pLimb(cyc,limb),tArm(12),0,1
   If GetRace(char)>0 Then EntityTexture pLimb(cyc,limb),tArmShade(6+GetRace(char)),0,2
  EndIf
 Next
 ;legs
 For limb=30 To 36
  If charRole(char)=0 Then EntityTexture pLimb(cyc,limb),tLegs(1+charBlock(char)),0,1
  If charRole(char)=1 Then EntityTexture pLimb(cyc,limb),tLegs(1),0,1
  If charRole(char)=>2 Then EntityTexture pLimb(cyc,limb),tLegs(6),0,1 
 Next
End Function

;ADJUST TO GANG
Function GangAdjust(char)
 If charRole(char)=0 Then charAccessory(char)=charGang(char)
 ;skinhead & shades
 If charGang(char)=1 And charHairStyle(char)>1 Then charHairStyle(char)=Rnd(0,1) : charSpecs(char)=4
 ;chest/vest for thug
 If charGang(char)=5 And charCostume(char)>2 Then charCostume(char)=Rnd(0,2)
End Function
;//////////////////////////////////////////////////////////////////////////////
;------------------------------ HARD TIME: CREDITS ----------------------------
;//////////////////////////////////////////////////////////////////////////////

;//////////////////////////////////////////////////////////////////////////////
;--------------------------------- INTRO --------------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function Intro()
;initial media
font(0)=LoadFont("Arial Cyr",13,0,0,0)
font(1)=LoadFont("Arial Cyr",16,0,0,0)
font(2)=LoadFont("Arial Cyr",20,0,0,0)
font(3)=LoadFont("Arial Cyr",24,0,0,0)
font(4)=LoadFont("Arial Cyr",36,0,0,0)
font(5)=LoadFont("Arial Cyr",42,0,0,0)
font(6)=LoadFont("Arial Cyr",48,0,0,0)
gTile=LoadImage("Graphics/Tile.png")
MaskImage gTile,255,0,255
For count=1 To 3
 gLogo(count)=LoadImage("Graphics/Logo0"+count+".png")
 MaskImage gLogo(count),255,0,255
Next
gMDickie=LoadImage("Graphics/MDickie.png")
MaskImage gMDickie,255,0,255
For count=1 To 4
 gMenu(count)=LoadImage("Graphics/Menu0"+count+".png")
 MaskImage gMenu(count),255,0,255
Next
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
logoX1#=-100 : logoX2#=900
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames
	
	;PROCESS     
    gotim=gotim+1
    logoX1#=logoX1#+40
    If logoX1#>400 Then logoX1#=400
    logoX2#=logoX2#-40
    If logoX2#<400 Then logoX2#=400
    If logoX1#=400 And logoX2#=400 And go=0 Then PlaySound sMenuGo : go=1
	
 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 DrawImage gLogo(1),rX#(logoX1#),rY#(300)
 DrawImage gLogo(2),rX#(logoX2#),rY#(300)

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
Loader(translate("Please Wait"),translate("Loading Game"))
sTheme=LoadSound("Sound/Theme.wav")
LoopSound sTheme
chTheme=PlaySound(sTheme)
musicVol#=1.0
ChannelVolume chTheme,musicVol#
FreeTimer timer
End Function

;//////////////////////////////////////////////////////////////////////////////
;------------------------------- 6. CREDITS -----------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function Credits()
;camera for fading
cam=CreateCamera()
CameraViewport cam,0,0,GraphicsWidth(),GraphicsHeight()
CameraClsMode cam,0,1
;sprite For fading
fader=CreateSprite()
ScaleSprite fader,30,30
SpriteViewMode fader,1
PositionEntity fader,0,20,-5
PointEntity cam,fader
If gamEnded=1 Then fadeAlpha#=1.0 Else fadeAlpha#=0
EntityAlpha fader,fadeAlpha# 
EntityColor fader,0,0,0
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
scroll#=0 : gamma=0
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames
	
	;timers
	keytim=keytim-1
	If keytim<1 Then keytim=0
	
	;PORTAL
    If gamEnded=0 Or fadeAlpha#<0.5 Then gotim=gotim+1
	If gotim>20 And keytim=0
	 If KeyDown(1) Or KeyDown(28) Or ButtonPressed() Then go=1
	EndIf
	
	;scroller
	If gotim>20
	 scroll#=scroll#-1
	 If KeyDown(200) Or JoyY()=-1 Then scroll#=scroll#-5
	 If KeyDown(208) Or JoyY()=1 Then scroll#=scroll#+6
	 If scroll#<-1300 Then scroll#=400
	 If scroll#>400 Then scroll#=-1300     
    EndIf
  
    ;fade in
    If gamEnded=1
     fadeAlpha#=fadeAlpha#-0.01
 	 If fadeAlpha#=<0 Then fadeAlpha#=0 : gamEnded=0
     EntityAlpha fader,fadeAlpha# 
    EndIf
	
 UpdateWorld
 Next

 ;DISPLAY
 TileImage gTile
 DrawMainLogo(rX#(400),rY#(300+scroll#))
 ;opening comment
 SetFont font(3)
 y=410
 Outline(translate("Credit is to be given - not"),rX#(400),rY#(y+scroll#),0,0,0,255,255,255) : y=y+25
 Outline(translate("taken. This game was made single-"),rX#(400),rY#(y+scroll#),0,0,0,255,255,255) : y=y+25
 Outline(translate("handedly in 3 months. Figure out"),rX#(400),rY#(y+scroll#),0,0,0,255,255,255) : y=y+25
 Outline(translate("how you feel about that..."),rX#(400),rY#(y+scroll#),0,0,0,255,255,255) : y=y+25
 ;roles
 y=550
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Translate"),"13Hz") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),"Telegram","@async13hhz") : y=y+110
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Concept"),"MDickie 2006") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Game Design"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Programming"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("2D Graphics"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("3D Modeling"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Texturing"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Animation"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Sound FX"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Music"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Scripts"),"Mat Dickie") : y=y+60
 DrawOption(-1,rX#(400),rY#(y+scroll#),translate("Publishing"),"MDickie.com") : y=y+110
 ;final logo
 DrawImage gMDickie,rX#(400),rY#(y+scroll#)  

 RenderWorld 1
 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
If go=1 Then PlaySound sMenuGo Else PlaySound sMenuBack
FreeTimer timer
FreeEntity cam
FreeEntity fader 
screen=1
End Function

;//////////////////////////////////////////////////////////////////////////////
;-------------------------------- 7. OUTRO ------------------------------------
;//////////////////////////////////////////////////////////////////////////////
Function Outro()
;frame rating
timer=CreateTimer(30)
;MAIN LOOP
colour=0
logoX1#=400 : logoX2#=400
go=0 : gotim=0 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames
	
	;PROCESS     
    gotim=gotim+1
    If gotim>50 
     If KeyDown(1) Or KeyDown(28) Or ButtonPressed() Or gotim>400 Then go=1
    EndIf
    ;logo slide
    logoX1#=logoX1#-40
    logoX2#=logoX2#+40
    ;text fade
    colour=colour+20
    If colour>255 Then colour=255
    ;music fade
    musicVol#=musicVol#-0.0025
    If musicVol#<0 Then musicVol#=0
    ChannelVolume chTheme,musicVol#
    
	
 UpdateWorld
 Next
 RenderWorld 1

 ;DISPLAY
 TileImage gTile
 ;SetFont font(4)
 ;Outline("'Turning nothing into",rX#(400),rY#(300)-20,0,0,0,colour,colour,colour)
 ;Outline("something is God work...'",rX#(400),rY#(300)+20,0,0,0,colour,colour,colour)
 ;Outline("'Tough times never last,",rX#(400),rY#(300)-20,0,0,0,colour,colour,colour)
 ;Outline("but tough people do...'",rX#(400),rY#(300)+20,0,0,0,colour,colour,colour)
 SetFont font(3)
 Outline(translate("'When a member of society falls down, he falls for those"),rX#(400),rY#(300)-48,0,0,0,colour,colour,colour)
 Outline(translate("behind him - as a caution against the stumbling stone."),rX#(400),rY#(300)-18,0,0,0,colour,colour,colour)
 Outline(translate("And he falls for those ahead of him - who, though"),rX#(400),rY#(300)+15,0,0,0,colour,colour,colour)
 Outline(translate("faster and surer of foot, failed to remove the stone...'"),rX#(400),rY#(300)+45,0,0,0,colour,colour,colour)
 SetFont font(2)
 Outline("- Kahlil Gibran",rX#(400),rY#(300)+75,0,0,0,colour,colour,colour)  
 DrawImage gLogo(1),rX#(logoX1#),rY#(300)
 DrawImage gLogo(2),rX#(logoX2#),rY#(300)
 DrawImage gMDickie,rX#(400),rY#(515)

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;leave
FreeTimer timer
StopChannel chTheme
SaveOptions()
screen=0
End Function

;---------------------------------------------------------------
;/////////////////////// 53. ENDING ////////////////////////////
;---------------------------------------------------------------
Function Ending()
;load setting
Loader(translate("Please Wait"),translate("Leaving Prison"))
world=LoadAnimMesh("World/Yard/Outro.3ds")
EntityTexture FindChild(world,"Sign01"),tSign(9),0,2
EntityTexture FindChild(world,"Net"),tNet
sAtmos=LoadSound("Sound/Ambience/Yard.wav")
;camera
cam=CreateCamera()
CameraViewport cam,0,0,GraphicsWidth(),GraphicsHeight()
camX#=375 : camY#=30 : camZ#=-20
camPivot=CreatePivot()
dummy=CreatePivot()
;fader
fader=CreateSprite()
ScaleSprite fader,20,20
SpriteViewMode fader,1
fadeAlpha#=0 : fadeTarget#=0
EntityAlpha fader,fadeAlpha#
EntityColor fader,0,0,0
;lighting
LoadLighting()
AmbientLight 200,190,170
For count=1 To no_lights
 LightColor light(count),200,180,160
Next
If optFog>0
 CameraFogMode cam,1
 CameraFogRange cam,500,1000
 CameraFogColor cam,160,130,100
EndIf
EntityColor FindChild(world,"Sky"),220,200,180
;background noise
LoopSound sAtmos
chAtmos=PlaySound(sAtmos)
ChannelVolume chAtmos,0.1
;load character
cyc=1
pChar(cyc)=gamChar(slot)
p(cyc)=LoadAnimMesh("Characters/Models/Model"+Dig$(charModel(pChar(cyc)),10)+".3ds")
pSeq(cyc,603)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard03.3ds")
pSeq(cyc,604)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard04.3ds") 
pSeq(cyc,1)=ExtractAnimSeq(p(cyc),770,850,pSeq(cyc,604)) ;standing
pSeq(cyc,2)=ExtractAnimSeq(p(cyc),915,995,pSeq(cyc,603)) ;walking
ApplyCostume(cyc) 
EntityTexture pLimb(cyc,1),tEyes(3),0,3
For limb=1 To 40
 pScar(cyc,limb)=charScar(pChar(cyc),limb)
 If pLimb(cyc,limb)>0 And pScar(cyc,limb)=>5
  HideEntity pLimb(cyc,limb)
 EndIf
Next
SeverLimbs(cyc)
For v=1 To weapList
 HideEntity FindChild(p(cyc),weapFile$(v))
Next
HideEntity FindChild(p(cyc),"Barbell") 
HideEntity FindChild(p(cyc),"Phone") 
pX#(cyc)=355 : pY#(cyc)=12 : pZ#(cyc)=-30
PositionEntity p(cyc),pX#(cyc),pY#(cyc),pZ#(cyc)
RotateEntity p(cyc),0,0,0
scaler#=charHeight(pChar(cyc))*0.0025
ScaleEntity p(cyc),0.34+scaler#,0.34+scaler#,0.34+scaler#
Animate p(cyc),1,Rnd(0.1,0.3),pSeq(cyc,1),0
pStepTim(cyc)=0 : pState(cyc)=1
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
;generate stories
For count=1 To 9
 its=0
 Repeat
  conflict=0 : its=its+1
  endChar(count)=Rnd(1,no_chars) 
  If charSnapped(endChar(count))=0 And its<200 Then conflict=1
  If endChar(count)=gamChar(slot) Or charRole(endChar(count))>0 Or charHealth(endChar(count))=<0 Then conflict=1
  For v=1 To count
   If v<>count And endChar(count)=endChar(v) Then conflict=1
  Next
 Until conflict=0
 endFate(count)=Rnd(1,70)
Next
endChar(10)=gamChar(slot)
endFate(10)=0
;frame ratings
timer=CreateTimer(30)
SeedRnd(MilliSecs())
;MAIN LOOP
promoTim=0 : page=1
go=0 : gotim=-20 : keytim=0
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames
	
	;counters
	keytim=keytim-1
	If keytim<1 Then keytim=0
	
	;PORTAL 
    gotim=gotim+1
    If gotim>100 Then promoTim=promoTim+1
    If gotim>50 And keytim=0
     If KeyDown(1) Or fadeAlpha#=>0.99 Then go=1
    EndIf
	
	;MOVEMENT
	cyc=1
	If gotim>200 And pState(cyc)<>2 Then Animate p(cyc),1,1.5,pSeq(cyc,2),10 : pState(cyc)=2
	If pState(cyc)=2 Then pZ#(cyc)=pZ#(cyc)+0.3 : pStepTim(cyc)=pStepTim(cyc)+1
	If pZ#(cyc)>225 Then fadeTarget#=1.0
	PositionEntity p(cyc),pX#(cyc),pY#(cyc),pZ#(cyc)
	;shadows
	For limb=1 To 40
     If pShadow(cyc,limb)>0
      RotateEntity pShadow(cyc,limb),90,EntityYaw(pLimb(cyc,limb),1),0
      PositionEntity pShadow(cyc,limb),EntityX(pLimb(cyc,limb),1),pY#(cyc)+0.4,EntityZ(pLimb(cyc,limb),1)
     EndIf
    Next
	;footsteps
    If pStepTim(cyc)>20/1.5
     ProduceSound(0,sStep(Rnd(3,4)),22050,0) 
     pStepTim(cyc)=0
    EndIf  
	
	;CAMERA
	;camera tracking 
    If gotim>0 
     camTX#=450 : camTY#=30 : camTZ#=100
     GetSmoothSpeeds(camX#,camTX#,camY#,camTY#,camZ#,camTZ#,240)
     If camX#<camTX# Then camX#=camX#+speedX#
     If camX#>camTX# Then camX#=camX#-speedX#
     If camY#<camTY# Then camY#=camY#+(speedY#/2)
     If camY#>camTY# Then camY#=camY#-(speedY#/2)
     If camZ#<camTZ# Then camZ#=camZ#+speedZ#
     If camZ#>camTZ# Then camZ#=camZ#-speedZ#
    EndIf 
    PositionEntity cam,camX#,camY#,camZ#
	PositionEntity camPivot,pX#(cyc),pY#(cyc)+25,pZ#(cyc)
	PointEntity cam,camPivot
	;fader
	If fadeAlpha#<fadeTarget# Then fadeAlpha#=fadeAlpha#+0.001
    If fadeAlpha#>fadeTarget# Then fadeAlpha#=fadeAlpha#-0.001
    If fadeAlpha#<0 Then fadeAlpha#=0
    If fadeAlpha#>1.0 Then fadeAlpha#=1.0
    PositionEntity dummy,camX#,camY#,camZ#
    RotateEntity dummy,EntityPitch(cam),EntityYaw(cam),EntityRoll(cam)
    MoveEntity dummy,0,0,3
    PositionEntity fader,EntityX(dummy),EntityY(dummy),EntityZ(dummy)
    EntityAlpha fader,fadeAlpha#  
	
 UpdateWorld
 Next  
 RenderWorld 1

 ;DISPLAY
 ;introduce widescreen
 If promoTim>0 
  If promoTim=<100 Then y#=PercentOf#(60,promoTim) Else y#=60
  Color 0,0,0 : Rect rX#(0),rY#(0),rX#(800),rY#(y#),1
  If promoTim=<100 Then y#=600-PercentOf#(120,promoTim) Else y#=480 
  Color 0,0,0 : Rect rX#(0),rY#(y#),rX#(800),rY#(600),1
 EndIf 
 ;display allumni
 If promoTim=>125 And promoTim=<275 And page=>1 And page=<10
  x=82 : y=539
  If charSnapped(endChar(page))>0 And charPhoto(endChar(page))>0
   DrawImage charPhoto(endChar(page)),rX#(x),rY#(y)
  Else
   DrawImage gPhoto,rX#(x),rY#(y)
  EndIf
  Color 50,50,50 : Rect rX#(x)-75,rY#(y)-50,150,100,0
  ;scriptA$=translate("Steve Austin never left the prison system.180")
  If endFate(page)=0
   scriptA$=charName$(endChar(page))+translate(" was never heard from again, but") 
   scriptB$=translate("tales of his life are still told to this day...")
  EndIf
  If endFate(page)=1 
   scriptA$=charName$(endChar(page))+translate(" was brutally murdered by") 
   scriptB$=translate("another inmate and never saw his release...")
  EndIf
  If endFate(page)=2 
   scriptA$=translate("Prison became too much for ")+charName$(endChar(page))+translate(".") 
   scriptB$=translate("He hung himself from the bars of his cell...")
  EndIf
  If endFate(page)=3 
   scriptA$=translate("Prison became too much for ")+charName$(endChar(page))+translate(".") 
   scriptB$=translate("He slit his wrists in the bathroom...")
  EndIf
  If endFate(page)=4 
   scriptA$=charName$(endChar(page))+translate(" never left the prison system.") 
   scriptB$=translate("He remained behind bars until the day he died...")
  EndIf
  If endFate(page)=5
   scriptA$=charName$(endChar(page))+translate(" kept disobeying the wardens") 
   scriptB$=translate("and was never considered for release...")
  EndIf
  If endFate(page)=6 
   scriptA$=charName$(endChar(page))+translate(" became embroiled in gang") 
   scriptB$=translate("culture and never left a life of crime...")
  EndIf
  If endFate(page)=7 
   scriptA$=charName$(endChar(page))+translate(" was released and went on") 
   scriptB$=translate("to marry his long-time girlfriend...")
  EndIf
  If endFate(page)=8 
   scriptA$=charName$(endChar(page))+translate(" was released and went on") 
   scriptB$=translate("to raise a family with his wife...")
  EndIf
  If endFate(page)=9 
   scriptA$=charName$(endChar(page))+translate(" returned to his wife and") 
   scriptB$=translate("children but never regained their trust...")
  EndIf
  If endFate(page)=10
   scriptA$=charName$(endChar(page))+translate(" returned to his wife and") 
   scriptB$=translate("kids and became a committed family man...")
  EndIf
  If endFate(page)=11 
   scriptA$=charName$(endChar(page))+translate(" got used to being surrounded") 
   scriptB$=translate("by men and became a rampant homosexual...")
  EndIf
  If endFate(page)=12 
   scriptA$=charName$(endChar(page))+translate(" was left by his wife, but") 
   scriptB$=translate("went on to find love with another...")
  EndIf
  If endFate(page)=13 
   scriptA$=charName$(endChar(page))+translate(" was moved to a mental hospital") 
   scriptB$=translate("after being driven mad by prison life...")
  EndIf
  If endFate(page)=14 
   scriptA$=charName$(endChar(page))+translate(" became a sexual predator and") 
   scriptB$=translate("was soon back behind bars for rape...")
  EndIf
  If endFate(page)=15
   scriptA$=charName$(endChar(page))+translate(" went on a killing spree upon")  
   scriptB$=translate("returning to find his wife having an affair...")
  EndIf
  If endFate(page)=16 
   scriptA$=charName$(endChar(page))+translate(" was accused of sexually abusing") 
   scriptB$=translate("his daughter and swiftly returned to prison...")
  EndIf
  If endFate(page)=17 
   scriptA$=charName$(endChar(page))+translate(" was killed by a speeding car") 
   scriptB$=translate("upon setting foot outside the prison gates...")
  EndIf
  If endFate(page)=18 
   scriptA$=charName$(endChar(page))+translate(" became involved in organized") 
   scriptB$=translate("crime and died during a drive-by shooting...")
  EndIf
  If endFate(page)=19 
   scriptA$=charName$(endChar(page))+translate(" developed a drug problem") 
   scriptB$=translate("and never made anything of his life...")
  EndIf
  If endFate(page)=20
   scriptA$=charName$(endChar(page))+translate(" developed a drug problem") 
   scriptB$=translate("and eventually died of an overdose...")
  EndIf
  If endFate(page)=21 
   scriptA$=charName$(endChar(page))+translate(" continued to study and went on") 
   scriptB$=translate("to become a productive member of society...")
  EndIf
  If endFate(page)=22 
   scriptA$=charName$(endChar(page))+translate(" went on to hold down a decent") 
   scriptB$=translate("job and never committed another crime...")
  EndIf
  If endFate(page)=23 
   scriptA$=charName$(endChar(page))+translate(" managed to find work, but was") 
   scriptB$=translate("soon arrested for killing his boss...")
  EndIf
  If endFate(page)=24 
   scriptA$=charName$(endChar(page))+translate(" was transferred to a more") 
   scriptB$=translate("conservative jail and was soon executed...")
  EndIf
  If endFate(page)=25
   scriptA$=charName$(endChar(page))+translate(" went on to write a best-selling")  
   scriptB$=translate("book about his experiences in prison...")
  EndIf
  If endFate(page)=26 
   scriptA$=charName$(endChar(page))+translate(" became a public speaker and") 
   scriptB$=translate("encouraged kids to avoid a life of crime...")
  EndIf
  If endFate(page)=27 
   scriptA$=charName$(endChar(page))+translate(" became a teacher, but was") 
   scriptB$=translate("later arrested for abusing his students...")
  EndIf
  If endFate(page)=28 
   scriptA$=charName$(endChar(page))+translate(" was recruited by a terrorist") 
   scriptB$=translate("cell and took part in a fatal bombing...")
  EndIf
  If endFate(page)=29 
   scriptA$=charName$(endChar(page))+translate(" was transferred to Guantanamo") 
   scriptB$=translate("Bay after getting involved in terrorism...")
  EndIf
  If endFate(page)=30
   scriptA$=charName$(endChar(page))+translate(" went on to make a movie about") 
   scriptB$=translate("his experiences and became a millionaire...")
  EndIf
  If endFate(page)=31
   scriptA$=charName$(endChar(page))+translate(" took part in a documentary") 
   scriptB$=translate("about the prison and became a TV star...")
  EndIf
  If endFate(page)=32
   scriptA$=charName$(endChar(page))+translate(" got to grips with computers") 
   scriptB$=translate("and went on to become a game designer...")
  EndIf
  If endFate(page)=33
   scriptA$=charName$(endChar(page))+translate(" got to grips with computers") 
   scriptB$=translate("and went on to create a popular website...")
  EndIf
  If endFate(page)=34
   scriptA$=charName$(endChar(page))+translate(" got to grips with computers") 
   scriptB$=translate("and went on to find a cushy job in IT...")
  EndIf
  If endFate(page)=35
   scriptA$=charName$(endChar(page))+translate(" was given his own radio show") 
   scriptB$=translate("and used it to share his experiences...")
  EndIf
  If endFate(page)=36
   scriptA$=charName$(endChar(page))+translate(" used his experience to serve") 
   scriptB$=translate("as a warden after completing his sentence...")
  EndIf
  If endFate(page)=37 
   scriptA$=charName$(endChar(page))+translate(" succumbed to a terminal illness") 
   scriptB$=translate("that he was hiding from his fellow inmates...")
  EndIf
  If endFate(page)=38 
   scriptA$=charName$(endChar(page))+translate(" continued a life of discipline") 
   scriptB$=translate("and became a decorated soldier in the army...")
  EndIf
  If endFate(page)=39 
   scriptA$=charName$(endChar(page))+translate(" continued a life of discipline") 
   scriptB$=translate("in the army, but was killed in battle...")
  EndIf
  If endFate(page)=40
   scriptA$=charName$(endChar(page))+translate(" went on to study medicine") 
   scriptB$=translate("and saved countless lives as a doctor...")
  EndIf
  If endFate(page)=41
   scriptA$=charName$(endChar(page))+translate(" went on to lead an uneventful") 
   scriptB$=translate("life and died in the comfort of his home...")
  EndIf
  If endFate(page)=42
   scriptA$=charName$(endChar(page))+translate(" trained as a chef and vowed") 
   scriptB$=translate("to improve the standard of prison food...")
  EndIf
  If endFate(page)=43
   scriptA$=charName$(endChar(page))+translate(" continued to stay in shape") 
   scriptB$=translate("and went on to become a personal trainer...")
  EndIf
  If endFate(page)=44
   scriptA$=charName$(endChar(page))+translate(" lost faith in human beings") 
   scriptB$=translate("and dedicated his life to animal welfare...")
  EndIf
  If endFate(page)=45
   scriptA$=charName$(endChar(page))+translate(" went on to work with animals,") 
   scriptB$=translate("but was mauled by an ungrateful lion...")
  EndIf
  If endFate(page)=46 
   scriptA$=charName$(endChar(page))+translate("'s vision continued to") 
   scriptB$=translate("deteriorate in prison and left him blind...")
  EndIf
  If endFate(page)=47 
   scriptA$=charName$(endChar(page))+translate(" became wheelchair bound after") 
   scriptB$=translate("breaking his back in a car accident...")
  EndIf
  If endFate(page)=48 
   scriptA$=charName$(endChar(page))+translate(" broke his neck in an accident") 
   scriptB$=translate("and never regained the use of his body...")
  EndIf
  If endFate(page)=49 
   scriptA$=charName$(endChar(page))+translate(" slipped into a coma after an") 
   scriptB$=translate("accident and never regained consciousness...")
  EndIf
  If endFate(page)=50
   scriptA$=charName$(endChar(page))+translate(" tried to climb Mount Everest") 
   scriptB$=translate("with no equipment and fell to his death...")
  EndIf
  If endFate(page)=51
   scriptA$=charName$(endChar(page))+translate(" embarked on an expedition to") 
   scriptB$=translate("the North Pole, but died on the first day...")
  EndIf
  If endFate(page)=52
   scriptA$=charName$(endChar(page))+translate(" became a movie stuntman, but") 
   scriptB$=translate("tragically died during a failed stunt...")
  EndIf
  If endFate(page)=53
   scriptA$=charName$(endChar(page))+translate(" started acting in porn films") 
   scriptB$=translate("and contracted every STD known to man...")
  EndIf
  If endFate(page)=54
   scriptA$=charName$(endChar(page))+translate(" claimed to be the Messiah and") 
   scriptB$=translate("tried to bring peace to The Middle East...")
  EndIf
  If endFate(page)=55
   scriptA$=charName$(endChar(page))+translate(" had an epiphany and embarked") 
   scriptB$=translate("on missionary work in the third world...")
  EndIf
  If endFate(page)=56
   scriptA$=charName$(endChar(page))+translate(" became a millionaire and used") 
   scriptB$=translate("his wealth to adopt impoverished children...")
  EndIf
  If endFate(page)=57 
   scriptA$=charName$(endChar(page))+translate(" moved to another country where") 
   scriptB$=translate("nobody knew of his criminal background...")
  EndIf
  If endFate(page)=58 
   scriptA$=translate("Upon release, ")+charName$(endChar(page))+translate(" forgot where he") 
   scriptB$=translate("lived and was left roaming the streets...")
  EndIf
  If endFate(page)=59 
   scriptA$=charName$(endChar(page))+translate(" failed to adjust to modern") 
   scriptB$=translate("life and became one of the homeless...")
  EndIf
  If endFate(page)=60
   scriptA$=charName$(endChar(page))+translate(" failed to adjust to life on") 
   scriptB$=translate("the outside and soon committed suicide...")
  EndIf 
  If endFate(page)=61
   scriptA$=translate("After being released, ")+charName$(endChar(page))+translate(" was") 
   scriptB$=translate("hunted down and killed by old enemies...")
  EndIf
  If endFate(page)=62
   scriptA$=charName$(endChar(page))+translate(" went on to become a political") 
   scriptB$=translate("activist and lobbied to improve prisons...")
  EndIf
  If endFate(page)=63
   scriptA$=charName$(endChar(page))+translate(" developed an affinity with") 
   scriptB$=translate("nature and ran away to live in the wild...")
  EndIf
  If endFate(page)=64
   scriptA$=charName$(endChar(page))+translate(" successfully escaped from") 
   scriptB$=translate("prison and still hasn't been found...")
  EndIf
  If endFate(page)=65
   scriptA$=charName$(endChar(page))+translate(" tried to escape from the") 
   scriptB$=translate("exercise yard, but was shot dead...")
  EndIf
  If endFate(page)=66
   scriptA$=charName$(endChar(page))+translate(" returned to the wrong house") 
   scriptB$=translate("and brought up someone else's family...")
  EndIf
  If endFate(page)=67 
   scriptA$=charName$(endChar(page))+translate(" went back to live with his") 
   scriptB$=translate("parents and remains grounded to this day...")
  EndIf
  If endFate(page)=68 
   scriptA$=charName$(endChar(page))+translate(" went back to school, but") 
   scriptB$=translate("failed to complete the 1st grade...")
  EndIf
  If endFate(page)=69 
   scriptA$=charName$(endChar(page))+translate(" dedicated his life to sport") 
   scriptB$=translate("and played in a Superbowl final...")
  EndIf
  If endFate(page)=70
   scriptA$=charName$(endChar(page))+translate(" united all the prison") 
   scriptB$=translate("gangs and now rules like a king...")
  EndIf 
  SetFont font(4)
  If GraphicsWidth()<800 Then SetFont font(3)
  If GraphicsWidth()>800 Then SetFont font(5)
  If GraphicsWidth()>1024 Then SetFont font(6)
  OutlineStraight(scriptA$,rX#(x)+90,rY#(520),30,30,30,250,250,250)
  OutlineStraight(scriptB$,rX#(x)+90,rY#(560),30,30,30,250,250,250)
 EndIf
 If promoTim>300 Then page=page+1 : promoTim=125
 ;mask shaky start
 If gotim=<0 Then Loader(translate("Please Wait"),translate("Leaving Prison"))

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;restore sound
If ChannelPlaying(chAtmos)>0 Then StopChannel chAtmos
;free entities
FreeTimer timer	
FreeEntity fader
FreeEntity world
FreeEntity cam 
FreeEntity camPivot
FreeEntity dummy
For cyc=1 To no_lights
 FreeEntity light(cyc)
Next
FreeEntity p(1)
For limb=1 To 40
 If pShadow(1,limb)>0
  FreeEntity pShadow(1,limb)
 EndIf
Next
;leave
If gamEnded=1
 gamName$(slot)=""
 screen=6
Else
 screen=5
EndIf
End Function
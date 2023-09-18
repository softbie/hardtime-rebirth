;//////////////////////////////////////////////////////////////////////////////
;------------------------------ HARD TIME: SCENES -----------------------------
;//////////////////////////////////////////////////////////////////////////////

;---------------------------------------------------------------
;///////////////////// 52. COURT CASE //////////////////////////
;---------------------------------------------------------------
Function CourtCase()
;load setting
Loader(translate("Please Wait"),translate("Preparing Court Case"))
world=LoadAnimMesh("World/Courtroom/Courtroom.3ds")
For count=1 To 10
 EntityTexture FindChild(world,"Crowd"+Dig$(count,10)),tCrowd,Rnd(0,3)
Next
sAtmos=LoadSound("Sound/Ambience/Crowd.wav")
;camera
cam=CreateCamera()
CameraViewport cam,0,0,GraphicsWidth(),GraphicsHeight()
camX#=50 : camY#=30 : camZ#=-80
PositionEntity cam,camX#,camY#,camZ#
camType=10 : camFoc=5
;pivots
camPivot=CreatePivot()
camPivX#=7 : camPivY#=19 : camPivZ#=127
PositionEntity camPivot,camPivX#,camPivY#,camPivZ#
dummy=CreatePivot()
;lighting
AmbientLight 200,200,200
LoadLighting()
;background noise
LoopSound sAtmos
chAtmos=PlaySound(sAtmos)
ChannelVolume chAtmos,0.1
;LOAD CHARACTERS
;calculate cast
no_plays=5
For cyc=1 To no_plays
 pChar(cyc)=0
Next
pChar(1)=gamChar(slot) ;player
pChar(2)=promoAccuser ;accuser
pChar(3)=1 ;player's lawyer
pChar(4)=2 ;accuser's lawyer
pChar(5)=3 ;judge
GenerateCharacter(2,2)
GenerateCharacter(3,3) 
;set locations
pX#(1)=-45 : pY#(1)=2 : pZ#(1)=10 : pA#(1)=-15
pX#(2)=55 : pY#(2)=2 : pZ#(2)=10 : pA#(2)=15
pX#(3)=-65 : pY#(3)=2 : pZ#(3)=7 : pA#(3)=-40
pX#(4)=75 : pY#(4)=2 : pZ#(4)=7 : pA#(4)=40
pX#(5)=7 : pY#(5)=19 : pZ#(5)=127 : pA#(5)=180
;load models
For cyc=1 To no_plays
 p(cyc)=LoadAnimMesh("Characters/Models/Model"+Dig$(charModel(pChar(cyc)),10)+".3ds")
 pSeq(cyc,601)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard01.3ds")
 pSeq(cyc,602)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard02.3ds")
 pSeq(cyc,603)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard03.3ds")
 pSeq(cyc,604)=LoadAnimSeq(p(cyc),"Characters/Sequences/Standard04.3ds") 
 If cyc=<2 And (charInjured(pChar(cyc))>0 Or charHealth(pChar(cyc))<10)
  pSeq(cyc,1)=ExtractAnimSeq(p(cyc),925,965,pSeq(cyc,602)) ;weary
 Else
  randy=Rnd(1,3)
  If randy=1 Then pSeq(cyc,1)=ExtractAnimSeq(p(cyc),0,40,pSeq(cyc,601)) ;standing
  If randy=2 Then pSeq(cyc,1)=ExtractAnimSeq(p(cyc),770,850,pSeq(cyc,604)) ;hands on hips
  If randy=3 Then pSeq(cyc,1)=ExtractAnimSeq(p(cyc),860,940,pSeq(cyc,604)) ;folded arms
 EndIf
 randy=Rnd(1,3)
 If randy=1 Then pSeq(cyc,2)=ExtractAnimSeq(p(cyc),755,835,pSeq(cyc,603)) ;speaking
 If randy=2 Then pSeq(cyc,2)=ExtractAnimSeq(p(cyc),770,850,pSeq(cyc,604)) ;hands on hips
 If randy=3 Then pSeq(cyc,2)=ExtractAnimSeq(p(cyc),860,940,pSeq(cyc,604)) ;folded arms
 pSeq(cyc,3)=ExtractAnimSeq(p(cyc),1215,1255,pSeq(cyc,603)) ;sitting
 ApplyCostume(cyc) 
 pEyes(cyc)=2 : pOldEyes(cyc)=-1
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
 PositionEntity p(cyc),pX#(cyc),pY#(cyc),pZ#(cyc)
 RotateEntity p(cyc),0,pA#(cyc),0
 scaler#=charHeight(pChar(cyc))*0.0025
 If cyc=5 Then scaler#=12*0.0025
 ScaleEntity p(cyc),0.34+scaler#,0.34+scaler#,0.34+scaler#
 If cyc=<4 Then Animate p(cyc),1,Rnd(0.1,0.3),pSeq(cyc,1),0
 If cyc=5 Then Animate p(cyc),1,Rnd(0.1,0.3),pSeq(cyc,3),0
 pState(cyc)=1 : pFoc(cyc)=0
Next
;point light
PointEntity light(1),p(5)
;RESET SITUATION
promoTim=-100 : promoStage=0
promoEffect=0 : promoVerdict=0
For count=1 To 10
 promoReact(count)=0
Next
If gamWarrant(slot)=0 Then promoStage=2 : promoVerdict=2
;calculate fines
promoCash=gamMoney(slot)/5
If promoCash<100 Then promoCash=100
If promoCash>10000 Then promoCash=10000
promoCash=RoundOff(promoCash,100)
;frame ratings
Loader(translate("Please Wait"),translate("Preparing Court Case"))
timer=CreateTimer(30)
SeedRnd(MilliSecs())
;MAIN LOOP
foc=1 : oldfoc=foc
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
    ;speed-up's
    If gotim>15 Then promoTim=promoTim+1 
    If promoTim>50 And keytim=0
     If KeyDown(1) Or KeyDown(28) Or ButtonPressed() Then promoTim=promoTim+100 : keytim=10
    EndIf
    ;leave
    If promoStage=3 And promoTim>10200 Then go=1

    ;THEME FADING
    If gotim>0
     musicVol#=musicVol#-0.0025
     If musicVol#=<0
      If ChannelPlaying(chTheme)>0 Then StopChannel chTheme
      musicVol#=0
     EndIf
     If ChannelPlaying(chTheme)>0 Then ChannelVolume chTheme,musicVol#
    EndIf
	
	;SPEAKING
	For cyc=1 To no_plays
	 ;change animation
	 If cyc=<4
	  If pSpeaking(cyc)=0 And pState(cyc)<>1 Then Animate p(cyc),1,Rnd(0.1,0.3),pSeq(cyc,1),10 : pState(cyc)=1
	  If pSpeaking(cyc) And pState(cyc)<>2 Then Animate p(cyc),1,Rnd(0.25,0.5),pSeq(cyc,2),10 : pState(cyc)=2
	 EndIf
	 ;expressions
	 FacialExpressions(cyc)
	Next
	
	;CAMERA
	Camera()
	
 UpdateWorld

 ;OVERRIDE ANIMATION
 If pFoc(5)>0 Then PointHead(5,pLimb(pFoc(5),1)) 

 Next  
 RenderWorld 1

 ;DISPLAY
 ;reset expressions
 oldFoc=camFoc
 For cyc=1 To no_plays
  pSpeaking(cyc)=0 
 Next
 ;introduce widescreen
 If promoTim>0 And promoTim<10000 
  y#=60
  If promoTim=<25 Then y#=PercentOf#(60,promoTim*4) 
  If promoTim=>9975 Then y#=PercentOf#(60,(10000-promoTim)*4) 
  Color 0,0,0 : Rect rX#(0),rY#(0),rX#(800),rY#(y#),1
  y#=480
  If promoTim=<25 Then y#=600-PercentOf#(120,promoTim*4) 
  If promoTim=>9975 Then y#=600-PercentOf#(120,(10000-promoTim)*4) 
  Color 0,0,0 : Rect rX#(0),rY#(y#),rX#(800),rY#(600),1
 EndIf 
 ;determine font
 SetFont font(2)
 If GraphicsWidth()<800 Then SetFont font(3)
 If GraphicsWidth()>800 Then SetFont font(5)
 If GraphicsWidth()>1024 Then SetFont font(6)
 ;OPENING LINE
 If promoStage=0 And promoTim>25 And promoTim<325
  Speak(5,2) : pFoc(5)=2
  Outline(translate("We're gathered to hear the case against Prisoner"),rX#(400),rY#(520),30,30,30,250,250,250)
  Outline(CellName$(pChar(1))+". "+translate("So, #NAME#, what's the story?", "#NAME#", charName$(pChar(2))),rX#(400),rY#(560),30,30,30,250,250,250)
  If promoTim>125 And promoReact(1)=0 Then PlaySound sMurmur : promoReact(1)=1
 EndIf
 ;0. INTRO
 If gamWarrant(slot)=0
  If promoStage=2
   If promoTim>25 And promoTim<325
    Speak(5,3) : pFoc(5)=1
    pEyes(1)=1 : pEyes(2)=3 : pEyes(3)=1 : pEyes(4)=3 
    Outline(translate("#NAME#, this court has seen you accused", "#NAME", charName$(gamChar(slot))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("#CRIME# and heard your defence...", "#CRIME#", Lower$(textCrime$(charCrime(gamChar(slot))))),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>350 And promoTim<650
    Speak(5,2) : pFoc(5)=0
    Outline(translate("The trial has gone on quite long enough, so i'd"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("like to take this moment to deliver my verdict..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>700 And promoTim<1000
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 Then PlaySound sJury(Rnd(1,2)) : PlaySound sPaper : statTim(6)=-100 : promoEffect=1 
    Outline(translate("I find you GUILTY and sentence you to"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("#COUNT# days in Southtown Correctional Facility!", "#COUNT#", charSentence(gamChar(slot))),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>1025 And promoTim<1325
    Speak(5,1) : pFoc(5)=1
    Outline(translate("That may not be a 'long' time, but it will be"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("HARD time! You'll be lucky if you survive..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>1350 And promoTim<1650
    Speak(5,2) : pFoc(5)=2
    Outline(translate("I'm now handing you over to the wardens, and"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("they'll help you settle into your new home..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>1675 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;1. DISSENT
 If gamWarrant(slot)=1
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("#NAME# seems to have a problem with authority!", "#NAME#", CellName$(pChar(1))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("He disrupts the system by ignoring my orders..."),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I'm not out of control - he's a control freak!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Nothing the prisoners do is ever enough for him..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("#NAME#, i'm not here to do your job!", "#NAME#", charName$(pChar(2))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("You should be able to handle your own prisoners..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("I've got my own rules to uphold - i haven't got"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("time for yours! Just try to be more tolerant..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("When the wardens tell you to do something, you"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("do it! You're in no position to disagree..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+1
     promoEffect=1
    EndIf 
    Outline(translate("I'm sentencing you to another day to make up for"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("the one you've wasted! Take it as a warning..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;2. GANG MEMBERSHIP
 If gamWarrant(slot)=2
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(CellName$(pChar(1))+translate(" is a known member of '")+textGang$(charGang(gamChar(slot)))+"';",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("a gang which conspires against the prison system!"),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I've simply found my place in a community!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Our only agenda is to support each other..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You've got quite an imagination, #NAME#!", "#NAME#", charName$(pChar(2))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("This supposed 'gang' shouldn't affect your job..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,2) : pFoc(5)=2
    Outline(translate("On the contrary, relationships can offer stability."),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Perhaps you need to find some friends of your own!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("I know that gang culture offers sanctuary, but"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("it comes at a deadly price and must be avoided!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : PlaySound sCash : statTim(7)=-100
     If gamMoney(slot)>0 Then gamMoney(slot)=0
     ChangeGang(gamChar(slot),0)
     promoEffect=1
    EndIf 
    Outline(translate("I have no choice but to seize your bank account"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and terminate your affiliation with the gang..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;3. TRYING TO ESCAPE
 If gamWarrant(slot)=3
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("#NAME# was caught out of his cell during", "#NAME#", CellName$(pChar(1))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("lockdown! He was obviously trying to escape..."),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I wasn't trying to escape! I was making my way"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("back to my cell when this guy grabbed me..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You've got quite an imagination, #NAME#!", "#NAME#", charName$(pChar(2))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("An inmate can't just 'stroll' out of the prison..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,2) : pFoc(5)=2
    Outline(translate("I see no evidence of an escape attempt here."),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("All you had to do was usher him to his cell..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("You've got no reason to be outside of your block"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("during lockdown! All it does is arouse suspicion..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100 
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+1
     PlaySound sCash : statTim(7)=-100
     If gamMoney(slot)>0 Then gamMoney(slot)=0
     promoEffect=1
    EndIf 
    Outline(translate("I have no choice but to seize your bank account"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and add another day for the one you've wasted..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;4. ILLEGAL ITEM
 If gamWarrant(slot)=4
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    If weapHabitat(weapType(gamItem(slot)))=0
     Outline(CellName$(pChar(1))+translate(" was seen wielding a ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
     Outline(translate("A prisoner has no right to such weapons..."),rX#(400),rY#(560),30,30,30,250,250,250)
    EndIf
    If weapHabitat(weapType(gamItem(slot)))>0
     Outline(CellName$(pChar(1))+translate(" was caught carrying a ")+Lower$(weapName$(weapType(gamItem(slot)))),rX#(400),rY#(520),30,30,30,250,250,250)
     Outline(translate("God knows what he had planned..."),rX#(400),rY#(560),30,30,30,250,250,250)
    EndIf
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I found that #NAME#, and was on my way to", "#NAME#", Lower$(weapName$(weapType(gamItem(slot))))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("report it to the wardens when they grabbed me!"),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(charName$(pChar(2))+translate(", there's no evidence to suggest"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("that this #NAME# was used for any crime!", "#NAME#", Lower$(weapName$(weapType(gamItem(slot))))),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("I can't punish this man over your suspicions!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Simply confiscate the item and leave it at that..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("A prisoner has no business carrying a"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(Lower$(weapName$(weapType(gamItem(slot))))+translate("! It just looks suspicious..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100 
     randy=Rnd(1,5)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("a day") Else sentence$=randy+translate(" days")
     promoEffect=1
    EndIf 
    Outline(translate("I'd like to nip this in the bud by adding"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(sentence$+translate(" to your sentence. Take it as a warning!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;5. DRUG ABUSE
 If gamWarrant(slot)=5
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("This junkie has descended into a life of drug abuse!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("He cares more about his fix than rehabilitation..."),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I wasn't doing anything wrong! I was simply making"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("use of the medication that the prison provides..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You've got quite an imagination, #NAME#!", "#NAME#", charName$(pChar(2))),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("There's a difference between 'using' and 'abusing'..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,2) : pFoc(5)=2
    Outline(translate("I can't blame this man for taking the edge off"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("prison life! Just try to be more compassionate..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("That medication is there to be used - not abused!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("You've done nothing to help your rehabilitation..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100 
     randy=Rnd(1,5)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("a day") Else sentence$=randy+translate(" days")
     PlaySound sCash : statTim(7)=-100
     gamMoney(slot)=gamMoney(slot)-promoCash
     If charCrime(gamChar(slot))<3 Then charCrime(gamChar(slot))=3
     promoEffect=1
    EndIf 
    Outline(translate("I'm fining you $")+GetFigure$(promoCash)+translate(", and adding ")+sentence$+translate(" to"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("make up for the ones you've spent in a stupor!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;6. TRADING ITEMS
 If gamWarrant(slot)=6
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(CellName$(pChar(1))+translate(" was caught trading ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("He's turning prison life into a business..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I wasn't 'trading' that ")+Lower$(weapName$(weapType(gamItem(slot))))+translate("! It was a"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("simple exchange of resources between friends..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You've got quite an imagination, ")+charName$(pChar(2))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("This man isn't trying to run a business empire..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,2) : pFoc(5)=2
    Outline(translate("These men have already lost their freedom, and"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("no amount of possessions can't change that fact..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("You're not supposed to 'profit' from the prison"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("experience - you're supposed to be punished by it!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100 
     randy=Rnd(1,5)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     PlaySound sCash : statTim(7)=-100
     If gamMoney(slot)>0 Then gamMoney(slot)=0
     If charCrime(gamChar(slot))<4 Then charCrime(gamChar(slot))=4
     promoEffect=1
    EndIf 
    Outline(translate("I sentence you to an extra ")+sentence$+translate(" to think about that,"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and i must also seize the fortune that you've amassed..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;7. STEALING
 If gamWarrant(slot)=7
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(CellName$(pChar(1))+translate(" was caught stealing a ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("The thief can't keep his hands to himself..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I didn't 'steal' that ")+Lower$(weapName$(weapType(gamItem(slot))))+translate("! I was just"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("trying to take back what was mine..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(charName$(pChar(2))+translate(", it's not my job to share"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("out the toys amongst the children!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("If an item comes between your prisoners, just"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("confiscate the damn thing and leave it at that..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("You're on a slippery slope, ")+CellName$(pChar(1))+translate("! I see"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("you sinking back into a life of crime..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100 
     randy=Rnd(1,5)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     PlaySound sCash : statTim(7)=-100
     gamMoney(slot)=gamMoney(slot)-weapValue(weapType(gamItem(slot)))
     If charCrime(gamChar(slot))<6 Then charCrime(gamChar(slot))=6
     promoEffect=1
    EndIf 
    Outline(translate("Perhaps another ")+sentence$+translate(" will straighten you out, and"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("i also order you to pay $")+GetFigure(weapValue(weapType(gamItem(slot))))+translate(" for a new ")+Lower$(weapName$(weapType(gamItem(slot))))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;8. ASSAULTING ANOTHER INMATE
 If gamWarrant(slot)=8
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(CellName$(pChar(1))+translate(" is a particularly aggressive inmate and"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("had to be restrained from harming the others!"),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I was forced to do the warden's job! It's anarchy"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("in there, so i constantly have to defend myself..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(charName$(pChar(2))+translate(", i'm not here to do your job!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("You should be able to defuse the odd scuffle..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,2) : pFoc(5)=2
    Outline(translate("I see no reason to punish this man over a bit of"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("roughhousing! Simply keep a close eye on him..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("Prison is a place of learning - not fighting!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Violent behaviour doesn't show much progress..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     randy=Rnd(1,5)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     If charCrime(gamChar(slot))<8 Then charCrime(gamChar(slot))=8
     promoEffect=1
    EndIf 
    Outline(translate("Since you obviously need more time to think about"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("that, i'm adding another ")+sentence$+translate(" to your sentence!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;9. ASSAULTING A WARDEN
 If gamWarrant(slot)=9
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(CellName$(pChar(1))+translate(" has become so aggressive that even"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("us wardens aren't safe from his outbursts!"),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I never tried to hurt anybody! I was just going"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("about my day and caught this guy in a bad mood..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(charName$(pChar(2))+translate(", i'm not here to do your job!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("You should be able to control your own prisoners..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("The only thing that has taken a 'beating' here"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("is your ego! You should give as good as you get..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("The wardens are there for the safety of the"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("prisoners and shouldn't have to take their abuse!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     randy=Rnd(3,7)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$="1 day" Else sentence$=randy+" days"
     PlaySound sCash : statTim(7)=-100
     gamMoney(slot)=gamMoney(slot)-promoCash
     If charCrime(gamChar(slot))<8 Then charCrime(gamChar(slot))=8
     promoEffect=1
    EndIf 
    Outline(translate("Maybe another ")+sentence$+translate(" will calm you down, and"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("i also order you to pay $")+GetFigure$(promoCash)+translate(" in damages..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;10. ASSAULT WITH WEAPON
 If gamWarrant(slot)=10
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("This animal was caught using a ")+Lower$(weapName$(weapType(gamItem(slot))))+translate(" as a"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("weapon! He intended to do some serious damage..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I didn't hurt anybody with that ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("I just happened to be holding it at the time..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("Just because a man is carrying a ")+Lower$(weapName$(weapType(gamItem(slot))))+translate(","),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("it doesn't mean he plans to use it as a weapon!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("Stop being so melodramatic, and simply confiscate"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("such items before the situation turns violent..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("You shouldn't be fighting at all - let alone with"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("weapons! That ")+Lower$(weapName$(weapType(gamItem(slot))))+translate(" could've killed someone..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100 
     randy=Rnd(5,10)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     promoEffect=1
    EndIf 
    Outline(translate("This is extremely disturbing behaviour, and i"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("sentence you to an extra ")+sentence$+translate(" to address it..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;11. GRIEVOUS BODILY HARM
 If gamWarrant(slot)=11
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("This animal beat ")+charName$(gamVictim(slot))+translate(" so badly"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("that the poor man lost ")+DescribeLimb$(gamVictim(slot))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I never intended to hurt ")+charName$(gamVictim(slot))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("He lost ")+DescribeLimb$(gamVictim(slot))+translate(" when he fell on the ground..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You're being a drama queen, ")+charName$(pChar(2))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(charName$(gamVictim(slot))+translate(" was never in any serious danger..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("It sounds like nothing more than an accident."),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Just try to deal with it better next time..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("It's a miracle that ")+charName$(gamVictim(slot))+translate(" survived!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("You're evidently a threat to your fellow inmates..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     randy=Rnd(5,10)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     PlaySound sCash : statTim(7)=-100
     gamMoney(slot)=gamMoney(slot)-1000
     If charCrime(gamChar(slot))<11 Then charCrime(gamChar(slot))=11
     promoEffect=1
    EndIf 
    Outline(translate("You deserve to be behind bars for another ")+sentence$+translate(","),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and i also order you to pay $")+GetFigure$(1000)+translate(" in medical fees..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;12. ATTEMPTED MURDER
 If gamWarrant(slot)=12
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("This animal viciously attacked ")+charName$(gamVictim(slot)),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and left the poor man fighting for his life!"),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I never intended to hurt ")+charName$(gamVictim(slot))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("It was a disagreement that got out of hand..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You're being a drama queen, ")+charName$(pChar(2))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(charName$(gamVictim(slot))+translate(" was never in any serious danger..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("It sounds like nothing more than an accident."),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("Just try to deal with it better next time..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("It's a miracle that ")+charName$(gamVictim(slot))+translate(" survived!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("You're evidently a threat to your fellow inmates..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     randy=Rnd(5,10)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     PlaySound sCash : statTim(7)=-100
     gamMoney(slot)=gamMoney(slot)-1000
     If charCrime(gamChar(slot))<12 Then charCrime(gamChar(slot))=12
     promoEffect=1
    EndIf 
    Outline(translate("You deserve to be behind bars for another ")+sentence$+translate(","),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and i order you to pay $")+GetFigure$(1000)+translate(" in medical fees..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;13. MURDER
 If gamWarrant(slot)=13
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(translate("This psycho viciously attacked ")+charName$(gamVictim(slot)),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("and succeeded in taking the poor man's life!"),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I didn't kill ")+charName$(gamVictim(slot))+translate("! I was there when it"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("happened, but i wasn't responsible for his death..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(translate("You just wanted a scapegoat, ")+charName$(pChar(2))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("A fatality doesn't look good on anybody's record..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("The simple fact is that ")+charName$(gamVictim(slot))+translate(" would"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("still be alive if you were doing your job!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(CellName$(pChar(1))+translate(", you murdered ")+charName$(gamVictim(slot))+translate(" because"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("you have absolutely no respect for human life!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     randy=Rnd(7,14)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$="1 day" Else sentence$=randy+" days"
     If charCrime(gamChar(slot))<14 Then charCrime(gamChar(slot))=14
     promoEffect=1
    EndIf 
    Outline(translate("You're a menace to society, and i don't want"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("to see you released for another ")+sentence$+translate(" yet..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;14. SERIAL MURDER
 If gamWarrant(slot)=14
  ;statements
  If promoStage=0
   If promoTim>350 And promoTim<650
    Speak(2,1)
    Outline(charName$(gamVictim(slot))+translate("'s recent death is just one of"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("many that can be traced back to this psycho!"),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>450 And promoReact(2)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(2)=1
   EndIf
   If promoTim>675 And promoTim<975
    Speak(1,1)
    Outline(translate("I didn't kill ")+charName$(gamVictim(slot))+translate(" or anybody else!"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("In fact, i was there to help save their lives..."),rX#(400),rY#(560),30,30,30,250,250,250)
    If promoTim>775 And promoReact(3)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(3)=1
   EndIf
   If promoTim>1000 Then promoStage=1 : promoTim=300
  EndIf
  ;positive verdict
  If promoStage=2 And promoVerdict=1
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=2
    Outline(charName$(pChar(2))+translate(", what were you doing while"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("all of these people were dropping dead?!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoStage=2 And promoTim>650 And promoTim<950 And promoVerdict=1
    Speak(5,1) : pFoc(5)=2
    Outline(translate("The simple fact is that these supposed victims"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("would all be alive if you were doing your job!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
  ;negative verdict
  If promoStage=2 And promoVerdict=2
   If promoTim>325 And promoTim<625
    Speak(5,1) : pFoc(5)=1
    Outline(translate("One death on your hands can be explained away,"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("but several makes you a cold-blooded killer!"),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>650 And promoTim<950
    Speak(5,1) : pFoc(5)=1
    If promoEffect=0 
     PlaySound sPaper : statTim(6)=-100
     randy=Rnd(10,20)
     charSentence(gamChar(slot))=charSentence(gamChar(slot))+randy
     If randy=1 Then sentence$=translate("1 day") Else sentence$=randy+translate(" days")
     If charCrime(gamChar(slot))<14 Then charCrime(gamChar(slot))=14
     promoEffect=1
    EndIf 
    Outline(translate("You're an evil human being, and i wouldn't"),rX#(400),rY#(520),30,30,30,250,250,250)
    Outline(translate("be surprised if you never leave this place..."),rX#(400),rY#(560),30,30,30,250,250,250)
   EndIf
   If promoTim>950 Then promoStage=3 : promoTim=9975 : camFoc=1
  EndIf
 EndIf
 ;INTERRUPT
 If promoStage=1
  If promoTim>325 And promoTim<625
   Speak(5,1) : pFoc(5)=0
   Outline(translate("OK, you can both stop bickering! I'll settle this."),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Just give me a minute to think over the facts..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>650 And promoTim<950
   Speak(5,2) : pFoc(5)=0
   promoVerdict=Rnd(1,2)
   randy=Rnd(0,110-charIntelligence(gamChar(slot)))
   If randy=<5 Then promoVerdict=1
   Outline(translate("After hearing each of your statements and reviewing"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("the evidence, this court rules in favour of..."),rX#(400),rY#(560),30,30,30,250,250,250)
   If promoTim>800 And promoReact(4)=0 Then PlaySound sMurmur : promoReact(4)=1
  EndIf
  If promoTim>1050 And promoTim<1200 And promoVerdict=1
   Speak(promoVerdict,3) : pEyes(1)=3 : pEyes(2)=1 : pEyes(3)=3 : pEyes(4)=1
   If promoReact(5)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(5)=1
   Outline(translate("...Prisoner ")+CellName$(pChar(promoVerdict))+"!",rX#(400),rY#(535),30,30,30,250,Rnd(150,220),100)
  EndIf
  If promoTim>1050 And promoTim<1200 And promoVerdict=2
   Speak(promoVerdict,3) : pEyes(1)=1 : pEyes(2)=3 : pEyes(3)=1 : pEyes(4)=3
   If promoReact(5)=0 Then PlaySound sJury(Rnd(1,2)) : promoReact(5)=1
   Outline("..."+charName$(pChar(promoVerdict))+"!",rX#(400),rY#(535),30,30,30,250,Rnd(150,220),100)
  EndIf
  If promoTim>1200 Then promoStage=2 : promoTim=300 : camFoc=5
 EndIf
 ;take photo
 If camFoc>0 And camFoc=oldFoc
  If pSpeaking(camFoc)>0 And ReachedCord(camX#,camZ#,camTX#,camTZ#,5) And ReachedCord(camPivX#,camPivZ#,camPivTX#,camPivTZ#,5)
   If charSnapped(pChar(camFoc))=0 Then TakePhoto(pChar(camFoc))
   If camFoc=<2 And charSnapped(camFoc)=0 Then TakePhoto(camFoc) 
  EndIf
 EndIf
 ;mask shaky start
 If gotim=<0 Then Loader(translate("Please Wait"),translate("Preparing Court Case"))

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()

Wend
;restore sound
If ChannelPlaying(chAtmos)>0 Then StopChannel chAtmos
;free entities
FreeTimer timer	
FreeEntity world
FreeEntity cam 
FreeEntity camPivot
FreeEntity dummy
For cyc=1 To no_lights
 FreeEntity light(cyc)
Next
For cyc=1 To no_plays
 FreeEntity p(cyc)
Next
;verdict effects
If gamWarrant(slot)>0
 RemovePromo(28) : RemovePromo(29)
 RemovePromo(88) : RemovePromo(89) : RemovePromo(90)
 If gamMission(slot)=19 Then CompleteMission(1)
 ChangeRelationship(pChar(1),pChar(2),-1)
 If promoVerdict=1
  charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+10
  charReputation(gamChar(slot))=charReputation(gamChar(slot))-1 : statTim(4)=-50
  charPromo(pChar(2),pChar(1))=28
 EndIf
 If promoVerdict=2
  charHappiness(gamChar(slot))=charHappiness(gamChar(slot))/2
  charReputation(gamChar(slot))=charReputation(gamChar(slot))+1 : statTim(4)=50
  charPromo(pChar(2),pChar(1))=29
 EndIf 
 For v=1 To no_chars
  If v<>gamChar(slot) And charRole(v)=0 And charPromo(v,gamChar(slot))=0
   If promoVerdict=1 And FriendlyChars(v,gamChar(slot))=0 Then charPromo(v,gamChar(slot))=89
   If promoVerdict=2 And FriendlyChars(v,gamChar(slot)) Then charPromo(v,gamChar(slot))=88
   If promoVerdict=2 And charGang(gamChar(slot))=6 And charGang(v)=6 Then charPromo(v,gamChar(slot))=90
  EndIf
 Next
EndIf
;relocate to home block
charX#(gamChar(slot))=0 : charZ#(gamChar(slot))=-325
charY#(gamChar(slot))=20 : charA#(gamChar(slot))=0
charLocation(gamChar(slot))=TranslateBlock(charBlock(gamChar(slot)))
gamLocation(slot)=charLocation(gamChar(slot))
camX#=-100 : camY#=50 : camZ#=-200
camPivX#=camX# : camPivY#=camY# : camPivZ#=camZ#
charBreakdown(gamChar(slot))=0
;initiation in hall
If gamWarrant(slot)=0
 charLocation(gamChar(slot))=9
 gamLocation(slot)=charLocation(gamChar(slot))
 charX#(gamChar(slot))=-260 : charZ#(gamChar(slot))=-240 : charA#(gamChar(slot))=160
 charLocation(pChar(2))=9
 charX#(pChar(2))=-250 : charZ#(pChar(2))=-260 : charA#(pChar(2))=135
 charPromo(pChar(2),gamChar(slot))=71 
 camX#=0 : camY#=75 : camZ#=0 
 camPivX#=camX# : camPivY#=camY# : camPivZ#=camZ#
EndIf
;get rid of offending item
If gamWarrant(slot)=4 Or gamWarrant(slot)=6 Or gamWarrant(slot)=7 Or gamWarrant(slot)=10
 If gamItem(slot)>0 And FindCarrier(gamItem(slot))=0
  weapLocation(gamItem(slot))=0
  gamItem(slot)=0
 EndIf
EndIf
;proceed
gamWarrant(slot)=0
screen=50
End Function

;----------------------------------------------------------------------
;/////////////////////////// CRIME PROMOS /////////////////////////////
;----------------------------------------------------------------------
Function CrimePromos(cyc,v,y#)
 ;101. ARRESTED FOR DISSENT
 If gamPromo=101
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("We've had enough of your attitude, ")+CellName$(pChar(v))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Perhaps a judge will teach you some respect..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;102. ARRESTED FOR CONSPIRACY
 If gamPromo=102
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("You joined the wrong gang, ")+CellName$(pChar(v))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(textGang$(charGang(pChar(v)))+translate(" are under investigation..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;103. ARRESTED FOR TRYING TO ESCAPE
 If gamPromo=103
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("Your little adventure is over, Magellan!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("Let's see you explain this to a judge..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;104. ARRESTED FOR ILLEGAL ITEM
 If gamPromo=104
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("We caught you red-handed, ")+CellName$(pChar(v))+translate("! You're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("under arrest for carrying a ")+Lower$(weapName$(weapType(gamItem(slot))))+translate("..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;105. ARRESTED FOR DRUG ABUSE
 If gamPromo=105
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("Sorry to blow your high, ")+CellName$(pChar(v))+translate(", but"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("that poison won't help your rehabilitation!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;106. ARRESTED FOR ILLEGAL TRADING
 If gamPromo=106
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("Business is over, ")+CellName$(pChar(v))+translate("! You're under"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("arrest for trading ")+Lower$(weapName$(weapType(gamItem(slot))))+translate("s..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;107. ARRESTED FOR STEALING
 If gamPromo=107
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("I saw you take that ")+Lower$(weapName$(weapType(gamItem(slot))))+translate(", ")+CellName$(pChar(v))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're under arrest for stealing..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;108. ARRESTED FOR ASSAULT
 If gamPromo=108
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("You've thrown your last punch, ")+CellName$(pChar(v))+translate("! You're"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("under arrest for assaulting another inmate..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;109. ARRESTED FOR ASSAULTING A WARDEN
 If gamPromo=109
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("You just picked the wrong fight, ")+CellName$(pChar(v))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're under arrest for assaulting a warden..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;110. ARRESTED FOR ASSAULT WITH WEAPON
 If gamPromo=110
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("You could've killed someone with that ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're under arrest for assault with a weapon..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;111. ARRESTED FOR ATTEMPTED MURDER
 If gamPromo=111
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(charName$(gamVictim(slot))+translate(" is scarred for life because of you!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're under arrest for grievous bodily harm..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;112. ARRESTED FOR ATTEMPTED MURDER
 If gamPromo=112
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("You almost killed ")+charName$(gamVictim(slot))+translate(", you animal!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're under arrest for attempted murder..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;113. ARRESTED FOR MURDER
 If gamPromo=113
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(translate("You killed ")+charName$(gamVictim(slot))+translate(", you animal!"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're going down for murder..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;114. ARRESTED FOR SERIAL KILLING
 If gamPromo=114
  If promoTim>25 And promoTim<325
   Speak(cyc,1) : promoAccuser=pChar(cyc) : DropWeapon(v,0)
   Outline(charName$(gamVictim(slot))+translate(" is your last victim, ")+CellName$(pChar(v))+"!",rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("You're going down for serial murder..."),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;//////////////////////////// ALARMS ////////////////////////////
 ;121. WANTED FOR DISSENT
 If gamPromo=121
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is under"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("arrest for disobeying the prison rules!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;122. WANTED FOR CONSPIRACY
 If gamPromo=122
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("for engaging in gang activity!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;123. WANTED FOR ESCAPE
 If gamPromo=123
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is under"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("arrest for conspiring to escape!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;124. WANTED FOR ILLEGAL ITEM
 If gamPromo=124
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is under"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("arrest for carrying a ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;125. WANTED FOR DRUG ABUSE
 If gamPromo=125
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("wanted for drug abuse!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;126. WANTED FOR ILLEGAL TRADING
 If gamPromo=126
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is under"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("arrest for trading ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;127. WANTED FOR ILLEGAL TRADING
 If gamPromo=127
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("for stealing a ")+Lower$(weapName$(weapType(gamItem(slot))))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;128. WANTED FOR ASSAULT
 If gamPromo=128
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("for assaulting another inmate!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;129. WANTED FOR ASSAULTING A WARDEN
 If gamPromo=129
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is under"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("arrest for assaulting a warden!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf 
 ;130. WANTED FOR ASSAULT WITH WEAPON
 If gamPromo=130
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("for using a ")+Lower$(weapName$(weapType(gamItem(slot))))+translate(" as a weapon!"),rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;131. WANTED FOR GRIEVIOUS BODILY HARM
 If gamPromo=131
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted for"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("mutilating the body of ")+charName$(gamVictim(slot))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;132. WANTED FOR ATTEMPTED MURDER
 If gamPromo=132
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted for"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("the attempted murder of ")+charName$(gamVictim(slot))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
 ;133. WANTED FOR MURDER
 If gamPromo=133 Or gamPromo=134
  If promoEffect=0 Then ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1) : promoEffect=1
  If promoTim>25 And promoTim<325
   Speak(cyc,1)
   Outline(translate("ATTENTION! Prisoner ")+CellName$(gamChar(slot))+translate(" is wanted for"),rX#(400),rY#(520),30,30,30,250,250,250)
   Outline(translate("the murder of ")+charName$(gamVictim(slot))+"!",rX#(400),rY#(560),30,30,30,250,250,250)
  EndIf
  If promoTim>325 And promoTim<9975 Then promoTim=9975 : promoUsed(gamPromo)=1
 EndIf
End Function
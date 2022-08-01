;-------------------------------------------------------------------------
;/////////////////////////////// HARD TIME ///////////////////////////////
;-------------------------------------------------------------------------
;~~~~~~~~~~~~~~~~~~~~~ Copyright © Mat Dickie 2007 ~~~~~~~~~~~~~~~~~~~~~~~
;~~~~~~~~~~ This program may not be re-released under any other ~~~~~~~~~~
;~~~~~~~ identity or sold commercially without express permission. ~~~~~~~
;~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

;FILE STRUCTURE
SeedRnd(MilliSecs())
AppTitle "Hard Time"              
Include "Texts.bb"
Include "Values.bb"
Include "Data.bb"
Include "Functions.bb"
Include "Menus.bb"
Include "Editor.bb"
Include "World.bb"
Include "Weapons.bb"
Include "Particles.bb"
Include "Players.bb"
Include "Anims.bb"
Include "Moves.bb"
Include "AI.bb"
Include "Promos.bb"
Include "Crimes.bb"
Include "Missions.bb"
Include "Credits.bb"

;INITIATE ENGINE 
LoadOptions()
ChangeResolution(optRes,0)
SetBuffer BackBuffer()
AutoMidHandle True
EnableDirectInput True
SeedRnd(MilliSecs())   

;LOADING PROCESS
;intro
Intro()
;load media
LoadImages()
LoadTextures()
LoadWeaponData()

;SCREEN MANAGEMENT
SeedRnd(MilliSecs())
screen=1
Repeat
 ;load screen
 LoadScreen(screen)
 ;get-out clause
 If KeyDown(56) And KeyDown(45) Then End
Until screen=0
End 

;SCREEN ACCESS
Function LoadScreen(request)
 ;main menus
 If request=1 Then MainMenu()
 If request=2 Then Options()
 If request=3 Then RedefineKeys()
 If request=4 Then RedefineGamepad()
 If request=5 Then SlotSelect()
 If request=6 Then Credits()
 If request=7 Then Outro()
 If request=8 Then EditSelect()
 ;3D scenes
 If request=50 Then Gameplay()
 If request=51 Then Editor()
 If request=52 Then CourtCase()
 If request=53 Then Ending()
End Function

;--------------------------------------------------------------------------
;/////////////////////////// 50. GAMEPLAY /////////////////////////////////
;--------------------------------------------------------------------------
Function Gameplay()
;adjust resolution
ChangeResolution(optRes,1)
;load location
Loader("Please Wait","Loading "+textLocation$(gamLocation(slot)))
LoadWorld()
;load atmosphere
Loader("Please Wait","Loading Atmosphere")
LoadAtmos()
;background noise
LoopSound sAtmos
chAtmos=EmitSound(sAtmos,cam)
ChannelVolume chAtmos,0.3
If gamLocation(slot)=6 Or gamLocation(slot)=8 Or gamLocation(slot)=9
 ChannelVolume chAtmos,AreaPopulation(gamLocation(slot),-1)*0.015
EndIf
;load players
For cyc=1 To optPlayLim
 pChar(cyc)=0
Next
no_plays=0
For char=1 To no_chars
 If charLocation(char)=gamLocation(slot) And no_plays<optPlayLim
  no_plays=no_plays+1
  pChar(no_plays)=char
 EndIf
Next
LoadPlayers()
;load weapons
Loader("Please Wait","Loading Weapons")
If gamLocation(slot)=10 Then PrepareCreations()
LoadWeapons()
LoadBullets()
;assign weapons to characters
For cyc=1 To no_plays
 If charWeapon(pChar(cyc))>0 Then AttainWeapon(cyc,charWeapon(pChar(cyc)))
Next
;load particles
If optFX>0
 Loader("Please Wait","Loading Effects")
 no_particles=500 
 If optFX=1 Then no_particles=no_particles/2
 LoadParticles()
EndIf
;load pools
If optGore=>2
 no_pools=50 
 If optFX=<1 Then no_pools=no_pools/2
 LoadPools()
EndIf
;fading in/out
fader=CreateSprite()
ScaleSprite fader,20,20
SpriteViewMode fader,1
fadeAlpha#=0 : fadeTarget#=0
EntityAlpha fader,fadeAlpha#
EntityColor fader,0,0,0
;reset status
gamPause=0 : gamDoor=0
gamPromo=0 : promoTim=0
If GetBlock(gamLocation(slot))>0 Then gamEscape(slot)=0
gamSpeed(slot)=1
;enable collisions
UpdateWorld
SetCollisions()
;frame rating
SeedRnd(MilliSecs())
Loader("Please Wait","Finalizing World")
timer=CreateTimer(30)
;MAIN LOOP
zoom#=1.0
go=0 : gotim=-50 : keytim=20
While go=0

 Cls
 frames=WaitTimer(timer)
 For framer=1 To frames
	
  ;COUNTERS
  keytim=keytim-1
  If keytim<1 Then keytim=0
  ;stat highlighters
  If gotim>0 And gamPromo=0
   For count=1 To 10
    If statTim(count)<0 Then statTim(count)=statTim(count)+1
    If statTim(count)>0 Then statTim(count)=statTim(count)-1  
   Next
  EndIf
	
  ;PORTAL
  gotim=gotim+1
  If gotim=0 Then ProduceSound(cam,sDoor(3),22050,1)
  If gotim>40 And keytim=0    
   If KeyDown(1) And pAnim(gamPlayer(slot))<20 And gamPromo=0 Then go=-1   
  EndIf
  
  ;THEME FADING
  If gotim>0
   musicVol#=musicVol#-0.0025
   If musicVol#=<0
    If ChannelPlaying(chTheme)>0 Then StopChannel chTheme
    musicVol#=0
   EndIf
   If ChannelPlaying(chTheme)>0 Then ChannelVolume chTheme,musicVol#
  EndIf

  ;FIDDLES
  If gotim>40 And keytim=0
   ;decrease attributes
   If KeyDown(12)
    If KeyDown(18) Then pHealth(gamPlayer(slot))=pHealth(gamPlayer(slot))-1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(35) Then charHappiness(gamChar(slot))=charHappiness(gamChar(slot))-1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(31) Then charStrength(gamChar(slot))=charStrength(gamChar(slot))-1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(30) Then charAgility(gamChar(slot))=charAgility(gamChar(slot))-1 : PlaySound sMenuBrowse : keytim=3 
    If KeyDown(23) Then charIntelligence(gamChar(slot))=charIntelligence(gamChar(slot))-1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(19) Then charReputation(gamChar(slot))=charReputation(gamChar(slot))-1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(50) Then gamMoney(slot)=gamMoney(slot)-10 : PlaySound sMenuBrowse : keytim=5
    If KeyDown(207) Then charSentence(gamChar(slot))=charSentence(gamChar(slot))-1 : PlaySound sMenuBrowse : keytim=5
   EndIf
   ;increase attributes
   If KeyDown(13)
    If KeyDown(18) Then pHealth(gamPlayer(slot))=pHealth(gamPlayer(slot))+1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(35) Then charHappiness(gamChar(slot))=charHappiness(gamChar(slot))+1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(31) Then charStrength(gamChar(slot))=charStrength(gamChar(slot))+1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(30) Then charAgility(gamChar(slot))=charAgility(gamChar(slot))+1 : PlaySound sMenuBrowse : keytim=3 
    If KeyDown(23) Then charIntelligence(gamChar(slot))=charIntelligence(gamChar(slot))+1 : PlaySound sMenuBrowse : keytim=3
    If KeyDown(19) Then charReputation(gamChar(slot))=charReputation(gamChar(slot))+1 : PlaySound sMenuBrowse : keytim=3 
    If KeyDown(50) Then gamMoney(slot)=gamMoney(slot)+10 : PlaySound sMenuBrowse : keytim=5
    If KeyDown(207) Then charSentence(gamChar(slot))=charSentence(gamChar(slot))+1 : PlaySound sMenuBrowse : keytim=5
   EndIf
   ;switch control
   If KeyDown(56) And KeyDown(15) And gamPromo=0
    v=gamPlayer(slot)
    Repeat
     v=v+1 : satisfied=1
     If v>no_plays Then v=1
     If pHealth(v)=<0 Then satisfied=0
    Until satisfied=1 Or v=gamPlayer(slot)
    If v<>gamPlayer(slot)
     PlaySound sMenuSelect : keytim=10
     temp=pControl(v)
     pControl(v)=pControl(gamPlayer(slot))
     pControl(gamPlayer(slot))=temp
     camFoc=v
     gamPlayer(slot)=v
     gamChar(slot)=pChar(v)
    EndIf
   EndIf
  EndIf
  
  ;ppppppppppppppppppppppp PAUSE LOOP pppppppppppppppppppppppppppppppp
  ;pause toggle
  If KeyDown(25) And gotim>20 And keytim=0
   PlaySound sMenuSelect : keytim=20
   If gamPause=1 Then gamPause=0 Else gamPause=1
  EndIf
  ;pause loop
  If gamPause=0 

   ;PROMOS
   If gamPromo=0 Then promoTim=promoTim-1 
   If promoTim<0 Then promoTim=0
   If gamPromo>0 
    ;timer
    If promoStage<>1 Then promoTim=promoTim+1 
    If promoTim>50 And promoStage<>1 And keytim=0
     If KeyDown(1) Or KeyDown(28) Or ButtonPressed() Or ActionPressed(gamPlayer(slot)) Then promoTim=promoTim+100 : keytim=10
    EndIf
    ;options
    If promoStage=1 And keytim=0
	 If KeyDown(200) Or JoyYDir()=-1 Then foc=foc-1 : PlaySound sMenuSelect : keytim=6
	 If KeyDown(208) Or JoyYDir()=1 Then foc=foc+1 : PlaySound sMenuSelect : keytim=6
	 If foc<1 Then foc=1
	 If foc>2 Then foc=2 
	 If charBreakdown(gamChar(slot))>0 Then foc=2
	 ;confirm
     If KeyDown(28) Or ButtonPressed()
	  PlaySound sMenuGo : keytim=10
	  If foc=1 Then promoStage=2 : promoTim=300
	  If foc=2 Then promoStage=3 : promoTim=300
	 EndIf
	 ;cancel
     If KeyDown(1) Or charBreakdown(gamChar(slot))>0
	  PlaySound sMenuBack : keytim=10
	  promoStage=3 : promoTim=300
	 EndIf 
	 ;prepare return camera
	 If promoStage<>1
	  If promoActor(1)>0
	   If pChar(promoActor(1))=gamChar(slot) Then camFoc=promoActor(2)
      EndIf
      If promoActor(2)
       If pChar(promoActor(2))=gamChar(slot) Then camFoc=promoActor(1) 
	  EndIf
	 EndIf
	 ;animated effects
	 cyc=promoActor(1) : v=promoActor(2)
	 If cyc>0 And v>0
	  If (gamPromo=1 Or gamPromo=18 Or gamPromo=53 Or gamPromo=72) And promoStage=2 Then ChangeAnim(v,21) ;drop weapon
	  If gamPromo=7 And promoStage=2 And pSeat(v)>0 Then ChangeAnim(v,101) ;vacate seat
	  If (gamPromo=8 Or gamPromo=11) And promoStage=2 And pBed(v)>0 Then ChangeAnim(v,101) ;vacate bed
	  If (gamPromo=16 Or gamPromo=17 Or gamPromo=48) And promoStage=2 Then ChangeAnim(v,25) : ChangeAnim(cyc,26) ;hand over item 
	  If (gamPromo=49 Or gamPromo=50) And promoStage=2 Then ChangeAnim(cyc,25) : ChangeAnim(v,26) ;acquire item  
	 EndIf
	EndIf
   EndIf
   ;announcements
   If gamPromo=0 And promoTim=0 And pAnim(gamPlayer(slot))<>76 And pAnim(gamPlayer(slot))<>77
    If gotim=20 And gamWarrant(slot)>0 And promoUsed(120+gamWarrant(slot))=0 Then TriggerPromo(0,0,120+gamWarrant(slot))
    If gotim>50 And gamWarrant(slot)=0
     If gamFatality(slot)>0 And gamPromo=0 Then TriggerPromo(0,0,31) ;fatality
     If gamArrival(slot)>0 And gamPromo=0 Then TriggerPromo(0,0,30) ;arrival
     If gamRelease(slot)>0 And gamPromo=0 Then TriggerPromo(0,0,32) ;release
     If gamGrowth(slot)<-1 And gamPromo=0 Then TriggerPromo(gamPlayer(slot),0,61) ;lost weight
     If gamGrowth(slot)>1 And gamPromo=0 Then TriggerPromo(gamPlayer(slot),0,62) ;gained weight
     randy=Rnd(0,100000)
     If randy=0 And gamBlackout(slot)=0 And gamPromo=0 And promoUsed(206)=0 Then TriggerPromo(0,0,206) ;power failure!
     If randy=1 And gamBombThreat(slot)=0 And gamPromo=0 And promoUsed(207)=0 Then TriggerPromo(0,0,207) ;bomb threat!
     If randy=2 And pInjured(gamPlayer(slot))=0 And gamPromo=0 And promoUsed(252)=0 ;sudden illness
      ProduceSound(p(gamPlayer(slot)),sChoke,22050,0.5) 
      TriggerPromo(gamPlayer(slot),0,252)
     EndIf
     ;corrupt warden
     randy=Rnd(0,1000000)
     If randy=<no_chars And gamPromo=0 And promoUsed(251)=0
      If charRole(randy)=1 And charLocation(randy)>0 And charSnapped(randy)>0
       ProduceSound(FindChild(world,"Tanoy01"),sTanoy,22050,1)
       promoVariable=randy : TriggerPromo(0,0,251)
      EndIf
     EndIf
    EndIf
   EndIf
   ;mission issues
   CheckMissions()
   ;clock usage
   For count=1 To no_promos
    If promoUsed(count)=>1
     promoUsed(count)=promoUsed(count)+1
     If count=>98 And count=<100 Then promoUsed(count)=promoUsed(count)+1
    EndIf
    If promoUsed(count)>6000 Then promoUsed(count)=0
   Next

   ;PLAYERS
   PlayerCycle()
   DisplayPlayers()
   ;preserve old positions
   For cyc=1 To no_plays
    pOldX#(cyc)=pX#(cyc)
    pOldY#(cyc)=pY#(cyc)
    pOldZ#(cyc)=pZ#(cyc)
    LimitStats(pChar(cyc))
   Next 

   ;WEAPONS
   WeaponCycle()
   BulletCycle()

   ;PARTICLE EFFECTS
   If optFX>0
    ;particles
    ParticleCycle()
    ;explosions
    ExplosionCycle()
   EndIf
   ;pools
   If optGore=>2
    PoolCycle()
   EndIf
  
   ;LOCATION NOVELTIES
   ;video screens
   If gamLocation(slot)=9
	randy=Rnd(0,100)
	If randy=<1 Then wScreen=Rnd(-5,10)
	If wScreen<0 Then wScreen=0
    If wScreen<>wOldScreen
     EntityTexture FindChild(world,"TV"),tScreen(wScreen)
     wOldScreen=wScreen
    EndIf
    randy=Rnd(0,2)
    If randy=0 And wScreen=0 Then PositionTexture tScreen(0),1,Rnd(0.0,2.0)
   EndIf
   ;food trays
   If gamLocation(slot)=8
    For tray=1 To no_chairs
     If FindChild(world,"Tray"+Dig$(tray,10))>0 And trayState(tray)=>0 And trayState(tray)<>trayOldState(tray)
      EntityTexture FindChild(world,"Tray"+Dig$(tray,10)),tTray(trayState(tray))
      trayOldState(tray)=trayState(tray)
     EndIf
    Next
   EndIf 
   ;phone calls
   If gotim>0 And gamLocation(slot)=9
    If phoneRing=0
     randy=Rnd(0,10000)
     If phonePromo>0 Then randy=Rnd(1,4)
     If randy=>1 And randy=<4
      If PhoneTaken(randy)=0 And LockDown()=0
       phoneRing=randy : phoneTim=Rnd(250,1000)
       If phonePromo=0 Then GetPhonePromo()
       LoopSound sRing
       chPhone=EmitSound(sRing,FindChild(world,"Phone"+Dig$(phoneRing,10)))  
      EndIf
     EndIf
    EndIf
    If phoneRing>0 
     randy=Rnd(0,1)
     If randy=0 Then PositionEntity FindChild(world,"Phone"+Dig$(phoneRing,10)),phoneX#(phoneRing),phoneY#(phoneRing)+Rnd(-0.35,0.35),phoneZ#(phoneRing)+Rnd(-0.15,0.15)
     If randy=1
      EntityColor FindChild(world,"Alarm"+Dig$(phoneRing,10)),Rnd(0,255),0,0
     Else
      EntityColor FindChild(world,"Alarm"+Dig$(phoneRing,10)),5,0,0
     EndIf
     EntityFX FindChild(world,"Alarm"+Dig$(phoneRing,10)),9
     phoneTim=phoneTim-1
     If phonePromo=>172 And phonePromo=<173 And phoneTim<1 Then phoneTim=1
     If phoneTim<0 Or go<>0
      PositionEntity FindChild(world,"Phone"+Dig$(phoneRing,10)),phoneX#(phoneRing),phoneY#(phoneRing),phoneZ#(phoneRing)
      EntityColor FindChild(world,"Alarm"+Dig$(phoneRing,10)),5,0,0 
      EntityFX FindChild(world,"Alarm"+Dig$(phoneRing,10)),0
      StopChannel chPhone
      phoneRing=0 : phoneTim=0
      If phonePromo<>172 And phonePromo<>173 Then phonePromo=0
     EndIf
    EndIf
   EndIf
   ;showers
   If gamLocation(slot)=11
    shower#=shower#+0.2
    PositionTexture tShower,0,shower#
    CreateParticle(75+Rnd(-15,15),11,50+Rnd(-15,15),6)
    CreateParticle(120+Rnd(-15,15),11,50+Rnd(-15,15),6)
   EndIf
   ;bomb threat
   gamBombThreat(slot)=gamBombThreat(slot)-1
   If gamBombThreat(slot)<0 Then gamBombThreat(slot)=0
   ;b=gamBombThreat(slot)
   ;If b=200 Or b=190 Or b=180 Or b=170 Or b=160 Or b=150 Or b=140 Or b=130 Or b=120 Or b=110 Or b=100 Or b=90 Or b=80 Or b=70 Or b=60 Or b=50 Or b=40 Or b=30 Or b=20 Or b=10
   randy=Rnd(0,30)
   If randy=<1 And gamBombThreat(slot)=>1 And gamBombThreat(slot)=<300
    If GetBlock(gamLocation(slot))>0 Then CreateExplosion(0,0,Rnd(-295,295),15,Rnd(-340,360),10)
    If gamLocation(slot)=2 Then CreateExplosion(0,0,Rnd(-15,475),15,Rnd(-50,485),10)
    If gamLocation(slot)=4 Then CreateExplosion(0,0,Rnd(-140,140),15,Rnd(-140,140),10)
    If gamLocation(slot)=6 Then CreateExplosion(0,0,Rnd(-140,140),15,Rnd(-140,140),10)
    If gamLocation(slot)=8 Then CreateExplosion(0,0,Rnd(-260,260),15,Rnd(-350,335),10)
    If gamLocation(slot)=9 Then CreateExplosion(0,0,Rnd(-290,290),15,Rnd(-290,290),10)
    If gamLocation(slot)=10 Then CreateExplosion(0,0,Rnd(-95,95),15,Rnd(-115,115),10)
    If gamLocation(slot)=11 Then CreateExplosion(0,0,Rnd(-140,140),15,Rnd(-70,70),10)
   EndIf
   ;sound alarm
   If gotim=0 And gamWarrant(slot)>0
    LoopSound sAlarm
    chAlarm=EmitSound(sAlarm,FindChild(world,"Tanoy01"))
   EndIf

   ;ATMOSPHERE
   ManageAtmos()

   ;CAMERA
   Camera() 
   ;If KeyDown(12) Then zoom#=zoom#-0.01
   ;If KeyDown(13) Then zoom#=zoom#+0.01
   ;CameraZoom cam,zoom#

  ;ppppppppppppppppppppp END OF PAUSE LOOP pppppppppppppppppppppppp
  EndIf
	
 If gamPause=0 Then UpdateWorld

 ;OVERRIDE ANIMATION
 For cyc=1 To no_plays
  ;point body
  If BodyViable(cyc)
   If pFoc(cyc)>0 Then PointBody(cyc,pLimb(pFoc(cyc),3))
  EndIf
  ;point head
  If HeadViable(cyc)
   If pFoc(cyc)>0 Then PointHead(cyc,pLimb(pFoc(cyc),1))
   If (cyc<>promoActor(1) And cyc<>promoActor(2)) Or (cyc=promoActor(1) And promoActor(2)=0)
    If gamLocation(slot)=9 And pSeat(cyc)=>1 And pSeat(cyc)=<6 Then PointHead(cyc,FindChild(world,"TV"))
    If OnComputer(cyc) Then PointHead(cyc,FindChild(world,"Screen"))
    If NearBasket(cyc) And Isolated(cyc,30) Then PointHead(cyc,FindChild(world,"Rim"))
    If gamPromo>0 And camFoc=0 Then PointHead(cyc,FindChild(world,"Tanoy01"))
   EndIf
  EndIf
  ;move correction 
  pCollisions(cyc)=CountCollisions(pMovePivot(cyc))
  If pCollisions(cyc)>0
   If pGrappler(cyc)>0 Or (pGrappling(cyc)>0 And pAnim(cyc)=>213)
    If EntityX(pLimb(cyc,30),1)>pOldMoveX#(cyc) Then shiftX#=-1 Else shiftX#=1
    If EntityZ(pLimb(cyc,30),1)>pOldMoveZ#(cyc) Then shiftZ#=-1 Else shiftZ#=1
    PositionEntity pPivot(cyc),EntityX(pPivot(cyc))+shiftX#,EntityY(pPivot(cyc)),EntityZ(pPivot(cyc))+shiftZ#
    If pGrappling(cyc)>0
     v=pGrappling(cyc)
     PositionEntity pPivot(v),EntityX(pPivot(v))+shiftX#,EntityY(pPivot(v)),EntityZ(pPivot(v))+shiftZ#
    EndIf
    If pGrappler(cyc)>0
     v=pGrappler(cyc)
     PositionEntity pPivot(v),EntityX(pPivot(v))+shiftX#,EntityY(pPivot(v)),EntityZ(pPivot(v))+shiftZ#
    EndIf
   EndIf
  EndIf
 Next

 Next 
 RenderWorld 1
 
 ;DISPLAY
 ;DrawImage gLogo(3),rX#(180),rY#(560)
 ;status
 show=1
 If gamPromo>0 Then show=0
 If pAnim(gamPlayer(slot))=>76 And pAnim(gamPlayer(slot))=<77 And pAnimTim(gamPlayer(slot))>150 Then show=0
 If show=1
  DisplayStatus(gamChar(slot),72,42)
  DisplayTime(727,30)
  If OnComputer(gamPlayer(slot)) And pAnim(gamPlayer(slot))=102 And pState(gamPlayer(slot))=109 And pAnimTim(gamPlayer(slot))>10
   DisplayFile(gamFile,100,530)
  EndIf
 EndIf
 ;diagnostics
 ;If KeyDown(56)
  ;SetFont fontNumber
  ;Outline("Zoom: "+zoom#,100,150,0,0,0,255,255,255)
  ;Outline("X: "+pX#(camFoc),100,150,0,0,0,255,255,255)
  ;Outline("Y: "+pY#(camFoc),100,165,0,0,0,255,255,255)
  ;Outline("Z: "+pZ#(camFoc),100,180,0,0,0,255,255,255) 
  ;Outline("A: "+pA#(camFoc),100,195,0,0,0,255,255,255)
  ;weaps=0 : vices=0
  ;For cyc=1 To no_weaps
   ;If weapState(cyc)>0 And weapLocation(cyc)>0
    ;weaps=weaps+1
    ;If weapType(cyc)=>16 And weapType(cyc)=<18 Then vices=vices+1
   ;EndIf
  ;Next
  ;Outline("Weapons: "+weaps+"/"+no_weaps,100,250,0,0,0,255,255,255) 
  ;Outline("Vices: "+vices+"/"+no_weaps,100,265,0,0,0,255,255,255) 
 ;EndIf
 ;display promo
 For cyc=1 To no_plays
  ResetExpressions(cyc)
 Next 
 If gamPromo>0
  DisplayPromo()
 EndIf
 ;paused state
 If gamPause=1
  DrawOption(-1,rX#(400),rY#(300),"PAUSED","")
  SetFont font(1)
  Outline("(Press 'P' to resume play)",rX#(400),rY#(300)+30,0,0,0,255,255,255)
 EndIf
 ;mask shaky start
 If gotim=<0 Then Loader("Please Wait","Finalizing World")

 Flip
 ;screenshot (F12)
 If KeyHit(88) Then Screenshot()
 ;save preview shot
 If go=-1
  gamPhoto(slot)=CreateImage(GraphicsWidth(),GraphicsHeight())
  GrabImage gamPhoto(slot),GraphicsWidth()/2,GraphicsHeight()/2
 EndIf

Wend
;restore sound
If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Restoring Sound")
If ChannelPlaying(chAtmos)>0 Then StopChannel chAtmos
If ChannelPlaying(chAlarm)>0 Then StopChannel chAlarm
If ChannelPlaying(chPhone)>0 Then StopChannel chPhone
FreeEntity camListener
;restore music
If go=-1 Or go=3 Or charHealth(gamChar(slot))=<0
 If ChannelPlaying(chTheme)=0
  LoopSound sTheme
  chTheme=PlaySound(sTheme)
 EndIf
 musicVol#=1.0
 ChannelVolume chTheme,musicVol#
EndIf
;remove world
If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Leaving "+textLocation$(oldLocation))
FreeTimer timer
FreeEntity fader
FreeEntity world
;remove camera
If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Removing Camera")
FreeEntity cam
FreeEntity camPivot
FreeEntity dummy
;remove lights
For cyc=1 To no_lights
 If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Removing Lights") 
 FreeEntity light(cyc)
Next
;remove players
For cyc=1 To no_plays
 If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Removing Players") 
 FreeEntity p(cyc)
 FreeEntity pPivot(cyc)
 FreeEntity pMovePivot(cyc) 
 For limb=1 To 40
  If pShadow(cyc,limb)>0
   FreeEntity pShadow(cyc,limb)
  EndIf
 Next
Next
;remove weapons
If no_weaps>0
 For cyc=1 To no_weaps
  If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Removing Weapons") 
  If weapLocation(cyc)=gamLocation(slot)
   If weapState(cyc)=0 Then weapLocation(cyc)=0
   FreeEntity weap(cyc)
   FreeEntity weapGround(cyc)
   FreeEntity weapWall(cyc) 
  EndIf
 Next
EndIf
;remove kits
If gamLocation(slot)=10
 For cyc=1 To 6
  FreeEntity kit(cyc)
 Next
EndIf
;remove bullets
For cyc=1 To no_bullets
 FreeEntity bullet(cyc) 
Next
;remove particles
If optFX>0
 If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Removing Effects")
 For cyc=1 To no_particles
  FreeEntity part(cyc)
 Next
EndIf
;remove pools
If optGore=>2
 If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Removing Effects")  
 For cyc=1 To no_pools
  FreeEntity pool(cyc)  
 Next
EndIf
;clear collisions
ClearCollisions
;remove unusued promos
If charHealth(gamChar(slot))>0 Then Loader("Please Wait","Saving Progress") 
If go=>1 Then RevisePromos()
;preserve locations
If LockDown()
 If GetBlock(gamLocation(slot))=charBlock(gamChar(slot)) Then gamEscape(slot)=1 ;left block
 If gamLocation(slot)=9 And GetBlock(charLocation(gamChar(slot)))=0 Then gamEscape(slot)=1 ;avoided block
EndIf
gamLocation(slot)=charLocation(gamChar(slot))
For cyc=1 To no_plays
 If pChar(cyc)<>gamChar(slot) Or go=-1
  If pSeat(cyc)>0
   pX#(cyc)=pLeaveX#(cyc) : pZ#(cyc)=pLeaveZ#(cyc)
   pY#(cyc)=pLeaveY#(cyc) : pA#(cyc)=pLeaveA#(cyc) 
  EndIf
  charX#(pChar(cyc))=pX#(cyc) : charZ#(pChar(cyc))=pZ#(cyc)
  charY#(pChar(cyc))=pY#(cyc) : charA#(pChar(cyc))=pA#(cyc)
 EndIf
 If pHealth(cyc)=<0 And charLocation(pChar(cyc))>0 And pChar(cyc)<>gamChar(slot)
  If charAttacker(pChar(cyc))=gamChar(slot) And charWitness(gamChar(slot))>0 And charHealth(charWitness(gamChar(slot)))>0
   If gamWarrant(slot)<12 Then gamWarrant(slot)=Rnd(12,13) : gamVictim(slot)=pChar(cyc)
  EndIf
  RuinMission(pChar(cyc))
  charLocation(pChar(cyc))=0
  gamFatality(slot)=pChar(cyc)
  For v=1 To no_chars
   If v<>gamChar(slot) And charPromo(v,gamChar(slot))=0 And charRelation(v,gamChar(slot))=>0 And charAngerTim(v,gamChar(slot))=0
    If charRelation(gamChar(slot),pChar(cyc))>0 And charRelation(v,pChar(cyc))>0 Then charPromo(v,gamChar(slot))=217 : charPromoRef(v)=pChar(cyc)
    If charRelation(gamChar(slot),pChar(cyc))<0 And charRelation(v,pChar(cyc))<0 Then charPromo(v,gamChar(slot))=218 : charPromoRef(v)=pChar(cyc) 
   EndIf
  Next
 EndIf
 charHealth(pChar(cyc))=pHealth(cyc)
 charHP(pChar(cyc))=pHP(cyc) 
 charInjured(pChar(cyc))=pInjured(cyc)
 charWeapon(pChar(cyc))=pWeapon(cyc)
 If pWeapon(cyc)>0 Then weapLocation(pWeapon(cyc))=charLocation(pChar(cyc))
 For limb=1 To 40
  charScar(pChar(cyc),limb)=pScar(cyc,limb)
 Next
Next
;body growth
If gamGrowth(slot)=-1 And charModel(gamChar(slot))>1 Then charModel(gamChar(slot))=charModel(gamChar(slot))-1 : gamGrowth(slot)=-2
If gamGrowth(slot)=1 And charModel(gamChar(slot))<5 Then charModel(gamChar(slot))=charModel(gamChar(slot))+1 : gamGrowth(slot)=2
;relocate CPU's
If go=>1
 RelocateChars()
EndIf
;prepare camera
If go=>1
 If GetBlock(gamLocation(slot))>0 Then camX#=-100 : camY#=50 : camZ#=-200
 If gamLocation(slot)=2 Then camX#=0 : camY#=25 : camZ#=250
 If gamLocation(slot)=4 Or gamLocation(slot)=6 Then camX#=-80 : camY#=25 : camZ#=-80
 If gamLocation(slot)=8 Then camX#=-80 : camY#=25 : camZ#=-260
 If gamLocation(slot)=9 Then camX#=0 : camY#=75 : camZ#=0 
 If gamLocation(slot)=10 Then camX#=-60 : camY#=40 : camZ#=-50 
 If gamLocation(slot)=11 Then camX#=0 : camY#=25 : camZ#=0
 camPivX#=camX# : camPivY#=camY# : camPivZ#=camZ#
EndIf
;regenerate weapons
If go=>1
 For cyc=1 To no_weaps
  randy=Rnd(0,50)
  If weapLocation(cyc)=0 Or weapState(cyc)=0 Then randy=Rnd(0,25)
  If randy=0 And cyc<>gamItem(slot) And cyc<>gamTarget(slot) And FindCarrier(cyc)=0 And InsideCell(weapX#(cyc),weapY#(cyc),weapZ#(cyc))=0
   randy=Rnd(0,1)
   If randy=0 And weapType(cyc)=>16 And weapType(cyc)=<18 And weapState(cyc)=0
    GenerateWeapon(cyc,weapType(cyc),0,0,0,0)
   Else 
    GenerateWeapon(cyc,0,0,0,0,0)
   EndIf
  EndIf
  If randy=<10 And weapAmmo(cyc)=0 And FindCarrier(cyc)<>gamChar(slot) Then weapAmmo(cyc)=Rnd(10,100)
 Next
EndIf
;save & exit
If go=-1 
 gamName$(slot)=CellName$(gamChar(slot))+": "+charName$(gamChar(slot))
 ResizeImage gamPhoto(slot),150,100
 SaveImage(gamPhoto(slot),"Data/Slot0"+slot+"/Photos/Game.bmp")
 MaskImage gamPhoto(slot),255,0,255
 screen=5
EndIf
SaveProgress()
SaveChars()
SavePhotos()
SaveItems()
;proceed
If go=1 Then screen=50
If go=2 Then screen=52
If go=3 Then gamEnded=1 : screen=53
If charHealth(gamChar(slot))=0 Then gamEnded=1 : gamName$(slot)="" : screen=6
End Function

;-----------------------------------------------------------------
;////////////////////// RELATED FUNCTIONS ////////////////////////
;-----------------------------------------------------------------

;GET STAT COLOUR
Function GetStatColour(stat)
 Color 200,200,200
 If statTim(stat)>0 Then Color 100,220,100
 If statTim(stat)<0 Then Color 220,100,100
End Function

;DISPLAY STATUS
Function DisplayStatus(char,x,y)
 ;check first
 LimitStats(char)
 ;money
 DrawImage gMoney,rX#(x),rY#(y)
 SetFont fontMoney
 r=230 : g=250 : b=130
 If statTim(7)<0 Or gamMoney(slot)<0 Then r=220 : g=100 : b=100
 If statTim(7)>0 Then r=255 : g=230 : b=100 
 Outline("$"+GetFigure$(gamMoney(slot)),rX#(x),rY#(y),0,0,0,r,g,b)
 ;health meter
 Color 0,0,0 : Rect rX#(x)+75,rY#(y)-10,200,10,1
 Color 150,80,75 : Rect rX#(x)+74,rY#(y)-11,200,10,1
 Color 130,0,0 : Rect rX#(x)+74,rY#(y)-11,200,10,0
 If charHealth(char)>0
  If charInjured(char)>0 Then Color Rnd(130,220),0,0 Else Color 90,200,50
  Rect rX#(x)+74,rY#(y)-11,charHealth(char)*2,10,1
  If charInjured(char)>0 Then Color 80,0,0 Else Color 0,130,0
  Rect rX#(x)+74,rY#(y)-11,charHealth(char)*2,10,0
 EndIf
 DrawImage gHealth,rX#(x)+68,rY#(y)-6
 ;happiness meter
 Color 0,0,0 : Rect rX#(x)+75,rY#(y)+4,200,10,1
 Color 150,80,75 : Rect rX#(x)+74,rY#(y)+3,200,10,1
 Color 130,0,0 : Rect rX#(x)+74,rY#(y)+3,200,10,0
 If charHappiness(char)>0
  If charBreakdown(char)>0 Then Color Rnd(130,220),0,0 Else Color 220,210,35
  Rect rX#(x)+74,rY#(y)+3,charHappiness(char)*2,10,1
  If charBreakdown(char)>0 Then Color 80,0,0 Else Color 130,120,0
  Rect rX#(x)+74,rY#(y)+3,charHappiness(char)*2,10,0
 EndIf
 DrawImage gHappiness,rX#(x)+68,rY#(y)+8 
 ;attribute headers
 SetFont font(1)
 Outline("Strength:",rX#(x)+112,rY#(y)-22,0,0,0,255,255,255)
 Outline("Agility:",rX#(x)+201,rY#(y)-22,0,0,0,255,255,255)
 Outline("Intelligence:",rX#(x)+104,rY#(y)+24,0,0,0,255,255,255)
 Outline("Reputation:",rX#(x)+211,rY#(y)+24,0,0,0,255,255,255)
 ;attribute numbers
 SetFont fontNumber
 GetStatColour(1)
 Outline(charStrength(char)+"%",rX#(x)+158,rY#(y)-22,0,0,0,ColorRed(),ColorGreen(),ColorBlue())
 GetStatColour(2)
 Outline(charAgility(char)+"%",rX#(x)+241,rY#(y)-22,0,0,0,ColorRed(),ColorGreen(),ColorBlue())
 GetStatColour(3)
 Outline(charIntelligence(char)+"%",rX#(x)+156,rY#(y)+24,0,0,0,ColorRed(),ColorGreen(),ColorBlue())
 GetStatColour(4)
 Outline(charReputation(char)+"%",rX#(x)+265,rY#(y)+24,0,0,0,ColorRed(),ColorGreen(),ColorBlue())
End Function

;DISPLAY TIME
Function DisplayTime(x,y)
 ;time
 SetFont fontClock
 r=200 : g=50 : b=50
 If statTim(5)>0 Then r=255 : g=0 : b=0
 Outline(Dig$(gamHours(slot),10)+":"+Dig$(gamMins(slot),10),rX#(x),rY#(y),0,0,0,r,g,b)
 ;sentence (days)
 offset#=14
 If charSentence(gamChar(slot))=>100 Then offset#=17
 If charSentence(gamChar(slot))=>1000 Then offset#=20
 SetFont font(1)
 Outline("Days",rX#(x)+offset#,rY#(y)+25,0,0,0,255,255,255)
 SetFont fontNumber
 GetStatColour(6)
 Outline(GetFigure$(charSentence(gamChar(slot))),rX#(x)-(offset#-1),rY#(y)+25,0,0,0,ColorRed(),ColorGreen(),ColorBlue())
 ;breakdown
 ;SetFont font(1)
 ;r=255 : g=255 : b=255
 ;If statTim(6)>0 Then r=100 : g=220 : b=100
 ;If statTim(6)<0 Then r=220 : g=100 : b=100
 ;Outline(GetSentence$(charSentence(gamChar(slot))),rX#(x),rY#(y)+25,0,0,0,r,g,b)
End Function

;DISPLAY FILE
Function DisplayFile(char,x,y) ;100,530
 ;photo
 If charSnapped(char)>0 And charPhoto(char)>0
  DrawImage charPhoto(char),rX#(x),rY#(y)
  Color 0,0,0 : Rect rX#(x)-75,rY#(y)-50,150,100,0
 Else
  DrawImage gPhoto,rX#(x),rY#(y)
 EndIf
 DrawLine(rX#(x)+15,rY#(y)-60,rX#(x)+245,rY#(y)-60,0,255,130)
 ;file ID
 SetFont fontComputer
 OutlineStraight("FILE:",rX#(x)-75,rY#(y)-61,0,0,0,0,255,130)
 Outline(char+"/"+no_chars,rX#(x)-15,rY#(y)-61,0,0,0,160,255,200)
 OutlineStraight("NAME:",rX#(x)+80,rY#(y)-39,0,0,0,0,255,130)
 OutlineStraight(Upper$(charName$(char)),rX#(x)+129,rY#(y)-39,0,0,0,160,255,200)
 OutlineStraight("AREA:",rX#(x)+80,rY#(y)-22,0,0,0,0,255,130)
 namer$=Upper$(textLocation(charLocation(char)))
 If charRole(char)=0 Then namer$="CELL "+charCell(char)+", "+Upper$(textBlock$(charBlock(char)))+" BLOCK"
 If charLocation(char)=0 And charHealth(char)=<0 Then namer$="DECEASED"
 If charLocation(char)=0 And charHealth(char)>0 Then namer$="RELEASED"
 If charRole(char)=2 Then namer$="COURTROOM"
 OutlineStraight(namer$,rX#(x)+127,rY#(y)-22,0,0,0,160,255,200)
 ;health data
 If gamLocation(slot)=6
  OutlineStraight("HEALTH:",rX#(x)+80,rY#(y)+2,0,0,0,0,255,130)
  affix$=""
  If charInjured(char)>0 Then affix$=" (INJURED)" 
  If charRole(char)=<1 And charLocation(char)=0 And charHealth(char)=0 Then affix$=" (DEAD)" 
  OutlineStraight(charHealth(char)+"%"+affix$,rX#(x)+143,rY#(y)+2,0,0,0,160,255,200)
  OutlineStraight("STRENGTH:",rX#(x)+80,rY#(y)+19,0,0,0,0,255,130)
  OutlineStraight(charStrength(char)+"%",rX#(x)+163,rY#(y)+19,0,0,0,160,255,200)
  OutlineStraight("AGILITY:",rX#(x)+80,rY#(y)+36,0,0,0,0,255,130)
  OutlineStraight(charAgility(char)+"%",rX#(x)+141,rY#(y)+36,0,0,0,160,255,200)
 EndIf
 ;mental data
 If gamLocation(slot)=4
  OutlineStraight("HAPPINESS:",rX#(x)+80,rY#(y)+2,0,0,0,0,255,130)
  If charBreakdown(char)>0 Then affix$=" (MANIC)" Else affix$=""
  OutlineStraight(charHappiness(char)+"%"+affix$,rX#(x)+171,rY#(y)+2,0,0,0,160,255,200)
  OutlineStraight("INTELLIGENCE:",rX#(x)+80,rY#(y)+19,0,0,0,0,255,130)
  OutlineStraight(charIntelligence(char)+"%",rX#(x)+185,rY#(y)+19,0,0,0,160,255,200)
  OutlineStraight("REPUTATION:",rX#(x)+80,rY#(y)+36,0,0,0,0,255,130)
  OutlineStraight(charReputation(char)+"%",rX#(x)+177,rY#(y)+36,0,0,0,160,255,200)
 EndIf
 ;crime data
 If gamLocation(slot)=9
  OutlineStraight("SENTENCE:",rX#(x)+80,rY#(y)+2,0,0,0,0,255,130)
  namer$=Upper$(GetSentence$(charSentence(char)))
  If charSentence(char)=0 Then namer$="NONE"
  OutlineStraight(namer$,rX#(x)+161,rY#(y)+2,0,0,0,160,255,200)
  OutlineStraight("CRIME:",rX#(x)+80,rY#(y)+19,0,0,0,0,255,130)
  OutlineStraight(Upper$(textCrime$(charCrime(char))),rX#(x)+133,rY#(y)+19,0,0,0,160,255,200)
  OutlineStraight("GANG:",rX#(x)+80,rY#(y)+36,0,0,0,0,255,130)
  OutlineStraight(Upper$(textGang$(charGang(char))),rX#(x)+129,rY#(y)+36,0,0,0,160,255,200)
 EndIf
End Function

;DESCRIBE SENTENCE
Function GetSentence$(sentence)
 thread$=""
 ;calculate years
 ;years=sentence/360
 ;If years<0 Then years=0
 ;If years>0
  ;sentence=sentence-(360*years)
  ;If years<>1 Then plural$="s" Else plural$=""
  ;If sentence>0 Then more$=", " Else more$="" 
  ;thread$=thread$+""+years+" Year"+plural$+""+more$
 ;EndIf
 ;calculate months
 months=sentence/30
 If months<0 Then months=0
 If months>0
  sentence=sentence-(30*months)
  If months<>1 Then plural$="s" Else plural$=""
  If sentence>0 Then more$=", " Else more$="" 
  thread$=thread$+""+months+" Month"+plural$+""+more$
 EndIf
 ;calculate weeks
 weeks=sentence/7
 If weeks<0 Then weeks=0
 If weeks>0
  sentence=sentence-(7*weeks)
  If weeks<>1 Then plural$="s" Else plural$=""
  If sentence>0 Then more$=", " Else more$="" 
  thread$=thread$+""+weeks+" Week"+plural$+""+more$
 EndIf
 ;calculate days
 days=sentence
 If days<0 Then days=0
 If days>0
  If days<>1 Then plural$="s" Else plural$=""
  thread$=thread$+""+days+" Day"+plural$
 EndIf
 Return thread$
End Function